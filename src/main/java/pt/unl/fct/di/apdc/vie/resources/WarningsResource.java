package pt.unl.fct.di.apdc.vie.resources;


import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
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

@Path("/warning")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class WarningsResource {

	private final String SU = "SU";
	private final String MOD = "MOD";
	private final String ADMIN = "ADMIN";
	private final String USER = "USER";
	private final String ORG = "ORG";
	
	private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
	//private final Gson g = new Gson();

	public WarningsResource() {
	}
	
	@POST
	@Path("/{username}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response warning(@PathParam("username") String username, FollowData data) {
		//data tem so o token
		//username e da pessoa q vai levar o warning
		//token da pessoa q vai dar o warning
		
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
			
			Key warningkey = datastore.newKeyFactory().setKind("Account").newKey(token.getString("token_username"));
			
			Entity userWarning = txn.get(warningkey);
			
			if(userWarning == null) {
				//user que se tenta dar warning n existe na base de dados
				txn.rollback();
				return Response.status(Status.FORBIDDEN).entity("No such user.").build(); 
			}
			
			String role = userWarning.getString("account_role");
			
			if(role.equals(SU) || role.equals(MOD) || role.equals(ADMIN)) {
				// user q da warning e su ou mod ou admin
				
				Key warnedKey = datastore.newKeyFactory().setKind("Account").newKey(username);
				
				Entity userWarned = txn.get(warnedKey);
				
				String warnedRole = userWarned.getString("account_role");
				
				if(warnedRole.equals(USER) || warnedRole.equals(ORG)) {
					long warnings = userWarned.getLong("account_warnings");
					warnings++;
					
					userWarned = Entity.newBuilder(userWarned)
							.set("account_warnings", warnings)
							.build();
					txn.update(userWarned);
					txn.commit();
					return Response.ok("Updated warnings.").build();
				}
				else {
					//esta a tentar dar um warning a um mod ou admin ou num su
					txn.rollback();
					return Response.status(Status.FORBIDDEN).entity("No permissions.").build();
				}
				
			}
			else {
				txn.rollback();
				return Response.status(Status.FORBIDDEN).entity("You don't have permisions.").build();
			}
			
			
		} finally {

			if (txn.isActive()) {
				txn.rollback();
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}
		
	}
}
