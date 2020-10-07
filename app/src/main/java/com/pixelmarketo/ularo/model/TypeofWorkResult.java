package com.pixelmarketo.ularo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TypeofWorkResult {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("type_name")
    @Expose
    private String type_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }
}
