package com.pixelmarketo.ularo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.pixelmarketo.ularo.utility.ErrorMessage;

import java.util.ArrayList;

public class UserProfileHelper {
    private static UserProfileHelper instance;
    private SQLiteDatabase db;
    private DataManager dm;
    Context cx;

    public UserProfileHelper(Context cx) {
        instance = this;
        this.cx = cx;
        dm = new DataManager(cx, DataManager.DATABASE_NAME, null, DataManager.DATABASE_VERSION);
    }
    private boolean isExist(UserProfileModel userDataModel) {
        read();
        Cursor cur = db.rawQuery("select * from " + UserProfileModel.TABLE_NAME + " where " + UserProfileModel.KEY_User_id + "='" + userDataModel.getUser_id() + "'", null);
        if (cur.moveToFirst()) {
            return true;
        }
        return false;
    }
    public static UserProfileHelper getInstance() {
        return instance;
    }

    public void open() {
        db = dm.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public void read() {
        db = dm.getReadableDatabase();
    }


    public void insertUserProfileModel(UserProfileModel userDataModel) {
        open();
        ContentValues values = new ContentValues();
        values.put(UserProfileModel.KEY_User_id, userDataModel.getUser_id());
        values.put(UserProfileModel.KEY_id, userDataModel.getId());
        values.put(UserProfileModel.KEY_uid, userDataModel.getUid());
        values.put(UserProfileModel.KEY_NAME, userDataModel.getDisplayName());
        values.put(UserProfileModel.KEY_provider, userDataModel.getProvider());
        values.put(UserProfileModel.KEY_EmailId, userDataModel.getEmaiiId());
        values.put(UserProfileModel.KEY_district, userDataModel.getDistrict());
        values.put(UserProfileModel.KEY_city, userDataModel.getCity());
        values.put(UserProfileModel.KEY_Profile_Pic, userDataModel.getProfile_pic());
        values.put(UserProfileModel.KEY_Phone, userDataModel.getUserPhone());
        values.put(UserProfileModel.KEY_police, userDataModel.getPolice());
        values.put(UserProfileModel.KEY_aadhar, userDataModel.getAadhar());

        if (!isExist(userDataModel)) {
            ErrorMessage.E("insert successfully");
            db.insert(UserProfileModel.TABLE_NAME, null, values);
        } else {
            ErrorMessage.E("update successfully" + userDataModel.getUser_id());
            db.update(UserProfileModel.TABLE_NAME, values, UserProfileModel.KEY_User_id + "=" + userDataModel.getUser_id(), null);
        }
        close();
    }

    public ArrayList<UserProfileModel> getUserProfileModel() {
        ArrayList<UserProfileModel> userItem = new ArrayList<UserProfileModel>();
        read();
        Cursor clientCur = db.rawQuery("select * from "+UserProfileModel.TABLE_NAME, null);

        if (clientCur != null && clientCur.getCount() > 0) {
            clientCur.moveToFirst();
            do {
                UserProfileModel userDataModel = new UserProfileModel();
                userDataModel.setUser_id(clientCur.getString(clientCur.getColumnIndex(UserProfileModel.KEY_User_id)));
                userDataModel.setId(clientCur.getString(clientCur.getColumnIndex(UserProfileModel.KEY_id)));
                userDataModel.setUid(clientCur.getString(clientCur.getColumnIndex(UserProfileModel.KEY_uid)));
                userDataModel.setDisplayName(clientCur.getString(clientCur.getColumnIndex(UserProfileModel.KEY_NAME)));
                userDataModel.setProvider(clientCur.getString(clientCur.getColumnIndex(UserProfileModel.KEY_provider)));
                userDataModel.setEmaiiId(clientCur.getString(clientCur.getColumnIndex(UserProfileModel.KEY_EmailId)));
                userDataModel.setProfile_pic(clientCur.getString(clientCur.getColumnIndex(UserProfileModel.KEY_Profile_Pic)));
                userDataModel.setUserPhone(clientCur.getString(clientCur.getColumnIndex(UserProfileModel.KEY_Phone)));
                userDataModel.setPolice(clientCur.getString(clientCur.getColumnIndex(UserProfileModel.KEY_police)));
                userDataModel.setDistrict(clientCur.getString(clientCur.getColumnIndex(UserProfileModel.KEY_district)));
                userDataModel.setCity(clientCur.getString(clientCur.getColumnIndex(UserProfileModel.KEY_city)));
                userDataModel.setAadhar(clientCur.getString(clientCur.getColumnIndex(UserProfileModel.KEY_aadhar)));
                userItem.add(userDataModel);

            } while ((clientCur.moveToNext()));
            clientCur.close();
        }
        close();
        return userItem;
    }
    public void delete() {
        try {
            open();
            db.delete(UserProfileModel.TABLE_NAME, null, null);
            close();
        }catch (Exception e){}

    }
}

