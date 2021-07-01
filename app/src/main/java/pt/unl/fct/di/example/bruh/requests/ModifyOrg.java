package pt.unl.fct.di.example.bruh.requests;

import com.google.gson.annotations.SerializedName;

public class ModifyOrg {
    @SerializedName("name")
    private String name;
    @SerializedName("phoneNumber")
    private String phoneNumber;
    @SerializedName("address")
    private String address;
    @SerializedName("country")
    private String country;
    @SerializedName("description")
    private String description;
    @SerializedName("serviceType")
    private String serviceType;
    @SerializedName("cardID")
    private String cardID;
    @SerializedName("owner")
    private String owner;
    @SerializedName("tokenID")
    private String tokenID;


    public ModifyOrg(String name, String phoneNumber, String address, String country,String description, String serviceType, String cardID,String owner, String tokenID) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.country = country;
        this.description = description;
        this.serviceType = serviceType;
        this.cardID = cardID;
        this.owner = owner;
        this.tokenID = tokenID;

    }
}
