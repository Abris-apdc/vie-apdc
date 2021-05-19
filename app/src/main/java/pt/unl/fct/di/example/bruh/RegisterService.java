package pt.unl.fct.di.example.bruh;

import retrofit2.Call;
import retrofit2.http.Body;

import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RegisterService {
    @POST("/rest/register")
    Call<String> createRegister(@Body Register register);

    @POST("/rest/register/organization")
    Call<String> createRegister(@Body RegisterOrg register);

    @POST("/rest/login")
    Call<AuthToken> createLogin(@Body Login login);

    @POST("/rest/logout")
    Call<String> doLogout(@Body Logout login);

    @GET("/rest/info/{username}")
    Call<UserInfo> getUserInfo (@Path("username") String username);

}
