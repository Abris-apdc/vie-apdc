package pt.unl.fct.di.example.bruh;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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
import pt.unl.fct.di.example.bruh.requests.UpdateRoute;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class MyRoutesFragment extends Fragment {
    public static final String SHARED_PREFS = "sharedPrefs";
    private ClientAPI clientAPI;
    ListView listView;
    TextView textView;
    String[] teste;
    ArrayAdapter<String> arrayAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_my_routes, container, false);
        listView = (ListView) view.findViewById(R.id.fragment_listview_my_routes);
        textView = (TextView) view.findViewById(R.id.fragment_textview_my_routes);
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
                    }
                    textView.setText("Choose a route");
                }else
                    textView.setText("Ups, no routes");
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
                Toast.makeText(getActivity(), item, Toast.LENGTH_SHORT).show();
                changeScreen(item);
            }
        });
        textView.setText("      Routes \n are loaded");
    }


    public void setValues(List<String> list){
        teste = new String[list.size()];
        for(int i = 0; i < list.size(); i ++){
            teste[i] = list.get(i);
        }

    }

    private void changeScreen(String item){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        RoutesMapsFragment st = new RoutesMapsFragment();
        st.setTextView(item);
        fragmentManager.beginTransaction().replace(R.id.fragment_container, st).commit();
    }
}