package com.mobileapp.wowapp.driver.model;

import java.io.Serializable;

public class ServiceProviderCompaign implements Serializable
{
    String name;
    String serviceProviderId;
    String compaignTitle;
    String appointmentStartTime;
    String appointmentEndTime;
    String compaignStartDate;
    String compaignEndDate;

    String stickerStartDate;
    String stickerEndDate;
    int newCompaignId;

    public int getNewCompaignId() {
        return newCompaignId;
    }

    public void setNewCompaignId(int newCompaignId) {
        this.newCompaignId = newCompaignId;
    }

    public String getStickerStartDate() {
        return stickerStartDate;
    }

    public void setStickerStartDate(String stickerStartDate) {
        this.stickerStartDate = stickerStartDate;
    }

    public String getStickerEndDate() {
        return stickerEndDate;
    }

    public void setStickerEndDate(String stickerEndDate) {
        this.stickerEndDate = stickerEndDate;
    }

    public String getCompaignStartDate() {
        return compaignStartDate;
    }

    public void setCompaignStartDate(String compaignStartDate) {
        this.compaignStartDate = compaignStartDate;
    }

    public String getCompaignEndDate() {
        return compaignEndDate;
    }

    public void setCompaignEndDate(String compaignEndDate) {
        this.compaignEndDate = compaignEndDate;
    }

    int timeInterval=0;

    public void setTimeInterval(int timeInterval) {
        this.timeInterval = timeInterval;
    }

    public int getTimeInterval() {
        return timeInterval;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompaignTitle() {
        return compaignTitle;
    }

    public String getServiceProviderId() {
        return serviceProviderId;
    }

    public void setServiceProviderId(String serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }

    public void setCompaignTitle(String compaignTitle) {
        this.compaignTitle = compaignTitle;
    }

    public String getAppointmentStartTime() {
        return appointmentStartTime;
    }

    public void setAppointmentStartTime(String appointmentStartTime) {
        this.appointmentStartTime = appointmentStartTime;
    }

    public String getAppointmentEndTime() {
        return appointmentEndTime;
    }

    public void setAppointmentEndTime(String appointmentEndTime) {
        this.appointmentEndTime = appointmentEndTime;
    }
}
