package pt.unl.fct.di.example.bruh;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import pt.unl.fct.di.example.bruh.requests.ChangePassword;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class ChangePasswordFragment  extends Fragment {
    public static final String SHARED_PREFS = "sharedPrefs";
    private ClientAPI clientAPI;
    private EditText oldPass, newPass, confirmation;
    private Button send;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_password,container,false);

        oldPass = (EditText) view.findViewById(R.id.fragment_password_old);
        newPass = (EditText) view.findViewById(R.id.fragment_password_new);
        confirmation = (EditText) view.findViewById(R.id.fragment_password_confirmation);
        send = (Button) view.findViewById(R.id.fragment_password_send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(v);
            }
        });
        return view;
    }

    private void delete(View v){
        SharedPreferences preferences = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        String token = preferences.getString("Authentication_Id", "");

        if (newPass.getText().toString().length() < 9){
            Toast.makeText(getActivity(), "New password is  to short.", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!newPass.getText().toString().equals(confirmation.getText().toString())){
            Toast.makeText(getActivity(), "Password don't match.", Toast.LENGTH_SHORT).show();
            return;
        }

        clientAPI = clientAPI.getInstance();

        ChangePassword cp = new ChangePassword(
                oldPass.getText().toString(),
                newPass.getText().toString(),
                confirmation.getText().toString(),
                token
        );

        clientAPI.getRegisterService().updatePassword(cp).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> r) {
                if(r.isSuccessful()) {
                    Toast.makeText(getActivity(), "Password was changed", Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragment_container, new PerfilFragment()).commit();
                }else
                    Toast.makeText(getActivity(), "Failed to change password", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed to change password", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
