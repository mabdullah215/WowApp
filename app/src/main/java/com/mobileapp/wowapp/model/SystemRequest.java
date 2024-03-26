package com.mobileapp.wowapp.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SystemRequest implements Serializable
{
    int id;
    String comments;
    String driver_id;
    String created_at;
    String updated_at;
    String expiry_at;
    int no_of_photos;
    List<Photos>photos=new ArrayList<>();
    String status;
    String rejection_note;
    String title="";
    String description;

    public class Photos implements Serializable
    {
        int id;
        int special_request_id;
        String name;
        String link;
        String created_at;
        String updated_at;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getSpecial_request_id() {
            return special_request_id;
        }

        public void setSpecial_request_id(int special_request_id) {
            this.special_request_id = special_request_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
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
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(String driver_id) {
        this.driver_id = driver_id;
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

    public String getExpiry_at() {
        return expiry_at;
    }

    public void setExpiry_at(String expiry_at) {
        this.expiry_at = expiry_at;
    }

    public int getNo_of_photos() {
        return no_of_photos;
    }

    public void setNo_of_photos(int no_of_photos) {
        this.no_of_photos = no_of_photos;
    }


    public List<Photos> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photos> photos) {
        this.photos = photos;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRejection_note() {
        return rejection_note;
    }

    public void setRejection_note(String rejection_note) {
        this.rejection_note = rejection_note;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
