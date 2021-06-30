package pt.unl.fct.di.example.bruh;

import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import pt.unl.fct.di.example.bruh.requests.Delete;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class DeleteFragment  extends Fragment {
    public static final String SHARED_PREFS = "sharedPrefs";
    private ClientAPI clientAPI;
    private EditText password;
    private Button send;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delete,container,false);

        password = (EditText) view.findViewById(R.id.fragment_delete_password);
        send = (Button) view.findViewById(R.id.fragment_delete_button);

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
        String username = preferences.getString("Authentication_username", "");
        String token = preferences.getString("Authentication_Id", "");

        clientAPI = clientAPI.getInstance();

        Delete delete = new Delete(
                username,
                //password.getText().toString(),
                token
        );

        clientAPI.getRegisterService().deleteUser(delete).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> r) {
                if(r.isSuccessful()) {
                    Toast.makeText(getActivity(), "User deleted", Toast.LENGTH_SHORT).show();

                    SharedPreferences preferences = getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();

                    editor.putString("Authentication_firstName", "");
                    editor.putString("Authentication_lastName", "");
                    editor.putString("Authentication_role", "");
                    editor.putString("Authentication_username", "");
                    editor.apply();
                    changeScreen();

                }else
                    Toast.makeText(getActivity(), "Failed to delete user", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed to delete user", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void changeScreen() {
        Intent intent;
        intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }
}
