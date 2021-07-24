package pt.unl.fct.di.example.bruh;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;


public class EventsByOrgFragment extends Fragment {

    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    String[] teste;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_events_by_org, container, false);
        listView = (ListView) view.findViewById(R.id.fragment_eventsByOrg_places);
        search();
        return view;
    }

    private void search() {

        arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, teste);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) listView.getItemAtPosition(position);
                Toast.makeText(getActivity(), item, Toast.LENGTH_SHORT).show();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                EventFragment st = new EventFragment();
                st.setEventName(item);
                fragmentManager.beginTransaction().replace(R.id.fragment_container, st).commit();
            }
        });
    }

    public void setValues(List<String> list){
        int u =list.size();
        teste = new String[u];
        for(int i = 0; i < list.size(); i++){
            teste[i] = list.get(i);
        }
    }
}