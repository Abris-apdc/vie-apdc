package pt.unl.fct.di.apdc.vie.resources;

import java.time.LocalDate;
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

import org.apache.commons.codec.digest.DigestUtils;

import com.google.cloud.Timestamp;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.ListValue;
import com.google.cloud.datastore.StringValue;
import com.google.cloud.datastore.Transaction;
import com.google.cloud.datastore.Value;
import com.google.gson.Gson;

import pt.unl.fct.di.apdc.vie.util.LocationData;
import pt.unl.fct.di.apdc.vie.util.OrgInfoData;
import pt.unl.fct.di.apdc.vie.util.RouteData;
import pt.unl.fct.di.apdc.vie.util.UserInfoData;

@Path("/map")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class MapsResource {
	
	private static final Logger LOG = Logger.getLogger(ProfileResource.class.getName());
	private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
	private final Gson g = new Gson();
	
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response addLocation(LocationData data) {
		Transaction txn = datastore.newTransaction();
		Key localKey = datastore.newKeyFactory().setKind("Location").newKey(data.getCoordinates());

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
			Entity local = txn.get(localKey);
			if (local != null) {
				//ja existe um user com o mesmo username
				txn.rollback();
				return Response.status(Status.FORBIDDEN).entity("Location alredy exists.").build();
			}
			else {
				
				local = Entity.newBuilder(localKey)
						.set("local_address", data.getAddress())
						.set("local_owner", data.getOwner())
						.set("local_info", data.getInfo())
						.set("local_schedule", data.getSchedule())
						.build();
				txn.add(local);

				LOG.info("Location " + data.getAddress() + "successfully added.");
				txn.commit();
				return Response.ok(g.toJson("Location addded.")).build();
			}
		} finally {

			if (txn.isActive()) {
				txn.rollback();
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}
	}
	
	
	@GET
	@Path("/{coordinates}")
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getPoint(@PathParam("coordinates") String coordinates) {
		
		Transaction txn = datastore.newTransaction();
		
		try {
				
			Key localKey = datastore.newKeyFactory().setKind("Location").newKey(coordinates);
			
			Entity location = txn.get(localKey);
			
			//a conta nao existe nem nos users nem nas orgs
			if(location == null ){
				txn.rollback();
				return Response.status(Status.NOT_FOUND).entity("Location doesn't exist.").build();
			}
			//um user
			LocationData ui = new LocationData(coordinates,
					location.getString("local_address"),
					location.getString("local_owner"),
					location.getString("local_info"),
					location.getString("local_schedule"), "");
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
	@Path("/route")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response addRoute(RouteData data) {
		Transaction txn = datastore.newTransaction();
		Key userKey;
		String type;
		if(data.getUsername().contains("@")) {
			type = "org";
			userKey = datastore.newKeyFactory().setKind("Organisation").newKey(data.getUsername());

		}
		else {
			type = "user";
			userKey = datastore.newKeyFactory().setKind("User").newKey(data.getUsername());

		}

		Entity user = txn.get(userKey);	

		try {
			if(user == null) {
				txn.rollback();
				return Response.status(Status.NOT_FOUND).entity("User doesn't exist.").build();
			}
			Key tokenKey = datastore.newKeyFactory().setKind("Token").newKey(data.getTokenID());

			Entity logged = txn.get(tokenKey);
			long end = logged.getLong("token_end_time");

			//o token expirou
			if(end <  System.currentTimeMillis()) {
				txn.delete(tokenKey);
				txn.commit();
				return Response.status(Status.FORBIDDEN).entity("Token expired.").build();
			}

			String[] locations1 = data.getLocations();
			List<Value<String>> route = new ArrayList<Value<String>>();
			for(String l : locations1) {
				Key localKey = datastore.newKeyFactory().setKind("Location").newKey(l);
				Entity local = txn.get(localKey);
				if (local == null) {
					txn.rollback();
					return Response.status(Status.FORBIDDEN).entity("Location does not exist.").build();
				}
				else {
					route.add( StringValue.of(l));
				}
			}

			Key routeKey = datastore.newKeyFactory().setKind("Route").newKey(data.getRouteName());

			Entity r = txn.get(routeKey);
			if(r != null) {
				txn.rollback();
				return Response.status(Status.CONFLICT).entity("Route already exist.").build();
			}
			r = Entity.newBuilder(routeKey)
					.set("route_locations_list", route)
					.set("route_owner", data.getUsername())
					.set("route_info", data.getInfo())
					.build();
			txn.add(r);

			//add to users routes
			List<Value<String>> routes = new LinkedList<Value<String>>();
			List<Value<String>> routes1 = user.getList(type.concat("_routes_list"));
			for(int i = 0;i<routes1.size();i++) {
				routes.add(routes1.get(i));
			}
			
			routes.add(StringValue.of(data.getRouteName()));
			
			user = Entity.newBuilder(user)
					.set(type.concat("_routes_list"), ListValue.of(routes))
					.build();
			txn.update(user);

			LOG.info("Route " + data.getRouteName() + "successfully added.");
			txn.commit();
			return Response.ok(g.toJson("Route addded.")).build();

		} finally {

			if (txn.isActive()) {
				txn.rollback();
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}
	}
	

}
