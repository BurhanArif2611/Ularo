package com.pixelmarketo.ularo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pixelmarketo.ularo.utility.ErrorMessage;

public class BidListResult implements Comparable<BidListResult>{
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("bid_id")
    @Expose
    private String bid_id;
    @SerializedName("user_id")
    @Expose
    private String user_id;
    @SerializedName("bidder_id")
    @Expose
    private String bidder_id;
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("user_name")
    @Expose
    private String user_name;

    @SerializedName("story_building")
    @Expose
    private String story_building;

    @SerializedName("area_in_square_feet")
    @Expose
    private String area_in_square_feet;
    @SerializedName("type_of_work")
    @Expose
    private String type_of_work;
    @SerializedName("map")
    @Expose
    private String map;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("paint_type")
    @Expose
    private String paint_type;
    @SerializedName("layer")
    @Expose
    private String layer;
    @SerializedName("dimension")
    @Expose
    private String dimension;
    @SerializedName("running_feet")
    @Expose
    private String running_feet;
    @SerializedName("type_of_glass")
    @Expose
    private String type_of_glass;
    @SerializedName("do_you_want_carpenter")
    @Expose
    private String do_you_want_carpenter;
    @SerializedName("no_of_kitchen")
    @Expose
    private String no_of_kitchen;
    @SerializedName("no_of_bathroom")
    @Expose
    private String no_of_bathroom;
    @SerializedName("no_of_room")
    @Expose
    private String no_of_room;
    @SerializedName("no_of_hall")
    @Expose
    private String no_of_hall;
    @SerializedName("no_of_basin")
    @Expose
    private String no_of_basin;
    @SerializedName("no_of_latrine")
    @Expose
    private String no_of_latrine;
    @SerializedName("purpose")
    @Expose
    private String purpose;
    @SerializedName("design")
    @Expose
    private String design;
    @SerializedName("service_type")
    @Expose
    private String service_type;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("created_at")
    @Expose
    private String created_at;
    @SerializedName("full_other")
    @Expose
    private String full_other;
    @SerializedName("no_of_point")
    @Expose
    private String no_of_point;
    @SerializedName("total_point")
    @Expose
    private String total_point;
    @SerializedName("total_point_6a")
    @Expose
    private String total_point_6a;
    @SerializedName("total_point_16a")
    @Expose
    private String total_point_16a;
    @SerializedName("no_of_mcb")
    @Expose
    private String no_of_mcb;
    @SerializedName("sub_cat")
    @Expose
    private String sub_cat;
    @SerializedName("bid_amount")
    @Expose
    private String bid_amount;
    @SerializedName("start_date")
    @Expose
    private String start_date;
    @SerializedName("end_date")
    @Expose
    private String end_date;
    @SerializedName("nego_amount")
    @Expose
    private String nego_amount;
    @SerializedName("total_day")
    @Expose
    private String total_days;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("angle_type")
    @Expose
    private String angle_type;
    @SerializedName("contact")
    @Expose
    private String contact;
    @SerializedName("pro_img")
    @Expose
    private String pro_img;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("rating")
    @Expose
    private float rating;
    @SerializedName("quick_mode")
    @Expose
    private String quick_mode;

    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("sub_cat_name")
    @Expose
    private String sub_cat_name;
    @SerializedName("total_job_work")
    @Expose
    private String total_job_work;
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getBidder_id() {
        return bidder_id;
    }

