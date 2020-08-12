package com.example.drpet;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.drpet.Model.DBManager;

import java.util.ArrayList;

public class RideDetails extends Fragment {
    private DBManager dbManager;
    int user_id;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ridedetails, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        dbManager = new DBManager(getActivity().getApplicationContext());
        dbManager.open();
        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("id_pref", Context.MODE_PRIVATE);
        user_id = pref.getInt("key_id", 0);


        dbManager = new DBManager(getActivity().getApplicationContext());
        dbManager.open();
        Cursor cursor = dbManager.fetchrideData(user_id);

//        if (0 <= cursor.getCount()){
//            Log.d("the Ride detail ","instructions"+cursor.getString(9));
//        }
        ListView myListview = getView().findViewById(R.id.ridedetail);


        ArrayList<RideDetail> dogList = dbManager.getAllData();

        RideAdapter myAdapter = new RideAdapter(dogList, this.getActivity());
        myListview.setAdapter(myAdapter);
    }
}
