package pt.unl.fct.di.example.bruh;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class ModifyFragment extends Fragment {
    public static final String SHARED_PREFS = "sharedPrefs";
    private ClientAPI clientAPI;
    private EditText firstName, lastName, email, gender, phone, landline, address, secondAddress, city, country, zipCode, nacionality, fLanguage, sLanguage, bio, level;
    String perfil;
    private Button update;
    private Spinner spinner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modify, container, false);
        firstName = (EditText) view.findViewById(R.id.activity_modify_first_name);
        lastName = (EditText) view.findViewById(R.id.activity_modify_last_name);
        email = (EditText) view.findViewById(R.id.activity_modify_email);
        gender = (EditText) view.findViewById(R.id.activity_modify_gender);
        phone = (EditText) view.findViewById(R.id.activity_modify_phone);
        landline = (EditText) view.findViewById(R.id.activity_modify_land_line);
        address = (EditText) view.findViewById(R.id.activity_modify_address);
        secondAddress = (EditText) view.findViewById(R.id.activity_modify_second_address);
        city = (EditText) view.findViewById(R.id.activity_modify_city);
        country = (EditText) view.findViewById(R.id.activity_modify_coutry);
        zipCode = (EditText) view.findViewById(R.id.activity_modify_zip_code);
        nacionality = (EditText) view.findViewById(R.id.activity_modify_nacionality);
        fLanguage = (EditText) view.findViewById(R.id.activity_modify_first_language);
        sLanguage = (EditText) view.findViewById(R.id.activity_modify_second_language);
        bio = (EditText) view.findViewById(R.id.activity_modify_description);
        level = (EditText) view.findViewById(R.id.activity_modify_level);

        update = (Button) view.findViewById(R.id.activity_modify_update);
        perfilMode(view);


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update(v);
            }
        });
        return view;
    }


    private void update(View v){
        SharedPreferences preferences = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String token = preferences.getString("Authentication_Id", "");

        clientAPI = clientAPI.getInstance();



        ModifyUser u = new ModifyUser(
             firstName.getText().toString(),
                lastName.getText().toString(),
                email.getText().toString(),
                gender.getText().toString(),
                phone.getText().toString(),
                landline.getText().toString(),
                address.getText().toString(),
                secondAddress.getText().toString(),
                city.getText().toString(),
                country.getText().toString(),
                zipCode.getText().toString(),
                nacionality.getText().toString(),
                fLanguage.getText().toString(),
                sLanguage.getText().toString(),
                bio.getText().toString(),
                level.getText().toString(),
                perfil,
                token
        );

        clientAPI.getRegisterService().updateUser(u).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> r) {
                if(r.isSuccessful()) {
                    Toast.makeText(getActivity(), "User Update", Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(getActivity(), "Failed to Update", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed to Update", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void perfilMode(View view){
        spinner = view.findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource( getActivity(),
                R.array.perfil_mode, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                perfil = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });
    }
}
