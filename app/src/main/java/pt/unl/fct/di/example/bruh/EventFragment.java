package pt.unl.fct.di.example.bruh;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

import pt.unl.fct.di.example.bruh.requests.EventData;
import pt.unl.fct.di.example.bruh.requests.Follow;
import pt.unl.fct.di.example.bruh.requests.IsFollowing;
import pt.unl.fct.di.example.bruh.requests.IsInEvent;
import pt.unl.fct.di.example.bruh.requests.JoinEvent;
import pt.unl.fct.di.example.bruh.requests.LeaveEvent;
import pt.unl.fct.di.example.bruh.requests.UserInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class EventFragment extends Fragment {
    public static final String SHARED_PREFS = "sharedPrefs";
    private static final String JOIN = "Join";
    private static final String LEAVE = "Leave";
    private ClientAPI clientAPI;
    private String eventName;
    private TextView name;
    private TextView coordinates, address, duration;
    private String lat, lng;
    private Button participants, join, map, route, owner;
    private List<String> list;
    private int nParticipants;
    @SuppressLint("WrongViewCast")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_event, container, false);
        name = (TextView) view.findViewById(R.id.fragment_event_name);
        coordinates = (TextView) view.findViewById(R.id.fragment_event_coordinates);
        address = (TextView) view.findViewById(R.id.fragment_event_address);
        duration = (TextView) view.findViewById(R.id.fragment_event_duration);
        participants = (Button) view.findViewById(R.id.fragment_event_participants);
        join = (Button) view.findViewById(R.id.fragment_event_join);
        map = (Button) view.findViewById(R.id.fragment_event_map);
        route = (Button) view.findViewById(R.id.fragment_event_route);
        owner = (Button) view.findViewById(R.id.fragment_event_owner);
        name.setText(eventName);

        getEventData();
        isInEvent();
        getList();

        owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ownerStuff();
            }
        });

        route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRoute();
            }
        });
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map();
            }
        });
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (join.getText().equals(JOIN)){
                    join();
                } else{
                    leave();
                }
            }
        });

        participants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nParticipants == 0)
                    Toast.makeText(getActivity(), "This event does not have participants", Toast.LENGTH_SHORT).show();
                else {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FollowersFragment st = new FollowersFragment();
                    st.setValues(list);
                    fragmentManager.beginTransaction().replace(R.id.fragment_container, st).commit();
                }
            }
        });

        return view;
    }
    private void ownerStuff(){
        Intent intent = new Intent(getActivity(), OtherProfileActivity.class);
        intent.putExtra("profileToLoad", owner.getText().toString());

        startActivity(intent);
    }

    private void addRoute(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Do you want to create a new route or add this event to an existing route?")
                .setCancelable(false)
                .setPositiveButton("Create Route", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        AddRouteFragment st = new AddRouteFragment();
                        st.setLocations(eventName);
                        fragmentManager.beginTransaction().replace(R.id.fragment_container, st).commit();
                    }
                })
                .setNegativeButton("Add to Route", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        RoutesFragment st = new RoutesFragment();
                        st.setEventToAdd(eventName);
                        fragmentManager.beginTransaction().replace(R.id.fragment_container, st).commit();
                    }
        });

        final AlertDialog alert = builder.create();
        alert.show();
    }
    private void map(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        EventMapsFragment st = new EventMapsFragment();
        st.setCoords(lat,lng,eventName);
        fragmentManager.beginTransaction().replace(R.id.fragment_container, st).commit();
    }

    private void getEventData(){
        clientAPI = clientAPI.getInstance();

        clientAPI.getRegisterService().getEventData(eventName).enqueue(new Callback<EventData>() {
            @Override
            public void onResponse(Call<EventData> call, Response<EventData> r) {
                if(r.isSuccessful()) {
                    String[] cords = r.body().getCoordinates().split(", ");
                    String org = r.body().getOrg();
                    lat = cords[0];
                    lng = cords[1];
                    coordinates.setText("        Coordinates: \n" + lat+ "\n" + lng);
                    address.setText(r.body().getAddress());
                    duration.setText("Duration: "+ r.body().getDuration());
                    owner.setText(org);

                }else
                    Toast.makeText(getActivity(), "Failed to get user profile", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<EventData> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed to get user profile", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void isInEvent(){
        SharedPreferences preferences = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String token = preferences.getString("Authentication_Id", "");
        clientAPI = clientAPI.getInstance();
        IsInEvent f = new IsInEvent(token);
        clientAPI.getRegisterService().isInEvent(eventName,f).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> r) {
                if(r.isSuccessful()) {
                    join.setText(JOIN);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                join.setText(LEAVE);
            }
        });
    }
    private void join(){
        SharedPreferences preferences = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String token = preferences.getString("Authentication_Id", "");

        clientAPI = clientAPI.getInstance();
        JoinEvent f = new JoinEvent(eventName,token);
        clientAPI.getRegisterService().joinEvent(f).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> r) {
                if(r.isSuccessful()) {
                    join.setText(LEAVE);
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                join.setText(LEAVE);

            }
        });
    }

    private void leave(){
        SharedPreferences preferences = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String token = preferences.getString("Authentication_Id", "");

        clientAPI = clientAPI.getInstance();
        LeaveEvent f = new LeaveEvent(eventName,token);
        clientAPI.getRegisterService().leaveEvent(f).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> r) {
                if(r.isSuccessful()) {
                    join.setText(JOIN);
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                join.setText(JOIN);

            }
        });
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