package com.example.drpet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.drpet.Model.DBManager;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    private DBManager dbManager;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new MenuFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_mainmenu);
        }

        Intent intent = getIntent();
        String email = intent.getStringExtra("email_key");

        Toast.makeText(MainActivity.this, email, Toast.LENGTH_SHORT).show();

        dbManager = new DBManager(MainActivity.this);
        dbManager.open();
        Cursor cursor = dbManager.fetchId(email);

        pref = getApplicationContext().getSharedPreferences("id_pref", MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();

        if (cursor.getCount() > 0){
            int u_id = cursor.getInt(0);
            System.out.println(u_id);

            edit.putInt("key_id", u_id);
            edit.commit();
        }




    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_mainmenu:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MenuFragment()).commit();
                break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileDetailFragment()).commit();
                break;
            case R.id.nav_pethospitalnear:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new PetHospitalNearByFragment()).commit();
                break;
            case R.id.nav_payment:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Payment()).commit();
                break;
            case R.id.nav_ridedetails:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new RideDetails()).commit();
                break;
            case R.id.nav_logout:
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("key_id", 0);
                editor.commit();

                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}