package pt.unl.fct.di.example.bruh;

import com.google.gson.annotations.SerializedName;

public class RegisterOrg {

    @SerializedName("name")
    private String name;
    @SerializedName("owner")
    private String owner;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("confirmation")
    private String confirmation;
    @SerializedName("id")
    private String id;
    @SerializedName("address")
    private String address;
    @SerializedName("serviceType")
    private String serviceType;

    public RegisterOrg(String name, String owner, String email,String password, String confirmation, String id, String address, String serviceType){
        this.name = name;
        this.owner = owner;
        this.email = email;
        this.password = password;
        this.confirmation = confirmation;
        this.id = id;
        this.address = address;
        this.serviceType = serviceType;
    }
}
