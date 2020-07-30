package com.example.drpet;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.example.drpet.Model.DBManager;
import com.google.android.gms.location.places.Place;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class PetHospitalNearByFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<NearbyHospitals>>, SharedPreferences.OnSharedPreferenceChangeListener  {



    private static final int EARTHQUAKE_LOADER_ID = 1;
    NearbyAdapter mAdapter;
    public double lat,log;
    ListView earthquakeListView;


    private static final String LOG_TAG = PetHospitalNearByFragment.class.getName();

    /** URL for earthquake data from the USGS dataset */
    private static  String USGS_REQUEST_URL;
//    private static  String USGS_REQUEST_URL =
//            "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=37.3117633,-122.05722&rankby=distance&type=veterinary_care&key=AIzaSyB8-VBp-EES8iDNiB-9pWCCwR0YspOuPeY";

    private DBManager dbManager;
    NearbyAdapter nw;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      return inflater.inflate(R.layout.pethospital, container, false);



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("id_pref", Context.MODE_PRIVATE);
        final int user_id = pref.getInt("key_id", 0);

        dbManager = new DBManager(getActivity().getApplicationContext());
        dbManager.open();
        Cursor cursor = dbManager.fetchlocationData(user_id);
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            lat= cursor.getDouble(1);
            log = cursor.getDouble(2);
           Log.d("pet hospil nearby", "lat" + lat + ",long:"+log);



        }


//        ArrayList<NearbyHospitals> nearhospital = new ArrayList<>();
//        nearhospital.add(new NearbyHospitals("MARKHAM","23KM","2.3"));
//        nearhospital.add(new NearbyHospitals("MARKHAM","23KM","2.3"));
//        nearhospital.add(new NearbyHospitals("MARKHAM","23KM","2.3"));
//        nearhospital.add(new NearbyHospitals("MARKHAM","23KM","2.3"));
//        nearhospital.add(new NearbyHospitals("MARKHAM","23KM","2.3"));

//
        // Find a reference to the {@link ListView} in the layout
       final ListView earthquakeListView = (ListView) getView().findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of nearbbyhospitals
//
////        ArrayList<NearbyHospitals> earthquakes = QueryUtils.extractnearByhospital();
//        NearbyAdapter adapter = new NearbyAdapter(getActivity(), nearhospital);
//
//        // Set the adapter on the {@link ListView}
//        // so the list can be populated in the user interface
//        earthquakeListView.setAdapter(adapter);

        mAdapter = new NearbyAdapter(getActivity(), new ArrayList<NearbyHospitals>());


        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(mAdapter);

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//
//                Bundle args = new Bundle();
//                args.putInt("doctor_id",1234);
//                DetailedViewHospitalFragment newFragment = new DetailedViewHospitalFragment ();
//                newFragment.setArguments(args);

//
//
//
//                Log.e("name: ", "> " +  o.get("mName"));
//                Log.e("address: ", "> " +  o.get("Maddress"));
////                Log.e("uname: ", "> " +  o.get("uname"));
////                Log.e("password: ", "> " +  o.get("password"));
//
//
//                DetailedViewHospitalFragment fragment = new DetailedViewHospitalFragment();
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                Bundle bundle = new Bundle();
//                bundle.putString("name", o.get("mName"));
//                bundle.putString("address", o.get("Maddress"));
//
//                Log.e("name: ", "> " +  o.get("name"));
//                Log.e("address: ", "> " +  o.get("address"));
//
//                fragment.setArguments(bundle);
//                transaction.replace(R.id.fragment_container, new DetailedViewHospitalFragment() );
//
//                transaction.commit();

//                FragmentManager fm = getActivity().getFragmentManager();
//                Bundle arguments = new Bundle();
//                arguments.putInt("VALUE1", 0);
//                arguments.putInt("VALUE2", 100);
//
//
//                fm.beginTransaction().replace(R.id.fragment_container, new DetailedViewHospitalFragment(arguments)).commit();



////////////////////////////////////original commit
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new DetailedViewHospitalFragment()).commit();

            }
        });







//        new
        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = getActivity().findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
//
        }


    }






    @NonNull
    @Override
    public Loader<List<NearbyHospitals>> onCreateLoader(int id, @Nullable Bundle args) {

        USGS_REQUEST_URL =  "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" +lat+ "," +log+ "&rankby=distance&type=veterinary_care&key=AIzaSyB8-VBp-EES8iDNiB-9pWCCwR0YspOuPeY";
        Uri baseUri = Uri.parse(USGS_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        return new NearbyLoadr(getActivity(), uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<NearbyHospitals>> loader, List<NearbyHospitals> nearhsptls) {
        // Hide loading indicator because the data has been loaded
//        View loadingIndicator = getActivity().findViewById(R.id.loading_indicator);
//        loadingIndicator.setVisibility(View.GONE);


        if (null != nearhsptls) {
//            mAdapter.clear();
            mAdapter.addFeeds(nearhsptls);
            mAdapter.notifyDataSetChanged();
            Log.d("ONLOAD FINISHED", "INSIDE THE NEARHOSPITAL LOOP");

        }

        // Set empty state text to display "No earthquakes found."
//        mEmptyStateTextView.setText(R.string.no_earthquakes);

        // Clear the adapter of previous earthquake data
        //mAdapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.

    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<NearbyHospitals>> loader) {


        mAdapter.clear();

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }


}
