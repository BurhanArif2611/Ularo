package com.pixelmarketo.ularo.database;

import android.database.sqlite.SQLiteDatabase;

public class UserProfileModel {
    public static final String TABLE_NAME = "UserProfile";
    public static final String KEY_ID = "_id";
    public static final String KEY_id = "id";
    public static final String KEY_User_id = "user_id";
    public static final String KEY_uid = "uid";
    public static final String KEY_NAME = "displayName";
    public static final String KEY_provider = "provider";
    public static final String KEY_EmailId = "emailId";
    public static final String KEY_Profile_Pic = "profile_pic";
    public static final String KEY_Phone = "userPhone";
    public static final String KEY_DOB = "dob";



    public static void creteTable(SQLiteDatabase db) {
        String CREATE_STUDENTTABLE = "create table " + TABLE_NAME + " ("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_id + " text, "
                + KEY_User_id + " text, "
                + KEY_NAME + " text, "
                + KEY_uid + " text, "
                + KEY_EmailId + " text, "
                + KEY_Profile_Pic + " text, "
                + KEY_Phone + " text, "
                + KEY_DOB+ " text, "
                + KEY_provider + " text " +
                ")";
        db.execSQL(CREATE_STUDENTTABLE);
    }

    public static void dropTable(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }
    String id,user_id,uid,displayName,provider,emaiiId,profile_pic,userPhone,dob;

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getEmaiiId() {
        return emaiiId;
    }

    public void setEmaiiId(String emaiiId) {
        this.emaiiId = emaiiId;
    }

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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setDOB(String dob) {
        this.dob = dob;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public static String getKeyDob() {
        return KEY_DOB;
    }


}


