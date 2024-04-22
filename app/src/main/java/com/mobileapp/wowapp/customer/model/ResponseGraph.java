package com.mobileapp.wowapp.customer.model;

public class ResponseGraph
{
    String date;
    double kms=0;
    double impressions=0;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getKms() {
        return kms;
    }

    public void setKms(double kms) {
        this.kms = kms;
    }

    public double getImpressions() {
        return impressions;
    }

    public void setImpressions(double impressions) {
        this.impressions = impressions;
    }
}