    public void setBidder_id(String bidder_id) {
        this.bidder_id = bidder_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getStory_building() {
        return story_building;
    }

    public void setStory_building(String story_building) {
        this.story_building = story_building;
    }

    public String getArea_in_square_feet() {
        return area_in_square_feet;
    }

    public void setArea_in_square_feet(String area_in_square_feet) {
        this.area_in_square_feet = area_in_square_feet;
    }

    public String getType_of_work() {
        return type_of_work;
    }

    public void setType_of_work(String type_of_work) {
        this.type_of_work = type_of_work;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPaint_type() {
        return paint_type;
    }

    public void setPaint_type(String paint_type) {
        this.paint_type = paint_type;
    }

    public String getLayer() {
        return layer;
    }

    public void setLayer(String layer) {
        this.layer = layer;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getRunning_feet() {
        return running_feet;
    }

    public void setRunning_feet(String running_feet) {
        this.running_feet = running_feet;
    }

    public String getType_of_glass() {
        return type_of_glass;
    }

    public void setType_of_glass(String type_of_glass) {
        this.type_of_glass = type_of_glass;
    }

    public String getDo_you_want_carpenter() {
        return do_you_want_carpenter;
    }

    public void setDo_you_want_carpenter(String do_you_want_carpenter) {
        this.do_you_want_carpenter = do_you_want_carpenter;
    }

    public String getNo_of_kitchen() {
        return no_of_kitchen;
    }

    public void setNo_of_kitchen(String no_of_kitchen) {
        this.no_of_kitchen = no_of_kitchen;
    }

    public String getNo_of_bathroom() {
        return no_of_bathroom;
    }

    public void setNo_of_bathroom(String no_of_bathroom) {
        this.no_of_bathroom = no_of_bathroom;
    }

    public String getNo_of_room() {
        return no_of_room;
    }

    public void setNo_of_room(String no_of_room) {
        this.no_of_room = no_of_room;
    }

    public String getNo_of_hall() {
        return no_of_hall;
    }

    public void setNo_of_hall(String no_of_hall) {
        this.no_of_hall = no_of_hall;
    }

    public String getNo_of_basin() {
        return no_of_basin;
    }

    public void setNo_of_basin(String no_of_basin) {
        this.no_of_basin = no_of_basin;
    }

    public String getNo_of_latrine() {
        return no_of_latrine;
    }

    public void setNo_of_latrine(String no_of_latrine) {
        this.no_of_latrine = no_of_latrine;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getDesign() {
        return design;
    }

    public void setDesign(String design) {
        this.design = design;
    }

    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getFull_other() {
        return full_other;
    }

    public void setFull_other(String full_other) {
        this.full_other = full_other;
    }

    public String getNo_of_point() {
        return no_of_point;
    }

    public void setNo_of_point(String no_of_point) {
        this.no_of_point = no_of_point;
    }

    public String getTotal_point() {
        return total_point;
    }

    public void setTotal_point(String total_point) {
        this.total_point = total_point;
    }

    public String getTotal_point_6a() {
        return total_point_6a;
    }

    public void setTotal_point_6a(String total_point_6a) {
        this.total_point_6a = total_point_6a;
    }

    public String getTotal_point_16a() {
        return total_point_16a;
    }

    public void setTotal_point_16a(String total_point_16a) {
        this.total_point_16a = total_point_16a;
    }

    public String getNo_of_mcb() {
        return no_of_mcb;
    }

    public void setNo_of_mcb(String no_of_mcb) {
        this.no_of_mcb = no_of_mcb;
    }

    public String getSub_cat() {
        return sub_cat;
    }

    public void setSub_cat(String sub_cat) {
        this.sub_cat = sub_cat;
    }

    public String getBid_id() {
        return bid_id;
    }

    public void setBid_id(String bid_id) {
        this.bid_id = bid_id;
    }

    public String getBid_amount() {
        return bid_amount;
    }

    public void setBid_amount(String bid_amount) {
        this.bid_amount = bid_amount;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getNego_amount() {
        return nego_amount;
    }

    public void setNego_amount(String nego_amount) {
        this.nego_amount = nego_amount;
    }

    public String getTotal_days() {
        return total_days;
    }

    public void setTotal_days(String total_days) {
        this.total_days = total_days;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAngle_type() {
        return angle_type;
    }

    public void setAngle_type(String angle_type) {
        this.angle_type = angle_type;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPro_img() {
        return pro_img;
    }

    public void setPro_img(String pro_img) {
        this.pro_img = pro_img;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getQuick_mode() {
        return quick_mode;
    }

    public void setQuick_mode(String quick_mode) {
        this.quick_mode = quick_mode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSub_cat_name() {
        return sub_cat_name;
    }

    public void setSub_cat_name(String sub_cat_name) {
        this.sub_cat_name = sub_cat_name;
    }

    public String getTotal_job_work() {
        return total_job_work;
    }

    public void setTotal_job_work(String total_job_work) {
        this.total_job_work = total_job_work;
    }

    @Override
    public int compareTo(BidListResult bidListResult) {
        return this.total_point.compareTo(bidListResult.getTotal_point());
    }
    public int compareTo(BidListResult bidListResult, String type) {
        ErrorMessage.E("type>>>>>>>>>>>>"+type);
        if (type.equals("date")){
            return this.end_date.compareTo(bidListResult.getEnd_date());
        }
        if (type.equals("total")) {
            return this.total_point.compareTo(bidListResult.getTotal_point());
        }
        return 0;
    }
}
