package pt.unl.fct.di.apdc.vie.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
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

import pt.unl.fct.di.apdc.vie.util.FollowData;

@Path("/enable")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class EnableResource {

	private final String SU = "SU";
	//private final String MOD = "MOD";
	private final String ADMIN = "ADMIN";
	private final String USER = "USER";
	private final String ORG = "ORG";
	
	private final String ENABLE = "ENABLE";
	
	private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
	//private final Gson g = new Gson();

	public EnableResource() {
	}
	
	@PUT
	@Path("/{username}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response enable(@PathParam("username") String username, FollowData data) {
		
		Transaction txn = datastore.newTransaction();
		try {
			
			Key tokenKey = datastore.newKeyFactory().setKind("Token").newKey(data.getTokenID());
			Entity token = txn.get(tokenKey);
			
			if(token == null) {
				txn.rollback();
				return Response.status(Status.FORBIDDEN).entity("You are not logged in.").build();
			}
			long end = token.getLong("token_end_time");
			
			//o token expirou
			if(end <  System.currentTimeMillis()) {
				txn.delete(tokenKey);
				txn.commit();
				return Response.status(Status.FORBIDDEN).entity("Token expired.").build();
			}
			
			Key enablingKey = datastore.newKeyFactory().setKind("Account").newKey(token.getString("token_username"));
			
			Entity userEnabling= txn.get(enablingKey);
			if(userEnabling == null) {
				//user que se tenta dar warning n existe na base de dados
				txn.rollback();
				return Response.status(Status.FORBIDDEN).entity("No such user.").build(); 
			}
			
			String role = userEnabling.getString("account_role");
			
			if(role.equals(SU)  || role.equals(ADMIN)) {
				// user q da disable e su ou admin
				
				Key enabledKey = datastore.newKeyFactory().setKind("Account").newKey(username);
				
				Entity userEnabled = txn.get(enabledKey);
				
				String enableRole = userEnabled.getString("account_role");
				
				if(enableRole.equals(USER) || enableRole.equals(ORG)) {
					
					userEnabled = Entity.newBuilder(userEnabled)
							.set("account_state", ENABLE)
							.build();
					txn.update(userEnabled);
					txn.commit();
					return Response.ok("Enabled account.").build();
				}
				else {
					//esta a tentar dar um warning a um mod ou admin ou num su
					txn.rollback();
					return Response.status(Status.FORBIDDEN).entity("No permission.").build();
				}
			}
			else {
				txn.rollback();
				return Response.status(Status.FORBIDDEN).entity("You don't have permission.").build();
			}
		} finally {

			if (txn.isActive()) {
				txn.rollback();
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}
	}
}
