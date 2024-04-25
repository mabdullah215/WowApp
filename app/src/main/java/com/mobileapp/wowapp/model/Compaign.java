package com.mobileapp.wowapp.model;


import android.icu.text.DecimalFormat;

import java.io.Serializable;

public class Compaign implements Serializable
{
    int id=0;
    String name;
    int customer_id;
    String customerId;
    int kms=0;
    String startTime;
    String endTime;
    int price=0;
    String design;
    int noOfCars=0;
    int no_of_cars;
    int city_id;
    int kms_per_day;
    String interval_start;
    String interval_end;
    double distance_covered;
    String customer_name;
    String shop_name;
    String status;
    City city;
    double totalDistanceCovered;
    double totalImpressions;
    int totalAssighnedDrivers;
    String duration;
    String start_datetime;
    String end_datetime;

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getNo_of_cars() {
        return no_of_cars;
    }

    public void setNo_of_cars(int no_of_cars) {
        this.no_of_cars = no_of_cars;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public int getKms_per_day() {
        return kms_per_day;
    }

    public void setKms_per_day(int kms_per_day) {
        this.kms_per_day = kms_per_day;
    }

    public String getInterval_start() {
        return interval_start;
    }

    public void setInterval_start(String interval_start) {
        this.interval_start = interval_start;
    }

    public String getInterval_end() {
        return interval_end;
    }

    public void setInterval_end(String interval_end) {
        this.interval_end = interval_end;
    }

    public double getDistance_covered() {
        return distance_covered;
    }

    public void setDistance_covered(double distance_covered) {
        this.distance_covered = distance_covered;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getStart_datetime() {
        return start_datetime;
    }

    public void setStart_datetime(String start_datetime) {
        this.start_datetime = start_datetime;
    }

    public String getEnd_datetime() {
        return end_datetime;
    }

    public void setEnd_datetime(String end_datetime) {
        this.end_datetime = end_datetime;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getKms() {
        return kms;
    }

    public void setKms(int kms) {
        this.kms = kms;
    }

    public String getStartTime()
    {
        if(startTime==null)
        {
            return start_datetime;
        }
        else
        {
            return startTime;
        }
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime()
    {
        if(endTime==null)
        {
            return end_datetime;
        }
        else
        {
            return endTime;
        }
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getNoOfCars() {
        return noOfCars;
    }

    public void setNoOfCars(int noOfCars) {
        this.noOfCars = noOfCars;
    }

    public double getTotalDistanceCovered()
    {
        DecimalFormat df = new DecimalFormat("0.0");
        String roundedValueStr = df.format(totalDistanceCovered);
        double roundedValue = Double.parseDouble(roundedValueStr);
        return roundedValue;
    }

    public void setTotalDistanceCovered(int totalDistanceCovered) {
        this.totalDistanceCovered = totalDistanceCovered;
    }

    public double getTotalImpressions()
    {
        DecimalFormat df = new DecimalFormat("0.0");
        String roundedValueStr = df.format(totalImpressions);
        double roundedValue = Double.parseDouble(roundedValueStr);
        return roundedValue;
    }

    public void setTotalImpressions(int totalImpressions) {
        this.totalImpressions = totalImpressions;
    }

    public int getTotalAssighnedDrivers() {
        return totalAssighnedDrivers;
    }

    public void setTotalAssighnedDrivers(int totalAssighnedDrivers) {
        this.totalAssighnedDrivers = totalAssighnedDrivers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDesign() {
        return design;
    }

    public void setDesign(String design) {
        this.design = design;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }



}
