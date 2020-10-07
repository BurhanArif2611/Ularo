package com.pixelmarketo.ularo.utility;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.pixelmarketo.ularo.activities.MyApplication;


public class SavedData {
    private static final String FCM_ID = "fcm_id";
    private static final String USerName = "userName";
    private static final String userImage = "userImage";
    private static final String MOBILE_NUM = "mobile";
    private static final String Latitude = "latitude";
    private static final String Longitude = "Longitude";
    private static final String Address = "Address";

    private static final String USERID = "userID";
    private static final String USERCONTACT = "userContact";
    private static final String CHEKC_DATA = "CHEKC_DATA";
    private static final String EMAIL = "email";
    private static final String Service_Charge = "service_charge";
    private static final String CITY = "city";
    private static final String Payment_Status = "status";
    private static final String AddToCart_Count = "addtocart";
    private static final String IMEI_Number = "IMEI";
    private static final String PROPERTY_COUNT = "PROPERTY_COUNT";
    private static final String VEHICLE_COUNT = "VEHICLE_COUNT";
    private static final String PaymentInfo = "PaymentInfo";


    static SharedPreferences prefs;

    public static SharedPreferences getInstance() {
        if (prefs == null) {
            prefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.getInstance());
        }

        return prefs;
    }


    public static String getFCM_ID() {
        return getInstance().getString(FCM_ID, "");
    }

    public static void saveFCM_ID(String fcm) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(FCM_ID, fcm);
        editor.apply();
    }

    public static String getUSerName() {
        return getInstance().getString(USerName, "");
    }

    public static void saveUserName(String username) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(USerName, username);
        editor.apply();
    }

    public static String getUserImage() {
        return getInstance().getString(userImage, "");
    }

    public static void saveUserImage(String img) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(userImage, img);
        editor.apply();
    }

    public static String getMobileNum() {
        return getInstance().getString(MOBILE_NUM, "");
    }

    public static void saveMobileNum(String mobile) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(MOBILE_NUM, mobile);
        editor.apply();
    }

    public static String getLongitude() {
        return getInstance().getString(Longitude, "");
    }

    public static void saveLongitude(String mobile) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(Longitude, mobile);
        editor.apply();
    }

    public static String getLatitude() {
        return getInstance().getString(Latitude, "");
    }

    public static void saveLatitude(String latitude) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(Latitude, latitude);
        editor.apply();
    }

    public static String getAddress() {
        return getInstance().getString(Address, "");
    }


    public static void saveAddress(String latitude) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(Address, latitude);
        editor.apply();
    }


    public static String getuserId() {
        return getInstance().getString(USERID, "");
    }

    public static void saveuserId(String userid) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(USERID, userid);
        editor.apply();
    }

    public static String getuserContact() {
        return getInstance().getString(USERCONTACT, "");
    }

    public static void saveuserContact(String contact) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(USERCONTACT, contact);
        editor.apply();
    }

    public static String getChekcData() {
        return getInstance().getString(CHEKC_DATA, "");
    }

    public static void saveCheckData(String fg) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(CHEKC_DATA, fg);
        editor.apply();
    }
  public static String getEmail() {
        return getInstance().getString(EMAIL, "");
    }

    public static void saveEmail(String fg) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(EMAIL, fg);
        editor.apply();
    }
  public static String getCity() {
        return getInstance().getString(CITY, "");
    }

    public static void saveCity(String fg) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(CITY, fg);
        editor.apply();
    }
    public static String getPayment_Status() {
        return getInstance().getString(Payment_Status, "");
    }

    public static void savePayment_Status(String fg) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(Payment_Status, fg);
        editor.apply();
    }
    public static String getService_Charge() {
        return getInstance().getString(Service_Charge, "");
    }

    public static void saveService_Charge(String fg) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(Service_Charge, fg);
        editor.apply();
    }
    public static String getAddToCart_Count() {
        return getInstance().getString(AddToCart_Count, "0");
    }

    public static void saveAddToCart_Count(String fg) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(AddToCart_Count, fg);
        editor.apply();
    }

    public static String getVEHICLE_COUNT() {
        return getInstance().getString(VEHICLE_COUNT, "0");
    }

    public static void saveVEHICLE_COUNT(String fg) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(VEHICLE_COUNT, fg);
        editor.apply();

    }

    public static String getPROPERTY_COUNT() {
        return getInstance().getString(PROPERTY_COUNT, "0");
    }

    public static void savePROPERTY_COUNT(String fg) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(PROPERTY_COUNT, fg);
        editor.apply();
    }
    public static String getPaymentInfo() {
        return getInstance().getString(PaymentInfo, "");
    }

    public static void savePaymentInfo(String fg) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(PaymentInfo, fg);
        editor.apply();
    }

    public static void saveIMEI_Number(String fg) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(IMEI_Number, fg);
        editor.apply();
    }
}
