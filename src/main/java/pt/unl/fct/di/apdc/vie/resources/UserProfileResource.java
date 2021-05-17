package pt.unl.fct.di.apdc.vie.resources;

import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Transaction;
import com.google.gson.Gson;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;

import pt.unl.fct.di.apdc.vie.util.FollowData;
import pt.unl.fct.di.apdc.vie.util.UserInfoData;

@Path("/profile")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class UserProfileResource {
	
	private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
	private final Gson g = new Gson();
	
	@GET
    @Path("/{username}")
    //@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
    public Response getRole(@PathParam("username") String username) throws EntityNotFoundException {
        Transaction txn = datastore.newTransaction();
        try {

            Key userKey = datastore.newKeyFactory().setKind("User").newKey(username);
            Entity logged = txn.get(userKey);

            if ((logged.getString("user_role").equals("USER"))
                    && logged.getString("user_name").equals(username)) {
                    UserInfoData ui = new UserInfoData(
                    		logged.getString("user_firstName"),
                    		logged.getString("user_lastName"),
                    		logged.getString("user_role"),
                    		logged.getString("user_name"));
                    txn.commit();
                    return Response.ok(g.toJson(ui)).build();
            } else {
                txn.rollback();
                return Response.status(Status.FORBIDDEN).entity("You do not have access to this operation.").build();
            }
        } finally {
			if(txn.isActive()) {
				txn.rollback();
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}
    }
	
	@PUT
	@Path("/{username}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response follow(@PathParam("username") String username, FollowData data) throws EntityNotFoundException {
		Transaction txn = datastore.newTransaction();
		try {
			boolean isUser = true;
			if(username.contains("@"))
				isUser = false;
			if(isUser) {
				
				Key userSeguidoKey = datastore.newKeyFactory().setKind("User").newKey(username);
				Entity userSeguido = txn.get(userSeguidoKey);
				if(userSeguido == null) {
					//user a ser seguido n existe no datastore
					txn.rollback();
					return Response.status(Status.FORBIDDEN).entity("The user you are trying to follow doesn't exists.").build();
				}
				
				String nrAntigoSeguidores = userSeguido.getString("user_followers");
				int nrSeguidores = Integer.parseInt(nrAntigoSeguidores);
				nrSeguidores++;

				userSeguido = Entity.newBuilder(userSeguido).set("user_followers", String.valueOf(nrSeguidores)).build();
				txn.update(userSeguido);
				
				Key userQueSegueTokenKey = datastore.newKeyFactory().setKind("Token").newKey(data.getTokenID());
				Entity userQueSegueToken = txn.get(userQueSegueTokenKey);

				if(userQueSegueToken == null) {
					//nao esta logged in
					txn.rollback();
					return Response.status(Status.FORBIDDEN).entity("You are not logged in.").build();
				}
				
				//incrementar o nr de seguidores
				
				//txn.commit();
				//return Response.ok("Nr seguidores incrementado.").build();
				
				//FALTA ADICIONAR O OUTRO USER NA LISTA DE SEGUIDORES
				
				
				Key userQueSegueKey = datastore.newKeyFactory().setKind("User").newKey(userQueSegueToken.getString("token_username"));
				Entity userQueSegue = txn.get(userQueSegueKey);
				
				//incrementar o nr de pessoas que segue
				String nrAntigoASeguir = userQueSegue.getString("user_following");
				int nrASeguir = Integer.parseInt(nrAntigoASeguir);
				nrASeguir++;
				
				userQueSegue = Entity.newBuilder(userQueSegue).set("user_following", String.valueOf(nrASeguir)).build();
				txn.update(userQueSegue);
				txn.commit();
				
				return Response.ok("Nr seguidores incrementado.").build();
			}
			else {
				//organization
				return Response.ok("Nr seguidores incrementado.").build();
			}
			
		} finally {
			if(txn.isActive()) {
				txn.rollback();
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}
	}
}
