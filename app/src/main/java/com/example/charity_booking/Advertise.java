package com.example.charity_booking;

public class Advertise {
    String charity;
    String charityid;
    String description;
    String downloadlink;

    public Advertise(String charity, String charityid, String description, String downloadlink) {
        this.charity = charity;
        this.charityid = charityid;
        this.description = description;
        this.downloadlink = downloadlink;
    }

    public String getCharity() {
        return charity;
    }

    public void setCharity(String charity) {
        this.charity = charity;
    }

    public String getCharityid() {
        return charityid;
    }

    public void setCharityid(String charityid) {
        this.charityid = charityid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDownloadlink() {
        return downloadlink;
    }

    public void setDownloadlink(String downloadlink) {
        this.downloadlink = downloadlink;
    }
}
