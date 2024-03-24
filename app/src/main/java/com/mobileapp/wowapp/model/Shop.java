package com.mobileapp.wowapp.model;

import java.io.Serializable;

public class Shop implements Serializable
{
    String city;
    String businessName;
    String name;
    String businessdetails;
    String businessaddress;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBusinessdetails() {
        return businessdetails;
    }

    public void setBusinessdetails(String businessdetails) {
        this.businessdetails = businessdetails;
    }

    public String getBusinessaddress() {
        return businessaddress;
    }

    public void setBusinessaddress(String businessaddress) {
        this.businessaddress = businessaddress;
    }
}
