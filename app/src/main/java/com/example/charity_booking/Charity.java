package com.example.charity_booking;

public class Charity {
    private String name;
    private String description;
    private String email;
    private String phone;
    private String address;
    private String charityid;

    public Charity(String name, String description, String email, String phone, String address, String charityid) {
        this.name = name;
        this.description = description;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.charityid = charityid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCharityid() {
        return charityid;
    }

    public void setCharityid(String charityid) {
        this.charityid = charityid;
    }
}
