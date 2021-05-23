package pt.unl.fct.di.example.bruh;

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
    @SerializedName("landLine")
    private String landLine;
    @SerializedName("address")
    private String address;
    @SerializedName("secondAddress")
    private String secondAddress;
    @SerializedName("city")
    private String city;
    @SerializedName("country")
    private String country;
    @SerializedName("cp")
    private String cp;
    @SerializedName("nationality")
    private String nationality;
    @SerializedName("firstLanguage")
    private String firstLanguage;
    @SerializedName("secondLanguage")
    private String secondLanguage;
    @SerializedName("description")
    private String description;
    @SerializedName("educationLevel")
    private String educationLevel;
    @SerializedName("perfil")
    private String perfil;
    @SerializedName("tokenID")
    private String tokenID;


    public ModifyUser(String firstName, String lastName, String email, String gender, String phoneNumber, String landLine, String address, String secondAddress, String city, String country, String cp, String nationality, String firstLanguage, String secondLanguage, String description, String educationLevel, String perfil, String tokenID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.landLine = landLine;
        this.address = address;
        this.secondAddress = secondAddress;
        this.city = city;
        this.country = country;
        this.cp = cp;
        this.nationality = nationality;
        this.firstLanguage = firstLanguage;
        this.secondLanguage = secondLanguage;
        this.description = description;
        this.educationLevel = educationLevel;
        this.perfil = perfil;
        this.tokenID = tokenID;

    }
}
