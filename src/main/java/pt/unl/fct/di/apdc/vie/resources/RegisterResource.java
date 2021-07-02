package pt.unl.fct.di.apdc.vie.resources;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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
import com.google.gson.Gson;

import pt.unl.fct.di.apdc.vie.util.OrgRegisterData;
import pt.unl.fct.di.apdc.vie.util.UserRegisterData;

@Path("/register")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class RegisterResource {

	private static final Logger LOG = Logger.getLogger(RegisterResource.class.getName());
	private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
	private final Gson g = new Gson();

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response doRegistration(UserRegisterData data) throws EntityNotFoundException {
		LOG.fine("Attempt to register user: " + data.getUsername());		
		Transaction txn = datastore.newTransaction();
		Key userKey = datastore.newKeyFactory().setKind("User").newKey(data.getUsername());

		try {
			Entity user = txn.get(userKey);
			if (user != null) {
				//ja existe um user com o mesmo username
				txn.rollback();
				return Response.status(Status.FORBIDDEN).entity("User alredy exists.").build();
			}
			else {
				LocalDate date = LocalDate.of(Integer.parseInt(data.getYear()), Integer.parseInt(data.getMonth()), Integer.parseInt(data.getDay()));
				List<Value<String>> followers = new ArrayList<Value<String>>();
				followers.add( StringValue.of("test"));
				List<Value<String>> following = new ArrayList<Value<String>>();
				following.add(StringValue.of("test"));
				
				List<Value<String>> routes = new ArrayList<Value<String>>();
				routes.add(StringValue.of("test"));
				
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
						//.set("user_tokenID", UUID.randomUUID().toString())
						.set("user_following_list", following)
						.set("user_following", 0)
						.set("user_followers_list", followers)
						.set("user_followers", 0)
						.set("user_routes_list", routes)
						.build();
				txn.add(user);

				LOG.info("User " + data.getUsername() + "successfully registered.");
				txn.commit();
				return Response.ok(g.toJson("User resgistered.")).build();
			}
		} finally {

			if (txn.isActive()) {
				txn.rollback();
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}
	}
	
	@POST
	@Path("/organisation")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response doOrganisationRegistration(OrgRegisterData data) throws EntityNotFoundException {
		
		if (data.getName() == "")
			return Response.status(Status.FORBIDDEN).entity("Invalid organisation name.").build();
		
		//if (data.getInfo() == "")
			//return Response.status(Status.FORBIDDEN).entity("Invalid organisation info.").build();
		
		//if(data.getSecondName() == "")
			//return Response.status(Status.FORBIDDEN).entity("Invalid organisation second name.").build();
		
		if (data.getOwner() == "")
			return Response.status(Status.FORBIDDEN).entity("Invalid owner name.").build();
		
		//if (data.getId() == "")
		//	return Response.status(Status.FORBIDDEN).entity("Invalid ID.").build();

		if (data.getPassword().length() < 9)
			return Response.status(Status.FORBIDDEN).entity("Password too short.").build();

		if (data.getPassword() == "")
			return Response.status(Status.FORBIDDEN).entity("Invalid password.").build();

		if (data.getEmail() == "" || !data.getEmail().contains("@"))
			return Response.status(Status.FORBIDDEN).entity("Invalid email.").build();

		//if (!data.getPassword().equals(data.getConfirmation()))
			//return Response.status(Status.FORBIDDEN).entity("Password dont match.").build();
		
		if(data.getAddress() == "") 
			return Response.status(Status.FORBIDDEN).entity("Invalid address.").build();
		
		//if(data.getCP() == "") 
			//return Response.status(Status.FORBIDDEN).entity("Invalid CP.").build();
		
		//if(data.getLocation() == "") 
			//return Response.status(Status.FORBIDDEN).entity("Invalid location.").build();
		
		//if(data.getCountry() == "") 
			//return Response.status(Status.FORBIDDEN).entity("Invalid country.").build();
		
		//if(data.getPhone() == "" && data.getPhone().length() < 9) 
			//return Response.status(Status.FORBIDDEN).entity("Invalid phone.").build();
		
		//if(data.getMobile() == "" && data.getMobile().length() < 9) 
		//	return Response.status(Status.FORBIDDEN).entity("Invalid mobile.").build();
		
		if(data.getServiceType() == "")
			return Response.status(Status.FORBIDDEN).entity("Invalid service.").build();
		
		Transaction txn = datastore.newTransaction();
		LOG.fine("Attempt to register organisation: " + data.getName());
		Key orgKey = datastore.newKeyFactory().setKind("Organisation").newKey(data.getEmail());

		try {
			Entity org = txn.get(orgKey);
			if (org != null) {
				//ja existe uma org com o mesmo username (mail)
				txn.rollback();
				return Response.status(Status.FORBIDDEN).entity("Organization alredy exists.").build();
			}
			else {
				List<Value<String>> followers = new LinkedList<Value<String>>();
				List<Value<String>> follows = new LinkedList<Value<String>>();
				List<Value<String>> routes = new ArrayList<Value<String>>();
				routes.add(StringValue.of("test"));
				
				
				org = Entity.newBuilder(orgKey)
						.set("org_name", data.getName())
						//.set("org_info", data.getInfo())
						//.set("org_second_name", data.getSecondName())
						.set("org_owner", data.getOwner())
						.set("org_cardID", data.getid())
						.set("org_address", data.getAddress())
						//.set("org_cp", data.getCP())
						//.set("org_location", data.getLocation())
						//.set("org_country", data.getCountry())
						//.set("org_phone", data.getPhone())
						//.set("org_mobile", data.getMobile())
						.set("org_pwd", DigestUtils.sha512Hex(data.getPassword()))
						.set("org_email", data.getEmail())
						.set("org_service", data.getServiceType())
						.set("org_role", "ORG")
						.set("org_state", "ENABLE")
						.set("org_creation_time", Timestamp.now())
						.set("org_following_list", ListValue.of(follows))
						.set("org_following", 0)
						.set("org_followers_list", ListValue.of(followers))
						.set("org_routes_list", routes)
						.set("org_followers", 0)
						.set("org_tokenID", UUID.randomUUID().toString())
						.build();
				txn.add(org);

				LOG.info("Organisation " + data.getName() + "successfully registered.");
				txn.commit();
				return Response.ok(g.toJson("Organisation resgistered.")).build();
			}
		} finally {

			if (txn.isActive()) {
				txn.rollback();
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}
	}

}
