package pt.unl.fct.di.example.bruh;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import pt.unl.fct.di.example.bruh.requests.EventInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment  extends Fragment {
    private Double lat, lng;
    private String eventName;
    private ClientAPI clientAPI;
    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            request(googleMap);
           // LatLng sydney = new LatLng(38, -10);
            //googleMap.addMarker(new MarkerOptions().position(sydney).title(eventName));
            //googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
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
