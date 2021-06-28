package pt.unl.fct.di.apdc.vie.resources;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Transaction;
import com.google.cloud.datastore.Value;
import com.google.gson.Gson;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.ListValue;
import com.google.cloud.datastore.StringValue;

import pt.unl.fct.di.apdc.vie.util.FollowData;
import pt.unl.fct.di.apdc.vie.util.OrgInfoData;
import pt.unl.fct.di.apdc.vie.util.UserInfoData;

@Path("/profile")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class ProfileResource {
	
	private static final Logger LOG = Logger.getLogger(ProfileResource.class.getName());
	private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
	private final Gson g = new Gson();
	
	@GET
	@Path("/{username}")
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getRole(@PathParam("username") String username) {
		
		Transaction txn = datastore.newTransaction();
		
		try {
				
			Key userKey = datastore.newKeyFactory().setKind("User").newKey(username);
			Key orgKey = datastore.newKeyFactory().setKind("Organisation").newKey(username);
			
			Entity userAccount = txn.get(userKey);
			Entity orgAccount = txn.get(orgKey);
			
			//a conta nao existe nem nos users nem nas orgs
			if(userAccount == null && orgAccount == null){
				txn.rollback();
				return Response.status(Status.NOT_FOUND).entity("Account doesn't exist.").build();
			}
			if(userAccount == null) {
				//uma org
				
				OrgInfoData oi = new OrgInfoData(
						orgAccount.getString("org_name"),
						orgAccount.getString("org_address"),
						orgAccount.getString("org_service"),
						orgAccount.getString("org_email"));	
				txn.commit();
				return Response.ok(g.toJson(oi)).build();
			}
			
			//um user
			UserInfoData ui = new UserInfoData(
					userAccount.getString("user_firstName"),
					userAccount.getString("user_lastName"),
					userAccount.getString("user_role"),
					userAccount.getString("user_name"));
			txn.commit();
			return Response.ok(g.toJson(ui)).build();

		} finally {
			if(txn.isActive()) {
				txn.rollback();
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}
	}

	@POST
	@Path("/follow/{username}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response follow(@PathParam("username") String username, FollowData data)  {
		//Transaction txn = datastore.newTransaction();

		Transaction txn = datastore.newTransaction();
		try {
			
			Key tokenKey = datastore.newKeyFactory().setKind("Token").newKey(data.getTokenID());
			Entity token = txn.get(tokenKey);

			if(token == null) {
				txn.rollback();
				return Response.status(Status.FORBIDDEN).entity("You are not logged in.").build();
			}
			Key followingKey;
			
			String followingUsername = token.getString("token_username");

			String roleFollowing = "user";
			if(followingUsername.contains("@"))
				roleFollowing = "org";

			if(roleFollowing.equals("user")) 
				followingKey = datastore.newKeyFactory().setKind("User").newKey(followingUsername);
			else
				followingKey = datastore.newKeyFactory().setKind("Organisation").newKey(followingUsername);
			
			Entity userFollowing = txn.get(followingKey);	
			
			long nFollowing = userFollowing.getLong(roleFollowing.concat("_following"));
			List<Value<String>> following = new LinkedList<Value<String>>();
			List<Value<String>> following1 = userFollowing.getList(roleFollowing.concat("_following_list"));
			for(int i = 0;i<following1.size();i++) {
				following.add(following1.get(i));
			}
			
			
			String roleFollowed = "user";
			if(username.contains("@")) 
				roleFollowed = "org";
			
			Key followedKey;
			if(roleFollowed.equals("user")) 
				followedKey = datastore.newKeyFactory().setKind("User").newKey(username);
			else
				followedKey = datastore.newKeyFactory().setKind("Organisation").newKey(username);

			Entity userFollowed = txn.get(followedKey);
			if(userFollowed == null) {
				txn.rollback();
				return Response.status(Status.FORBIDDEN).entity("The user you are trying to follow doesn't exists.").build();
			}

			String followedPerfil = userFollowed.getString(roleFollowed.concat("_perfil"));
			
			if(followedPerfil.equals("Public")) {
				long nFollowers = userFollowed.getLong(roleFollowed.concat( "_followers"));
				List<Value<String>> followers = new LinkedList<Value<String>>();
				List<Value<String>> followers1 = userFollowed.getList(roleFollowed.concat("_followers_list"));
				for(int i = 0;i<followers1.size();i++) {
					followers.add(followers1.get(i));
				}
				followers.add(StringValue.of(followingUsername));
				nFollowers++;
				
				userFollowed = Entity.newBuilder(userFollowed)
						.set(roleFollowed.concat("_followers"), nFollowers)
						.set(roleFollowed.concat("_followers_list"), ListValue.of(followers))
						.build();
				
				txn.update(userFollowed);
				
				//gajo que segue:
				following.add(StringValue.of(username));
				nFollowing++;
				
				userFollowing = Entity.newBuilder(userFollowing)
						.set(roleFollowing.concat("_following"), nFollowing)
						.set(roleFollowing.concat("_following_list"), ListValue.of(following))
						.build();
				txn.update(userFollowing);
			}
			else {		//Private
				List<Value<String>> requests = new LinkedList<Value<String>>();
				List<Value<String>> requests1 = userFollowed.getList(roleFollowed.concat("_requests_list"));
				for(int i = 0;i<requests1.size();i++) {
					requests.add(requests1.get(i));
				}
				requests.add(StringValue.of(username));
				userFollowed = Entity.newBuilder(userFollowed).set(roleFollowed.concat("_requests_list"), requests).build();
				txn.update(userFollowed);
			}

			txn.commit();
			return Response.ok("Number of followers updated.").build();

		} finally {
			if(txn.isActive()) {
				txn.rollback();
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}
	}
}
