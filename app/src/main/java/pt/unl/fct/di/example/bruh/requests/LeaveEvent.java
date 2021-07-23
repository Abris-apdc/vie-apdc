package pt.unl.fct.di.example.bruh.requests;

import com.google.gson.annotations.SerializedName;

public class LeaveEvent {
    @SerializedName("event")
    private String event;

    @SerializedName("tokenID")
    private String tokenID;

    public LeaveEvent(String event, String tokenID ) {
        this.event = event;
        this.tokenID = tokenID;
    }
}

