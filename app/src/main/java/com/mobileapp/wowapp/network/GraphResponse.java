package com.mobileapp.wowapp.network;

import java.util.List;

public class GraphResponse
{
    String status;
    String statusCode;
    String message;
    ResponseData data;
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ResponseData getData() {
        return data;
    }

    public void setData(ResponseData data) {
        this.data = data;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class ResponseData
    {
        double TotalKms;
        double TotalImpressions;
        List<GraphPoints> Analytics;

        public double getTotalKms() {
            return TotalKms;
        }

        public void setTotalKms(double totalKms) {
            TotalKms = totalKms;
        }

        public double getTotalImpressions() {
            return TotalImpressions;
        }

        public void setTotalImpressions(double totalImpressions) {
            TotalImpressions = totalImpressions;
        }

        public List<GraphPoints> getAnalytics() {
            return Analytics;
        }

        public void setAnalytics(List<GraphPoints> analytics) {
            Analytics = analytics;
        }
    }
    public class GraphPoints
    {
        String date;
        float kms=0;
        float impressions=0;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public float getKms() {
            return kms;
        }

        public void setKms(float kms) {
            this.kms = kms;
        }

        public float getImpressions() {
            return impressions;
        }

        public void setImpressions(float impressions) {
            this.impressions = impressions;
        }
    }

}
