package pt.unl.fct.di.example.bruh;

import com.google.gson.annotations.SerializedName;

public class Delete {
    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("tokenID")
    private String tokenID;

    public Delete(String username, String password, String tokenID) {
        this.username = username;
        this.password = password;
        this.tokenID = tokenID;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getTokenID(){
        return tokenID;
    }

}
