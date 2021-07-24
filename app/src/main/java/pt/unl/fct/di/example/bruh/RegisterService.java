package pt.unl.fct.di.example.bruh;

import java.util.List;

import pt.unl.fct.di.example.bruh.requests.AuthToken;
import pt.unl.fct.di.example.bruh.requests.ChangePassword;
import pt.unl.fct.di.example.bruh.requests.ChangeRole;
import pt.unl.fct.di.example.bruh.requests.Delete;
import pt.unl.fct.di.example.bruh.requests.EventData;
import pt.unl.fct.di.example.bruh.requests.EventInfo;
import pt.unl.fct.di.example.bruh.requests.Follow;
import pt.unl.fct.di.example.bruh.requests.IsFollowing;
import pt.unl.fct.di.example.bruh.requests.IsInEvent;
import pt.unl.fct.di.example.bruh.requests.JoinEvent;
import pt.unl.fct.di.example.bruh.requests.LeaveEvent;
import pt.unl.fct.di.example.bruh.requests.Login;
import pt.unl.fct.di.example.bruh.requests.Logout;
import pt.unl.fct.di.example.bruh.requests.ModifyOrg;
import pt.unl.fct.di.example.bruh.requests.ModifyUser;
import pt.unl.fct.di.example.bruh.requests.OrgInfo;
import pt.unl.fct.di.example.bruh.requests.PlaceEvent;
import pt.unl.fct.di.example.bruh.requests.Register;
import pt.unl.fct.di.example.bruh.requests.RegisterOrg;
import pt.unl.fct.di.example.bruh.requests.UserInfo;
import pt.unl.fct.di.example.bruh.requests.Warning;
import retrofit2.Call;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
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

    @PUT("/rest/update/organisation")
    Call<String> updateOrg(@Body ModifyOrg orgInfo);

    @HTTP(method = "DELETE", path = "/rest/delete", hasBody = true)
    Call<String> deleteUser(@Body Delete userInfo);

    @PUT("/rest/update/pass")
    Call<String> updatePassword(@Body ChangePassword userInfo);

    @GET("/rest/profile/{username}")
    Call<UserInfo> userInfo(@Path("username") String username);

    @GET("/rest/profile/{username}")
    Call<OrgInfo> orgInfo(@Path("username") String username);

    @PUT("/rest/changeRole")
    Call<String> changeRole(@Body ChangeRole username);

    @POST("/rest/warning/{username}")
    Call<String> giveWarning(@Path("username") String username, @Body Warning follow);

    @POST("/rest/profile/follow/{username}")
    Call<String> follow(@Path("username") String username, @Body Follow follow);

    @HTTP(method = "DELETE", path = "/rest/profile/unfollow/{username}", hasBody = true)
    Call<String> unfollow(@Path("username") String username, @Body Follow follow);

    @POST("/rest/profile/isFollowing/{username}")
    Call<String> isFollowing(@Path("username") String username, @Body IsFollowing follow);

    @POST("/rest/map")
    Call<String> addEvent(@Body PlaceEvent event);

    @GET("/rest/getFollowers/{username}")
    Call<List<String>> getFollowersList(@Path("username") String username);

    @GET("/rest/getFollowing/{username}")
    Call<List<String>> getFollowingList(@Path("username") String username);

    @GET("/rest/map/list")
    Call<List<EventInfo>> gettAllEvents();

    @GET("/rest/event/{event}/list")
    Call<List<String>> getParticipants(@Path("event") String event);

    @POST("/rest/event/join")
    Call<String> joinEvent(@Body JoinEvent joinEvent);

    @POST("/rest/event/leave")
    Call<String> leaveEvent(@Body LeaveEvent leaveEvent);

    @GET("/rest/map/get/{event}")
    Call<EventData> getEventData(@Path("event") String event);

    @GET("/rest/map/list/{orgName}")
    Call<List<String>> getEventsByOrg(@Path("orgName") String orgName);

    @POST("/rest/event/isPart/{event}/")
    Call<String> isInEvent(@Path("event") String event, @Body IsInEvent isInEvent);
}
