package pt.unl.fct.di.example.bruh;

import android.annotation.SuppressLint;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.location.FusedLocationProviderClient;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class AddPlaceActivity extends AppCompatActivity {
    private EditText newPlaceAddress;
    private EditText newPlaceDescription;
    private EditText newPlaceName;
    private SupportMapFragment supportMapFragment;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LatLng placeLocation = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);
        this.fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        this.newPlaceName = (EditText)this.findViewById(R.id.activity_add_place_name);
        this.newPlaceDescription = (EditText)this.findViewById(R.id.activity_add_place_description);
        this.newPlaceAddress  = (EditText)this.findViewById(R.id.activity_add_place_address);
        this.supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.maps);
        getCurrentLocation();


    }



    private void getCurrentLocation() {
        this.supportMapFragment.getMapAsync(new OnMapReadyCallback(){


            @SuppressLint("WrongConstant")
            public void onMapReady(final GoogleMap googleMap) {
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener(){


                    public void onMapClick(LatLng latLng) {
                        AddPlaceActivity.this.placeLocation = latLng;
                        googleMap.clear();
                        googleMap.addMarker(new MarkerOptions().position(AddPlaceActivity.this.placeLocation));
                        Geocoder geocoder = new Geocoder(AddPlaceActivity.this, Locale.getDefault());
                        List list = null;
                        try {
                            list = geocoder.getFromLocation(AddPlaceActivity.this.placeLocation.latitude, AddPlaceActivity.this.placeLocation.longitude, 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (list.size() > 0) {
                            String string2 = ((Address)list.get(0)).getAddressLine(0);
                            AddPlaceActivity.this.newPlaceAddress.setText((CharSequence)string2);
                        }
                        return;
                    }
                });
                if (Build.VERSION.SDK_INT >= 23) {
                    if (AddPlaceActivity.this.getApplicationContext().checkSelfPermission("android.permission.ACCESS_FINE_LOCATION") == 0) {
                       AddPlaceActivity.this.fusedLocationProviderClient.getLastLocation().addOnSuccessListener((OnSuccessListener)new OnSuccessListener<Location>(){


                            public void onSuccess(Location location) {
                                if (location != null) {
                                   LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom((LatLng)latLng, (float)15.0f));
                                }
                            }
                        });
                        return;
                    }
                    AddPlaceActivity.this.requestPermissions(new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 44);
                }
            }

        });
    }
}