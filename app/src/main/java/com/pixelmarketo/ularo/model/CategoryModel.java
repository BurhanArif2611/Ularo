package com.pixelmarketo.ularo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CategoryModel implements Serializable {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("category")
    @Expose
    private List <CategoryResult> result = null;
    @SerializedName("advisement")
    @Expose
    private List <AdvertisementModel> ad = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<CategoryResult> getResult() {
        return result;
    }

    public void setResult(List<CategoryResult> result) {
        this.result = result;
    }

    public List<AdvertisementModel> getAd() {
        return ad;
    }

    public void setAd(List<AdvertisementModel> ad) {
        this.ad = ad;
    }
}
