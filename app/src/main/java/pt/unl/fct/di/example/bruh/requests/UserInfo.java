package pt.unl.fct.di.example.bruh.requests;

import com.google.gson.annotations.SerializedName;

public class UserInfo {
    @SerializedName("firstName")
    private String firstName;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("role")
    private String role;

    @SerializedName("username")
    private String username;

    @SerializedName("bDay")
    private String bDay;



    public UserInfo(String role, String username, String firstName,String lastName,String bDay ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.username = username;
        this.bDay = bDay;

    }
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

}
