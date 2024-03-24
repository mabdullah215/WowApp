package com.mobileapp.wowapp.model;

import com.mobileapp.wowapp.customer.model.City;
import com.mobileapp.wowapp.customer.model.CompaignDriverModel;
import com.mobileapp.wowapp.driver.model.Driver;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Compaign implements Serializable
{
    int id=0;
    String name;
    String customerId;
    int kms=0;
    String startTime;
    String endTime;
    int price=0;
    String design;
    int noOfCars=0;
    String status;
    City city;
    int totalDistanceCovered;
    int totalImpressions;
    int totalAssighnedDrivers;
    String duration;
    String start_datetime;
    String end_datetime;

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

    public int getTotalDistanceCovered() {
        return totalDistanceCovered;
    }

    public void setTotalDistanceCovered(int totalDistanceCovered) {
        this.totalDistanceCovered = totalDistanceCovered;
    }

    public int getTotalImpressions() {
        return totalImpressions;
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
