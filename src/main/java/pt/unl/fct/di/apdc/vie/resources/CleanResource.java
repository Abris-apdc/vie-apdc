package pt.unl.fct.di.apdc.vie.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;


import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.Transaction;
import com.google.gson.Gson;

import pt.unl.fct.di.apdc.vie.util.EventInfo;

@Path("/clean")
public class CleanResource {
	
	private static final Logger LOG = Logger.getLogger(DeleteResource.class.getName());
	private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
	private final Gson g = new Gson();
	
	@Path("/")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response cleanTokens()  {
		Transaction txn = datastore.newTransaction();
		try {
			
			Query<Entity> eventQuery = Query.newEntityQueryBuilder()
					.setKind("Token")
					.build();
			QueryResults<Entity> tokens = txn.run(eventQuery);
			
			
			List<EventInfo> allResults = new ArrayList<>();
			
			tokens.forEachRemaining(e -> {if(e.getLong("token_end_time") <  System.currentTimeMillis()) {txn.delete(e.getKey());}});
				
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

}
