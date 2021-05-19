package pt.unl.fct.di.example.bruh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindActivity extends AppCompatActivity {
    public static final String SHARED_PREFS = "sharedPrefs";
    private Fragment perfil = new PerfilFragment();
    ListView listView;
    String[] teste;
    ArrayAdapter<String> arrayAdapter;
    private ClientAPI clientAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        listView = findViewById(R.id.listview);
        search("all");

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type here to Search.");
        searchView.setTranslationZ(1);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                search(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                return true;
            case R.id.maps_search:
                maps();
                Toast.makeText(this, "Maping", Toast.LENGTH_LONG).show();
                return true;
            case R.id.logout_search:
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
        String username = preferences.getString("Authentication_username", "");

        clientAPI = clientAPI.getInstance();


        Logout l = new Logout(
                username, token
        );
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Authentication_Id", "null");
        editor.putString("Authentication_username", "null");
        editor.apply();
        Toast.makeText(getApplicationContext(), l.getUsername() + "  " + l.getToken(), Toast.LENGTH_SHORT).show();
        clientAPI.getRegisterService().doLogout(l).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> r) {
                if (r.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "User logout", Toast.LENGTH_SHORT).show();
                    login();
                } else
                    Toast.makeText(getApplicationContext(), "Failed to logout", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed to logout", Toast.LENGTH_SHORT).show();
            }
        });


    }

    protected void login() {
        Intent intent = new Intent(this, LoginActivity.class);

        startActivity(intent);
    }

    protected void maps() {
        Intent intent = new Intent(this, MapsActivity.class);

        startActivity(intent);
    }

    private void search(String pattern) {
        clientAPI = clientAPI.getInstance();
        clientAPI.getRegisterService().getUserInfo(pattern).enqueue(new Callback<String[]>() {
            @Override
            public void onResponse(Call<String[]> call, Response<String[]> r) {


                if (r.isSuccessful()) {
                    teste = r.body();
                    arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, teste);
                    listView.setAdapter(arrayAdapter);

                }
            }

            @Override
            public void onFailure(Call<String[]> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error during search, try again", Toast.LENGTH_SHORT).show();
            }
            
        });
    }


}