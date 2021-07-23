package pt.unl.fct.di.example.bruh.requests;

import com.google.gson.annotations.SerializedName;

public class JoinEvent {
    @SerializedName("event")
    private String event;

    @SerializedName("tokenID")
    private String tokenID;

    public JoinEvent(String event, String tokenID ) {
        this.event = event;
        this.tokenID = tokenID;
    }
}
