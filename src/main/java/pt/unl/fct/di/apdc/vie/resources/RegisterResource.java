package pt.unl.fct.di.apdc.vie.resources;


import java.time.LocalDate;
import java.util.UUID;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.codec.digest.DigestUtils;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.cloud.Timestamp;
import com.google.cloud.datastore.*;
//import com.google.gson.Gson;

import pt.unl.fct.di.apdc.vie.util.RegisterData;

@Path("/register")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class RegisterResource {

	private static final Logger LOG = Logger.getLogger(RegisterData.class.getName());

	private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response doRegistration(RegisterData data) throws EntityNotFoundException {
		
		if (data.getFirstName() == "")
			return Response.status(Status.FORBIDDEN).entity("Invalid first name.").build();
		
		if (data.getLastName() == "")
			return Response.status(Status.FORBIDDEN).entity("Invalid last name.").build();
		
		if (data.getUsername() == "")
			return Response.status(Status.FORBIDDEN).entity("Invalid username.").build();

		if (data.getPassword().length() < 9)
			return Response.status(Status.FORBIDDEN).entity("Password too short.").build();

		if (data.getPassword() == "")
			return Response.status(Status.FORBIDDEN).entity("Invalid password.").build();

		if (data.getEmail() == "" || !data.getEmail().contains("@"))
			return Response.status(Status.FORBIDDEN).entity("Invalid email.").build();

		if (!data.getPassword().equals(data.getConfirmation()))
			return Response.status(Status.FORBIDDEN).entity("Password dont match.").build();
		if((LocalDate.now().getYear() - data.getYear()) < 15) {
			return Response.status(Status.FORBIDDEN).entity("You must be over 15 years old.").build();
		}
		Transaction txn = datastore.newTransaction();
		LOG.fine("Attempt to register user: " + data.getUsername());
		Key userKey = datastore.newKeyFactory().setKind("User").newKey(data.getUsername());

		try {
			Entity user = txn.get(userKey);
			if (user != null)
				return Response.status(Status.BAD_REQUEST).entity("User alredy exists").build();
			else {
				LocalDate date = LocalDate.of(data.getYear(), data.getMonth(), data.getDay());
				
				
				user = Entity.newBuilder(userKey)
						.set("user_name", data.getUsername())
						.set("user_firstName", data.getFirstName())
						.set("user_lastName", data.getLastName())
						.set("user_pwd", DigestUtils.sha512Hex(data.getPassword()))
						.set("user_email", data.getEmail())
						.set("user_birthDay", date.toString())
						.set("user_perfil", "Public")
						.set("user_role", "USER")
						.set("user_state", "ENABLE")
						.set("user_creation_time", Timestamp.now())
						.set("user_tokenID", UUID.randomUUID().toString())
						.set("user_following", 0)
						.set("user_followers", 0)
						.build();
				txn.add(user);

				LOG.info("User " + data.getUsername() + "successfully registered.");
				txn.commit();
				return Response.ok("User resgistered").build();
			}
		} finally {

			if (txn.isActive())
				txn.rollback();
		}
	}

}
