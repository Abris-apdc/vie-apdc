package pt.unl.fct.di.example.bruh.requests;

import com.google.gson.annotations.SerializedName;

public class ChangePassword {
    @SerializedName("oldPass")
    private String oldPass;

    @SerializedName("newPass")
    private String newPass;


    @SerializedName("tokenID")
    private String tokenID;

    public ChangePassword(String oldPass, String newPass, String tokenID) {
        this.oldPass = oldPass;
        this.newPass = newPass;
        this.tokenID = tokenID;
    }
}
