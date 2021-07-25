package pt.unl.fct.di.apdc.vie.resources;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
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
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.ListValue;
import com.google.cloud.datastore.StringValue;
import com.google.cloud.datastore.Transaction;
import com.google.cloud.datastore.Value;
import com.google.gson.Gson;

import pt.unl.fct.di.apdc.vie.util.FollowData;
import pt.unl.fct.di.apdc.vie.util.JoinEventData;

@Path("/event")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class EventResource {
	
	private static final Logger LOG = Logger.getLogger(MapsResource.class.getName());
	private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
	private final Gson g = new Gson();
	
	@POST
	@Path("/join") 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response joinEvent(JoinEventData data) {		
		Transaction txn = datastore.newTransaction();
		Key eventKey = datastore.newKeyFactory().setKind("Event").newKey(data.getEvent());
		
		try {
			
			Key tokenKey = datastore.newKeyFactory().setKind("Token").newKey(data.getTokenID());
			
			Entity logged = txn.get(tokenKey);
			long end = logged.getLong("token_end_time");
			
			//o token expirou
			if(end <  System.currentTimeMillis()) {
				txn.delete(tokenKey);
				txn.commit();
				return Response.status(Status.FORBIDDEN).entity("Token expired.").build();
			}
			
			Key accountKey = datastore.newKeyFactory().setKind("Account").newKey(logged.getString("token_username"));

			Entity event = txn.get(eventKey);
			
			if(event == null) {
				txn.commit();
				return Response.status(Status.FORBIDDEN).entity("Event does not exist.").build();
			}
			Entity account = txn.get(accountKey);
			if (account == null) {
				txn.rollback();
				return Response.status(Status.FORBIDDEN).entity("User does not exist.").build();
			}
			
			List<Value<String>> participants = event.getList("event_participants_list");
			List<Value<String>> participantsNew = new LinkedList<Value<String>>();
			
			List<Value<String>> events = account.getList("account_events_list");
			List<Value<String>> eventsNew = new LinkedList<Value<String>>();
			
			for(int i = 0;i<participants.size();i++) {
				participantsNew.add(participants.get(i));
			}
			
			for(int i = 0;i<events.size();i++) {
				eventsNew.add(events.get(i));
			}
			
			participantsNew.add(StringValue.of(logged.getString("token_username")));
			eventsNew.add(StringValue.of(data.getEvent()));

			
			event = Entity.newBuilder(event)
					.set("event_participants_list", ListValue.of(participantsNew))
					.build();
			
			account = Entity.newBuilder(account)
					.set("account_events_list", ListValue.of(eventsNew))
					.build();
			
			txn.update(event);
			
			txn.update(account);
			
			txn.commit();
			return Response.ok(g.toJson("Event updated.")).build();
		} finally {

			if (txn.isActive()) {
				txn.rollback();
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}
		
	}
	
	@POST
	@Path("/leave") 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response leaveEvent(JoinEventData data) {		
		Transaction txn = datastore.newTransaction();
		Key eventKey = datastore.newKeyFactory().setKind("Event").newKey(data.getEvent());
		
		try {
			
			Key tokenKey = datastore.newKeyFactory().setKind("Token").newKey(data.getTokenID());
			
			Entity logged = txn.get(tokenKey);
			long end = logged.getLong("token_end_time");
			
			//o token expirou
			if(end <  System.currentTimeMillis()) {
				txn.delete(tokenKey);
				txn.commit();
				return Response.status(Status.FORBIDDEN).entity("Token expired.").build();
			}
			
			Key accountKey = datastore.newKeyFactory().setKind("Account").newKey(logged.getString("token_username"));

			Entity event = txn.get(eventKey);
			
			if(event == null) {
				txn.commit();
				return Response.status(Status.FORBIDDEN).entity("Event does not exist.").build();
			}
			Entity account = txn.get(accountKey);
			if (account == null) {
				txn.rollback();
				return Response.status(Status.FORBIDDEN).entity("User does not exist.").build();
			}
			
			List<Value<String>> participants = event.getList("event_participants_list");
			List<Value<String>> participantsNew = new LinkedList<Value<String>>();
			
			List<Value<String>> events = account.getList("account_events_list");
			List<Value<String>> eventsNew = new LinkedList<Value<String>>();
			
			boolean found = false;
			
			for(int i = 0;i<participants.size();i++) {
				if(!participants.get(i).get().equals(logged.getString("token_username"))) {
					participantsNew.add(participants.get(i));
				}
				else {
					found = true;
				}
			}			
			
			if(!found) {
				txn.rollback();
				return Response.status(Status.FORBIDDEN).entity("User is not in event list.").build();
			}
			
			for(int i = 0;i<events.size();i++) {
				if(!events.get(i).get().equals(data.getEvent()))
					eventsNew.add(events.get(i));
			}
			
			
			event = Entity.newBuilder(event)
					.set("event_participants_list", ListValue.of(participantsNew))
					.build();
			
			account = Entity.newBuilder(account)
					.set("account_events_list", ListValue.of(eventsNew))
					.build();
			
			txn.update(event);
			
			txn.update(account);
			
			txn.commit();
			return Response.ok(g.toJson("Event updated.")).build();
		} finally {

			if (txn.isActive()) {
				txn.rollback();
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}
		
	}
	
	@GET
	@Path("/{event}/list") 
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getEventList(@PathParam("event") String eventName) {		
		Transaction txn = datastore.newTransaction();
		Key eventKey = datastore.newKeyFactory().setKind("Event").newKey(eventName);
		
		try {

			Entity event = txn.get(eventKey);
			
			if(event == null) {
				txn.commit();
				return Response.status(Status.FORBIDDEN).entity("Event does not exist.").build();
			}
			
			List<Value<String>> participants = event.getList("event_participants_list");
			List<String> participants1 = new ArrayList<>();
			for(int i = 0; i < participants.size(); i++)
				participants1.add(participants.get(i).get());
			
			txn.commit();
			return Response.ok(g.toJson(participants1)).build();
		} finally {

			if (txn.isActive()) {
				txn.rollback();
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}
		
	}
	
	@POST
	@Path("/isPart/{event}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response isPart(@PathParam("event") String event, FollowData data)  {
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
			
			
			Key key = datastore.newKeyFactory().setKind("Account").newKey(token.getString("token_username"));
			
			Entity user = txn.get(key);
			
			List<Value<String>> events = user.getList("account_events_list");
			
			for(int i = 0; i<events.size();i++) {
				if(events.get(i).get().equals(event)) {
					txn.commit();
					return Response.ok().build();
				}
			}
			txn.commit();
			return Response.status(Status.NOT_FOUND).build();
			
		} finally {
			if(txn.isActive()) {
				txn.rollback();
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}
	}
	
	@GET 
	@Path("/{account}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getUserEvents(@PathParam("account") String account)  {
		Transaction txn = datastore.newTransaction();
		Key userKey = datastore.newKeyFactory().setKind("Account").newKey(account);
		
		try {

			Entity user = txn.get(userKey);
			
			if(user == null) {
				txn.commit();
				return Response.status(Status.FORBIDDEN).entity("User does not exist.").build();
			}
			
			List<Value<String>> events = user.getList("account_events_list");
			List<String> events1 = new ArrayList<>();
			for(int i = 0; i < events.size(); i++)
				events1.add(events.get(i).get());
			
			txn.commit();
			return Response.ok(g.toJson(events1)).build();
		} finally {

			if (txn.isActive()) {
				txn.rollback();
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}
	}
	
	

	
}
