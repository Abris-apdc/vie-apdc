package pt.unl.fct.di.example.bruh;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class OtherProfileFragment extends Fragment {
    String username;
    TextView profile;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_other_profile, container, false);
        profile = (TextView) view.findViewById(R.id.set_profile);
        profile.setText(username);

        return view;
    }

    public void setValues(String username){
        this.username = username;
    }
}
