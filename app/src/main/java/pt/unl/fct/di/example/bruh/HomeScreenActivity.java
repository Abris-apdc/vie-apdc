package pt.unl.fct.di.example.bruh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class HomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home_screen);
        new Handler().postDelayed(new Runnable(){

            public void run() {
                Intent intent = new Intent((Context)HomeScreenActivity.this, LoginActivity.class);
                HomeScreenActivity.this.startActivity(intent);
            }
        }, 3000L);
    }
}