package pt.unl.fct.di.example.bruh;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import pt.unl.fct.di.example.bruh.requests.GetRoutes;
import pt.unl.fct.di.example.bruh.requests.RouteData;
import pt.unl.fct.di.example.bruh.requests.UpdateRoute;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class RoutesFragment extends Fragment {
    public static final String SHARED_PREFS = "sharedPrefs";
    private ClientAPI clientAPI;
    private String eventToAdd;
    ListView listView;
    TextView textView;
    String[] teste;
    ArrayAdapter<String> arrayAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_routes, container, false);
        listView = (ListView) view.findViewById(R.id.fragment_listview_routes);
        textView = (TextView) view.findViewById(R.id.fragment_textview_routes);
        request();

        return view;
    }
    private void request(){
        SharedPreferences preferences = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String username = preferences.getString("Authentication_username", "");
        String token = preferences.getString("Authentication_Id", "");

        clientAPI = clientAPI.getInstance();

        GetRoutes getRoutes = new GetRoutes(username,token);

        clientAPI.getRegisterService().getRoutes(getRoutes).enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> r) {
                if(r.isSuccessful()) {
                    if(r.body().size()>0) {
                        setValues(r.body());
                        search();
                        textView.setText("Choose a route");
                    }else
                        textView.setText("Ups, no routes");

                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed to create route", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void search() {

        arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, teste);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) listView.getItemAtPosition(position);
                updateRoute(item);
            }
        });
        textView.setText("      Routes \n are loaded");
    }
    private void updateRoute(String route){
        SharedPreferences preferences = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String token = preferences.getString("Authentication_Id", "");

        clientAPI = clientAPI.getInstance();

        UpdateRoute updateRoute = new UpdateRoute(token,eventToAdd);

        clientAPI.getRegisterService().updateRoute(route, updateRoute).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> r) {
                if(r.isSuccessful()) {
                    Toast.makeText(getActivity(), r.body().toString(), Toast.LENGTH_SHORT).show();
                    changeScreen();
                }
            }

            @Override
            public void onFailure(Call<String>call, Throwable t) {
                Toast.makeText(getActivity(), "Failed to create route", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setValues(List<String> list){
        teste = new String[list.size()];
        for(int i = 0; i < list.size(); i ++){
            teste[i] = list.get(i);
        }

    }

    public void setEventToAdd(String eventToAdd){
        this.eventToAdd = eventToAdd;
    }
    private void changeScreen(){
        Intent intent = new Intent(getActivity(), MainActivity.class);

        startActivity(intent);
    }
}