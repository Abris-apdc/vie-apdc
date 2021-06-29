package pt.unl.fct.di.example.bruh;

import retrofit2.Call;
import retrofit2.http.Body;

import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RegisterService {
    @POST("/rest/register")
    Call<String> createRegister(@Body Register register);

    @POST("/rest/register/organisation")
    Call<String> createRegister(@Body RegisterOrg register);

    @POST("/rest/login")
    Call<AuthToken> createLogin(@Body Login login);

    @POST("/rest/logout")
    Call<String> doLogout(@Body Logout login);

    @GET("/rest/find/{pattern}")
    Call<String[]> getUserInfo (@Path("pattern") String username);

    @PUT("/rest/update/")
    Call<String> updateUser(@Body ModifyUser userInfo);

    @HTTP(method = "DELETE", path = "/rest/delete", hasBody = true)
    Call<String> deleteUser(@Body Delete userInfo);
}
