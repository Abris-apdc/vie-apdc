package pt.unl.fct.di.example.bruh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static final String SHARED_PREFS = "sharedPrefs";


    private ClientAPI clientAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                Toast.makeText(this, "Searching", Toast.LENGTH_LONG).show();
                return true;
            case R.id.help:
                Toast.makeText(this, "Helping", Toast.LENGTH_LONG).show();
                return true;
            case R.id.maps:
                maps();
                Toast.makeText(this, "Maping", Toast.LENGTH_LONG).show();
                return true;
            case R.id.logout:
                logout();
                Toast.makeText(this, "Bye!", Toast.LENGTH_LONG).show();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void logout() {

        SharedPreferences preferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String token = preferences.getString("Authentication_Id", "");
        String username = preferences.getString("Authentication_username", "");

       clientAPI = clientAPI.getInstance();


      Logout l = new Logout(
               username ,token
        );

      String s = "99d2e95e-38d3-468b-a8fa-1084328b09da";
        Toast.makeText(getApplicationContext(), l.getUsername() + "  " + l.getToken(), Toast.LENGTH_SHORT).show();
        clientAPI.getRegisterService().doLogout(l).enqueue(new Callback<String>() {
            @Override
           public void onResponse(Call<String> call, Response<String> r) {
                Toast.makeText(getApplicationContext(), "User logout", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);

                startActivity(intent);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                //  Intent intent = new Intent(LoginActivity.this,LoginActivity.class);

                // startActivity(intent);
                Toast.makeText(getApplicationContext(), "Error Creating User: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }

    protected void maps(){
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);

        startActivity(intent);
    }
}