package pt.unl.fct.di.apdc.vie.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.Transaction;
import com.google.gson.Gson;



@Path("/find")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class FindResource {
	
	private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
	//private final KeyFactory orgKeyFactory = datastore.newKeyFactory().setKind("Organisation");

	private final Gson g = new Gson();

	public FindResource() {
	}
	
	@GET
	@Path("/{pattern}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getOrgs(@PathParam("pattern") String pattern )  {
		Transaction txn = datastore.newTransaction();
		try {

			Query<Entity> query = Query.newEntityQueryBuilder()
					.setKind("Organisation")
					.build();
			QueryResults<Entity> results = txn.run(query);

			List<String> orgs = new ArrayList<>();
			results.forEachRemaining(e -> {
				if(e.getString("org_name").toLowerCase().contains(pattern.toLowerCase()))
					orgs.add(e.getString("org_email"));
			});
			
			
			txn.commit();
			return Response.ok(g.toJson(orgs)).build();

		} catch (Exception e) {

			txn.rollback();
			return Response.status(Status.FORBIDDEN).entity("Attempt to see organisations by name failed.").build();
		} finally {
			if (txn.isActive()) {
				txn.rollback();
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}
	}

	@GET
	@Path("/user/{pattern}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getUsers(@PathParam("pattern") String pattern )  {
		Transaction txn = datastore.newTransaction();
		try {

			Query<Entity> query = Query.newEntityQueryBuilder()
					.setKind("User")
					.build();
			QueryResults<Entity> results = txn.run(query);

			List<String> users = new ArrayList<>();
			results.forEachRemaining(e -> {
				if(e.getString("user_name").toLowerCase().contains(pattern.toLowerCase()))
					users.add(e.getString("user_name"));
			});
			
			txn.commit();
			return Response.ok(g.toJson(users)).build();

		} catch (Exception e) {

			txn.rollback();
			return Response.status(Status.FORBIDDEN).entity("Attempt to see users by name failed.").build();
		} finally {
			if (txn.isActive()) {
				txn.rollback();
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}
	}

}
