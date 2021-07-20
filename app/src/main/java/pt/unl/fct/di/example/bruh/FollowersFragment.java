package pt.unl.fct.di.example.bruh;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;


public class FollowersFragment extends Fragment {

    ListView listView;
    String[] teste;
    ArrayAdapter<String> arrayAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view =  inflater.inflate(R.layout.fragment_followers, container, false);
        listView = (ListView) view.findViewById(R.id.fragment_listview);
        search();

        return view;
    }

    public void onItemClick(ArrayAdapter<String> adapter, View v, int position, long id) {
        String item = (String) adapter.getItem(position);
    }
    protected void profile(String item) {
        Intent intent = new Intent(getActivity(), OtherProfileActivity.class);
        intent.putExtra("profileToLoad", item);

        startActivity(intent);
    }
    private void search() {

        arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, teste);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) listView.getItemAtPosition(position);
                // Toast.makeText(getApplicationContext(), item, Toast.LENGTH_SHORT).show();
                profile(item);
            }
        });
    }

    public void setValues(List<String> list){
        teste = new String[list.size()];
        for(int i = 0; i < list.size(); i ++){
            teste[i] = list.get(i);
        }

    }
}