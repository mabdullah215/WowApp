package com.mobileapp.wowapp.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class City implements Serializable
{
    int id;
    String name;
    double longitude;
    double latitude;
    int average_impression;
    double money_constant;

    public int getAverage_impression() {
        return average_impression;
    }

    public void setAverage_impression(int average_impression) {
        this.average_impression = average_impression;
    }

    public double getMoney_constant() {
        return money_constant;
    }

    public void setMoney_constant(double money_constant) {
        this.money_constant = money_constant;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

}
