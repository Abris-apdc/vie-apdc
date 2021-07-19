package pt.unl.fct.di.example.bruh;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class SettingsFragment extends Fragment {
    public String role;
    private Button edit, photo,password, delete;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        edit = (Button) view.findViewById(R.id.fraagment_settings_edit);
        photo = (Button) view.findViewById(R.id.fraagment_settings_photo);
        password = (Button) view.findViewById(R.id.fraagment_settings_password);
        delete = (Button) view.findViewById(R.id.fraagment_settings_delete);


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit();
            }
        });
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photo();
            }
        });
        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });
        return view;
    }

    private void edit(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        if (role.equals("ORG")) {
            fragmentManager.beginTransaction().replace(R.id.fragment_container, new ModifyOrgFragment()).commit();
        } else
            fragmentManager.beginTransaction().replace(R.id.fragment_container, new ModifyFragment()).commit();
    }

    private void photo(){
      //  Intent intent = new Intent(getActivity(), PerfilPhotoActivity.class);
        //startActivity(intent);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, new ChangePerfilPhotoFragment()).commit();
    }
    private void password(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, new ChangePasswordFragment()).commit();
    }

    private void delete(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, new DeleteFragment()).commit();
    }

    public void setRole(String role) {
        this.role = role;
    }
}
