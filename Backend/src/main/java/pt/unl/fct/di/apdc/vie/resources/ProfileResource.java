package pt.unl.fct.di.apdc.vie.resources;

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
		String role = "user";
		if(username.contains("@")) 
			role = "org";
		Key followedKey;
		if(role.equals("user")) 
			followedKey = datastore.newKeyFactory().setKind("User").newKey(username);
		else
			followedKey = datastore.newKeyFactory().setKind("Organisation").newKey(username);

		Entity userFollowed = datastore.get(followedKey);
		if(userFollowed == null) {

			return Response.status(Status.FORBIDDEN).entity("The user you are trying to follow doesn't exists.").build();
		}

		int nFollowers = (int) userFollowed.getLong(role.concat( "_followers"));
		//List<Value<String>> followers = userFollowed.getList(role.concat("_followers_list"));
		List<Value<String>> followers = userFollowed.getList(role.concat("_followers_list"));
		
		
		nFollowers++;

		//userFollowed = Entity.newBuilder(userFollowed).set(role.concat("_followers"), String.valueOf(nFollowers)).build();


		Key tokenKey = datastore.newKeyFactory().setKind("Token").newKey(data.getTokenID());
		Entity token = datastore.get(tokenKey);

		if(token == null) {

			return Response.status(Status.FORBIDDEN).entity("You are not logged in.").build();
		}
		Key followingKey;
		String role2;
		if(token.getString("token_role").equals("USER")) {
			followingKey = datastore.newKeyFactory().setKind("User").newKey(token.getString("token_username"));	
			role2 = "user";
		}
		else {
			followingKey = datastore.newKeyFactory().setKind("Organisation").newKey(token.getString("token_username"));
			role2 = "org";
		}
		Entity userFollowing = datastore.get(followingKey);
		int nFollowing = (int) userFollowing.getLong(role2.concat("_following"));
		//List<Value<String>> following =  userFollowing.getList(role2.concat("_following_list"));
		List<Value<String>> following = userFollowing.getList(role2.concat("_following_list"));

		nFollowing++;

		//userFollowing = Entity.newBuilder(userFollowing).set(role2.concat("_following"), String.valueOf(nFollowing)).build();
		
		
		followers.add( (Value<String>) StringValue.of( token.getString("token_username")));
		following.add( (Value<String>) StringValue.of( username));
		
		userFollowed = Entity.newBuilder(userFollowed).set(role.concat("_followers_list"), followers).set(role.concat("_followers"), String.valueOf(nFollowers)).build();

		//userFollowed = Entity.newBuilder(userFollowed).set(role.concat("_followers"), String.valueOf(nFollowers)).build();
		
		userFollowing = Entity.newBuilder(userFollowing).set(role2.concat("_following_list"), following).set(role2.concat("_following"), String.valueOf(nFollowing)).build();
		//userFollowing = Entity.newBuilder(userFollowing).set(role2.concat("_following"), String.valueOf(nFollowing)).build();
			

			//txn.update(userFollowed);
			//txn.update(userFollowing);
			txn.update(userFollowed);
			txn.update(userFollowing);
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
