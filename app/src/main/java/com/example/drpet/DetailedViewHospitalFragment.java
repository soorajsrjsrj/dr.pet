package com.example.drpet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;


public class DetailedViewHospitalFragment extends Fragment {

    int value1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = this.getArguments();
        int s = b.getInt("doctor_id");
           Log.d("detailed view hospital", "value...." + s);


        TextView textfielddetailed = (TextView) getView().findViewById(R.id.hospitalnme);
        textfielddetailed.setText(s);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        Bundle b = this.getArguments();
        int s = b.getInt("doctor_id");
           Log.d("detailed view hospital", "value...." + s);


        TextView textfielddetailed = (TextView) getView().findViewById(R.id.hospitalnme);
        textfielddetailed.setText(s);


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detailed_view_hospital, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//         TextView textfielddetailed = (TextView) getView().findViewById(R.id.detailname);
//         textfielddetailed.setText(value1);
    }
}