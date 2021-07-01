package pt.unl.fct.di.example.bruh;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import pt.unl.fct.di.example.bruh.requests.ModifyOrg;
import pt.unl.fct.di.example.bruh.requests.ModifyUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class ModifyOrgFragment extends Fragment {
    public static final String SHARED_PREFS = "sharedPrefs";
    private ClientAPI clientAPI;
    private EditText name, phone, address, country, description,serviceType,cardID, owner;

    private Button update;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modify_org, container, false);
        name = (EditText) view.findViewById(R.id.activity_modify_org_name);
        phone = (EditText) view.findViewById(R.id.activity_modify_org_phone);
        address = (EditText) view.findViewById(R.id.activity_modify_org_adress);
        country = (EditText) view.findViewById(R.id.activity_modify_org_country);
        description = (EditText) view.findViewById(R.id.activity_modify_org_description);
        serviceType = (EditText) view.findViewById(R.id.activity_modify_org_service);
        cardID = (EditText) view.findViewById(R.id.activity_modify_org_card);
        owner = (EditText) view.findViewById(R.id.activity_modify_org_owner);

        update = (Button) view.findViewById(R.id.activity_modify_org_update);



        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update(v);
            }
        });
        return view;
    }


    private void update(View v){
        SharedPreferences preferences = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String token = preferences.getString("Authentication_Id", "");

        clientAPI = clientAPI.getInstance();



        ModifyOrg u = new ModifyOrg(
                name.getText().toString(),
                phone.getText().toString(),
                address.getText().toString(),
                country.getText().toString(),
                description.getText().toString(),
                serviceType.getText().toString(),
                cardID.getText().toString(),
                owner.getText().toString(),
                token
        );

        clientAPI.getRegisterService().updateOrg(u).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> r) {
                if(r.isSuccessful()) {
                    Toast.makeText(getActivity(), "Organisation Update", Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container, new PerfilFragment()).commit();
                }else
                    Toast.makeText(getActivity(), "Failed to Update", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed to Update", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
