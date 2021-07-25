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

import java.util.List;

import pt.unl.fct.di.example.bruh.requests.ChangeRole;
import pt.unl.fct.di.example.bruh.requests.Follow;
import pt.unl.fct.di.example.bruh.requests.IsFollowing;
import pt.unl.fct.di.example.bruh.requests.UserInfo;
import pt.unl.fct.di.example.bruh.requests.Warning;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class OtherProfileRolesFragment  extends Fragment {
    public static final String SHARED_PREFS = "sharedPrefs";
    private static final String FOLLOW = "Follow";
    private static final String UNFOLLOW = "Unfollow";
    private static final String ENABLE = "Enable Account";
    private static final String DISABLE = "Disable Account";
    private static final String FOLLOWING = "Following: ";
    private static final String FOLLOWERS = "Followers: ";
    String username, myRole;
    TextView profile, fn,ln,role;
    Button changeRole, warning, follow, disable;
    private TextView following, followers;
    private int nFollowers;

    private ClientAPI clientAPI;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_other_profile_roles, container, false);
        profile = (TextView) view.findViewById(R.id.set_profile_roles);
        fn = (TextView) view.findViewById(R.id.other_profile_fn_roles);
        ln = (TextView) view.findViewById(R.id.other_profile_ln_roles);
        role = (TextView) view.findViewById(R.id.other_profile_role_roles);
        following = (TextView) view.findViewById(R.id.other_profile_following_roles);
        followers = (TextView) view.findViewById(R.id.other_profile_followers_roles);
        changeRole = (Button) view.findViewById(R.id.other_profile_change_roles);
        warning = (Button) view.findViewById(R.id.other_profile_warning);
        follow = (Button) view.findViewById(R.id.other_profile_follow_roles);
        disable = (Button) view.findViewById(R.id.other_profile_disable_roles);
        profile.setText(username);
        setRole();
        isDisable();
        followers();
        following();
        isFollowing();
        doRequest(view);

        disable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (disable.getText().equals(ENABLE)){
                    enable();
                } else{
                    disable();
                }
            }
        });

        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (follow.getText().equals(FOLLOW)){
                    follow();
                } else{
                    unfollow();
                }
            }
        });

        warning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                warning();
            }
        });

        changeRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              getRole();
            }
        });

        return view;
    }
    private void isDisable(){

        clientAPI = clientAPI.getInstance();
        clientAPI.getRegisterService().isDisable(username).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> r) {
                if(r.isSuccessful()) {
                    disable.setText(DISABLE);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                follow.setText(UNFOLLOW);
            }
        });
    }

    private void disable(){
        SharedPreferences preferences = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String token = preferences.getString("Authentication_Id", "");

        clientAPI = clientAPI.getInstance();
        Follow f = new Follow(token);
        clientAPI.getRegisterService().disable(username, f).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> r) {
                if(r.isSuccessful()) {
                    disable.setText(ENABLE);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                disable.setText(ENABLE);
            }
        });
    }

    private void enable(){
        SharedPreferences preferences = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String token = preferences.getString("Authentication_Id", "");

        clientAPI = clientAPI.getInstance();
        Follow f = new Follow(token);
        clientAPI.getRegisterService().enable(username, f).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> r) {
                if(r.isSuccessful()) {
                    disable.setText(DISABLE);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                disable.setText(DISABLE);
            }
        });
    }

    private void followers(){
        clientAPI = clientAPI.getInstance();

        clientAPI.getRegisterService().getFollowersList(username).enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> r) {
                if(r.isSuccessful()) {
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

        clientAPI.getRegisterService().getFollowingList(username).enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> r) {
                if(r.isSuccessful()) {
                    int number = r.body().size();
                    following.setText(FOLLOWING + number);
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed to get user following number", Toast.LENGTH_SHORT).show();
            }
        });
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
    private void setRole(){
        SharedPreferences preferences = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        myRole = preferences.getString("Authentication_role", "");
        if(myRole.equals("SU") || myRole.equals("ADMIN")){
            disable.setVisibility(View.VISIBLE);
        }
    }
    private void getRole() {

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

    private void isFollowing(){

        SharedPreferences preferences = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String token = preferences.getString("Authentication_Id", "");
        clientAPI = clientAPI.getInstance();
        IsFollowing f = new IsFollowing(token);
        clientAPI.getRegisterService().isFollowing(username,f).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> r) {
                if(r.isSuccessful()) {
                    follow.setText(FOLLOW);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                follow.setText(UNFOLLOW);
            }
        });
    }

    private void warning(){
        SharedPreferences preferences = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String token = preferences.getString("Authentication_Id", "");

        Warning warning = new Warning(token);
        clientAPI = clientAPI.getInstance();

        clientAPI.getRegisterService().giveWarning(username, warning ).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> r) {
                if(r.isSuccessful()) {
                  //  Toast.makeText(getActivity(), r.body().toString(), Toast.LENGTH_SHORT).show();
                    changeScreen();
                }else
                    Toast.makeText(getActivity(), r.body().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
               // Toast.makeText(getActivity(), "Failed to do warning", Toast.LENGTH_SHORT).show();
                changeScreen();

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
                    follow.setText(UNFOLLOW);
                    followers.setText(FOLLOWERS + (nFollowers + 1));
                    Toast.makeText(getActivity(), UNFOLLOW, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                follow.setText(UNFOLLOW);
                followers.setText(FOLLOWERS + (nFollowers + 1));
                Toast.makeText(getActivity(), UNFOLLOW, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void unfollow(){
        SharedPreferences preferences = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String token = preferences.getString("Authentication_Id", "");

        clientAPI = clientAPI.getInstance();
        Follow f = new Follow(token);
        clientAPI.getRegisterService().unfollow(username, f).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> r) {
                if(r.isSuccessful()) {
                    follow.setText(FOLLOW);
                    followers.setText(FOLLOWERS + (nFollowers));
                    Toast.makeText(getActivity(), FOLLOW, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                follow.setText(FOLLOW);
                followers.setText(FOLLOWERS + (nFollowers));
                Toast.makeText(getActivity(), FOLLOW, Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void changeScreen(){
        Intent intent = new Intent(getActivity(), MainActivity.class);

        startActivity(intent);
    }
    public void setValues(String username){
        this.username = username;
    }
}