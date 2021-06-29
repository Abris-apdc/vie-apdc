package pt.unl.fct.di.example.bruh;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private EditText firstName, lastName, username, password, confirmation, email, bday;
    private String fYear, fMonth, fDay;
    private Button send, organization;
    private Calendar myCalendar;

    public static final String SHARED_PREFS = "sharedPrefs";
    private ClientAPI clientAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstName = findViewById(R.id.activity_register_firstName);
        lastName = findViewById(R.id.activity_register_lastName);
        username = findViewById(R.id.activity_register_username);
        password = findViewById(R.id.activity_register_password);
        confirmation = findViewById(R.id.activity_register_confirmation);
        email = findViewById(R.id.activity_register_email);
        send = findViewById(R.id.activity_register_send);
        organization = findViewById(R.id.activity_register_institucion);

        clientAPI = ClientAPI.getInstance();

        myCalendar = Calendar.getInstance();

        bday = (EditText) findViewById(R.id.activity_register_birthday);
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                fYear = String.valueOf(year);
                fMonth = String.valueOf(monthOfYear + 1);
                fDay = String.valueOf(dayOfMonth);
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();

            }

        };

        bday.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog(RegisterActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        send.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                if (firstName.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Invalid first name.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (lastName.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Invalid last name.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(username.getText().toString().equals("") || username.getText().toString().contains(" ")){
                    Toast.makeText(getApplicationContext(), "Invalid username.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.getText().toString().length() < 9){
                    Toast.makeText(getApplicationContext(), "Password to short.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!password.getText().toString().equals(confirmation.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Password don't match.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (email.getText().toString() == "" || !email.getText().toString().contains("@")){
                    Toast.makeText(getApplicationContext(), "Invalid email.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(fYear == null){
                    Toast.makeText(getApplicationContext(), "Invalid date", Toast.LENGTH_SHORT).show();
                    return;
                }

                if((LocalDate.now().getYear() - Integer.parseInt(fYear))<15){
                    Toast.makeText(getApplicationContext(), "You must be over 15 years old.", Toast.LENGTH_SHORT).show();
                    return;

                }


                Register r = new Register(
                        firstName.getText().toString(),
                        lastName.getText().toString(),
                        username.getText().toString(),
                        password.getText().toString(),
                        confirmation.getText().toString(),
                        email.getText().toString(),
                        fYear,
                        fMonth,
                        fDay
                );

                clientAPI.getRegisterService().createRegister(r).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> r) {


                        if (r.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), r.body(), Toast.LENGTH_SHORT).show();
                            SharedPreferences preferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();

                            editor.putString("Authentication_firstName", firstName.getText().toString());
                            editor.putString("Authentication_lastName", lastName.getText().toString());
                            editor.putString("Authentication_role", "Voluntário");
                            editor.putString("Authentication_username", username.getText().toString());
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
        organization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerOrganisation();
            }
        });
    }



    private void updateLabel() {
        String myFormat = "yyyy/dd/MM"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);

        bday.setText(sdf.format(myCalendar.getTime()));
    }
    private void registerOrganisation(){
        Intent intent = new Intent(this, RegisterOrganisationActivity.class);

        startActivity(intent);
    }
    private void changeScreen() {
        Intent intent = new Intent(this, HomeScreenActivity.class);

        startActivity(intent);
    }
}