package pt.unl.fct.di.example.bruh.requests;

import com.google.gson.annotations.SerializedName;

public class ChangeRole {
    @SerializedName("username")
    private String username;

    @SerializedName("tokenID")
    private String tokenID;

    @SerializedName("newRole")
    private String newRole;



    public ChangeRole(String username,
                  String tokenID,String newRole) {
        this.username = username;
        this.tokenID = tokenID;
        this.newRole = newRole;

    }

    public String getUsername() {
        return username;
    }

    public String getNewRole() {
        return newRole;
    }

    public String getTokenID(){
        return tokenID;
    }


}
