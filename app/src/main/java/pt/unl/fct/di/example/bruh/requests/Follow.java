package pt.unl.fct.di.example.bruh.requests;

import com.google.gson.annotations.SerializedName;

public class Follow {



    @SerializedName("tokenID")
    private String tokenID;

    public Follow( String tokenID ) {


        this.tokenID = tokenID;

    }

}
