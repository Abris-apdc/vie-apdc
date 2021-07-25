package pt.unl.fct.di.apdc.vie.resources;

import javax.ws.rs.GET;
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
import com.google.cloud.datastore.Transaction;


@Path("/isDisable")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class StateResource {

	//private static final String ENABLE = "ENABLE";
	private static final String DISABLE = "DISABLE";
	
	private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
	//private final Gson g = new Gson();

	public StateResource() {
	}
	
	@GET
	@Path("/{username}")
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response warning(@PathParam("username") String username) {
		
		Transaction txn = datastore.newTransaction();

		try {
			Key key = datastore.newKeyFactory().setKind("Account").newKey(username);
			
			Entity account = txn.get(key);
			
			if(account == null) {
				txn.rollback();
				return Response.status(Status.FORBIDDEN).entity("No such user.").build(); 
			}
			
			String state = account.getString("account_state");
			if( state.equals(DISABLE)){
				txn.commit();
				return Response.ok().build();
			}
			
			txn.rollback();
			return Response.status(Status.NOT_FOUND).build();
			
		} finally {

			if (txn.isActive()) {
				txn.rollback();
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}
	}
}
