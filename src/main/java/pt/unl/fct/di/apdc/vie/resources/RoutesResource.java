package pt.unl.fct.di.apdc.vie.resources;

import java.util.ArrayList;
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

import org.apache.commons.codec.digest.DigestUtils;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.ListValue;
import com.google.cloud.datastore.StringValue;
import com.google.cloud.datastore.Transaction;
import com.google.cloud.datastore.Value;
import com.google.gson.Gson;

import pt.unl.fct.di.apdc.vie.util.DeleteEventData;
import pt.unl.fct.di.apdc.vie.util.DeleteRouteData;
import pt.unl.fct.di.apdc.vie.util.GetRoutesData;
import pt.unl.fct.di.apdc.vie.util.ModifyRouteData;
import pt.unl.fct.di.apdc.vie.util.RouteData;
import pt.unl.fct.di.apdc.vie.util.RouteInfo;

@Path("/route")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class RoutesResource {
	
	private static final String SU = "SU";


	
	private static final Logger LOG = Logger.getLogger(RoutesResource.class.getName());
	private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
	private final Gson g = new Gson();
	
	
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response addRoute(RouteData data) {
		Transaction txn = datastore.newTransaction();
		Key userKey = datastore.newKeyFactory().setKind("Account").newKey(data.getUsername());

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
				Key localKey = datastore.newKeyFactory().setKind("Event").newKey(l);
				Entity event = txn.get(localKey);
				if (event == null) {
					txn.rollback();
					return Response.status(Status.FORBIDDEN).entity("Event does not exist.").build();
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
					.set("route_events_list", route)
					.set("route_owner", data.getUsername())
					.set("route_info", data.getInfo())
					.build();
			txn.add(r);

			//add to users routes
			List<Value<String>> routes = new LinkedList<Value<String>>();
			List<Value<String>> routes1 = user.getList("account_routes_list");
			for(int i = 0;i<routes1.size();i++) {
				routes.add(routes1.get(i));
			}
			
			routes.add(StringValue.of(data.getRouteName()));
			
			user = Entity.newBuilder(user)
					.set("account_routes_list", ListValue.of(routes))
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
	
	
	@POST
	@Path("/get")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response listRoutes(GetRoutesData data) {
		Transaction txn = datastore.newTransaction();
		try {
			
			
			Key userKey = datastore.newKeyFactory().setKind("Account").newKey(data.getUsername());
			Entity user = txn.get(userKey);	
			
			if(user == null) {
				txn.rollback();
				return Response.status(Status.NOT_FOUND).entity("User does not exist.").build();
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
			
			
			List<String> routes = new ArrayList<>();
			
			List<Value<String>> routes1 = user.getList("account_routes_list");
			for(int i = 0;i<routes1.size();i++) {
				routes.add(routes1.get(i).get());
				
			} 
			

			txn.commit();
			return Response.ok(g.toJson(routes)).build();

		} finally {
			if (txn.isActive()) {
				txn.rollback();
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}
	}
	
	@POST
	@Path("/pull/{route}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getRoute(@PathParam("route") String routeName) {
		Transaction txn = datastore.newTransaction();
		try {
			
			
			Key routeKey = datastore.newKeyFactory().setKind("Route").newKey(routeName);
			Entity route = txn.get(routeKey);	
			
			if(route == null) {
				txn.rollback();
				return Response.status(Status.NOT_FOUND).entity("Route does not exist.").build();
			}
			
			RouteInfo aux = new RouteInfo(
					routeName, route.getString("route_owner"), route.getString("route_info"));
					
						
			List<Value<String>> events = route.getList("route_events_list");
			List<String> events1 = new ArrayList<>();
			List<String> coords = new ArrayList<>();
			for(int i = 0;i<events.size();i++) {
				events1.add(events.get(i).get());
				Key eventKey = datastore.newKeyFactory().setKind("Event").newKey(events.get(i).get());
				Entity event = txn.get(eventKey);
				coords.add(event.getString("event_coordinates"));
				
			} 
			
			aux.setEvents(events1);
			aux.setCoords(coords);
			
			

			txn.commit();
			return Response.ok(g.toJson(aux)).build();

		} finally {
			if (txn.isActive()) {
				txn.rollback();
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}
	}
	
	
	
	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response modifyRoute(ModifyRouteData data) {
		
		Transaction txn = datastore.newTransaction();

		try {

			Key tokenKey = datastore.newKeyFactory().setKind("Token").newKey(data.getTokenID());

			Entity token = txn.get(tokenKey);
			long end = token.getLong("token_end_time");

			//o token expirou
			if(end <  System.currentTimeMillis()) {
				txn.delete(tokenKey);
				txn.commit();
				return Response.status(Status.FORBIDDEN).entity("Token expired.").build();
			}


				Key routeKey = datastore.newKeyFactory().setKind("Route").newKey(data.getRouteName());
				Entity route = txn.get(routeKey);

				if(route == null) {
					return Response.status(Status.NOT_FOUND).entity("Route does not exist.").build();
				}
				
				if (!token.getString("token_username").equals(route.getString("route_owner"))) {
					txn.rollback();
					return Response.status(Status.FORBIDDEN).entity("Attempt to update route failed.").build();
				
				}
				
				if(data.getRouteLocations().length != 0) {
					String[] locations1 = data.getRouteLocations();
					List<Value<String>> route1 = new ArrayList<Value<String>>();
					for(String l : locations1) {
						Key localKey = datastore.newKeyFactory().setKind("Event").newKey(l);
						Entity local = txn.get(localKey);
						if (local == null) {
							txn.rollback();
							return Response.status(Status.FORBIDDEN).entity("Event does not exist.").build();
						}
						else {
							route1.add( StringValue.of(l));
						}
					}
					

					route = Entity.newBuilder(route)
							.set("route_events_list", ListValue.of(route1))
							.build();
					txn.update(route);
					
					
					
				}
				
				if(data.getInfo() != "") {
					route = Entity.newBuilder(route)
							.set("route_info", ListValue.of(data.getInfo()))
							.build();
					txn.update(route);
				}
					
			
				txn.commit();
				return Response.ok(g.toJson("Route was updated.")).build();

		} finally {

			if (txn.isActive()) {
				txn.rollback();
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}
		
	}
	
	@POST
	@Path("/add/{route}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addEvent(@PathParam("route") String routeName, DeleteEventData data) {
		Transaction txn = datastore.newTransaction();

		try {
			Key tokenKey = datastore.newKeyFactory().setKind("Token").newKey(data.getTokenID());

			Entity token = txn.get(tokenKey);
			long end = token.getLong("token_end_time");

			//o token expirou
			if(end <  System.currentTimeMillis()) {
				txn.delete(tokenKey);
				txn.commit();
				return Response.status(Status.FORBIDDEN).entity("Token expired.").build();
			}
			
			Key routeKey = datastore.newKeyFactory().setKind("Route").newKey(routeName);
			Entity route = txn.get(routeKey);

			if(route == null) {
				return Response.status(Status.NOT_FOUND).entity("Route does not exist.").build();
			}
			
			Key eventKey = datastore.newKeyFactory().setKind("Event").newKey(data.getName());
			Entity event = txn.get(eventKey);
			
			if(event == null) {
				return Response.status(Status.NOT_FOUND).entity("Event does not exist.").build();
			}
			
			if(!route.getString("route_owner").equals(token.getString("token_username"))) {
				return Response.status(Status.FORBIDDEN).entity("You are not the owner.").build();
			}
			 
			List<Value<String>> events = route.getList("route_events_list");
			List<Value<String>> events1 = new ArrayList<>();
			for(int i = 0; i < events.size(); i ++) {
				events1.add(events.get(i));
			}
			events1.add(StringValue.of(data.getName()));
			
			route = Entity.newBuilder(route)
					.set("route_events_list", events1)
					.build();
			txn.update(route);
			txn.commit();
			
			return Response.ok(g.toJson("Route was updated.")).build();

			
		} finally {

			if (txn.isActive()) {
				txn.rollback();
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}
	}

	@DELETE
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response deleteRoute(DeleteRouteData data) {
		Transaction txn = datastore.newTransaction();

		try {

			Key tokenKey = datastore.newKeyFactory().setKind("Token").newKey(data.getTokenID());

			Entity token = txn.get(tokenKey);
			long end = token.getLong("token_end_time");

			//o token expirou
			if(end <  System.currentTimeMillis()) {
				txn.delete(tokenKey);
				txn.commit();
				return Response.status(Status.FORBIDDEN).entity("Token expired.").build();
			}

			Key accountKey = datastore.newKeyFactory().setKind("Account").newKey(token.getString("token_username"));
			Entity account = txn.get(accountKey);

			Key routeKey = datastore.newKeyFactory().setKind("Route").newKey(data.getRoute());
			Entity route = txn.get(routeKey);

			if(route == null) {
				return Response.status(Status.NOT_FOUND).entity("Route does not exist.").build();
			}


			if (!token.getString("token_username").equals(route.getString("route_owner"))) {
				txn.rollback();
				return Response.status(Status.FORBIDDEN).entity("Attempt to update route failed.").build();
			
			}
				
				
			

				List<Value<String>> routes = new LinkedList<Value<String>>();
				List<Value<String>> routes1 = account.getList("account_routes_list");
				for(int i = 0;i<routes1.size();i++) {

					if(!routes1.get(i).get().equals(data.getRoute()))
						routes.add(routes1.get(i));
				}

				//routes.add(StringValue.of(data.getRouteName()));

				account = Entity.newBuilder(account)
						.set("account_routes_list", ListValue.of(routes))
						.build();
				txn.update(account);


				txn.delete(routeKey);
				txn.commit();
				return Response.ok(g.toJson("Route was deleted.")).build();

			
		} finally {

			if (txn.isActive()) {
				txn.rollback();
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}
	}

	
	
}
