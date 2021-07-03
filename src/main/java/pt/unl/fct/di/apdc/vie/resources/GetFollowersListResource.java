package pt.unl.fct.di.apdc.vie.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.Transaction;
import com.google.cloud.datastore.Value;
import com.google.gson.Gson;

@Path("/getFollowers")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class GetFollowersListResource {
	
	private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
	private final Gson g = new Gson();
	
	public GetFollowersListResource() {
	}
	
	@Path("/{username}")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getFollowers(@PathParam("username") String username)  {
		Transaction txn = datastore.newTransaction();
		try {
			Key accountKey = datastore.newKeyFactory().setKind("Account").newKey(username);
			Entity account = txn.get(accountKey);
			
			if(account == null){
				txn.rollback();
				return Response.status(Status.NOT_FOUND).entity("Account doesn't exist.").build();
			}
			
			if(account.getString("account_perfil").equals("Private")) {
				txn.rollback();
				return Response.status(Status.FORBIDDEN).entity("Account is Private.").build();
			}
			
			List<Value<String>> followers = account.getList("account_followers_list");
			List<String> allResults = new ArrayList<>();
			
			for(int i = 0; i<followers.size();i++) {
				allResults.add(followers.get(i).get());
			}
			txn.commit();
			return Response.ok(g.toJson(allResults)).build();
			
		} finally {
			if(txn.isActive()) {
				txn.rollback();
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}
	}
}
