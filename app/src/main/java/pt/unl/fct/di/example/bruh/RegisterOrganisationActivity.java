package pt.unl.fct.di.example.bruh;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import pt.unl.fct.di.example.bruh.requests.RegisterOrg;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterOrganisationActivity extends AppCompatActivity {
    private EditText name, owner, email, password, confirmation, id, address, serviceType;
    private String fYear, fMonth, fDay;
    private Button send;


    public static final String SHARED_PREFS = "sharedPrefs";
    private ClientAPI clientAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_organisation);
        name = findViewById(R.id.activity_register_orgname);
        owner = findViewById(R.id.activity_register_orgowner);
        email = findViewById(R.id.activity_register_orgemail);
        password = findViewById(R.id.activity_register_orgpassword);
        confirmation = findViewById(R.id.activity_register_orgconfirmation);
        id = findViewById(R.id.activity_register_orgid);
        address = findViewById(R.id.activity_register_orgadress);
        serviceType = findViewById(R.id.activity_register_orgservice);

        send = findViewById(R.id.activity_register_orgsend);

        clientAPI = ClientAPI.getInstance();

        send.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                if (name.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Invalid organisation name.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (owner.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Invalid owner name.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (email.getText().toString() == "" || !email.getText().toString().contains("@") || email.getText().toString().contains(" ") ) {
                    Toast.makeText(getApplicationContext(), "Invalid email.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.getText().toString().length() < 9) {
                    Toast.makeText(getApplicationContext(), "Password to short.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.getText().toString().equals(confirmation.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Password don't match.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (id.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Invalid id.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (address.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Invalid address.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (serviceType.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Invalid service type.", Toast.LENGTH_SHORT).show();
                    return;
                }


                RegisterOrg r = new RegisterOrg(
                       name.getText().toString(),
                        owner.getText().toString(),
                        email.getText().toString(),
                        password.getText().toString(),
                        id.getText().toString(),
                        address.getText().toString(),
                        serviceType.getText().toString()
                );

                clientAPI.getRegisterService().createRegister(r).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> r) {


                        if (r.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), r.body(), Toast.LENGTH_SHORT).show();
                            SharedPreferences preferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();

                            editor.putString("Authentication_owner", owner.getText().toString());
                            editor.putString("Authentication_firstName", name.getText().toString());
                            editor.putString("Authentication_lastName", "");
                            editor.putString("Authentication_role", "Organisation");
                            editor.putString("Authentication_username", email.getText().toString());
                            editor.putString("Authentication_address", address.getText().toString());
                            editor.apply();
                            changeScreen();
                        } else
                            Toast.makeText(getApplicationContext(), "Username already taken.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Error Creating user, try again", Toast.LENGTH_SHORT).show();
                    }


                });


            }
        });
    }


    private void changeScreen() {
        Intent intent = new Intent(this, HomeScreenActivity.class);

        startActivity(intent);
    }
}