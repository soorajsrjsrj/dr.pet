package com.example.drpet;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.loader.content.AsyncTaskLoader;

import java.util.List;

public class NearbyLoadr extends AsyncTaskLoader<List<NearbyHospitals>> {

    /** Tag for log messages */
    private static final String LOG_TAG = NearbyLoadr.class.getName();

    /** Query URL */
    private String mUrl;


    public NearbyLoadr(@NonNull Context context,String url) {
        super(context);

        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<NearbyHospitals> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of earthquakes.
        List<NearbyHospitals> nearbyHospitals = QueryUtils.fetchhospitalsdata(mUrl);
        return nearbyHospitals;
    }
}