package pt.unl.fct.di.apdc.vie.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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
	
	private static final Logger LOG = Logger.getLogger(FindResource.class.getName());
	private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
	private final Gson g = new Gson();
	
	//private final KeyFactory orgKeyFactory = datastore.newKeyFactory().setKind("Organisation");

	public FindResource() {
	}
	
	@GET
	@Path("/{pattern}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getOrgs(@PathParam("pattern") String pattern )  {
		Transaction txn = datastore.newTransaction();
		try {
			boolean all = false;
			if(pattern.equals("all"))
				all = true;
			Query<Entity> accountsQuery = Query.newEntityQueryBuilder()
					.setKind("Account")
					.build();
			QueryResults<Entity> accountsResults = txn.run(accountsQuery);
			
			/*Query<Entity> usersQuery = Query.newEntityQueryBuilder()
					.setKind("User")
					.build();
			QueryResults<Entity> usersResults = txn.run(usersQuery);*/
			
			List<String> allResults = new ArrayList<>();
			//List<String> users = new ArrayList<>();
			if(!all) {
			accountsResults.forEachRemaining(e -> {
				if(e.getString("account_name").toLowerCase().contains(pattern.toLowerCase()))
					allResults.add(e.getString("account_name"));
			});
			/*accountsResults.forEachRemaining(e -> {
				if(e.getString("account_name").toLowerCase().contains(pattern.toLowerCase()))
					allResults.add(e.getString("account_name"));
			});*/
			}
			else {
				//all
				accountsResults.forEachRemaining(e -> {allResults.add(e.getString("account_name"));});
				//possivelmente adicionar tb as organizacoes
			}
			txn.commit();
			return Response.ok(g.toJson(allResults)).build();

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

}
