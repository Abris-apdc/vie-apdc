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

@Path("/update")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class ModifyResource {

	private static final String USER = "USER";
	private static final String ORG = "ORG";
	
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
			
			if (token.getString("token_role").equals(USER)) {

				Key userKey = datastore.newKeyFactory().setKind("User").newKey(token.getString("token_username"));
				Entity user = datastore.get(userKey);

				if (data.getCP() != null)
					user = Entity.newBuilder(user).set("user_zip_code", data.getCP()).build();

				if (data.getNationality() != null)
					user = Entity.newBuilder(user).set("user_nationality", data.getNationality()).build();

				if (data.getFirstLanguage() != null)
					user = Entity.newBuilder(user).set("user_first_language", data.getFirstLanguage()).build();

				if (data.getSecondLanguage() != null)
					user = Entity.newBuilder(user).set("user_second_language", data.getSecondLanguage()).build();

				if (data.getGender() != null)
					user = Entity.newBuilder(user).set("user_gender", data.getGender()).build();

				if (data.getCountry() != null)
					user = Entity.newBuilder(user).set("user_country", data.getCountry()).build();

				if (data.getDescription() != null)
					user = Entity.newBuilder(user).set("user_description", data.getDescription()).build();

				if (data.getSecondAddress() != null)
					user = Entity.newBuilder(user).set("user_second_address", data.getSecondAddress()).build();

				if (data.getEducationLevel() != null)
					user = Entity.newBuilder(user).set("user_education_level", data.getEducationLevel()).build();

				if (data.getEmail() != null) {
					if (data.getEmail().contains("@"))
						user = Entity.newBuilder(user).set("user_email", data.getEmail()).build();
					else {
						txn.rollback();
						return Response.status(Status.FORBIDDEN).entity("Insert valid email.").build();
					}
				}

				if (data.getLandLine() != null) {
					if (data.getLandLine().length() == 9)
						user = Entity.newBuilder(user).set("user_landline", data.getLandLine()).build();
					else {
						txn.rollback();
						return Response.status(Status.FORBIDDEN).entity("Insert valid landline number.").build();
					}
				}

				if (data.getPhoneNumber() != null) {
					if (data.getPhoneNumber().length() == 9)

						user = Entity.newBuilder(user).set("user_phone", data.getPhoneNumber()).build();
					else {
						txn.rollback();
						return Response.status(Status.FORBIDDEN).entity("Insert valid phone number.").build();
					}
				}

				if (data.getAddress() != null)
					user = Entity.newBuilder(user).set("user_address", data.getAddress()).build();

				if (data.getSecondAddress() != null)
					user = Entity.newBuilder(user).set("user_secondAddress", data.getAddress()).build();

				if (data.getCity() != null)
					user = Entity.newBuilder(user).set("user_city", data.getCity()).build();

				if (data.getPerfil() != null) {
					if (data.getPerfil() != "Publico" || data.getPerfil() != "Privado") {
						txn.rollback();
						return Response.status(Status.FORBIDDEN).entity("Not a type of perfil.").build();
					}
					else
						user = Entity.newBuilder(user).set("user_perfil", data.getPerfil()).build();
				}

				if (data.getNewPass() != null && data.getNewPass().equals(data.getNewConfirmation())) {
					if (data.getNewPass().length() < 9) {
						txn.rollback();
						return Response.status(Status.FORBIDDEN).entity("Password too short.").build();
					}
					else if (user.getString("user_pwd")
									.equals(DigestUtils.sha512Hex(data.getPassword()).toString()))
						user = Entity.newBuilder(user).set("user_pwd", DigestUtils.sha512Hex(data.getNewPass()))
						.build();

					else {
						txn.rollback();
						return Response.status(Status.FORBIDDEN).entity("Password dont match").build();
					}

				}
				txn.update(user);
				txn.commit();
				return Response.ok(g.toJson("User modified.")).build();
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
	@Path("/organisation")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response modify(ModifyOrgData data) {

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

				if (data.getCP() != null)
					user = Entity.newBuilder(user).set("org_zip_code", data.getCP()).build();

				if (data.getCountry() != null)
					user = Entity.newBuilder(user).set("org_country", data.getCountry()).build();

				if (data.getDescription() != null)
					user = Entity.newBuilder(user).set("org_description", data.getDescription()).build();

				if (data.getPhoneNumber() != null) {
					if (data.getPhoneNumber().length() == 9)

						user = Entity.newBuilder(user).set("org_phone", data.getPhoneNumber()).build();
					else {
						txn.rollback();
						return Response.status(Status.FORBIDDEN).entity("Insert valid phone number.").build();
					}
				}
				
				if (data.getMobilePhone() != null) {
					if (data.getMobilePhone().length() == 9)

						user = Entity.newBuilder(user).set("org_phone", data.getMobilePhone()).build();
					else {
						txn.rollback();
						return Response.status(Status.FORBIDDEN).entity("Insert valid phone number.").build();
					}
				}

				if (data.getAddress() != null)
					user = Entity.newBuilder(user).set("org_address", data.getAddress()).build();

				if (data.getSecondName() != null)
					user = Entity.newBuilder(user).set("org_second_name", data.getSecondName()).build();

				if (data.getCity() != null)
					user = Entity.newBuilder(user).set("org_city", data.getCity()).build();
				
				if (data.getCardID() != null)
					user = Entity.newBuilder(user).set("org_cardID", data.getCardID()).build();
				
				if (data.getOwner() != null)
					user = Entity.newBuilder(user).set("org_owner", data.getOwner()).build();
				
				if (data.getServiceType() != null)
					user = Entity.newBuilder(user).set("org_serviceType", data.getServiceType()).build();
				
				if (data.getName() != null)
					user = Entity.newBuilder(user).set("org_name", data.getName()).build();

				if (data.getNewPass() != null && data.getNewPass().equals(data.getNewConfirmation())) {
					if (data.getNewPass().length() < 9) {
						txn.rollback();
						return Response.status(Status.FORBIDDEN).entity("Password too short.").build();
					}
					else if (user.getString("org_pwd")
									.equals(DigestUtils.sha512Hex(data.getPassword()).toString()))
						user = Entity.newBuilder(user).set("org_pwd", DigestUtils.sha512Hex(data.getNewPass()))
						.build();

					else {
						txn.rollback();
						return Response.status(Status.FORBIDDEN).entity("Password dont match").build();
					}

				}
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
}
