package com.mobileapp.wowapp.model;

import com.mobileapp.wowapp.serviceprovider.model.ServiceProvider;

import java.io.Serializable;

public class Appointment implements Serializable
{
    int id;
    int driver_id;
    String appointment_time;
    String created_at;
    String car_front;
    String car_back;
    String car_left;
    String car_right;
    String updated_at;
    int campaign_id;
    int sticker_request;
    String status;
    int shop_id;
    ServiceProvider shop;
    Compaign campaign;

    public ServiceProvider getShop() {
        return shop;
    }

    public void setShop(ServiceProvider shop) {
        this.shop = shop;
    }

    public Compaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Compaign campaign) {
        this.campaign = campaign;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(int driver_id) {
        this.driver_id = driver_id;
    }

    public String getAppointment_time() {
        return appointment_time;
    }

    public void setAppointment_time(String appointment_time) {
        this.appointment_time = appointment_time;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getCar_front() {
        return car_front;
    }

    public void setCar_front(String car_front) {
        this.car_front = car_front;
    }

    public String getCar_back() {
        return car_back;
    }

    public void setCar_back(String car_back) {
        this.car_back = car_back;
    }

    public String getCar_left() {
        return car_left;
    }

    public void setCar_left(String car_left) {
        this.car_left = car_left;
    }

    public String getCar_right() {
        return car_right;
    }

    public void setCar_right(String car_right) {
        this.car_right = car_right;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getCampaign_id() {
        return campaign_id;
    }

    public void setCampaign_id(int campaign_id) {
        this.campaign_id = campaign_id;
    }

    public int getSticker_request() {
        return sticker_request;
    }

    public void setSticker_request(int sticker_request) {
        this.sticker_request = sticker_request;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }
}
