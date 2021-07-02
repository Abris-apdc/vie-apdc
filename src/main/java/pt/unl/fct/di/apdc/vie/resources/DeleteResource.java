package pt.unl.fct.di.apdc.vie.resources;


import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.codec.digest.DigestUtils;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.Transaction;
import com.google.gson.Gson;

import pt.unl.fct.di.apdc.vie.util.DeleteData;



@Path("/delete")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class DeleteResource {

	private static final String SU = "SU";
	//private static final String ORG = "ORG";
	
	private static final Logger LOG = Logger.getLogger(DeleteResource.class.getName());
	private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
	private final Gson g = new Gson();
	
	public DeleteResource() {
	}
	

	@Path("/")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response removeSelf(DeleteData data)  {
		Transaction txn = datastore.newTransaction();
		try {
			//boolean isUser = true;
			//if(data.getUsername().contains("@"))
				//isUser = false;
			//Key userKey;
			//if(isUser)
			
			//else
				//userKey = datastore.newKeyFactory().setKind("Organisation").newKey(data.getUsername());
			
			Key tokenKey = datastore.newKeyFactory().setKind("Token").newKey(data.getTokenID());
			
			Entity token = txn.get(tokenKey);
			long end = token.getLong("token_end_time");
			
			//o token expirou
			if(end <  System.currentTimeMillis()) {
				txn.delete(tokenKey);
				txn.commit();
				return Response.status(Status.FORBIDDEN).entity("Token expired.").build();
			}
			
			Key accountKey = datastore.newKeyFactory().setKind("Account").newKey(data.getUsername());
			Entity account = txn.get(accountKey);

			String hashedPWD;
			//if(isUser)
			hashedPWD = account.getString("account_pwd");
			//else
			//hashedPWD = account.getString("org_pwd");
			
			if(token.getString("token_role").equals(SU)) {
				//se ele for SU pode apagar qq conta
				txn.delete(accountKey);
				txn.commit();
				return Response.ok(g.toJson("Account deleted.")).build();
			}
			
			//pass errada
			if(!hashedPWD.equals(DigestUtils.sha512Hex(data.getPassword()))) {
				txn.rollback();
				return Response.status(Status.FORBIDDEN).entity("Wrong pass.").build();
			}
			
			if (token.getString("token_username").equals(data.getUsername())) {
				//ele tenta remover-se a si proprio
				txn.delete(tokenKey);
				txn.delete(accountKey);
				txn.commit();
				return Response.ok(g.toJson("Your account was deleted.")).build();
				
			} 
			/*else if (logged.getString("token_role").equals(ORG) && logged.getString("token_username").equals(data.getUsername())) {
				txn.delete(tokenKey);
				txn.delete(userKey);
				txn.commit();
				return Response.ok().entity("Organization deleted.").build();
			}*/
			else {
				txn.rollback();
				return Response.status(Status.FORBIDDEN).entity("Attempt to remove account failed.").build();
			}
		} finally {

			if (txn.isActive()) {
				txn.rollback();
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}
	}
}
