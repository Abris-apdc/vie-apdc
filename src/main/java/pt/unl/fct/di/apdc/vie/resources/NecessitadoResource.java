package pt.unl.fct.di.apdc.vie.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.codec.digest.DigestUtils;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.Transaction;
import com.google.gson.Gson;

import pt.unl.fct.di.apdc.vie.util.FollowData;
import pt.unl.fct.di.apdc.vie.util.NecessitadoInfoData;
import pt.unl.fct.di.apdc.vie.util.NecessitadoRegisterData;

@Path("/necessitado")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class NecessitadoResource {

	
	private static final String SU = "SU";
	private static final String ADMIN = "ADMIN";
	
	private static final String PEND = "PEND";
	private static final String OK = "OK";
	
	private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
	private final Gson g = new Gson();

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response doRegistration(NecessitadoRegisterData data){
		
		Transaction txn = datastore.newTransaction();

		try {
			
			Key userKey = datastore.newKeyFactory().setKind("Necessitado").newKey(data.getNrCartao());
			Entity user = txn.get(userKey);
			if (user != null) {
				//ja existe uma pessoa com o mesmo nr de cartao
				txn.rollback();
				return Response.status(Status.FORBIDDEN).entity("Number alredy exists.").build();
			}
			user = Entity.newBuilder(userKey)
					.set("necessitado_first_name", data.getFirstName())
					.set("necessitado_last_name", data.getLastName())
					.set("necessitado_nr_cartao", data.getNrCartao())
					.set("necessitado_pass", DigestUtils.sha512Hex(data.getPassword()))
					.set("necessitado_descricao", data.getDescricao())
					.set("necessitado_local", data.getLocal())
					.set("necessitado_email", data.getEmail())
					.set("necessitado_phone", data.getPhone())
					.set("necessitado_state", "PEND")
					.build();
			
			txn.add(user);
			txn.commit();
			return Response.ok(g.toJson("Request send.")).build();
		} finally {

			if (txn.isActive()) {
				txn.rollback();
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}
	}
	
	@GET
	@Path("/{number}")
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getNecessitado(@PathParam("number") String number){
		Transaction txn = datastore.newTransaction();
		try {
			
			Key accountKey = datastore.newKeyFactory().setKind("Necessitado").newKey(number);
			Entity account = txn.get(accountKey);
			if(account == null){
				txn.rollback();
				return Response.status(Status.NOT_FOUND).entity("Account doesn't exist.").build();
			}
			NecessitadoInfoData info = new NecessitadoInfoData(
					account.getString("necessitado_first_name"),
					account.getString("necessitado_last_name"),
					account.getString("necessitado_nr_cartao"),
					account.getString("necessitado_descricao"),
					account.getString("necessitado_local"),
					account.getString("necessitado_email"),
					account.getString("necessitado_phone"),
					account.getString("necessitado_state")
					);
			txn.commit();
			return Response.ok(g.toJson(info)).build();
			
		} finally {
			if(txn.isActive()) {
				txn.rollback();
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}
	}
	
	@DELETE
	@Path("/delete/{number}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response delete(@PathParam("number") String number, FollowData data){
		Transaction txn = datastore.newTransaction();
		try {
			Key tokenKey = datastore.newKeyFactory().setKind("Token").newKey(data.getTokenID());
			
			Entity token = txn.get(tokenKey);
			
			if(token == null) {
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
			
			Key accountKey = datastore.newKeyFactory().setKind("Account").newKey(token.getString("token_username"));
			
			Entity account = txn.get(accountKey);
			if(!account.getString("account_role").equals(ADMIN)
					&& !account.getString("account_role").equals(SU)) {
				txn.rollback();
				return Response.status(Status.FORBIDDEN).entity("You don't have those permissions.").build();
			}
			
			//e admin ou su
			
			Key necessitadoKey = datastore.newKeyFactory().setKind("Necessitado").newKey(number);
			//Entity necessitado = txn.get(necessitadoKey);
			
			txn.delete(necessitadoKey);
			txn.commit();
			return Response.ok("Deleted.").build();
			
		} finally {
			if(txn.isActive()) {
				txn.rollback();
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}
	}
	
	@PUT
	@Path("/update/{number}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response update(@PathParam("number") String number, FollowData data){
		Transaction txn = datastore.newTransaction();
		try {
			Key tokenKey = datastore.newKeyFactory().setKind("Token").newKey(data.getTokenID());

			Entity token = txn.get(tokenKey);

			if(token == null) {
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
			
			Key accountKey = datastore.newKeyFactory().setKind("Account").newKey(token.getString("token_username"));
			
			Entity account = txn.get(accountKey);
			if(!account.getString("account_role").equals(ADMIN)
					&& !account.getString("account_role").equals(SU)) {
				txn.rollback();
				return Response.status(Status.FORBIDDEN).entity("You don't have those permissions.").build();
			}
			
			//e admin ou su
			
			Key necessitadoKey = datastore.newKeyFactory().setKind("Necessitado").newKey(number);
			Entity necessitado = txn.get(necessitadoKey);
			
			necessitado = Entity.newBuilder(necessitado).set("necessitado_state", "OK").build();
			
			txn.update(necessitado);
			txn.commit();
			return Response.ok("Updated.").build();
		} finally {
			if(txn.isActive()) {
				txn.rollback();
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}
	}
	
	@GET
	@Path("/list")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response list(){
		Transaction txn = datastore.newTransaction();
		try {
			
			Query<Entity> accountsQuery = Query.newEntityQueryBuilder()
					.setKind("Necessitado")
					.build();
			QueryResults<Entity> accountsResults = txn.run(accountsQuery);
			
			List<String> allResults = new ArrayList<>();
			
			accountsResults.forEachRemaining(e -> {allResults.add(e.getString("necessitado_nr_cartao"));});

			txn.commit();
			return Response.ok(g.toJson(allResults)).build();
		} finally {
			if(txn.isActive()) {
				txn.rollback();
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}
	}
	
	@GET
	@Path("/listPend")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response listPend(){
		Transaction txn = datastore.newTransaction();
		try {
			
			Query<Entity> accountsQuery = Query.newEntityQueryBuilder()
					.setKind("Necessitado")
					.build();
			QueryResults<Entity> accountsResults = txn.run(accountsQuery);
			
			List<String> allResults = new ArrayList<>();
			
			accountsResults.forEachRemaining(e -> {if(e.getString("necessitado_state").equals(PEND))allResults.add(e.getString("necessitado_nr_cartao"));});

			txn.commit();
			return Response.ok(g.toJson(allResults)).build();
		} finally {
			if(txn.isActive()) {
				txn.rollback();
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}
	}
	
	@GET
	@Path("/listOK")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response listOK(){
		Transaction txn = datastore.newTransaction();
		try {
			
			Query<Entity> accountsQuery = Query.newEntityQueryBuilder()
					.setKind("Necessitado")
					.build();
			QueryResults<Entity> accountsResults = txn.run(accountsQuery);
			
			List<String> allResults = new ArrayList<>();
			
			accountsResults.forEachRemaining(e -> {if(e.getString("necessitado_state").equals(OK))allResults.add(e.getString("necessitado_nr_cartao"));});

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
