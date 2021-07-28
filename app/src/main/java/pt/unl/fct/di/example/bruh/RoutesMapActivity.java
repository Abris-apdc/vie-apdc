package pt.unl.fct.di.example.bruh;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

import pt.unl.fct.di.example.bruh.helpers.FetchURL;
import pt.unl.fct.di.example.bruh.helpers.TaskLoadedCallback;
import pt.unl.fct.di.example.bruh.requests.DeleteRoute;
import pt.unl.fct.di.example.bruh.requests.EventInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoutesMapActivity extends AppCompatActivity implements TaskLoadedCallback, OnMapReadyCallback {
    public static final String SHARED_PREFS = "sharedPrefs";
    private Double lat, lng;
    private String eventName;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private ClientAPI clientAPI;
    private Button delete, button;
    private List<LatLng> coords = new ArrayList<>();
    private List<Marker> markers = new ArrayList<>();
    private MapFragment supportMapFragment;
    private GoogleMap gMap;
    private int listSize;
    private Polyline polyline = null;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes_map);
        this.fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        textView = findViewById(R.id.maps_routes_name);
        delete = findViewById(R.id.maps_routes_button);
        button = findViewById(R.id.maps_button);


        Intent local = getIntent();
        eventName = local.getStringExtra("routeToLoad");
        textView.setText(eventName);
        this.supportMapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.maps_routes_map);
        supportMapFragment.getMapAsync(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listSize >= 2) {
                    String url = getUrl();
                    new FetchURL(RoutesMapActivity.this).execute(url, "driving");
                } else
                    Toast.makeText(RoutesMapActivity.this, "Not enough events in this route", Toast.LENGTH_SHORT).show();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(RoutesMapActivity.this);
                builder.setMessage("Do you really want to delete this route?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                                delete();
                            }
                        });
                final AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    private void request() {
        clientAPI = clientAPI.getInstance();

        clientAPI.getRegisterService().getEventsByRoute(eventName).enqueue(new Callback<List<EventInfo>>() {
            @Override
            public void onResponse(Call<List<EventInfo>> call, Response<List<EventInfo>> r) {
                if (r.isSuccessful()) {
                    for (int i = 0; i < r.body().size(); i++) {
                        listSize = r.body().size();
                        EventInfo e = r.body().get(i);
                        String[] cords = e.getCoordinates().split(", ");
                        Double lat = Double.parseDouble(cords[0]);
                        Double lng = Double.parseDouble(cords[1]);
                        LatLng sydney = new LatLng(lat, lng);

                        MarkerOptions mo = new MarkerOptions().position(sydney).title(e.getEvent());
                        Marker m = gMap.addMarker(mo);
                        coords.add(sydney);
                        markers.add(m);
                        gMap.addMarker(mo);

                    }
                }
            }

            @Override
            public void onFailure(Call<List<EventInfo>> call, Throwable t) {
                Toast.makeText(RoutesMapActivity.this, "", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private String getUrl(){
        String origin = "origin=" + coords.get(0).latitude + "," + coords.get(0).longitude;
        String dest = "destination=" + coords.get(listSize-1).latitude + "," + coords.get(listSize-1).longitude;
        String parameters = origin + "&" + dest;
        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;

        if (listSize == 2) {
           return  (url  + "&key=" + getString(R.string.direct_key));
        }
        else {
            url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&waypoints=";

            for(int i = 1; i < listSize -1 ; i++){
                String s = coords.get(i).latitude + "," + coords.get(i).longitude;
                if (i == listSize -2 && i != 1){
                    s ="|"+ coords.get(i).latitude + "," + coords.get(i).longitude;
                }
                url = url + s;

            }
            return (url  + "&key=" + getString(R.string.direct_key));
        }
    }
    private void delete(){
        SharedPreferences preferences = this.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String token = preferences.getString("Authentication_Id", "");

        clientAPI = clientAPI.getInstance();

        DeleteRoute updateRoute = new DeleteRoute(token,eventName);
        clientAPI.getRegisterService().deleteRoute(updateRoute).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> r) {
                if (r.isSuccessful()) {
                    changeScreen();
                    Toast.makeText(RoutesMapActivity.this, r.body().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(RoutesMapActivity.this, "", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void changeScreen(){
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);
    }


    @Override
    public void onTaskDone(Object... values) {
        if(polyline != null )
            polyline.remove();
        polyline = gMap.addPolyline((PolylineOptions) values[0]);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;
        request();

        if (Build.VERSION.SDK_INT >= 23) {
            if (RoutesMapActivity.this.getApplicationContext().checkSelfPermission("android.permission.ACCESS_FINE_LOCATION") == 0) {
                fusedLocationProviderClient.getLastLocation().addOnSuccessListener((OnSuccessListener)new OnSuccessListener<Location>(){


                    public void onSuccess(Location location) {
                        if (location != null) {
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom((LatLng)latLng, (float)5.0f));
                        }
                    }
                });
                return;
            }
        }
    }
}