package com.example.drpet;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class PetHospitalNearByFragment extends Fragment{


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pethospital, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<NearbyHospitals> nearhospital = new ArrayList<>();
        nearhospital.add(new NearbyHospitals("MARKHAM","23KM","2.3"));
        nearhospital.add(new NearbyHospitals("MARKHAM","23KM","2.3"));
        nearhospital.add(new NearbyHospitals("MARKHAM","23KM","2.3"));
        nearhospital.add(new NearbyHospitals("MARKHAM","23KM","2.3"));
        nearhospital.add(new NearbyHospitals("MARKHAM","23KM","2.3"));


        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) getView().findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of nearbbyhospitals

        ArrayList<NearbyHospitals> earthquakes = QueryUtils.extractnearByhospital();
        NearbyAdapter adapter = new NearbyAdapter(getActivity(), nearhospital);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);


    }
}
