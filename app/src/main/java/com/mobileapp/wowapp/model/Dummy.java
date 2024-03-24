package com.mobileapp.wowapp.model;

public class Dummy
{
    String name;
    String email;
    String userId;
    String password;
    String confirmPassword;
    String userName;
    String mobile;
    String userType;

    public Dummy(String name, String email, String userId, String password, String confirmPassword, String userName, String mobile, String userType) {
        this.name = name;
        this.email = email;
        this.userId = userId;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.userName = userName;
        this.mobile = mobile;
        this.userType = userType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
