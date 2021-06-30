package pt.unl.fct.di.example.bruh.requests;

import android.widget.EditText;

import com.google.gson.annotations.SerializedName;

public class ModifyUser {

    @SerializedName("firstName")
    private String firstName;
    @SerializedName("lastName")
    private String lastName;
    @SerializedName("email")
    private String email;
    @SerializedName("gender")
    private String gender;
    @SerializedName("phoneNumber")
    private String phoneNumber;
    @SerializedName("address")
    private String address;
    @SerializedName("nationality")
    private String nationality;
    @SerializedName("firstLanguage")
    private String firstLanguage;
    @SerializedName("description")
    private String description;
    @SerializedName("perfil")
    private String perfil;
    @SerializedName("tokenID")
    private String tokenID;


    public ModifyUser(String firstName, String lastName, String email, String gender, String phoneNumber, String address,  String nationality, String firstLanguage,  String description, String perfil, String tokenID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.nationality = nationality;
        this.firstLanguage = firstLanguage;
        this.description = description;
        this.perfil = perfil;
        this.tokenID = tokenID;

    }
}
