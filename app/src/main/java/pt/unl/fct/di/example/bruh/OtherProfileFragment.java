package pt.unl.fct.di.example.bruh;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import pt.unl.fct.di.example.bruh.requests.Follow;
import pt.unl.fct.di.example.bruh.requests.UserInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class OtherProfileFragment extends Fragment {
    public static final String SHARED_PREFS = "sharedPrefs";
    private String username;
    private Boolean folloing;
    private TextView profile, fn,ln,role;
    private Button follow;

    private ClientAPI clientAPI;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_other_profile, container, false);
        profile = (TextView) view.findViewById(R.id.set_profile);
        fn = (TextView) view.findViewById(R.id.other_profile_fn);
        ln = (TextView) view.findViewById(R.id.other_profile_ln);
        role = (TextView) view.findViewById(R.id.other_profile_role);
        follow = (Button) view.findViewById(R.id.other_profile_follow);

        profile.setText(username);
        doRequest(view);

        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                follow();
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

    private void follow(){
        SharedPreferences preferences = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String token = preferences.getString("Authentication_Id", "");

        clientAPI = clientAPI.getInstance();
        Follow f = new Follow(token);
        clientAPI.getRegisterService().follow(username, f).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> r) {
                if(r.isSuccessful()) {
                    folloing = true;
                    follow.setText("Following");
                    Toast.makeText(getActivity(), "Following", Toast.LENGTH_SHORT).show();
                }else {
                  //  Toast.makeText(getActivity(), "Failed to get user profile", Toast.LENGTH_SHORT).show();
                    folloing = false;
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                folloing = false;
                follow.setText("Following");
                Toast.makeText(getActivity(), "Following", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void setValues(String username){
        this.username = username;
    }
}
