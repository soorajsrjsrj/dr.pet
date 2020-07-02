package com.example.drpet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drpet.Model.DBManager;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    private DBManager dbManager;
    SharedPreferences pref;

    ImageView nav_profile;
    TextView fullname, nav_email;

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
        View header = navigationView.getHeaderView(0);

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

        nav_profile = header.findViewById(R.id.nav_user_profile_pic);
        fullname = header.findViewById(R.id.nav_user_name);
        nav_email = header.findViewById(R.id.nav_user_email);

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

        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("id_pref", Context.MODE_PRIVATE);
        final int user_id = pref1.getInt("key_id", 0);
        Cursor cursor1 = dbManager.fetchUserData(user_id);

        if (cursor1.getCount() > 0){

            String user_email = cursor1.getString(3);
            nav_email.setText(user_email.toString());

            System.out.println(cursor1.getString(3));
            String full = cursor1.getString(1) + " " + cursor1.getString(2);
            fullname.setText(full);

            byte[] pro_pic = cursor1.getBlob(6);
            System.out.println(pro_pic);

            Bitmap bt = getImage(pro_pic);
            nav_profile.setImageBitmap(bt);
        }



    }

    public static Bitmap getImage(byte[] image) {
        System.out.println("get back bitmap");
        System.out.println(image);
        return BitmapFactory.decodeByteArray(image, 0, image.length);
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