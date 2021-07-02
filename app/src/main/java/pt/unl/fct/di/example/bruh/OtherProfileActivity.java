package pt.unl.fct.di.example.bruh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import pt.unl.fct.di.example.bruh.requests.Logout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtherProfileActivity extends AppCompatActivity {
    public static final String SHARED_PREFS = "sharedPrefs";
    private Fragment perfil = new PerfilFragment();
    private String role;

    private ClientAPI clientAPI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent local = getIntent();
        String profile = local.getStringExtra("profileToLoad");

        setContentView(R.layout.activity_other_profile);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListner);
        getRole();
        if(role.equals("SU") || role.equals("ADMIN")) {
            OtherProfileRolesFragment p = new OtherProfileRolesFragment();
            p.setValues(profile);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, p).commit();
        } else {
            OtherProfileFragment p = new OtherProfileFragment();
            p.setValues(profile);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, p).commit();
        }
    }

    private void getRole() {
        SharedPreferences preferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        role = preferences.getString("Authentication_role", "");
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
                return true;
            case R.id.maps:
                maps();
                Toast.makeText(this, "Maping", Toast.LENGTH_LONG).show();
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
        Intent intent = new Intent(this, MapsActivity.class);

        startActivity(intent);
    }


}