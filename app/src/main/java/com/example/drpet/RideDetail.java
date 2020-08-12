package com.example.drpet;

public class RideDetail {

    private String hospName;
    private String hospAddress;
    private String distance;
    private String price;

    public RideDetail(String hospName, String hospAddress, String distance, String price) {
        this.hospName = hospName;
        this.hospAddress = hospAddress;
        this.distance = distance;
        this.price = price;
    }

    public String getHospName() {
        return hospName;
    }

    public String getHospAddress() {
        return hospAddress;
    }

    public String getDistance() {
        return distance;
    }

    public String getPrice() {
        return price;
    }
}
