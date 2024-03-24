package com.mobileapp.wowapp.customer.model;

public class Analytics
{
    String title;
    String desc;
    int count;

    public Analytics(String title, String desc, int count) {
        this.title = title;
        this.desc = desc;
        this.count = count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
