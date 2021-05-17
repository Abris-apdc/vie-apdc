package pt.unl.fct.di.apdc.vie.resources;


import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.Transaction;

import pt.unl.fct.di.apdc.vie.util.DeleteData;



@Path("/delete")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class DeleteResource {

	private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
	private static final String USER = "USER";
	private static final String ORG = "ORG";
	public DeleteResource() {
	}
	

	@Path("/")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response removeSelf(DeleteData data) throws EntityNotFoundException {
		Transaction txn = datastore.newTransaction();
		try {
			boolean isUser = true;
			if(data.getUsername().contains("@"))
				isUser = false;
			Key userKey;
			if(isUser)
				userKey = datastore.newKeyFactory().setKind("User").newKey(data.getUsername());
			else
				userKey = datastore.newKeyFactory().setKind("Organisation").newKey(data.getUsername());
			Key tokenKey = datastore.newKeyFactory().setKind("Token").newKey(data.getTokenID());
			Entity logged = txn.get(tokenKey);
			long end = logged.getLong("token_end_time");
			if(end <  System.currentTimeMillis()) { //o token expirou
				txn.rollback();
				return Response.status(Status.FORBIDDEN).entity("token expired").build();
			}
			if (logged.getString("token_role").equals(USER) && logged.getString("token_username").equals(data.getUsername())) {

				txn.delete(tokenKey);
				txn.delete(userKey);
				txn.commit();
				return Response.ok().entity("User deleted").build();
				
			} 
			else if (logged.getString("token_role").equals(ORG) && logged.getString("token_username").equals(data.getUsername())) {
				txn.delete(tokenKey);
				txn.delete(userKey);
				txn.commit();
				return Response.ok().entity("Organization deleted").build();
			}
			else {
				txn.rollback();
				return Response.status(Status.FORBIDDEN).entity("Attempt to remove user failed.").build();
			}
		} catch (Exception e) {

			txn.rollback();
			return Response.status(Status.FORBIDDEN).entity("Attempt to remove user failed.").build();
		} finally {

			if (txn.isActive()) {
				txn.rollback();
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}
	}
}
