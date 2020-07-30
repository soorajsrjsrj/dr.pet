package com.example.drpet;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;



public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("id_pref", MODE_PRIVATE);

        int chckId = preferences.getInt("key_id", 0);
        System.out.println(chckId);

        if (chckId == 0){
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
        }else{
            startActivity(new Intent(this, MainActivity.class));
        }




        finish();
    }
}