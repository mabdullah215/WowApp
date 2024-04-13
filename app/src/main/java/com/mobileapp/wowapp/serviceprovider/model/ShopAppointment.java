package com.mobileapp.wowapp.serviceprovider.model;


import java.io.Serializable;

public class ShopAppointment implements Serializable
{
    int id;
    int shop_id;
    int driver_id;
    String appointment_time;
    String created_at;
    String updated_at;
    String status;
    int campaign_id;
    String car_front;
    String car_back;
    String car_right;
    String car_left;
    int sticker_request;
    ShopDriver driver;
    ShopCampaign campaign;

    public ShopDriver getDriver() {
        return driver;
    }

    public void setDriver(ShopDriver driver) {
        this.driver = driver;
    }

    public ShopCampaign getCampaign() {
        return campaign;
    }

    public void setCampaign(ShopCampaign campaign) {
        this.campaign = campaign;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
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

    public String getDate()
    {
        return appointment_time.split(" ")[0];
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCampaign_id() {
        return campaign_id;
    }

    public void setCampaign_id(int campaign_id) {
        this.campaign_id = campaign_id;
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

    public String getCar_right() {
        return car_right;
    }

    public void setCar_right(String car_right) {
        this.car_right = car_right;
    }

    public String getCar_left() {
        return car_left;
    }

    public void setCar_left(String car_left) {
        this.car_left = car_left;
    }

    public int getSticker_request() {
        return sticker_request;
    }

    public void setSticker_request(int sticker_request) {
        this.sticker_request = sticker_request;
    }

    public class ShopDriver implements Serializable
    {
        int id;
        String name;
        String profile_pic;
        String car_make;
        String car_model;
        String car_type;
        String car_color;
        String car_year;
        int total_earning;

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

        public String getProfile_pic() {
            return profile_pic;
        }

        public void setProfile_pic(String profile_pic) {
            this.profile_pic = profile_pic;
        }

        public String getCar_make() {
            return car_make;
        }

        public void setCar_make(String car_make) {
            this.car_make = car_make;
        }

        public String getCar_model() {
            return car_model;
        }

        public void setCar_model(String car_model) {
            this.car_model = car_model;
        }

        public String getCar_type() {
            return car_type;
        }

        public void setCar_type(String car_type) {
            this.car_type = car_type;
        }

        public String getCar_color() {
            return car_color;
        }

        public void setCar_color(String car_color) {
            this.car_color = car_color;
        }

        public String getCar_year() {
            return car_year;
        }

        public void setCar_year(String car_year) {
            this.car_year = car_year;
        }

        public int getTotal_earning() {
            return total_earning;
        }

        public void setTotal_earning(int total_earning) {
            this.total_earning = total_earning;
        }
    }

    public class ShopCampaign implements Serializable
    {
        int id;
        String name;
        String design;
        String start_datetime;
        String end_datetime;

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

        public String getDesign() {
            return design;
        }

        public void setDesign(String design) {
            this.design = design;
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
    }

}
