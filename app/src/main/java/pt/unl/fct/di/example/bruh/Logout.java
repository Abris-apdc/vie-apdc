package pt.unl.fct.di.example.bruh;
import com.google.gson.annotations.SerializedName;
public class Logout {

    @SerializedName("username")
    private String username;
    @SerializedName("tokenID")
    private String token;

    public Logout(String username ,String token) {
        this.username = username;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }

}
