package pt.unl.fct.di.example.bruh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    public static final String SHARED_PREFS = "sharedPrefs";

    private EditText username, password;
    private Button send, register;

    private ClientAPI clientAPI;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.activity_login_username);
        password = findViewById(R.id.activity_login_password);
        send = findViewById(R.id.activity_login_send);
        register = findViewById(R.id.activity_login_register);

        clientAPI = clientAPI.getInstance();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login l = new Login(
                        username.getText().toString(),
                        password.getText().toString()
                );

                clientAPI.getRegisterService().createLogin(l).enqueue(new Callback<AuthToken>() {
                    @Override
                   public void onResponse(Call<AuthToken> call, Response<AuthToken> r) {
                        Toast.makeText(getApplicationContext(), "User  created", Toast.LENGTH_SHORT).show();

                        SharedPreferences preferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("Authentication_Id",r.body().getTokenID());
                        editor.putString("Authentication_username",r.body().getUsername());
                        editor.apply();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                        startActivity(intent);
                   }

                    @Override
                    public void onFailure(Call<AuthToken> call, Throwable t) {
                        Intent intent = new Intent(LoginActivity.this,LoginActivity.class);

                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "Error Creating User: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);

                startActivity(intent);
            }
        });
    }
}