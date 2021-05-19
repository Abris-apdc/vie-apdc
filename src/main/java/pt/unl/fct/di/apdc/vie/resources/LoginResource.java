package pt.unl.fct.di.apdc.vie.resources;

import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
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
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.Transaction;
import com.google.gson.Gson;

import pt.unl.fct.di.apdc.vie.util.AuthToken;
import pt.unl.fct.di.apdc.vie.util.LoginData;



@Path("/login")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class LoginResource {

	private static final Logger LOG = Logger.getLogger(LoginResource.class.getName());
	private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
	private final Gson g = new Gson();
	
	private KeyFactory userKeyFactory = datastore.newKeyFactory().setKind("User");
	private KeyFactory orgKeyFactory = datastore.newKeyFactory().setKind("Organisation");

	public LoginResource() {
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response doLogin(LoginData data) {
		LOG.fine("Attempt to log in user: " + data.getUsername());

		boolean isUser = !data.getUsername().contains("@");
		Key key;
		if(!isUser)
			key = orgKeyFactory.newKey(data.getUsername());
		else
			key = userKeyFactory.newKey(data.getUsername());
		
		Transaction txn = datastore.newTransaction();
		try {
			Entity user = txn.get(key);
			if(user == null) {
				//user does not exit
				txn.rollback();
				LOG.warning("Failed login attempt. Username '" + data.getUsername() + "' doesn't exist in datastore.");
				return Response.status(Status.NOT_FOUND).entity("Wrong pass or username.").build();
			}
			
			//pass errada
			String hashedPWD;
			if(isUser)
				hashedPWD = user.getString("user_pwd");
			else
				hashedPWD = user.getString("org_pwd");
			
			if(!hashedPWD.equals(DigestUtils.sha512Hex(data.getPassword()))) {
				txn.rollback();
				LOG.warning("Failed login attempt. Wrong password for username: " + data.getUsername());
				return Response.status(Status.FORBIDDEN).entity("Wrong pass or username.").build();
			}
			
			//creates token
			AuthToken authToken;
			if(isUser) {
				authToken = new AuthToken(data.getUsername(), user.getString("user_tokenID"));
				authToken.setRole(user.getString("user_role"));
			}
			else {
				authToken = new AuthToken(data.getUsername(), user.getString("org_tokenID"));
				authToken.setRole(user.getString("org_role"));
			}
			Key tokenKey = datastore.newKeyFactory()
					.setKind("Token")
					.newKey(authToken.getTokenID());
			Entity token = txn.get(tokenKey);
			if(token != null) {	//already logged in
				txn.rollback();
				LOG.warning(data.getUsername() + " tryed to login while loged in.");
				return Response.status(Status.FORBIDDEN).entity("You are already logged in.").build();
			}
			
			//account disabled
			if(isUser && user.getString("user_state").equals("DISABLED")) { 
				txn.rollback();
				LOG.warning(data.getUsername() + " tryed to login while the account is disabled.");
				return Response.status(Status.FORBIDDEN).entity("Disabled account. Try later.").build();
			}
			else if(!isUser && user.getString("org_state").equals("DISABLED")) { 
				txn.rollback();
				LOG.warning(data.getUsername() + " tryed to login while the account is disabled.");
				return Response.status(Status.FORBIDDEN).entity("Disabled account. Try later.").build();
			}
			
			token = Entity.newBuilder(tokenKey)
					.set("token_id", authToken.getTokenID())
					.set("token_role", authToken.getRole())
					.set("token_username", authToken.getUsername())
					.set("token_start_time", authToken.getCreationData())
					.set("token_end_time", authToken.getExpirationData())
					.build();
			txn.put(token);
			txn.commit();

			LOG.info("'" + data.getUsername() + "' logged in sucessefully.");
			return Response.ok(g.toJson(authToken)).build();
			
		} finally {
			if(txn.isActive()) {
				txn.rollback();
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}
	}
}
