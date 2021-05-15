package pt.unl.fct.di.example.bruh;

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

    public UserInfo(String firstName,String lastName, String role, String username) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.username = username;

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
