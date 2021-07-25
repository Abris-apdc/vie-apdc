package pt.unl.fct.di.apdc.vie.resources;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.codec.digest.DigestUtils;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.ListValue;
import com.google.cloud.datastore.Transaction;
import com.google.cloud.datastore.Value;
import com.google.gson.Gson;

import pt.unl.fct.di.apdc.vie.util.DeleteData;



@Path("/delete")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class DeleteResource {

	private static final String SU = "SU";
	//private static final String ORG = "ORG";
	
	private static final Logger LOG = Logger.getLogger(DeleteResource.class.getName());
	private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
	private final Gson g = new Gson();
	
	public DeleteResource() {
	}
	

	@Path("/")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response removeSelf(DeleteData data)  {
		Transaction txn = datastore.newTransaction();
		try {
			//boolean isUser = true;
			//if(data.getUsername().contains("@"))
				//isUser = false;
			//Key userKey;
			//if(isUser)
			
			//else
				//userKey = datastore.newKeyFactory().setKind("Organisation").newKey(data.getUsername());
			
			Key tokenKey = datastore.newKeyFactory().setKind("Token").newKey(data.getTokenID());
			
			Entity token = txn.get(tokenKey);
			long end = token.getLong("token_end_time");
			
			//o token expirou
			if(end <  System.currentTimeMillis()) {
				txn.delete(tokenKey);
				txn.commit();
				return Response.status(Status.FORBIDDEN).entity("Token expired.").build();
			}
			
			Key accountKey = datastore.newKeyFactory().setKind("Account").newKey(data.getUsername());
			Entity account = txn.get(accountKey);

			String hashedPWD;
			//if(isUser)
			hashedPWD = account.getString("account_pwd");
			//else
			//hashedPWD = account.getString("org_pwd");
			
			if(token.getString("token_role").equals(SU)) {
				//se ele for SU pode apagar qq conta
				txn.delete(accountKey);
				txn.commit();
				return Response.ok(g.toJson("Account deleted.")).build();
			}
			
			//pass errada
			if(!hashedPWD.equals(DigestUtils.sha512Hex(data.getPassword()))) {
				txn.rollback();
				return Response.status(Status.FORBIDDEN).entity("Wrong pass.").build();
			}
			
			if (token.getString("token_username").equals(data.getUsername())) {
				//CLEAN FOLLOWING
				List<Value<String>> following = account.getList("account_following_list");
				for(int i = 0; i < following.size(); i++) {
					Key followingKey = datastore.newKeyFactory().setKind("Account").newKey(following.get(i).get());
					Entity followingAcc = txn.get(followingKey);
					List<Value<String>> following2 = followingAcc.getList("account_followers_list");
					long nFollowers = followingAcc.getLong("account_followers");
					List<Value<String>> following2New = new ArrayList<>();
					for(int j = 0; j < following2.size(); j++) {
						if(!following2.get(j).get().equals(data.getUsername())) {
							following2New.add(following2.get(j));
						}
					}
					nFollowers --;
					followingAcc = Entity.newBuilder(followingAcc)
							.set("account_followers_list", ListValue.of(following2New))
							.set("account_followers", nFollowers)
							.build();
					txn.update(followingAcc);
						
				}
				//CLEAN FOLLOWERS
				List<Value<String>> followers = account.getList("account_followers_list");
				for(int i = 0; i < followers.size(); i++) {
					Key followerKey = datastore.newKeyFactory().setKind("Account").newKey(followers.get(i).get());
					Entity followerAcc = txn.get(followerKey);
					List<Value<String>> follower = followerAcc.getList("account_following_list");
					long nFollowing = followerAcc.getLong("account_following");
					List<Value<String>> followerNew = new ArrayList<>();
					for(int j = 0; j < follower.size(); j++) {
						if(!follower.get(j).get().equals(data.getUsername())) {
							followerNew.add(follower.get(j));
						}
					}
					nFollowing --;
					followerAcc = Entity.newBuilder(followerAcc)
							.set("account_following_list", ListValue.of(followerNew))
							.set("account_following", nFollowing)
							.build();
					txn.update(followerAcc);
						
				}
				
				//CLEAN EVENTS JOINED
				List<Value<String>> events = account.getList("account_events_list");
				for(int i = 0; i < events.size(); i++) {
					Key eventKey = datastore.newKeyFactory().setKind("Event").newKey(events.get(i).get());
					Entity event = txn.get(eventKey);
					List<Value<String>> participants = event.getList("event_participants_list");
					List<Value<String>> participantsNew = new ArrayList<>();
					for(int j = 0; j < participants.size(); j++) {
						if(!participants.get(j).get().equals(data.getUsername())) {
							participantsNew.add(participants.get(j));
						}
					}
					event = Entity.newBuilder(event)
							.set("event_participants_list", ListValue.of(participantsNew))
							.build();
					txn.update(event);
						
				}
				
				txn.delete(tokenKey);
				txn.delete(accountKey);
				txn.commit();
				return Response.ok(g.toJson("Your account was deleted.")).build();
				
			} 
			/*else if (logged.getString("token_role").equals(ORG) && logged.getString("token_username").equals(data.getUsername())) {
				txn.delete(tokenKey);
				txn.delete(userKey);
				txn.commit();
				return Response.ok().entity("Organization deleted.").build();
			}*/
			else {
				txn.rollback();
				return Response.status(Status.FORBIDDEN).entity("Attempt to remove account failed.").build();
			}
		} finally {

			if (txn.isActive()) {
				txn.rollback();
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}
	}
	
	private void removeFollows() {
		
	}
	
	private void removeEvents() {
		
	}
}
