package pt.unl.fct.di.example.bruh;

import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import java.util.List;

import pt.unl.fct.di.example.bruh.requests.EventInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class FavoritesFragment  extends Fragment {

    public static final String SHARED_PREFS = "sharedPrefs";
    private ClientAPI clientAPI;
    ListView listView;
    TextView textView;
    String id, role;
    ArrayAdapter<String> arrayAdapter;
    String[] teste;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_favorites,container,false);
        listView = (ListView) view.findViewById(R.id.fragment_favorites_list);
        textView = (TextView) view.findViewById(R.id.fragment_favorites_text);
        getStuff();
        if(role.equals("ORG"))
            orgRequest();
        else
            request();
        return view;
    }

    private void getStuff(){
        SharedPreferences preferences = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        id = preferences.getString("Authentication_username", "");
        role = preferences.getString("Authentication_role", "");

    }
    private void orgRequest(){
        clientAPI = clientAPI.getInstance();

        clientAPI.getRegisterService().getEventsByOrg(id).enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> r) {
                if(r.isSuccessful()) {
                    if (r.body().size() > 0) {
                        teste = new String[r.body().size()];
                        for (int i = 0; i < r.body().size(); i++) {
                            teste[i] = r.body().get(i);
                        }
                        search();
                    }else{
                        textView.setText("     You are not \n    owner of any \n         event");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed to get events", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void request(){


        clientAPI = clientAPI.getInstance();

        clientAPI.getRegisterService().getEventsByUser(id).enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> r) {
                if(r.isSuccessful()) {
                    if (r.body().size() > 0) {
                        teste = new String[r.body().size()];
                        for (int i = 0; i < r.body().size(); i++) {
                            teste[i] = r.body().get(i);
                        }
                        search();
                    }else{
                        textView.setText("     You are not \n    joined in any \n         event");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed to get events", Toast.LENGTH_SHORT).show();
            }
        });


    }
    private void search() {
        textView.setText("  Wait a little \n  is loading \n  the events");
        arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, teste);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) listView.getItemAtPosition(position);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                EventFragment st = new EventFragment();
                st.setEventName(item);
                fragmentManager.beginTransaction().replace(R.id.fragment_container, st).commit();
            }
        });
        if(role.equals("ORG"))
            textView.setText("  Your events \n     are loaded");
        else
            textView.setText("  Joined events \n     are loaded");
    }
}

