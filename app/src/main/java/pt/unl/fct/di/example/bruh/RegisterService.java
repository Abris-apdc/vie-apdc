package pt.unl.fct.di.example.bruh;

import retrofit2.Call;
import retrofit2.http.Body;

import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RegisterService {
    @POST("/rest/register")
    Call<Register> createRegister(@Body Register register);

    @POST("/rest/login")
    Call<AuthToken> createLogin(@Body Login login);

    @POST("/rest/logout")
    Call<String> doLogout(@Body Logout login);
}
