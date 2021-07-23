package pt.unl.fct.di.example.bruh.requests;

import com.google.gson.annotations.SerializedName;

public class IsInEvent {
    @SerializedName("tokenID")
    private String tokenID;

    public IsInEvent( String tokenID ) {
        this.tokenID = tokenID;
    }
}
