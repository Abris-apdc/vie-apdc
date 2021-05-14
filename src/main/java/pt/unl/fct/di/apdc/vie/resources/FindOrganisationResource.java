package pt.unl.fct.di.apdc.vie.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StructuredQuery.PropertyFilter;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.Transaction;
import com.google.gson.Gson;



@Path("/find")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class FindOrganisationResource {
	
	private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
	private final KeyFactory orgKeyFactory = datastore.newKeyFactory().setKind("Organisation");

	private final Gson g = new Gson();

	public FindOrganisationResource() {
	}
	
	@PUT
	@Path("/{pattern}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getOrgs(@PathParam("pattern") String pattern )  {
		Transaction txn = datastore.newTransaction();
		try {

			Query<Entity> query = Query.newEntityQueryBuilder()
					.setKind("Organisation")
					.setFilter( PropertyFilter.ge("org_name", pattern)
							)
					.build();
			QueryResults<Entity> results = datastore.run(query);

			List<String> orgs = new ArrayList<>();
			results.forEachRemaining(e -> {
				orgs.add(e.getKey().getName()); 
			});
			return Response.ok(g.toJson(orgs)).build();

		} catch (Exception e) {

			txn.rollback();
			return Response.status(Status.FORBIDDEN).entity("Attempt to see organisations by name failed.").build();
		} finally {

			if (txn.isActive())
				txn.rollback();
		}
	}


}
