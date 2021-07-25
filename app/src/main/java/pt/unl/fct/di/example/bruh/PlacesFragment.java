package pt.unl.fct.di.example.bruh;

import androidx.fragment.app.Fragment;

import android.content.Intent;
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

import pt.unl.fct.di.example.bruh.requests.Delete;
import pt.unl.fct.di.example.bruh.requests.EventInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class PlacesFragment  extends Fragment {
    private ClientAPI clientAPI;
    ListView listView;
    TextView textView;
    ArrayAdapter<String> arrayAdapter;
    String[] teste;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_places,container,false);
        listView = (ListView) view.findViewById(R.id.fragment_listview_places);
        textView = (TextView) view.findViewById(R.id.fragment_textview_places);
        request();
        return view;
    }
    public void onItemClick(ArrayAdapter<String> adapter, View v, int position, long id) {
        String item = (String) adapter.getItem(position);
    }

    private void request(){
        clientAPI = clientAPI.getInstance();

        clientAPI.getRegisterService().gettAllEvents().enqueue(new Callback<List<EventInfo>>() {
            @Override
            public void onResponse(Call<List<EventInfo>> call, Response<List<EventInfo>> r) {
                if(r.isSuccessful()) {
                    if(r.body().size() > 0) {
                        teste = new String[r.body().size()];
                        for (int i = 0; i < r.body().size(); i++) {
                            teste[i] = r.body().get(i).getEvent();
                        }
                        search();
                    } else {
                        textView.setText("     There are \n    no events");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<EventInfo>> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed to delete user", Toast.LENGTH_SHORT).show();
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
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                EventFragment st = new EventFragment();
                st.setEventName(item);
                fragmentManager.beginTransaction().replace(R.id.fragment_container, st).commit();
            }
        });
        textView.setText("  All events \n are loaded");
    }
}





