package pt.unl.fct.di.example.bruh;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import pt.unl.fct.di.example.bruh.requests.RouteData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class AddRouteFragment extends Fragment {
    public static final String SHARED_PREFS = "sharedPrefs";
    private ClientAPI clientAPI;
    private String[] locations;
    private EditText routeName, info;
    private Button button;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_route, container, false);
        routeName = (EditText) view.findViewById(R.id.fragment_add_route_name);
        info = (EditText) view.findViewById(R.id.fragment_add_route_info);
        button = (Button) view.findViewById(R.id.fragment_add_route_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send();
            }
        });
        return view;
    }
    private void  send(){
        SharedPreferences preferences = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String username = preferences.getString("Authentication_username", "");
        String token = preferences.getString("Authentication_Id", "");

        clientAPI = clientAPI.getInstance();
        if (!routeName.getText().toString().matches("^[A-Za-z0-9 _]*[A-Za-z0-9][A-Za-z0-9 _]*$")) {
            Toast.makeText(getActivity(), "Incorrect character.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (routeName.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Invalid route name.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (info.getText().toString().equals("")){
            Toast.makeText(getActivity(), "Invalid info.", Toast.LENGTH_SHORT).show();
            return;
        }
        RouteData routeData = new RouteData(username,token,locations,routeName.getText().toString(), info.getText().toString());

        clientAPI.getRegisterService().createRoute(routeData).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> r) {
                if(r.isSuccessful()) {
                    Toast.makeText(getActivity(), "Route added", Toast.LENGTH_SHORT).show();

                    changeScreen();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed to create route", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setLocations(String location) {
        this.locations = new String[1];
        this.locations[0] = location;
    }
    private void changeScreen(){
        Intent intent = new Intent(getActivity(), MainActivity.class);

        startActivity(intent);
    }
}