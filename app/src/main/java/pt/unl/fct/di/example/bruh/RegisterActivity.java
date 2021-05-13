package pt.unl.fct.di.example.bruh;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private EditText firstName, lastName, username, password, confirmation, email, bday;
    private int fYear, fMonth, fDay;
    private Button send;
    private Calendar myCalendar;


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

        clientAPI = clientAPI.getInstance();

        myCalendar = Calendar.getInstance();

        bday = (EditText) findViewById(R.id.activity_register_birthday);
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                fYear = year;
                fMonth = monthOfYear + 1;
                fDay = dayOfMonth;
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();

            }

        };

        bday.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(RegisterActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                clientAPI.getRegisterService().createRegister(r).enqueue(new Callback<Register>() {
                    @Override
                    public void onResponse(Call<Register> call, Response<Register> r) {

                        Toast.makeText(getApplicationContext(), "User " + r.body().getUsername() + " created", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Register> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Error Creating User: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }


                });

                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);

                startActivity(intent);

            }
        });

    }
    private void updateLabel() {
        String myFormat = "yy/dd/MM"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);

        bday.setText(sdf.format(myCalendar.getTime()));
    }
}