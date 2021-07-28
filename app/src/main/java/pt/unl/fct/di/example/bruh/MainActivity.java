package pt.unl.fct.di.example.bruh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import pt.unl.fct.di.example.bruh.requests.Logout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final int PERMISSIONS_REQUEST_ENABLE_GPS = 1;
    public static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 2;
    public static final int ERROR_DIALOG_REQUEST = 3;
    private Fragment perfil = new PerfilFragment();
    private boolean mLocationPermissionGranted = false;

    private ClientAPI clientAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListner);
        checkMapServices();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListner =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selected = null;
                    switch (item.getItemId()) {
                        case R.id.nav_home:

                            selected = new HomeFragment();
                            break;
                        case R.id.nav_favorites:
                            selected = new FavoritesFragment();
                            break;
                        case R.id.nav_places:
                            selected = new PlacesFragment();
                            break;
                        case R.id.nav_perfil:
                            selected = perfil;
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selected).commit();
                    return true;
                }
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_bruh:
                find();
                Toast.makeText(this, "Searching", Toast.LENGTH_LONG).show();
                return true;
            case R.id.logout:
                logout();
                Toast.makeText(this, "Bye!", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void logout() {

        SharedPreferences preferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String token = preferences.getString("Authentication_Id", "");

       clientAPI = clientAPI.getInstance();

      Logout l = new Logout(
            token
        );
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Authentication_Id", "null");
        editor.putString("Authentication_username", "null");
        editor.apply();

        clientAPI.getRegisterService().doLogout(l).enqueue(new Callback<String>() {
            @Override
           public void onResponse(Call<String> call, Response<String> r) {
                if(r.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "User logout", Toast.LENGTH_SHORT).show();
                    login();
                }else
                    Toast.makeText(getApplicationContext(), "Failed to logout", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed to logout", Toast.LENGTH_SHORT).show();
            }
        });



    }
    protected void login(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    protected void find(){
        Intent intent = new Intent(this, FindActivity.class);

        startActivity(intent);
    }

    protected void maps(){
        Intent intent = new Intent(this, AddPlaceActivity.class);

        startActivity(intent);
    }

    private boolean checkMapServices(){
        if(isServicesOK()){
            if(isMapsEnabled()){
                return true;
            }
        }
        return false;
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This application requires GPS to work properly, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        Intent enableGpsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(enableGpsIntent, PERMISSIONS_REQUEST_ENABLE_GPS);
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public boolean isMapsEnabled(){
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
            return false;
        }
        return true;
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    public boolean isServicesOK(){

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if(available == ConnectionResult.SUCCESS){

            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ENABLE_GPS: {
                if(mLocationPermissionGranted){

                }
                else{
                    getLocationPermission();
                }
            }
        }

    }


}