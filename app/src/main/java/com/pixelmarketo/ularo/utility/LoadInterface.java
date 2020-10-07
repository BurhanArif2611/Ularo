package com.pixelmarketo.ularo.utility;


import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface LoadInterface {

    @POST("user/user_login")
    @FormUrlEncoded
    Call<ResponseBody> user_login(@Field(value = "mobile") String mobile, @Field(value = "password") String password, @Field(value = "device_id") String device_id);

    @POST("user/user_register")
    @FormUrlEncoded
    Call<ResponseBody> user_register(@Field(value = "full_name") String full_name,
                                     @Field(value = "mobile") String mobile,
                                     @Field(value = "address") String address,
                                     @Field(value = "district") String district,
                                     @Field(value = "state") String state,
                                     @Field(value = "city") String city,
                                     @Field(value = "pincode") String pincode,
                                     @Field(value = "password") String password,
                                     @Field(value = "device_id") String device_id);

    @POST("user/category")
    @FormUrlEncoded
    Call<ResponseBody> get_category(@Field(value = "district") String district);

    @GET("bidder/category")
    Call<ResponseBody> bidder_category();

    @POST("bidder/new_service_man/Api/service_request_form")
    @FormUrlEncoded
    Call<ResponseBody> form(@Field(value = "user_id") String user_id,
                            @Field(value = "category_id") String category_id,
                            @Field(value = "story_building") String story_building,
                            @Field(value = "area_in_square_feet") String area_in_square_feet,
                            @Field(value = "type_of_work") String type_of_work,
                            @Field(value = "paint_type") String paint_type,
                            @Field(value = "layer") String layer,
                            @Field(value = "do_you_want_carpenter ") String do_you_want_carpenter,
                            @Field(value = "dimension") String dimension,
                            @Field(value = "running_feet") String running_feet,
                            @Field(value = "type_of_glass") String type_of_glass,
                            @Field(value = "no_of_kitchen") String no_of_kitchen,
                            @Field(value = "no_of_bathroom") String no_of_bathroom,
                            @Field(value = "no_of_basin") String no_of_basin,
                            @Field(value = "no_of_latrine") String no_of_latrine,
                            @Field(value = "no_of_room") String no_of_room,
                            @Field(value = "no_of_hall") String no_of_hall,
                            @Field(value = "purpose") String purpose,
                            @Field(value = "service_type") String service_type,
                            @Field(value = "description") String description,
                            @Field(value = "full_other") String full_other,
                            @Field(value = "sub_cat") String sub_cat,
                            @Field(value = "no_of_point") String no_of_point,
                            @Field(value = "total_point") String total_point,
                            @Field(value = "total_point_6a") String total_point_6a,
                            @Field(value = "total_point_16a") String total_point_16a,
                            @Field(value = "no_of_mcb") String no_of_mcb,
                            @Field(value = "angle_type") String angle_type,
                            @Field(value = "map") String map,
                            @Field(value = "total_job_work") String total_job_work,
                            @Field(value = "quick_mode") String quick_mode

    );

    @Multipart
    @POST("bidder/new_service_man/Api/service_request_form")
    Call<ResponseBody> form_with_image(@Part("user_id") RequestBody user_id,
                                       @Part("category_id") RequestBody category_id,
                                       @Part("story_building") RequestBody story_building,
                                       @Part("area_in_square_feet") RequestBody area_in_square_feet,
                                       @Part("type_of_work") RequestBody type_of_work,
                                       @Part("service_type") RequestBody service_type,
                                       @Part("running_feet") RequestBody running_feet,
                                       @Part("description") RequestBody description,
                                       @Part(value = "map") RequestBody map,
                                       @Part(value = "quick_mode") RequestBody quick_mode,
                                       @Part MultipartBody.Part part);

    @GET("user/type_of_work")
    Call<ResponseBody> type_of_work();

    @POST("bidder/new_service_man/Api/user_profile_update")
    @FormUrlEncoded
    Call<ResponseBody> update_profile(@Field(value = "user_id") String user_id,
                                      @Field(value = "full_name") String full_name,
                                      @Field(value = "mobile") String mobile,
                                      @Field(value = "address") String address);

    @Multipart
    @POST("bidder/new_service_man/Api/user_profile_update")
//http://ularo.in/api/bidder/new_service_man/Api/user_profile_update
    Call<ResponseBody> update_profile_with_image(@Part("user_id") RequestBody user_id,
                                                 @Part("full_name") RequestBody full_name,
                                                 @Part("mobile") RequestBody mobile,
                                                 @Part("address") RequestBody address,
                                                 @Part MultipartBody.Part part);

    @Multipart
    @POST("bidder/new_service_man/Api/registration_service_man")
//http://ularo.in/api/bidder/new_service_man/Api/registration_service_man  //bidder/service_man_register
    Call<ResponseBody> bidder_registration(@Part("full_name") RequestBody full_name,
                                           @Part("mobile") RequestBody mobile,
                                           @Part("address") RequestBody address,
                                           @Part("type_of_service[]") RequestBody type_of_service,
                                           @Part("bidder_type") RequestBody bidder_type,
                                           @Part("repair_maintance") RequestBody repair_maintance,
                                           @Part("password") RequestBody password,
                                           @Part(value = "district") RequestBody district,
                                           @Part(value = "state") RequestBody state,
                                           @Part(value = "city") RequestBody city,
                                           @Part(value = "pincode") RequestBody pincode,
                                           @Part MultipartBody.Part part,
                                           @Part MultipartBody.Part part1);

    @POST("bidder/sevice_man_login")
    @FormUrlEncoded
    Call<ResponseBody> bidder_login(@Field(value = "mobile") String mobile, @Field(value = "password") String password, @Field(value = "device_id") String device_id);


    @POST("user/change_password")
    @FormUrlEncoded
    Call<ResponseBody> change_password(@Field(value = "user_id") String user_id, @Field(value = "current_password") String current_password, @Field(value = "new_password") String new_password, @Field(value = "confirm_password") String confirm_password);

    @POST("bidder/change_password")
    @FormUrlEncoded
    Call<ResponseBody> change_password_bidder(@Field(value = "user_id") String user_id, @Field(value = "current_password") String current_password, @Field(value = "new_password") String new_password, @Field(value = "confirm_password") String confirm_password);

    @POST("user/forget_password")
    @FormUrlEncoded
    Call<ResponseBody> forgot_password_user(@Field(value = "user_id") String user_id, @Field(value = "current_password") String current_password, @Field(value = "new_password") String new_password, @Field(value = "confirm_password") String confirm_password);

    @POST("bidder/forget_password")
    @FormUrlEncoded
    Call<ResponseBody> forgot_password_bidder(@Field(value = "user_id") String user_id, @Field(value = "current_password") String current_password, @Field(value = "new_password") String new_password, @Field(value = "confirm_password") String confirm_password);

    @POST("bidder/service_by_vendor_id")
    @FormUrlEncoded
    Call<ResponseBody> get_service_by_id(@Field(value = "user_id") String user_id);

    // http://ularo.in/api/bidder/update_bid_by_vendorid//user/approve_bid_by_user

    @POST("bidder/bid_list_byvendor_id")
    @FormUrlEncoded
    Call<ResponseBody> bid_list(@Field(value = "user_id") String user_id, @Field(value = "category_id") String category_id
            , @Field(value = "area_of") String area_of, @Field(value = "story_building") String story_bulding,@Field(value = "city") String city,@Field(value = "running_feet") String running_feet);

    @POST("bidder/update_bid_by_vendorid")
    @FormUrlEncoded
    Call<ResponseBody> create_bid(@Field(value = "id") String id, @Field(value = "bid_amount") String bid_amount
            , @Field(value = "nego_amount") String nego_amount
            , @Field(value = "end_date") String end_date
            , @Field(value = "start_date") String start_date
            , @Field(value = "total_days") String total_days);

    @POST("user/approve_bid_by_user")
    @FormUrlEncoded
    Call<ResponseBody> bid_response(@Field(value = "id") String id, @Field(value = "payment") String payment, @Field(value = "payment_method") String payment_method, @Field(value = "tranjestion_id") String tranjestion_id, @Field(value = "district") String district);

    @POST("user/service_request_bid")
    @FormUrlEncoded
    Call<ResponseBody> user_request_bid_list(@Field(value = "user_id") String user_id);

    @POST("user/user_bid_list")
    @FormUrlEncoded
    Call<ResponseBody> user_bid_list(@Field(value = "user_id") String user_id, @Field(value = "request_id") String request_id, @Field(value = "price") String price, @Field(value = "rating") String rating,@Field(value = "end_date") String end_date);
//http://ularo.in/api/bidder/approve_pending_list //status 0 pending   //status 1 approve

    @POST("bidder/approve_pending_list")
    @FormUrlEncoded
    Call<ResponseBody> approve_pending_list(@Field(value = "user_id") String user_id, @Field(value = "status") String status);

    @POST("user/approve_pending_list_user")
    @FormUrlEncoded
    Call<ResponseBody> approve_pending_list_user(@Field(value = "user_id") String user_id, @Field(value = "status") String status);

    @POST("bidder/order_history")
    @FormUrlEncoded
    Call<ResponseBody> order_history(@Field(value = "user_id") String user_id);

    @POST("user/order_history")
    @FormUrlEncoded
    Call<ResponseBody> user_order_history(@Field(value = "user_id") String user_id);

    @Multipart
    @POST("bidder/new_service_man/Api/bidder_profile_update")
//http://ularo.in/api/bidder/new_service_man/Api/bidder_profile_update
    Call<ResponseBody> bidder_update_profile_with_image(@Part("user_id") RequestBody user_id,
                                                        @Part("full_name") RequestBody full_name,
                                                        @Part("address") RequestBody address,
                                                        @Part("type_of_service[]") RequestBody type_of_service,
                                                        @Part("repair_maintance") RequestBody repair_maintance,
                                                        @Part MultipartBody.Part part,
                                                        @Part MultipartBody.Part part1,
                                                        @Part MultipartBody.Part part2);

    @POST("bidder/new_service_man/Api/bidder_profile_update")
    @FormUrlEncoded
    Call<ResponseBody> bidder_update_profile(@Field(value = "user_id") String user_id,
                                             @Field(value = "full_name") String full_name,
                                             @Field(value = "address") String address,
                                             @Field(value = "type_of_service[]") String type_of_service,
                                             @Field(value = "repair_maintance") String repair_maintance);


    @GET("about_us")
    Call<ResponseBody> about_us();

    @GET("term_condition")
    Call<ResponseBody> term_condition_user();

    @GET("term_condition_bidder")
    Call<ResponseBody> term_condition_bidder();

    @GET("frienchiese_term")
    Call<ResponseBody> term_condition_frienchiese();


    @Multipart
    @POST("bidder/new_service_man/Api/registration_frienchiese_partner")
    Call<ResponseBody> frienchiese_registration(@Part("name_of_organisation") RequestBody name_of_organisation,
                                                @Part("contact_work") RequestBody contact_work,
                                                @Part("contact_home") RequestBody contact_home,
                                                @Part("mob") RequestBody mob,
                                                @Part("address") RequestBody address,
                                                @Part("area_for_frienchiese") RequestBody area_for_frienchiese,
                                                @Part("legal") RequestBody legal,
                                                @Part("tehsil") RequestBody tehsil,
                                                @Part(value = "district") RequestBody district,
                                                @Part(value = "state") RequestBody state,
                                                @Part(value = "pincode") RequestBody pincode,
                                                @Part(value = "term_condition") RequestBody term_condition,
                                                @Part(value = "invest_budget") RequestBody invest_budget,
                                                @Part MultipartBody.Part part);


    @POST("bidder/new_service_man/Api/confirm_payment")
    @FormUrlEncoded
    Call<ResponseBody> confirm_payment_registration(@Field(value = "user_id") String user_id,
                                                    @Field(value = "transaction_id") String transaction_id,
                                                    @Field(value = "payment_mode") String payment_mode,
                                                    @Field(value = "pay_amount") String pay_amount,
                                                    @Field(value = "district") String district
                                                    );

    @POST("bidder/question_answer.php")
    @FormUrlEncoded
    Call<ResponseBody> question(@Field(value = "user_id") String user_id,
                                @Field(value = "question") String question,
                                @Field(value = "answer") String answer,
                                @Field(value = "mark") String mark);

    @Multipart
    @POST("bidder/new_service_man/Api/advisement")
    Call<ResponseBody> advertisement(@Part("user_name") RequestBody user_name,
                                     @Part("contact") RequestBody contact,
                                     @Part("transaction_id") RequestBody transaction_id,
                                     @Part("payment_mode") RequestBody payment_mode,
                                     @Part("pay_amount") RequestBody pay_amount,
                                     @Part("district") RequestBody district,
                                     @Part("package") RequestBody package_am,
                                     @Part MultipartBody.Part part);

    @POST("user/bidder_rating")
    @FormUrlEncoded
    Call<ResponseBody> rating(@Field(value = "user_id") String user_id,
                              @Field(value = "bidder_id") String bidder_id,
                              @Field(value = "rating") String rating,
                              @Field(value = "comment") String comment);


    @POST("frienchiese_partner_confirm_payment")
    @FormUrlEncoded
    Call<ResponseBody> frienchiese_partner_confirm_payment(@Field(value = "id") int id,
                                                           @Field(value = "transaction_id") String transaction_id,
                                                           @Field(value = "payment_mode") String payment_mode,
                                                           @Field(value = "contract_fee") String contract_fee,
                                                           @Field(value = "refund_amount") String refund_amount,
                                                           @Field(value = "total_amount") String total_amount);

    @POST("user/repair_mant_vendor")
    @FormUrlEncoded
    Call<ResponseBody> repair_list(@Field(value = "id") String id,@Field("city") String city);
}