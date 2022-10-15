package com.example.charity_booking;

public class Booking {
    private String name;
    private String date;
    private String time;
    private String donationtype;
    private String phone;

    public Booking(String name, String date, String time, String donationtype) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.donationtype = donationtype;
    }

    public Booking(String name, String date, String time, String donationtype, String phone) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.donationtype = donationtype;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDonationtype() {
        return donationtype;
    }

    public void setDonationtype(String donationtype) {
        this.donationtype = donationtype;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
