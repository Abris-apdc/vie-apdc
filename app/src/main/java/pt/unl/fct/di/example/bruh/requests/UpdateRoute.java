package pt.unl.fct.di.example.bruh.requests;

import com.google.gson.annotations.SerializedName;

public class UpdateRoute {
    @SerializedName("name")
    private String name;

    @SerializedName("tokenID")
    private String tokenID;

    public UpdateRoute(String tokenID,String name) {
        this.name = name;
        this.tokenID = tokenID;
    }
}
