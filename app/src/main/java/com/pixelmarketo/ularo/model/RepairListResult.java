package com.pixelmarketo.ularo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RepairListResult {
    @SerializedName("ser_id")
    @Expose
    private String ser_id;
    @SerializedName("fname")
    @Expose
    private String fname;
    @SerializedName("mobile")
    @Expose
    private String mobile;

    public String getSer_id() {
        return ser_id;
    }

    public void setSer_id(String ser_id) {
        this.ser_id = ser_id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
