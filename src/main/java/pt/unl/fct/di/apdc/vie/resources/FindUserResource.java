package pt.unl.fct.di.apdc.vie.resources;

import java.util.LinkedList;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.cloud.datastore.*;

import javax.ws.rs.Consumes;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

import javax.ws.rs.Produces;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;

import pt.unl.fct.di.apdc.vie.util.FindUserData;



@Path("/get")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class FindUserResource {

	/**
	 * A Logger Object
	 */

	private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

	private final Gson g = new Gson();

	public FindUserResource() {
	}


	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getUser(FindUserData data) throws EntityNotFoundException {
		Transaction txn = datastore.newTransaction();
		try {

			Key tokenKey = datastore.newKeyFactory().setKind("Token").newKey(data.tokenID);
			Entity logged = txn.get(tokenKey);

			if (logged.getString("token_role").equals("USER") 
					&& logged.getString("token_username").equals(data.username)) {

				return Response.ok().entity(g.toJson(getUser(txn, data.pattern))).build();
			} else {
				txn.rollback();
				return Response.status(Status.FORBIDDEN).entity("You do not have access to this operation.").build();
			}
		} catch (Exception e) {

			txn.rollback();
			return Response.status(Status.FORBIDDEN).entity("Attempt to see users by first name failed.").build();
		} finally {

			if (txn.isActive()) {
				txn.rollback();
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}
	}

	private List<String> getUser(Transaction txn, String role) throws EntityNotFoundException {

		DatastoreService data = DatastoreServiceFactory.getDatastoreService();

		Filter propertyFilter = new FilterPredicate("user_firstName", FilterOperator.EQUAL, role);
		Query q = new Query("User").setFilter(propertyFilter);

		PreparedQuery pq = data.prepare(q);
		List<com.google.appengine.api.datastore.Entity> u = pq.asList(FetchOptions.Builder.withDefaults());
		List<String> l = new LinkedList<String>();
		for (int i = 0; i < u.size(); i++) {
			com.google.appengine.api.datastore.Entity user = data.get(u.get(i).getKey());
			String s = (String) user.getProperty("user_name");
			l.add(s);
		}
		return l;
	}

}
