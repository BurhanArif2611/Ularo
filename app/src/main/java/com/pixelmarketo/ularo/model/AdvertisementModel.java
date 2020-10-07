package com.pixelmarketo.ularo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdvertisementModel {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("adv_image")
    @Expose
    private String adv_image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdv_image() {
        return adv_image;
    }

    public void setAdv_image(String adv_image) {
        this.adv_image = adv_image;
    }
}
