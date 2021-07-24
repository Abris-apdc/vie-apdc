package pt.unl.fct.di.example.bruh.requests;

import com.google.gson.annotations.SerializedName;

public class EventInfo {
  @SerializedName("event")
    private String event;

    @SerializedName("coordinates")
    private String coordinates;

    public EventInfo(String event, String coordinates ) {
        this.event = event;
        this.coordinates = coordinates;
    }


    public String getEvent() {
        return event;
    }

    public String getCoordinates() {
        return coordinates;
    }
}
