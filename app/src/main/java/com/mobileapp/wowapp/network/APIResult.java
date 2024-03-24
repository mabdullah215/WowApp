package com.mobileapp.wowapp.network;

import java.util.List;

public class APIResult
{
    String status;
    String statusCode ;
    String message;
    List<Object>data;
    Object singledata;

    public Object getSingledata() {
        return singledata;
    }

    public void setSingledata(Object singledata) {
        this.singledata = singledata;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
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
}
