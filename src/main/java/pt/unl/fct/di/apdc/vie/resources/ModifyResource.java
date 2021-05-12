package pt.unl.fct.di.apdc.vie.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.codec.digest.DigestUtils;

import com.google.cloud.datastore.*;
//import com.google.gson.Gson;

import pt.unl.fct.di.apdc.vie.util.ModifyData;

@Path("/update")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class ModifyResource {

	private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

	private static final String USER = "USER";

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response modify(ModifyData data) {

		Transaction txn = datastore.newTransaction();
		Key tokenKey = datastore.newKeyFactory().setKind("Token").newKey(data.getTokenID());

		try {
			Entity token = datastore.get(tokenKey);
			if (token.getString("token_role").equals(USER)) {

				try {
					Key userKey = datastore.newKeyFactory().setKind("User").newKey(token.getString("token_username"));
					Entity user = datastore.get(userKey);

					if (data.getCP() != null && !data.getCP().equals(""))
						user = Entity.newBuilder(user).set("user_zip_code", data.getCP()).build();

					if (data.getNationality() != null && !data.getNationality().equals(""))
						user = Entity.newBuilder(user).set("user_nationality", data.getNationality()).build();

					if (data.getFirstLanguage() != null && !data.getFirstLanguage().equals(""))
						user = Entity.newBuilder(user).set("user_first_language", data.getFirstLanguage()).build();

					if (data.getSecondLanguage() != null && !data.getSecondLanguage().equals(""))
						user = Entity.newBuilder(user).set("user_second_language", data.getSecondLanguage()).build();

					if (data.getGender() != null && !data.getGender().equals(""))
						user = Entity.newBuilder(user).set("user_gender", data.getGender()).build();

					if (data.getCountry() != null && !data.getCountry().equals(""))
						user = Entity.newBuilder(user).set("user_country", data.getCountry()).build();

					if (data.getDescription() != null && !data.getDescription().equals(""))
						user = Entity.newBuilder(user).set("user_description", data.getDescription()).build();

					if (data.getSecondAdress() != null && !data.getSecondAdress().equals(""))
						user = Entity.newBuilder(user).set("user_second_adress", data.getSecondAdress()).build();

					if (data.getEducationLevel() != null && !data.getEducationLevel().equals(""))
						user = Entity.newBuilder(user).set("user_education_level", data.getEducationLevel()).build();

					if (data.getEmail() != null && !data.getEmail().equals(""))
						if (data.getEmail().contains("@"))
							user = Entity.newBuilder(user).set("user_email", data.getEmail()).build();
						else
							return Response.status(Status.FORBIDDEN).entity("Insert valid email.").build();

					if (data.getLandLine() != null && !data.getLandLine().equals(""))
						if (data.getLandLine().length() == 9)
							user = Entity.newBuilder(user).set("user_landline", data.getLandLine()).build();
						else
							return Response.status(Status.FORBIDDEN).entity("Insert valid landline number.").build();

					if (data.getPhoneNumber() != null && !data.getPhoneNumber().equals(""))
						if (data.getPhoneNumber().length() == 9)

							user = Entity.newBuilder(user).set("user_phone", data.getPhoneNumber()).build();
						else
							return Response.status(Status.FORBIDDEN).entity("Insert valid phone number.").build();

					if (data.getAdress() != null && !data.getAdress().equals(""))
						user = Entity.newBuilder(user).set("user_adress", data.getAdress()).build();

					if (data.getAdress() != null && !data.getAdress().equals(""))
						user = Entity.newBuilder(user).set("user_secondAdress", data.getAdress()).build();

					if (data.getCity() != null && !data.getCity().equals(""))
						user = Entity.newBuilder(user).set("user_city", data.getCity()).build();

					if (data.getPerfil() != null && !data.getPerfil().equals(""))
						if (data.getPerfil() != "Publico" || data.getPerfil() != "Privado")
							return Response.status(Status.FORBIDDEN).entity("Not a type of perfil.").build();
						else
							user = Entity.newBuilder(user).set("user_perfil", data.getPerfil()).build();

					if (data.getNewPass() != null && !data.getNewPass().equals("")
							&& data.getNewPass().equals(data.getNewConfirmation())) {
						if (data.getNewPass().length() < 9)
							return Response.status(Status.FORBIDDEN).entity("Password too short.").build();
						else if (data.getPassword().equals(data.getConfirmation()) && user.getString("user_pwd")
								.equals(DigestUtils.sha512Hex(data.getPassword()).toString()))
							user = Entity.newBuilder(user).set("user_pwd", DigestUtils.sha512Hex(data.getNewPass()))
									.build();

						else
							return Response.status(Status.FORBIDDEN).entity("Password dont match").build();

					}
					txn.update(user);
					txn.commit();
					return Response.ok().build();
				} catch (Exception e) {
					return Response.status(Status.CONFLICT).build();
				}
			}
			return Response.status(Status.NOT_FOUND).build();
		} finally {
			if (txn.isActive())
				txn.rollback();
		}
	}
}
