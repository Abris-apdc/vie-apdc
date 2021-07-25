package pt.unl.fct.di.example.bruh.requests;

import com.google.gson.annotations.SerializedName;

public class RouteData {
    @SerializedName("username")
    private String username;

    @SerializedName("tokenID")
    private String tokenID;

    @SerializedName("locations")
    private String[] locations;

    @SerializedName("routeName")
    private String routName;

    @SerializedName("info")
    private String info;

    public RouteData(String username, String tokenID, String[] locations, String routName, String info) {
        this.username = username;
        this.tokenID = tokenID;
        this.locations = locations;
        this.routName = routName;
        this.info = info;
    }
}
