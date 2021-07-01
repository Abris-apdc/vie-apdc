package pt.unl.fct.di.example.bruh.requests;
import com.google.gson.annotations.SerializedName;
public class Logout {


    @SerializedName("tokenID")
    private String token;

    public Logout(String token) {

        this.token = token;
    }

    public String getToken() {
        return token;
    }

}
