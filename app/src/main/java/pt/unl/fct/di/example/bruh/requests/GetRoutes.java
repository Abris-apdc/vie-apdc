package pt.unl.fct.di.example.bruh.requests;

import com.google.gson.annotations.SerializedName;

public class GetRoutes {
    @SerializedName("username")
    private String username;


    @SerializedName("tokenID")
    private String tokenID;

    public GetRoutes(String username, String tokenID ) {
        this.username = username;
        this.tokenID = tokenID;
    }
}
