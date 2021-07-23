package pt.unl.fct.di.example.bruh;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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
import androidx.fragment.app.FragmentManager;

import java.util.List;

import pt.unl.fct.di.example.bruh.requests.UserInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class PerfilFragment extends Fragment {
    public static final String SHARED_PREFS = "sharedPrefs";
    private static final String FOLLOWING = "Following: ";
    private static final String FOLLOWERS = "Followers: ";
    private List<String> list, following_list;
    private TextView firstName, lastName, role, username;
    private Button edit, followers, following;
    private ClientAPI clientAPI;
    private int nFollowers, nfollowing;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        firstName = (TextView) view.findViewById(R.id.activity_perfil_name);
        lastName = (TextView) view.findViewById(R.id.activity_perfil_lastName);
        role = (TextView) view.findViewById(R.id.activity_perfil_role);
        username = (TextView) view.findViewById(R.id.activity_perfil_username);

        edit = (Button) view.findViewById(R.id.activity_perfil_edit);
        followers = (Button) view.findViewById(R.id.activity_perfil_followers);
        following = (Button) view.findViewById(R.id.activity_perfil_following);
        getUserInfo(view);
        followers();
        following();

        following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FollowersFragment st = new FollowersFragment();
                st.setValues(following_list);
                fragmentManager.beginTransaction().replace(R.id.fragment_container, st).commit();
            }
        });

        followers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nFollowers == 0)
                    Toast.makeText(getActivity(), "You do not have followers", Toast.LENGTH_SHORT).show();
                else {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FollowersFragment st = new FollowersFragment();
                    st.setValues(list);
                    fragmentManager.beginTransaction().replace(R.id.fragment_container, st).commit();
                }
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nFollowers == 0)
                    Toast.makeText(getActivity(), "You do not follow people", Toast.LENGTH_SHORT).show();
                else {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    SettingsFragment st = new SettingsFragment();
                    st.setRole(role.getText().toString());
                    fragmentManager.beginTransaction().replace(R.id.fragment_container, st).commit();
                }
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

    private void followers(){
        clientAPI = clientAPI.getInstance();

        clientAPI.getRegisterService().getFollowersList(username.getText().toString()).enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> r) {
                if(r.isSuccessful()) {
                    list= r.body();
                    nFollowers= r.body().size();
                    followers.setText(FOLLOWERS + nFollowers);
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed to get user followers", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void following(){
        clientAPI = clientAPI.getInstance();

        clientAPI.getRegisterService().getFollowingList(username.getText().toString()).enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> r) {
                if(r.isSuccessful()) {
                    following_list = r.body();
                    nfollowing = r.body().size();
                    following.setText(FOLLOWING + nfollowing);
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed to get user following number", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
