package com.mobileapp.wowapp.serviceprovider.model;

import java.io.Serializable;

public class ServiceProvider implements Serializable
{
    int userId;
    String email;
    String number;
    String type;
    String profilePic;
    String address;
    String birthday;
    String nationality;
    boolean verified;
    String bankName;
    String accountName;
    String iban;
    String businessDetails;
    String registrationNo;
    String operatingTime;
    String stickerOnDuration;
    String stickerOffDuration;
    String carsWorkedAtSameTime;
    String carsWorkedInADay;
    String id;
    String name;
    String businessName;
    String business_name;
    String businessStartTime;
    String businessEndTime;
    String breakStartTime;
    String breakEndTime;
    String profile_pic;
    int duration;
    String businessAddress;
    String logo;
    String city;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getBusinessDetails() {
        return businessDetails;
    }

    public void setBusinessDetails(String businessDetails) {
        this.businessDetails = businessDetails;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getOperatingTime() {
        return operatingTime;
    }

    public void setOperatingTime(String operatingTime) {
        this.operatingTime = operatingTime;
    }

    public String getStickerOnDuration() {
        return stickerOnDuration;
    }

    public void setStickerOnDuration(String stickerOnDuration) {
        this.stickerOnDuration = stickerOnDuration;
    }

    public String getStickerOffDuration() {
        return stickerOffDuration;
    }

    public void setStickerOffDuration(String stickerOffDuration) {
        this.stickerOffDuration = stickerOffDuration;
    }

    public String getCarsWorkedAtSameTime() {
        return carsWorkedAtSameTime;
    }

    public void setCarsWorkedAtSameTime(String carsWorkedAtSameTime) {
        this.carsWorkedAtSameTime = carsWorkedAtSameTime;
    }

    public String getCarsWorkedInADay() {
        return carsWorkedInADay;
    }

    public void setCarsWorkedInADay(String carsWorkedInADay) {
        this.carsWorkedInADay = carsWorkedInADay;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public String getId() {
        return id;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName()
    {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBusinessName()
    {
        if(businessName==null)
        {
            return business_name;
        }
        else
        {
            return businessName;
        }
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessStartTime() {
        return businessStartTime;
    }

    public void setBusinessStartTime(String businessStartTime) {
        this.businessStartTime = businessStartTime;
    }

    public String getBusinessEndTime() {
        return businessEndTime;
    }

    public void setBusinessEndTime(String businessEndTime) {
        this.businessEndTime = businessEndTime;
    }

    public String getBreakStartTime() {
        return breakStartTime;
    }

    public void setBreakStartTime(String breakStartTime) {
        this.breakStartTime = breakStartTime;
    }

    public String getBreakEndTime() {
        return breakEndTime;
    }

    public void setBreakEndTime(String breakEndTime) {
        this.breakEndTime = breakEndTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
