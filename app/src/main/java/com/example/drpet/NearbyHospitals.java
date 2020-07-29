package com.example.drpet;

public class NearbyHospitals {

    private String mPlaceId;

    private String mName;
    private String maddress;

    private double mstartLatitude;
    private String murlToImage;




    private double startLongitude;
    private double endlat;
    private double endlng;


    public NearbyHospitals(String mPlaceId,String mName, String mAddress, double mstartLatitude, double startLongitude, String urlToImage) {
        this.mPlaceId = mPlaceId;
        this.mName = mName;
        this.maddress = mAddress;
//        this.mRating = mRating;
        this.mstartLatitude = mstartLatitude;
        this.startLongitude = startLongitude;
        murlToImage = urlToImage;

    }
    public String getMurlToImage() {
        return murlToImage;
    }

    public void setEndlat(double endlat) {
        this.endlat = endlat;
    }



    public void setEndlng(double endlng) {
        this.endlng = endlng;
    }
//    public NearbyHospitals(String mName, String mDistance, String mRating) {
//        this.mName = mName;
//        this.mDistance = mDistance;
//        this.mRating = mRating;
//    }

//    public String getmName() {
////        return mName;
////    }
////
////    public String () {
////        return mDistance;
////    }
////
////    public String getmRating() {
////        return mRating;
////    }


    public String getmPlaceId() {
        return mPlaceId;
    }

    public String getmName() {
        return mName;
    }

    public String getMaddress() {
        return maddress;
    }



    public double getMstartLatitude() {
        return mstartLatitude;
    }

    public double getStartLongitude() {
        return startLongitude;
    }


}
