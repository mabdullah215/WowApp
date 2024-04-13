package com.mobileapp.wowapp.serviceprovider.model;


public class DateModel
{
    String date;
    int events=0;
    Long timestamp;
    String day="";
    String month="";

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public DateModel(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void addEvent()
    {
        events=events+1;
    }
    public int getEvents() {
        return events;
    }
    public void setEvents(int events) {
        this.events = events;
    }
}
