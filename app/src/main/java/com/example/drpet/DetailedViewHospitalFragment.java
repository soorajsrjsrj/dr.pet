package com.example.drpet;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.drpet.Model.DBManager;
import com.squareup.picasso.Picasso;

import static com.example.drpet.NearbyAdapter.calculateDistance;


public class DetailedViewHospitalFragment extends Fragment {

    int value1;
    public double endlat, endlog;
    private DBManager dbManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detailed_view_hospital, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        dbManager = new DBManager(getActivity().getApplicationContext());
        dbManager.open();
        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("id_pref", Context.MODE_PRIVATE);
        final int user_id = pref.getInt("key_id", 0);


        dbManager = new DBManager(getActivity().getApplicationContext());
        dbManager.open();
        Cursor cursor = dbManager.fetchlocationData(user_id);
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            endlat= cursor.getDouble(1);
            endlog = cursor.getDouble(2);
            Log.d("pet hospital nearby", "lat" + endlat + ",long:"+endlog);



        }

        Bundle b = this.getArguments();
        String hospname = b.getString("hospname");
        String hospaddress = b.getString("hospaddress");
        String hospimg = b.getString("hospimg");
        Double hosplat = b.getDouble("hosplat");
        Double hosplong = b.getDouble("hosplong");




        final TextView textfielname = (TextView) getView().findViewById(R.id.hospitalnme);
        final TextView textfieldaddress = (TextView) getView().findViewById(R.id.rating);
        final ImageView textfieldimg = (ImageView) getView().findViewById(R.id.hospitalicon);
        final TextView textfielddistance = (TextView) getView().findViewById(R.id.editkmdistance);
        final TextView textfielprice = (TextView) getView().findViewById(R.id.editpricedistance);


        textfielname.setText(hospname);
        textfieldaddress.setText(hospaddress);


        Double distance = calculateDistance(hosplat, hosplong, endlat, endlog);


        Double km = Double.valueOf(Math.round( distance / 1000));
//        Log.d("DETAILED view hospital", "distance...." + km);
        textfielddistance.setText(km.toString()+" km");
        textfielprice.setText(km.toString()+" CAD");

        String urlimage ="https://maps.googleapis.com/maps/api/place/photo?maxwidth=200&photoreference="+hospimg+"&sensor=false&key=AIzaSyB8-VBp-EES8iDNiB-9pWCCwR0YspOuPeY";
        Picasso.get().load(urlimage).into(textfieldimg);

    }
}