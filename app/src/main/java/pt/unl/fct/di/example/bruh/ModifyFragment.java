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
import androidx.fragment.app.FragmentManager;

import pt.unl.fct.di.example.bruh.requests.ModifyUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class ModifyFragment extends Fragment {
    public static final String SHARED_PREFS = "sharedPrefs";
    private ClientAPI clientAPI;
    private EditText firstName, lastName, email, phone,  address,  nacionality, fLanguage, bio;
    private String gender,perfil;
    private Button update;
    private Spinner spinner, genders;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modify, container, false);
        firstName = (EditText) view.findViewById(R.id.activity_modify_first_name);
        lastName = (EditText) view.findViewById(R.id.activity_modify_last_name);
        email = (EditText) view.findViewById(R.id.activity_modify_email);
        genders(view);
        phone = (EditText) view.findViewById(R.id.activity_modify_phone);
        address = (EditText) view.findViewById(R.id.activity_modify_address);
        nacionality = (EditText) view.findViewById(R.id.activity_modify_nacionality);
        fLanguage = (EditText) view.findViewById(R.id.activity_modify_first_language);
        bio = (EditText) view.findViewById(R.id.activity_modify_description);

        update = (Button) view.findViewById(R.id.activity_modify_update);
        perfilMode(view);
        genders(view);


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
                gender,
                phone.getText().toString(),
                address.getText().toString(),
                nacionality.getText().toString(),
                fLanguage.getText().toString(),
                bio.getText().toString(),
                perfil,
                token
        );

        clientAPI.getRegisterService().updateUser(u).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> r) {
                if(r.isSuccessful()) {
                    Toast.makeText(getActivity(), "User Update", Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container, new PerfilFragment()).commit();
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

    private void genders(View view){
        genders = view.findViewById(R.id.activity_modify_gender);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource( getActivity(),
                R.array.genders, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        genders.setAdapter(adapter);
        genders.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
