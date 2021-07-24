package pt.unl.fct.di.apdc.vie.resources;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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
		Key userKey = datastore.newKeyFactory().setKind("Account").newKey(data.getUsername());

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
				//followers.add( StringValue.of("test"));
				List<Value<String>> following = new ArrayList<Value<String>>();
				List<Value<String>> requests = new LinkedList<Value<String>>();
				List<Value<String>> events = new ArrayList<Value<String>>();
				//following.add(StringValue.of("test"));
				
				List<Value<String>> routes = new ArrayList<Value<String>>();
				
				user = Entity.newBuilder(userKey)						
						.set("account_name", data.getUsername())
						.set("account_firstName", data.getFirstName())
						.set("account_lastName", data.getLastName())
						.set("account_pwd", DigestUtils.sha512Hex(data.getPassword()))
						.set("account_email", data.getEmail())
						.set("account_birthDay", date.toString())
						.set("account_perfil", "Public")
						//.set("account_owner", "")
						//.set("account_cardID", "")
						.set("account_address", "")
						.set("account_nationality", "")
						.set("account_role", "USER")
						.set("account_state", "ENABLE")
						.set("account_creation_time", Timestamp.now())
						.set("account_gender", "")
						.set("account_phone", "")
						//.set("account_service", "")
						.set("account_following_list", following)
						.set("account_following", 0)
						.set("account_followers_list", followers)
						.set("account_followers", 0)
						.set("account_requests_list", requests)
						.set("account_routes_list", routes)
						.set("account_events_list", events)
						.set("account_warnings", 0)
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
		
		/*if (data.getName() == "")
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
		*/
		Transaction txn = datastore.newTransaction();
		LOG.fine("Attempt to register organisation: " + data.getName());
		Key orgKey = datastore.newKeyFactory().setKind("Account").newKey(data.getName());

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
				List<Value<String>> requests = new LinkedList<Value<String>>();
				List<Value<String>> routes = new ArrayList<Value<String>>();
				List<Value<String>> events = new ArrayList<Value<String>>();

				routes.add(StringValue.of("test"));
				
				
				org = Entity.newBuilder(orgKey)						
						.set("account_name", data.getName())
						.set("account_perfil", "Public")
						//.set("org_second_name", data.getSecondName())
						.set("account_owner", data.getOwner())
						.set("account_cardID", data.getid())
						.set("account_address", data.getAddress())
						.set("account_country", "")
						//.set("account_firstName", "")
						//.set("account_lastName", "")
						//.set("account_birthDay", "")
						.set("account_pwd", DigestUtils.sha512Hex(data.getPassword()))
						.set("account_email", data.getEmail())
						//.set("account_gender", "")
						.set("account_phone", "")
						.set("account_service", data.getServiceType())
						.set("account_role", "ORG")
						.set("account_state", "ENABLE")
						.set("account_creation_time", Timestamp.now())
						.set("account_following_list", follows)
						.set("account_following", 0)
						.set("account_followers_list", followers)
						.set("account_requests_list", requests)
						.set("account_routes_list", routes)
						.set("account_events_list", events)
						.set("account_followers", 0)
						.set("account_warnings", 0)
						//.set("org_tokenID", UUID.randomUUID().toString())
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
