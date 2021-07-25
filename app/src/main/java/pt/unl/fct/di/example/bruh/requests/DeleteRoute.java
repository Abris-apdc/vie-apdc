package pt.unl.fct.di.example.bruh.requests;

import com.google.gson.annotations.SerializedName;

public class DeleteRoute {


        @SerializedName("tokenID")
        private String tokenID;

        @SerializedName("route")
        private String route;


        public DeleteRoute( String tokenID, String route) {
            this.route = route;
            this.tokenID = tokenID;
        }
}
