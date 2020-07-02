package com.example.drpet;

public class NearbyHospitals {

    private String mName;
    private String mDistance;
    private String mRating;

    public NearbyHospitals(String mName, String mDistance, String mRating) {
        this.mName = mName;
        this.mDistance = mDistance;
        this.mRating = mRating;
    }

    public String getmName() {
        return mName;
    }

    public String getmDistance() {
        return mDistance;
    }

    public String getmRating() {
        return mRating;
    }
}
