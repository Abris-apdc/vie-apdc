package pt.unl.fct.di.apdc.vie.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Transaction;
import com.google.gson.Gson;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;

import pt.unl.fct.di.apdc.vie.util.UserInfoData;

@Path("/info")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class UserInfoResource {
	
	private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
	private final Gson g = new Gson();
	
	@GET
    @Path("/{username}")
    //@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getRole(@PathParam("username") String username) throws EntityNotFoundException {
        Transaction txn = datastore.newTransaction();
        try {

            Key userKey = datastore.newKeyFactory().setKind("User").newKey(username);
            Entity logged = txn.get(userKey);

            if ((logged.getString("user_role").equals("USER"))
                    && logged.getString("user_name").equals(username)) {
                    UserInfoData ui = new UserInfoData(
                    		logged.getString("user_firstName"),
                    		logged.getString("user_lastName"),
                    		logged.getString("user_role"),
                    		logged.getString("user_name"));
                return Response.ok().entity(g.toJson(ui)).build();
            } else {
                txn.rollback();
                return Response.status(Status.FORBIDDEN).entity("You do not have access to this operation.").build();
            }
        } catch (Exception e) {

            txn.rollback();
            return Response.status(Status.FORBIDDEN).entity("Attempt to get user failed").build();
        } finally {

            if (txn.isActive())
                txn.rollback();
        }
    }
}
