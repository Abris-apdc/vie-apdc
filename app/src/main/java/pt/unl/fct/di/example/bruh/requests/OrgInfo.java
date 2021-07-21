package pt.unl.fct.di.example.bruh.requests;

import com.google.gson.annotations.SerializedName;

public class OrgInfo {



    @SerializedName("role")
    private String role;

    @SerializedName("username")
    private String username;

    @SerializedName("address")
    private String address;

    @SerializedName("service")
    private String service;

    @SerializedName("email")
    private String email;



    public OrgInfo(String role, String username, String address,String service,String email ) {
        this.address= address;
        this.service = service;
        this.role = role;
        this.username = username;
        this.email = email;

    }
    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }
}
