package com.example.drpet;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.drpet.Model.DBManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;

public class MenuFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

//    public static final String TAG = FragmentLocation.class.getSimpleName();
    public static final int REQUEST_CODE_FOR_PERMISSIONS = 1;
    GoogleApiClient mGoogleApiClient;
    LatLng mLatLng;
    GoogleMap mGoogleMap;
    Marker mCurrLocationMarker;
    private LinearLayout linearMap;
    Location mLastLocation;
    LocationManager locationManager;
    boolean statusOfGPS;
    private Dialog mDialogGPS;
    View view;
    LocationRequest mLocationRequest;
    SupportMapFragment mFragment;
    FragmentManager fragmentManager;
    private DBManager dbManager;

    LinearLayout profile_page;
    LinearLayout payment_page;

    public double x3,x4;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("id_pref", Context.MODE_PRIVATE);
//        final int user_id = pref.getInt("key_id", 0);

        dbManager = new DBManager(getActivity().getApplicationContext());
        dbManager.open();
//        Cursor cursor = dbManager.fetchUserData(user_id);
//        dbManager.insertintolocation(x3, x4,user_id);

        view=inflater.inflate(R.layout.fragment_menu,container,false);
        fragmentManager=getChildFragmentManager();
        mFragment = (SupportMapFragment)fragmentManager.findFragmentById(R.id.map);
        mFragment.getMapAsync(this);
        if (!isGooglePlayServicesAvailable()) {
            Toast.makeText(getActivity(), "play services not available", Toast.LENGTH_SHORT).show();
        }
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        statusOfGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return view;



    }



    // database usage
//    SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("id_pref", Context.MODE_PRIVATE);
//    final int user_id = pref.getInt("key_id", 0);
//
//    Cursor cursor = dbManager.fetchUserData(user_id);

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("id_pref", Context.MODE_PRIVATE);
          final int user_id = pref.getInt("key_id", 0);




        //database
        dbManager = new DBManager(getActivity().getApplicationContext());
        dbManager.open();
        Cursor cursor = dbManager.fetchlocationData(user_id);

if(cursor.getCount()>0){
    int id = 1;
   dbManager.updateintolocation(id,x3,x4);
//    dbManager.insertintolocation(x3, x4, id);
    Log.d("location database", "inside the update loop");

}else {
    int id = user_id;
//    dbManager.updateintolocation(id,x3,x4);
    dbManager.insertintolocation(x3, x4, id);
    Log.d("location database", "inside the insert loop");
}




        profile_page = (LinearLayout) getView().findViewById(R.id.profile_page);
        payment_page = (LinearLayout) getView().findViewById(R.id.payment_page);

        profile_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getFragmentManager() != null;
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileDetailFragment()).commit();
            }
        });
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000); //5 seconds
        mLocationRequest.setFastestInterval(2000); //3 seconds
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        mLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        //new
        x3 = mLatLng.latitude;
        x4 = mLatLng.longitude;
        //location information inserting into database
//           SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("id_pref", Context.MODE_PRIVATE);
//        final int user_id = pref.getInt("key_id", 0);
//
//        Cursor cursor = dbManager.fetchUserData(user_id);


        int id = 1;
        dbManager.updateintolocation(id,x3, x4);
//        Toast.makeText(getActivity().getApplicationContext(), "insert location sucessfully to the database", Toast.LENGTH_SHORT).show();

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(mLatLng);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(mLatLng));
        // Zoom in the Google Map
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(15));

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED &&
                    getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mGoogleMap.setMyLocationEnabled(true);

            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION
                                , Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_CODE_FOR_PERMISSIONS);
            }
        } else {
            buildGoogleApiClient();
            mGoogleMap.setMyLocationEnabled(true);
        }

        //show dialog when click on location top-right side located on map.
        mGoogleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                statusOfGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                if (!statusOfGPS) {
//                    turnOnGps();
                } else {
                    getCurrentLocation(mLastLocation);
                }
                return false;
            }
        });
    }

    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, getActivity(), 0).show();
            return false;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_FOR_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED &&
                            getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                                    == PackageManager.PERMISSION_GRANTED) {
                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mGoogleMap.setMyLocationEnabled(true);
                    }

                } else {
                    if (!(shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) && (!(shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)))) {
                        Snackbar snackbar = Snackbar.make(linearMap, "Never asked"
                                , Snackbar.LENGTH_INDEFINITE);
                        snackbar.setAction("Allow", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                                intent.setData(uri);
                                startActivity(intent);
                            }
                        });
                        snackbar.show();
                    }
                }
                break;
        }
    }

    private void getCurrentLocation(Location location) {

        mLastLocation = location;

        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        if (locationManager != null) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mLastLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);//getting last location
            }
            if (mLastLocation != null) {
                if (mGoogleMap != null) {
                    Log.d("activity", "LOC by Network");
                    mLatLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());

                    x3 = mLatLng.latitude;
                    x4 = mLatLng.longitude;
                    //location information inserting into database
//                    SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("id_pref", Context.MODE_PRIVATE);
//                    final int user_id = pref.getInt("key_id", 0);
//
//                    Cursor cursor = dbManager.fetchUserData(user_id);
//
//                    int id = user_id;
//                    dbManager.insertintolocation(x3, x4,id);
//                    Toast.makeText(getActivity().getApplicationContext(), "insert location sucessfully to the database", Toast.LENGTH_SHORT).show();




                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(mLatLng);
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                    mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(mLatLng));
                    mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                }
            }
        }
    }
//
//    private void turnOnGps() {
//        if (mGoogleMap != null) {
//            mGoogleMap.clear();
//        }
//        statusOfGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);//getting status of gps whether gps is on or off.
//        mDialogGPS = new Dialog(getActivity(), R.style.MyDialogTheme);
//        mDialogGPS.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        mDialogGPS.setContentView(R.layout.dialog_turnongps);
//        TextView txtCancel = (TextView) mDialogGPS.findViewById(R.id.txtCancel);
//        TextView txtOK = (TextView) mDialogGPS.findViewById(R.id.txtSetting);
//
//        ImageView imgLocation = (ImageView) mDialogGPS.findViewById(R.id.imgLocation);
//
//        imgLocation.setImageResource(R.drawable.ic_location_my);
//
//        txtCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mDialogGPS.dismiss();
//                //finish();
//                if (!statusOfGPS) {
//                    getCurrentLocationByDefault();
//                } else {
//                    getCurrentLocation(mLastLocation);
//                }
//            }
//        });
//
//        txtOK.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //It is use to redirect to setting->location to turn on gps when press ok.
//                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
//                mDialogGPS.dismiss();
//                if (!statusOfGPS) {
//                    getCurrentLocationByDefault();
//                } else {
//                    getCurrentLocation(mLastLocation);
//                }
//            }
//        });
//        mDialogGPS.show();
//    }

    private void getCurrentLocationByDefault() {
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        if (mGoogleMap != null) {
            LatLng xFrom1 = new LatLng(0.0, 0.0);
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(xFrom1, (float) 0.0));

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(xFrom1);
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_ridetaxi));
            mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);
        } else {
            Log.i("MainActivity", "getCurrentLocationByDefault else");
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        statusOfGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (mDialogGPS != null) {
            if (mDialogGPS.isShowing()) {
                mDialogGPS.dismiss();
            }
        }
        if (!statusOfGPS) {
//            turnOnGps();
        } else {
            getCurrentLocation(mLastLocation);
        }

    }
    protected synchronized void buildGoogleApiClient() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient = null;
        }
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();}}