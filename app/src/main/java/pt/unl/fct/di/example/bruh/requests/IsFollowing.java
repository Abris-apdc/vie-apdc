package pt.unl.fct.di.example.bruh.requests;

import com.google.gson.annotations.SerializedName;

public class IsFollowing {
    @SerializedName("tokenID")
    private String tokenID;

    public IsFollowing( String tokenID ) {
        this.tokenID = tokenID;
    }
}
