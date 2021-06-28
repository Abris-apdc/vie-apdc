package pt.unl.fct.di.apdc.vie.resources;

import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.codec.digest.DigestUtils;

import com.google.cloud.datastore.*;
import com.google.gson.Gson;

import pt.unl.fct.di.apdc.vie.util.ModifyData;
import pt.unl.fct.di.apdc.vie.util.ModifyOrgData;
import pt.unl.fct.di.apdc.vie.util.ModifyPassData;

@Path("/update")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class ModifyResource {

	private static final String USER = "USER";
	private static final String ORG = "ORG";
	private static final String ADMIN = "ADMIN";
	private static final String SU = "SU";
	private static final String MOD = "MOD";
	
	private static final Logger LOG = Logger.getLogger(ModifyResource.class.getName());
	private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
	private final Gson g = new Gson();
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response modify(ModifyData data) {

		Transaction txn = datastore.newTransaction();
		Key tokenKey = datastore.newKeyFactory().setKind("Token").newKey(data.getTokenID());

		try {
			
			Entity token = datastore.get(tokenKey);
			
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
			
			if (token.getString("token_role").equals(USER) 
					|| token.getString("token_role").equals(ADMIN) 
					|| token.getString("token_role").equals(SU)
					|| token.getString("token_role").equals(MOD)) {

				Key userKey = datastore.newKeyFactory().setKind("User").newKey(token.getString("token_username"));
				Entity user = datastore.get(userKey);
				if(data.getFirstName() != null && !data.getFirstName().equals(""))
					user = Entity.newBuilder(user).set("user_firstName", data.getFirstName()).build();
				if(data.getLastName() != null && !data.getLastName().equals(""))
					user = Entity.newBuilder(user).set("user_lastName", data.getLastName()).build();
				if (data.getEmail() != null && !data.getEmail().equals(""))
					user = Entity.newBuilder(user).set("user_email", data.getEmail()).build();
				if (data.getGender() != null && !data.getGender().equals(""))
					user = Entity.newBuilder(user).set("user_gender", data.getGender()).build();
				if (data.getPhoneNumber() != null && !data.getPhoneNumber().equals(""))
					user = Entity.newBuilder(user).set("user_phone", data.getPhoneNumber()).build();
				if (data.getAddress() != null && !data.getAddress().equals(""))
					user = Entity.newBuilder(user).set("user_address", data.getAddress()).build();
				if (data.getNationality() != null && !data.getNationality().equals(""))
					user = Entity.newBuilder(user).set("user_nationality", data.getNationality()).build();
				if (data.getFirstLanguage() != null && !data.getFirstLanguage().equals(""))
					user = Entity.newBuilder(user).set("user_first_language", data.getFirstLanguage()).build();
				if (data.getDescription() != null && !data.getDescription().equals(""))
					user = Entity.newBuilder(user).set("user_description", data.getDescription()).build();
				if (data.getPerfil() != null && !data.getPerfil().equals("")) 
					user = Entity.newBuilder(user).set("user_perfil", data.getPerfil()).build();
				txn.update(user);
				txn.commit();
				return Response.ok(g.toJson("User modified.")).build();
			}
			else {
				txn.rollback();
				return Response.status(Status.FORBIDDEN).entity("You don't have access to this operation. If you are an organisation user /organisation after.").build();
			}
		} finally {
			if (txn.isActive()) {
				txn.rollback();
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}
	}
	
	@PUT
	@Path("/organisation")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response modifyOrg(ModifyOrgData data) {

		Transaction txn = datastore.newTransaction();
		Key tokenKey = datastore.newKeyFactory().setKind("Token").newKey(data.getTokenID());

		try {
			
			Entity token = datastore.get(tokenKey);
			
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
			
			if (token.getString("token_role").equals(ORG)) {

				Key userKey = datastore.newKeyFactory().setKind("Organisation").newKey(token.getString("token_username"));
				Entity user = datastore.get(userKey);

				if (data.getCP() != null && !data.getCP().equals(""))
					user = Entity.newBuilder(user).set("org_zip_code", data.getCP()).build();

				if (data.getCountry() != null && !data.getCountry().equals(""))
					user = Entity.newBuilder(user).set("org_country", data.getCountry()).build();

				if (data.getDescription() != null && !data.getDescription().equals(""))
					user = Entity.newBuilder(user).set("org_description", data.getDescription()).build();

				if (data.getPhoneNumber() != null && !data.getPhoneNumber().equals("")) {
					if (data.getPhoneNumber().length() == 9)

						user = Entity.newBuilder(user).set("org_phone", data.getPhoneNumber()).build();
					else {
						txn.rollback();
						return Response.status(Status.FORBIDDEN).entity("Insert valid phone number.").build();
					}
				}
				
				if (data.getMobilePhone() != null && !data.getMobilePhone().equals("")) {
					if (data.getMobilePhone().length() == 9)

						user = Entity.newBuilder(user).set("org_phone", data.getMobilePhone()).build();
					else {
						txn.rollback();
						return Response.status(Status.FORBIDDEN).entity("Insert valid phone number.").build();
					}
				}

				if (data.getAddress() != null && !data.getAddress().equals(""))
					user = Entity.newBuilder(user).set("org_address", data.getAddress()).build();

				if (data.getSecondName() != null && !data.getSecondName().equals(""))
					user = Entity.newBuilder(user).set("org_second_name", data.getSecondName()).build();

				if (data.getCity() != null && !data.getCity().equals(""))
					user = Entity.newBuilder(user).set("org_city", data.getCity()).build();
				
				if (data.getCardID() != null && !data.getCardID().equals(""))
					user = Entity.newBuilder(user).set("org_cardID", data.getCardID()).build();
				
				if (data.getOwner() != null && !data.getOwner().equals(""))
					user = Entity.newBuilder(user).set("org_owner", data.getOwner()).build();
				
				if (data.getServiceType() != null && !data.getServiceType().equals(""))
					user = Entity.newBuilder(user).set("org_serviceType", data.getServiceType()).build();
				
				if (data.getName() != null && !data.getName().equals(""))
					user = Entity.newBuilder(user).set("org_name", data.getName()).build();

				txn.update(user);
				txn.commit();
				return Response.ok(g.toJson("Organisation modified.")).build();
			}
			else {
				txn.rollback();
				return Response.status(Status.FORBIDDEN).entity("You don't have access to this operation.").build();
			}
		} finally {
			if (txn.isActive()) {
				txn.rollback();
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}
	}
	
	@PUT
	@Path("/pass")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response modifyPass(ModifyPassData data) {


		Transaction txn = datastore.newTransaction();
		Key tokenKey = datastore.newKeyFactory().setKind("Token").newKey(data.getTokenID());

		try {

			Entity token = datastore.get(tokenKey);

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

			String role;
			Key userKey;
			if (token.getString("token_role").equals(USER)) {
				role = "user";
				userKey = datastore.newKeyFactory().setKind("User").newKey(token.getString("token_username"));

			}
			else if (token.getString("token_role").equals(ORG)) {
				role = "org";
				userKey = datastore.newKeyFactory().setKind("Organisation").newKey(token.getString("token_username"));

			}
			else {
				txn.rollback();
				return Response.status(Status.FORBIDDEN).entity("You don't have access to this operation.").build();
			}
			Entity user = datastore.get(userKey);
			
			String hashedPWD =(String) user.getString(role + "_pwd");

			if(!hashedPWD.equals(DigestUtils.sha512Hex(data.getOldPass())))  {
			//if(!data.getOldPassword().equals(user.getString(role + "_pwd"))) {
				txn.rollback();
				return Response.status(Status.FORBIDDEN).entity("Incorrect password.").build();
			}
			/*if(!data.getNewPass().equals(data.getConfirmation())) {
				txn.rollback();
				return Response.status(Status.FORBIDDEN).entity("Passwords dont match.").build();
			}*/
			if(data.getNewPass().length() < 9) {
				txn.rollback();
				return Response.status(Status.FORBIDDEN).entity("Invalid password.").build();
			}
			
			String newHashed = (String) DigestUtils.sha512Hex(data.getNewPass());
			user = Entity.newBuilder(user).set(role + "_pwd", newHashed).build();

			txn.update(user);
			txn.commit();
			return Response.ok(g.toJson("User modified.")).build();

		} finally {
			if (txn.isActive()) {
				txn.rollback();
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}

	}
}
