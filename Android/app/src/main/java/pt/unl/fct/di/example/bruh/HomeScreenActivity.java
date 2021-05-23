package pt.unl.fct.di.example.bruh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.Toast;

public class HomeScreenActivity extends AppCompatActivity {
    public static final String SHARED_PREFS = "sharedPrefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home_screen);
        new Handler().postDelayed(new Runnable(){

            public void run() {
                jumpLogin();
            }
        }, 3000L);
    }

    private void jumpLogin(){
        SharedPreferences preferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();

        String token = preferences.getString("Authentication_Id", "");
        if (token.equals("null") || token == null){
            Intent intent = new Intent((Context)HomeScreenActivity.this, LoginActivity.class);
            HomeScreenActivity.this.startActivity(intent);
        }else {
            Intent intent = new Intent(this, MainActivity.class);

            startActivity(intent);
        }

    }
}