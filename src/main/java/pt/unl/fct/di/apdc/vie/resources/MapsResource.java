package pt.unl.fct.di.apdc.vie.resources;

import java.time.LocalDate;
import java.util.ArrayList;
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

import com.google.cloud.Timestamp;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.StringValue;
import com.google.cloud.datastore.Transaction;
import com.google.cloud.datastore.Value;
import com.google.gson.Gson;

import pt.unl.fct.di.apdc.vie.util.LocationData;

@Path("/map")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class MapsResource {
	
	private static final Logger LOG = Logger.getLogger(ProfileResource.class.getName());
	private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
	private final Gson g = new Gson();
	
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response addLocation(LocationData data) {
		Transaction txn = datastore.newTransaction();
		Key localKey = datastore.newKeyFactory().setKind("Location").newKey(data.getCoordinates());

		try {
			Key tokenKey = datastore.newKeyFactory().setKind("Token").newKey(data.getTokenID());
			
			Entity logged = txn.get(tokenKey);
			long end = logged.getLong("token_end_time");
			
			//o token expirou
			if(end <  System.currentTimeMillis()) {
				txn.delete(tokenKey);
				txn.commit();
				return Response.status(Status.FORBIDDEN).entity("Token expired.").build();
			}
			Entity local = txn.get(localKey);
			if (local != null) {
				//ja existe um user com o mesmo username
				txn.rollback();
				return Response.status(Status.FORBIDDEN).entity("Location alredy exists.").build();
			}
			else {
				
				local = Entity.newBuilder(localKey)
						.set("local_address", data.getAddress())
						.set("local_owner", data.getOwner())
						.set("local_info", data.getInfo())
						.set("local_schedule", data.getSchedule())
						.build();
				txn.add(local);

				LOG.info("Location " + data.getAddress() + "successfully added.");
				txn.commit();
				return Response.ok(g.toJson("Location addded.")).build();
			}
		} finally {

			if (txn.isActive()) {
				txn.rollback();
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}
	}

}
