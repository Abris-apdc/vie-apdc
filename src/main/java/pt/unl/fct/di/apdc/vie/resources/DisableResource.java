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

@Path("/disable")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class DisableResource {
	
	private final String SU = "SU";
	//private final String MOD = "MOD";
	private final String ADMIN = "ADMIN";
	private final String USER = "USER";
	private final String ORG = "ORG";
	
	private final String DISABLE = "DISABLE";
	
	private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
	//private final Gson g = new Gson();

	public DisableResource() {
	}
	
	@PUT
	@Path("/{username}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response disable(@PathParam("username") String username, FollowData data) {
		
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
			
			Key disablingKey = datastore.newKeyFactory().setKind("Account").newKey(token.getString("token_username"));
			
			Entity userDisabling= txn.get(disablingKey);
			if(userDisabling == null) {
				//user que se tenta dar warning n existe na base de dados
				txn.rollback();
				return Response.status(Status.FORBIDDEN).entity("No such user.").build(); 
			}
			
			String role = userDisabling.getString("account_role");
			
			if(role.equals(SU)  || role.equals(ADMIN)) {
				// user q da disable e su ou admin
				
				Key disabledKey = datastore.newKeyFactory().setKind("Account").newKey(username);
				
				Entity userDisabled = txn.get(disabledKey);
				
				String disableRole = userDisabled.getString("account_role");
				
				if(disableRole.equals(USER) || disableRole.equals(ORG)) {
					
					userDisabled = Entity.newBuilder(userDisabled)
							.set("account_state", DISABLE)
							.build();
					txn.update(userDisabled);
					txn.commit();
					return Response.ok("Disabled account.").build();
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
