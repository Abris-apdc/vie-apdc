package pt.unl.fct.di.example.bruh;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import pt.unl.fct.di.example.bruh.requests.UserInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class OtherProfileRolesFragment  extends Fragment {
    public static final String SHARED_PREFS = "sharedPrefs";
    String username, myRole;
    TextView profile, fn,ln,role;
    Button changeRole;

    private ClientAPI clientAPI;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_other_profile_roles, container, false);
        profile = (TextView) view.findViewById(R.id.set_profile_roles);
        fn = (TextView) view.findViewById(R.id.other_profile_fn_roles);
        ln = (TextView) view.findViewById(R.id.other_profile_ln_roles);
        role = (TextView) view.findViewById(R.id.other_profile_role_roles);
        changeRole = (Button) view.findViewById(R.id.other_profile_change_roles);

        profile.setText(username);
        doRequest(view);
        changeRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              getRole();
            }
        });

        return view;
    }

    private void doRequest(View view) {

        clientAPI = clientAPI.getInstance();

        clientAPI.getRegisterService().userInfo(profile.getText().toString()).enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> r) {
                if(r.isSuccessful()) {
                    fn.setText(r.body().getFirstName());
                    ln.setText(r.body().getLastName());
                    role.setText(r.body().getRole());
                }else
                    Toast.makeText(getActivity(), "Failed to get user profile", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed to get user profile", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getRole() {
        SharedPreferences preferences = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        myRole = preferences.getString("Authentication_role", "");
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        if(myRole.equals("SU")) {
            ChangeRoleSUFragment su = new ChangeRoleSUFragment();
            su.setRole(myRole);
            su.setUsername(profile.getText().toString());
            fragmentManager.beginTransaction().replace(R.id.fragment_container, su).commit();
        }
        else {
            ChangeRoleAdminFragment su = new ChangeRoleAdminFragment();
            su.setRole(myRole);
            su.setUsername(profile.getText().toString());
            fragmentManager.beginTransaction().replace(R.id.fragment_container, su).commit();
        }

    }


    public void setValues(String username){
        this.username = username;
    }
}