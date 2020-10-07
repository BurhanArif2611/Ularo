package com.pixelmarketo.ularo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CategoryResult implements Serializable {

    @SerializedName("category_id")
    @Expose
    private String category_id;
    @SerializedName("category_name")
    @Expose
    private String category_name;
    @SerializedName("category_image")
    @Expose
    private String category_image;
    @SerializedName("sub_cat")
    @Expose
    private List<SubCategory> result = null;
    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCategory_image() {
        return category_image;
    }

    public void setCategory_image(String category_image) {
        this.category_image = category_image;
    }

    public List<SubCategory> getResult() {
        return result;
    }

    public void setResult(List<SubCategory> result) {
        this.result = result;
    }
}
