package com.example.projectjune24.workerinfo;

public class prodBean {
    String name;
    String mobile;
    String address;
    String Special;
    String date;

    public prodBean(String name, String mobile, String address, String special, String date) {
        this.name = name;
        this.mobile = mobile;
        this.address = address;
        Special = special;
        this.date = date;
    }

    public prodBean() {
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getAddress() {
        return address;
    }

    public String getDate() {
        return date;
    }

    public String getSpecial() {
        return Special;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setSpecial(String special) {
        Special = special;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
