package pt.unl.fct.di.example.bruh.requests;

import com.google.gson.annotations.SerializedName;

public class EventData {
    @SerializedName("eventName")
    private String eventName;

    @SerializedName("coordinates")
    private String coordinates;

    @SerializedName("address")
    private String address;

    @SerializedName("org")
    private String org;

    @SerializedName("duration")
    private String duration;



    public EventData(String eventName,String coordinates,String address,String org, String duration) {
        this.eventName = eventName;
        this.coordinates = coordinates;
        this.address = address;
        this.org = org;
        this.duration = duration;

    }
    public String getEventName() {
        return eventName;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public String getAddress() {
        return address;
    }

    public String getOrg() {
        return org;
    }

    public String getDuration() { return duration; }
}
