package pt.unl.fct.di.apdc.vie.resources;


import java.util.ArrayList;
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
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.Transaction;
import com.google.gson.Gson;

import pt.unl.fct.di.apdc.vie.util.EventData;

@Path("/map")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class MapsResource {
	
	private static final Logger LOG = Logger.getLogger(MapsResource.class.getName());
	private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
	private final Gson g = new Gson();
	
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response addEvent(EventData data) {
		Transaction txn = datastore.newTransaction();
		Key eventKey = datastore.newKeyFactory().setKind("Event").newKey(data.getName());

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
			else if(!logged.getString("token_role").equals("ORG")) {
				txn.commit();
				return Response.status(Status.FORBIDDEN).entity("Action not allowed.").build();
			}
			Entity event = txn.get(eventKey);
			if (event != null) {
				//ja existe um user com o mesmo username
				txn.rollback();
				return Response.status(Status.FORBIDDEN).entity("Event alredy exists.").build();
			}
			else {
				
				event = Entity.newBuilder(eventKey)
						.set("event_coordinates", data.getCoordinates())
						.set("event_address", data.getAddress())
						.set("event_org", data.getOrg())
						.set("event_duration", data.getDuration())
						.build();
				txn.add(event);

				LOG.info("Event " + data.getAddress() + "successfully added.");
				txn.commit();
				return Response.ok(g.toJson("Event addded.")).build();
			}
		} finally {

			if (txn.isActive()) {
				txn.rollback();
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}
	}
	
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response listEvents() {
		Transaction txn = datastore.newTransaction();
		try {
			
			Query<Entity> eventQuery = Query.newEntityQueryBuilder()
					.setKind("Event")
					.build();
			QueryResults<Entity> eventsResults = txn.run(eventQuery);
			
			
			List<String> allResults = new ArrayList<>();
			
			eventsResults.forEachRemaining(e -> {allResults.add(e.getKey().toString());});
				
			txn.commit();
			return Response.ok(g.toJson(allResults)).build();

		} catch (Exception e) {
			txn.rollback();
			return Response.status(Status.FORBIDDEN).entity("Attempt to see events failed.").build();
		} finally {
			if (txn.isActive()) {
				txn.rollback();
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}

	}
	
	@GET
	@Path("/list/{organization}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response listEventsByOrg(@PathParam("organization") String org) {
		Transaction txn = datastore.newTransaction();
		try {
			
			Query<Entity> eventsQuery = Query.newEntityQueryBuilder()
					.setKind("Event")
					.build();
			QueryResults<Entity> eventsResults = txn.run(eventsQuery);
			
		
			
			List<String> allResults = new ArrayList<>();

				eventsResults.forEachRemaining(e -> {
				if(e.getString("event_org").toLowerCase().equals(org.toLowerCase()))
					allResults.add(e.getString("event_name"));});
		
			
			txn.commit();
			return Response.ok(g.toJson(allResults)).build();

		} catch (Exception e) {
			txn.rollback();
			return Response.status(Status.FORBIDDEN).entity("Attempt to see events by organization failed.").build();
		} finally {
			if (txn.isActive()) {
				txn.rollback();
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}

	}

	
}
	

	
	
