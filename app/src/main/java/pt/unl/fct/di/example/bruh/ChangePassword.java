package pt.unl.fct.di.example.bruh;

import com.google.gson.annotations.SerializedName;

public class ChangePassword {
    @SerializedName("oldPass")
    private String oldPass;

    @SerializedName("newPass")
    private String newPass;

    @SerializedName("confirmation")
    private String confirmation;

    @SerializedName("tokenID")
    private String tokenID;

    public ChangePassword(String oldPass, String newPass, String confirmation, String tokenID) {
        this.oldPass = oldPass;
        this.newPass = newPass;
        this.confirmation = confirmation;
        this.tokenID = tokenID;
    }
}
