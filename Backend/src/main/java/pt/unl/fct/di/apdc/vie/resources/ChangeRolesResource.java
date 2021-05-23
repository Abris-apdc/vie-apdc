package pt.unl.fct.di.apdc.vie.resources;

import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.Transaction;
import com.google.gson.Gson;

import pt.unl.fct.di.apdc.vie.util.ChangeRoleData;

@Path("/changeRole")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class ChangeRolesResource {

	private static final String USER = "USER";
	private static final String ORG = "ORG";
	private static final String ADMIN = "ADMIN";
	private static final String MOD = "MOD";
	
	private static final Logger LOG = Logger.getLogger(DeleteResource.class.getName());
	private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
	private final Gson g = new Gson();
	
	public ChangeRolesResource() {
	}
	

	@Path("/")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response changeRole(ChangeRoleData data)  {
		Transaction txn = datastore.newTransaction();
		try {
			Key tokenKey = datastore.newKeyFactory().setKind("Token").newKey(data.getTokenID());
			Entity token = txn.get(tokenKey);

			if(token == null) {
				//não está logged in
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
			
			Key userAMudarKey = datastore.newKeyFactory().setKind("User").newKey(data.getUsername());
			Entity userAMudar = txn.get(userAMudarKey);
			
			//user a mudar nao existe
			if(userAMudar == null) {
				txn.rollback();
				return Response.status(Status.FORBIDDEN).entity("The user you are trying to change doesn't exist.").build();
			}
			
			if(!data.getNewRole().equals(USER)
					&& !data.getNewRole().equals(ORG)
					&& !data.getNewRole().equals(ADMIN)
					&& !data.getNewRole().equals(MOD)) {
				txn.rollback();
				return Response.status(Status.FORBIDDEN).entity("Not a recognized role.").build();
			}
			
			//tentar meter alguem org
			if(data.getNewRole().equals(ORG)) {
				txn.rollback();
				return Response.status(Status.FORBIDDEN).entity("You can't change someone's role to ORG.").build();
			}
			
			Key userQueMudaKey = datastore.newKeyFactory().setKind("User").newKey(token.getString("token_username"));
			Entity userQueMuda = txn.get(userQueMudaKey);
			
			if(userQueMuda == null) {
				//ele e uma org dai nao existir nos "User"
				txn.rollback();
				return Response.status(Status.FORBIDDEN).entity("You can't change roles.").build();
			}
			
			String userQueMudaRole = userQueMuda.getString("user_role");
			
			//orgs users e mods nao podem mudar roles
			if (userQueMudaRole.equals(USER)
					|| userQueMudaRole.equals(MOD)) {
				txn.rollback();
				return Response.status(Status.FORBIDDEN).entity("You can't change roles.").build();
			}
			
			if(userQueMudaRole.equals(ADMIN) && !data.getNewRole().equals(MOD)) {
				txn.rollback();
				return Response.status(Status.FORBIDDEN).entity("You can't change roles.").build();
			}
			
			if(userQueMudaRole.equals(ADMIN) && !userAMudar.getString("user_role").equals(USER)) {
				txn.rollback();
				return Response.status(Status.FORBIDDEN).entity("You can't change roles.").build();
			}
			
			userAMudar = Entity.newBuilder(userAMudar)
					.set("user_role",data.getNewRole())
					.build();
			txn.update(userAMudar);
			txn.commit();
			return Response.ok(g.toJson("Role changed.")).build();
			
		} finally {
			if(txn.isActive()) {
				txn.rollback();
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}
	}
}
