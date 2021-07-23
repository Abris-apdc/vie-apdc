package pt.unl.fct.di.example.bruh;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EventFragment extends Fragment {
    private ClientAPI clientAPI;
    private String eventName;
    private TextView name;
    private Button participants, join;
    private List<String> list;
    private int nParticipants;
    @SuppressLint("WrongViewCast")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_event, container, false);
        name = (TextView) view.findViewById(R.id.fragment_event_name);
        participants = (Button) view.findViewById(R.id.fragment_event_participants);
        join = (Button) view.findViewById(R.id.fragment_event_join);
        name.setText(eventName);
        getList();

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                join();
            }
        });

        participants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FollowersFragment st = new FollowersFragment();
                st.setValues(list);
                fragmentManager.beginTransaction().replace(R.id.fragment_container, st).commit();
            }
        });

        return view;
    }

    private void join(){

    }

    private void getList(){
        clientAPI = clientAPI.getInstance();

        clientAPI.getRegisterService().getParticipants(eventName).enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> r) {
                if(r.isSuccessful()) {
                    list= r.body();
                    nParticipants = r.body().size();
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed to get user followers", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}