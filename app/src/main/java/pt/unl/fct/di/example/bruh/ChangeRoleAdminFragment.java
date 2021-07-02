package pt.unl.fct.di.example.bruh;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import pt.unl.fct.di.example.bruh.requests.ChangeRole;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class ChangeRoleAdminFragment extends Fragment {
    private String role, username, nr;
    private Spinner spinner;

    private Button send;

    public static final String SHARED_PREFS = "sharedPrefs";

    private ClientAPI clientAPI;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_change_role_admin, container, false);
        loadSpinner(view);

        send = (Button) view.findViewById(R.id.change_role_admin_button);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeRole();
            }
        });
        return view;
    }

    private void loadSpinner(View view) {
        spinner = view.findViewById(R.id.change_role_admin_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource( getActivity(),
                R.array.admin_roles, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nr = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void setRole(String role){
        this.role = role;
    }

    public void setUsername(String username){
        this.username = username;
    }

    private void  changeRole() {
        SharedPreferences preferences = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String token = preferences.getString("Authentication_Id", "");

        ChangeRole c = new ChangeRole(username, token,nr);
        clientAPI = clientAPI.getInstance();

        clientAPI.getRegisterService().changeRole(c).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> r) {
                if(r.isSuccessful()) {
                    Toast.makeText(getActivity(), r.body().toString(), Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(getActivity(), r.body().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed to get user profile", Toast.LENGTH_SHORT).show();
            }
        });
    }
}