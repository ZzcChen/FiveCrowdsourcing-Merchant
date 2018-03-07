package com.example.administrator.fivecrowdsourcing_merchant.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/3/6.
 */

public class AddressInfo implements Serializable {
    private double latitude;//纬度
    private double longtitude;//经度
    private String city;//市
    private String district;//区
    private String street;//街道

    public AddressInfo() {
    }

    public AddressInfo(double latitude, double longtitude, String city, String district, String street) {
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.city = city;
        this.district = district;
        this.street = street;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
