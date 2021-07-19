package pt.unl.fct.di.example.bruh.requests;

import com.google.gson.annotations.SerializedName;

public class PlaceEvent {

    @SerializedName("name")
    private String name;
    @SerializedName("coordinates")
    private String coordinates;
    @SerializedName("address")
    private String address;
    @SerializedName("org")
    private String org;
    @SerializedName("duration")
    private String duration;
    @SerializedName("tokenID")
    private String tokenID;


    public PlaceEvent(String name, String coordinates, String address, String org,String duration,  String tokenID) {
        this.name = name;
        this.coordinates = coordinates;
        this.address = address;
        this.org = org;
        this.duration = duration;
        this.tokenID = tokenID;

    }
}
