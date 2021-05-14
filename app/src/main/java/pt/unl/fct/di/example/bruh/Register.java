package pt.unl.fct.di.example.bruh;

import com.google.gson.annotations.SerializedName;

public class Register {


    @SerializedName("firstName")
    private String firstname;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("confirmation")
    private String confirmation;

    @SerializedName("email")
    private String email;

    @SerializedName("year")
    private String year;
    @SerializedName("month")
    private String month;

    @SerializedName("day")
    private String day;




    public Register(String firstname, String lastName,String username, String password, String confirmation, String email, String year,String month, String day) {

        this.firstname = firstname;
       this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.confirmation = confirmation;
        this.email = email;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmation() {
        return confirmation;
    }

    public String getEmail() {
        return email;
    }
}
