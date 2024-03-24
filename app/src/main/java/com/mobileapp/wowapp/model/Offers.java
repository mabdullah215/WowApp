package com.mobileapp.wowapp.model;


public class Offers
{
    String id;
    String compaignid="";
    String title="";
    String city;
    int numofcars=0;
    double latitude;
    double longitude;
    double totalDistance=0;
    String startTime="";

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    String endtime="";
    String date="";
    String userid="";


    public String getCompaignid() {
        return compaignid;
    }

    public void setCompaignid(String compaignid) {
        this.compaignid = compaignid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumofcars() {
        return numofcars;
    }

    public void setNumofcars(int numofcars) {
        this.numofcars = numofcars;
    }

}
