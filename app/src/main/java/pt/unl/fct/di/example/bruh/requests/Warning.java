package pt.unl.fct.di.example.bruh.requests;

import com.google.gson.annotations.SerializedName;

public class Warning {
    @SerializedName("tokenID")
    private String tokenID;

    public Warning( String tokenID ) {
        this.tokenID = tokenID;
    }
}
