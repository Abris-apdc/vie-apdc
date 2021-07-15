package pt.unl.fct.di.apdc.vie.resources;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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

import pt.unl.fct.di.apdc.vie.util.AccountData;
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
				
			Key accountKey = datastore.newKeyFactory().setKind("Account").newKey(username);
			//Key orgKey = datastore.newKeyFactory().setKind("Organisation").newKey(username);
			
			Entity account = txn.get(accountKey);
			//Entity orgAccount = txn.get(orgKey);
			
			//conta nao existe
			if(account == null){
				txn.rollback();
				return Response.status(Status.NOT_FOUND).entity("Account doesn't exist.").build();
			}
			if(account.getString("account_perfil").equals("Private")) {
				txn.rollback();
				return Response.status(Status.FORBIDDEN).entity("Account is Private.").build();
			}
			String role = account.getString("account_role");
			if(role.equals("ORG")) {
				
				//uma org
				
				OrgInfoData oi = new OrgInfoData(
						account.getString("account_role"),
						account.getString("account_name"),
						account.getString("account_address"),
						account.getString("account_service"),
						account.getString("account_email"));	
				txn.commit();
				return Response.ok(g.toJson(oi)).build();
			}
			
			//um user
			UserInfoData ui = new UserInfoData(
					account.getString("account_role"),
					account.getString("account_name"),
					account.getString("account_firstName"),
					account.getString("account_lastName"),
					account.getString("account_birthDay"));
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
			long end = token.getLong("token_end_time");
			
			//o token expirou
			if(end <  System.currentTimeMillis()) {
				txn.delete(tokenKey);
				txn.commit();
				return Response.status(Status.FORBIDDEN).entity("Token expired.").build();
			}
			
			Key followingKey;
			
			String followingUsername = token.getString("token_username");

			//String roleFollowing = "user";
			//if(followingUsername.contains("@"))
				//roleFollowing = "org";

			//if(roleFollowing.equals("user")) 
				followingKey = datastore.newKeyFactory().setKind("Account").newKey(followingUsername);
			//else
				//followingKey = datastore.newKeyFactory().setKind("Organisation").newKey(followingUsername);
			
			Entity userFollowing = txn.get(followingKey);	
			
			long nFollowing = userFollowing.getLong("account_following");
			List<Value<String>> following = new LinkedList<Value<String>>();
			List<Value<String>> following1 = userFollowing.getList("account_following_list");
			for(int i = 0;i<following1.size();i++) {
				following.add(following1.get(i));
			}
			
			
			//String roleFollowed = "user";
			//if(username.contains("@")) 
				//roleFollowed = "org";
			
			Key followedKey;
			//if(roleFollowed.equals("user")) 
				followedKey = datastore.newKeyFactory().setKind("Account").newKey(username);
			//else
				//followedKey = datastore.newKeyFactory().setKind("Organisation").newKey(username);

			Entity userFollowed = txn.get(followedKey);
			if(userFollowed == null) {
				txn.rollback();
				return Response.status(Status.FORBIDDEN).entity("The user you are trying to follow doesn't exists.").build();
			}

			String followedPerfil = userFollowed.getString("account_perfil");
			
			if(followedPerfil.equals("Public")) {
				long nFollowers = userFollowed.getLong("account_followers");
				List<Value<String>> followers = new LinkedList<Value<String>>();
				List<Value<String>> followers1 = userFollowed.getList("account_followers_list");
				for(int i = 0;i<followers1.size();i++) {
					followers.add(followers1.get(i));
				}
				followers.add(StringValue.of(followingUsername));
				nFollowers++;
				
				userFollowed = Entity.newBuilder(userFollowed)
						.set("account_followers", nFollowers)
						.set("account_followers_list", ListValue.of(followers))
						.build();
				
				txn.update(userFollowed);
				
				//gajo que segue:
				following.add(StringValue.of(username));
				nFollowing++;
				
				userFollowing = Entity.newBuilder(userFollowing)
						.set("account_following", nFollowing)
						.set("account_following_list", ListValue.of(following))
						.build();
				txn.update(userFollowing);
			}
			else {		//Private
				List<Value<String>> requests = new LinkedList<Value<String>>();
				List<Value<String>> requests1 = userFollowed.getList("account_requests_list");
				for(int i = 0;i<requests1.size();i++) {
					requests.add(requests1.get(i));
				}
				requests.add(StringValue.of(username));
				userFollowed = Entity.newBuilder(userFollowed).set("account_requests_list", requests).build();
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
	
	@DELETE
	@Path("/unfollow/{username}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response unfollow(@PathParam("username") String username, FollowData data)  {
		Transaction txn = datastore.newTransaction();
		try {
			
			Key tokenKey = datastore.newKeyFactory().setKind("Token").newKey(data.getTokenID());
			Entity token = txn.get(tokenKey);
			
			if(token == null) {
				txn.rollback();
				return Response.status(Status.FORBIDDEN).entity("You are not logged in.").build();
			}
			long end = token.getLong("token_end_time");
			
			//o token expirou
			if(end <  System.currentTimeMillis()) {
				txn.delete(tokenKey);
				txn.commit();
				return Response.status(Status.FORBIDDEN).entity("Token expired.").build();
			}
			
			String unfollowingUsername = token.getString("token_username");
			
			Key unfollowingKey = datastore.newKeyFactory().setKind("Account").newKey(unfollowingUsername);
			
			Entity userUnfollowing = txn.get(unfollowingKey);	
			
			long nFollowing = userUnfollowing.getLong("account_following");
			
			boolean changedFollowing = false;
			
			List<Value<String>> following = new LinkedList<Value<String>>();
			List<Value<String>> following1 = userUnfollowing.getList("account_following_list");
			for(int i = 0;i<following1.size();i++) {
				if(following1.get(i).get().equals(username))
					changedFollowing = true;
				else
					following.add(following1.get(i));
			}
			//following ja n tem o gajo a parar de seguir
			
			//decrease nr following counter
			if(changedFollowing)
				nFollowing--;
			
			userUnfollowing = Entity.newBuilder(userUnfollowing)
					.set("account_following", nFollowing)
					.set("account_following_list", ListValue.of(following))
					.build();
			txn.update(userUnfollowing);
			
			
			Key unfollowedKey= datastore.newKeyFactory().setKind("Account").newKey(username);
			
			Entity userUnfollowed = txn.get(unfollowedKey);
			if(userUnfollowed == null) {
				txn.rollback();
				return Response.status(Status.FORBIDDEN).entity("The user you are trying to follow doesn't exists.").build();
			}
			
			long nFollowers = userUnfollowed.getLong("account_followers");
			
			boolean changedFollowers = false;
			List<Value<String>> followers = new LinkedList<Value<String>>();
			List<Value<String>> followers1 = userUnfollowed.getList("account_followers_list");
			for(int i = 0;i<followers1.size();i++) {
				if(followers1.get(i).get().equals(unfollowingUsername))
					changedFollowers = true;
				else
					followers.add(followers1.get(i));
			}
			//followers ja n tem o gajo q parou de seguir
			
			//decrease nr followers counter
			if(changedFollowers)
				nFollowers--;
			
			userUnfollowed = Entity.newBuilder(userUnfollowed)
					.set("account_followers", nFollowers)
					.set("account_followers_list", ListValue.of(followers))
					.build();
			
			txn.update(userUnfollowed);
			
			txn.commit();
			return Response.ok("Updated.").build();
			
		} finally {
			if(txn.isActive()) {
				txn.rollback();
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}
	}
	
	@GET
	@Path("/get/{username}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getProfile(@PathParam("username") String username)  {

		Transaction txn = datastore.newTransaction();
		
		try {
				
			Key accountKey = datastore.newKeyFactory().setKind("Account").newKey(username);
			
			Entity account = txn.get(accountKey);
			
			//conta nao existe
			if(account == null){
				txn.rollback();
				return Response.status(Status.NOT_FOUND).entity("Account does not exist.").build();
			}
			
			long nfollowers = account.getLong("account_followers");
			long nfollowing = account.getLong("account_following");
			long nwarnings = account.getLong("account_warnings");
			
			
			
			AccountData aux = new AccountData(
					account.getString("account_address"),
					account.getString("account_birthDay"),
					account.getString("account_cardID"),
					account.getString("account_email"),
					account.getString("account_firstName"),
					nfollowers,
					nfollowing,
					account.getString("account_gender"),
					account.getString("account_lastName"),
					account.getString("account_name"),
					account.getString("account_owner"),
					account.getString("account_perfil"),
					account.getString("account_phone"),
					account.getString("account_role"),
					account.getString("account_service"),
					account.getString("account_state"),
					nwarnings);
			List<Value<String>> followers = account.getList("account_followers_list");
			List<Value<String>> following = account.getList("account_following_list");
			List<Value<String>> reqs = account.getList("account_requests_list");
			List<Value<String>> routes = account.getList("account_routes_list");
			aux.setFollowers_list(followers);
			aux.setFollowing_list(following);
			aux.setReqs(reqs);
			aux.setRoutes(routes);
			txn.commit();
			return Response.ok(g.toJson(aux)).build();

		} finally {
			if(txn.isActive()) {
				txn.rollback();
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}
	}
	
	
}
