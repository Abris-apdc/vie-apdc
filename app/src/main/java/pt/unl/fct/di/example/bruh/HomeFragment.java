package pt.unl.fct.di.example.bruh;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

import pt.unl.fct.di.example.bruh.requests.EventInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment  extends Fragment {
    private Double lat, lng;
    private String eventName;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private ClientAPI clientAPI;
    private OnMapReadyCallback callback = new OnMapReadyCallback() {


        @SuppressLint("WrongConstant")
        @Override
        public void onMapReady(GoogleMap googleMap) {
            request(googleMap);
            if (Build.VERSION.SDK_INT >= 23) {
                if (getActivity().getApplicationContext().checkSelfPermission("android.permission.ACCESS_FINE_LOCATION") == 0) {
                    fusedLocationProviderClient.getLastLocation().addOnSuccessListener((OnSuccessListener)new OnSuccessListener<Location>(){


                        public void onSuccess(Location location) {
                            if (location != null) {
                                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom((LatLng)latLng, (float)9.0f));
                            }
                        }
                    });
                    return;
                }
                // getActivity().this.requestPermissions(new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 44);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home,container,false);
        this.fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.maps);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }


    private void request(GoogleMap googleMap){
        clientAPI = clientAPI.getInstance();

        clientAPI.getRegisterService().gettAllEvents().enqueue(new Callback<List<EventInfo>>() {
            @Override
            public void onResponse(Call<List<EventInfo>> call, Response<List<EventInfo>> r) {
                if(r.isSuccessful()) {
                    for(int i = 0; i < r.body().size(); i++){
                        EventInfo e = r.body().get(i);
                        String[] cords = e.getCoordinates().split(", ");
                        Double lat = Double.parseDouble(cords[0]);
                        Double lng =  Double.parseDouble(cords[1]);
                        LatLng sydney = new LatLng(lat, lng);
                        googleMap.addMarker(new MarkerOptions().position(sydney).title(e.getEvent()));

                    }
                }
            }

            @Override
            public void onFailure(Call<List<EventInfo>> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed to delete user", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
