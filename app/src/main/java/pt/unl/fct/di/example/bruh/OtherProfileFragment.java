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

import java.util.List;

import pt.unl.fct.di.example.bruh.requests.Follow;
import pt.unl.fct.di.example.bruh.requests.IsFollowing;
import pt.unl.fct.di.example.bruh.requests.UserInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class OtherProfileFragment extends Fragment {
    public static final String SHARED_PREFS = "sharedPrefs";
    private static final String FOLLOW = "Follow";
    private static final String UNFOLLOW = "Unfollow";
    private static final String FOLLOWING = "Following: ";
    private static final String FOLLOWERS = "Followers: ";
    private String username;
    private TextView profile, fn,ln,role;
    private Button follow;
    private TextView following, followers;
    private int nFollowers;

    private ClientAPI clientAPI;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_other_profile, container, false);
        profile = (TextView) view.findViewById(R.id.set_profile);
        fn = (TextView) view.findViewById(R.id.other_profile_fn);
        ln = (TextView) view.findViewById(R.id.other_profile_ln);
        role = (TextView) view.findViewById(R.id.other_profile_role);
        following = (TextView) view.findViewById(R.id.other_profile_following);
        followers = (TextView) view.findViewById(R.id.other_profile_followers);
        follow = (Button) view.findViewById(R.id.other_profile_follow);

        profile.setText(username);
        followers();
        following();
        isFollowing();
        doRequest();


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

        return view;
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

    private void doRequest() {

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
                    follow.setText(UNFOLLOW);
                    followers.setText(FOLLOWERS + (nFollowers +1));
                    Toast.makeText(getActivity(), UNFOLLOW, Toast.LENGTH_SHORT).show();
                }else {
                  //  Toast.makeText(getActivity(), "Failed to get user profile", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                follow.setText(UNFOLLOW);
                followers.setText(FOLLOWERS + (nFollowers +1));
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

    public void setValues(String username){
        this.username = username;
    }
}
