package pt.unl.fct.di.example.bruh;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import pt.unl.fct.di.example.bruh.requests.UserInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class PerfilFragment extends Fragment {
    private static final String FIRST_NAME = "username";
    private static final String LAST_NAME = "lastName";
    private static final String ROLE = "role";
    private static final String USERNAME = "username";

    public static final String SHARED_PREFS = "sharedPrefs";
    private TextView firstName, lastName, role, username;
    private Button edit;
    private ClientAPI clientAPI;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        firstName = (TextView) view.findViewById(R.id.activity_perfil_name);
        lastName = (TextView) view.findViewById(R.id.activity_perfil_lastName);
        role = (TextView) view.findViewById(R.id.activity_perfil_role);
        username = (TextView) view.findViewById(R.id.activity_perfil_username);

        edit = (Button) view.findViewById(R.id.activity_perfil_edit);

        getUserInfo(view);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                SettingsFragment st = new SettingsFragment();
                st.setRole(role.getText().toString());
                fragmentManager.beginTransaction().replace(R.id.fragment_container, st).commit();
            }
        });

        return view;
    }

    protected void getUserInfo(View v) {

        SharedPreferences preferences = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        String r = preferences.getString("Authentication_role", "");
        String id = preferences.getString("Authentication_username", "");

        role.setText(r);
        username.setText(id);
        doRequest(v);
    }

    private void doRequest(View view) {

        clientAPI = clientAPI.getInstance();

        clientAPI.getRegisterService().userInfo(username.getText().toString()).enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> r) {
                if(r.isSuccessful()) {
                    firstName.setText(r.body().getFirstName());
                    lastName.setText(r.body().getLastName());


                }else
                    Toast.makeText(getActivity(), "Failed to get user profile", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed to get user profile", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
