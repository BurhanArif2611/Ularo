package com.pixelmarketo.ularo.Firebase;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.pixelmarketo.ularo.R;
import com.pixelmarketo.ularo.bidderContent.BidderHomeActivity;
import com.pixelmarketo.ularo.database.UserProfileHelper;
import com.pixelmarketo.ularo.userContent.UserHomeActivity;
import com.pixelmarketo.ularo.utility.ErrorMessage;
import com.pixelmarketo.ularo.utility.SavedData;

import org.json.JSONObject;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";


    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        SavedData.saveFCM_ID(s);
        Log.e("", "Refreshed token:" + s);

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e("From data: ", "From data: " + remoteMessage.getData().toString());
        Log.e(TAG, "From data: " + remoteMessage.getData().toString());

        try {
            String Response = remoteMessage.getData().toString();
            JSONObject jsonObject = new JSONObject(Response);
            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
            JSONObject jsonObject2 = jsonObject1.getJSONObject("message");
            JSONObject jsonObject3 = jsonObject2.getJSONObject("msg");
            String title = jsonObject2.getString("notificationTitle");
            Log.e(TAG, "Data Payload: " + title);
            String type = jsonObject1.getString("user_type");
            Log.e(TAG, "Data Payload: " + type);

            Information_message(title,type);
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
       /* try {
            Log.e(TAG, "From data: " + remoteMessage.getData().toString());
            String Response = remoteMessage.getData().toString();
            JSONObject jsonObject = new JSONObject(Response);
            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
            JSONObject jsonObject2 = jsonObject1.getJSONObject("message");
            title = jsonObject2.getString("title");*//*, jsonObject2.getString("")*//*
            ErrorMessage.E("MyFirebaseMessagingService" + title);
            if (title.equals("Assign New Request")) {
                Assign_New_Request(title, jsonObject2.getString("body"));
            } else if (title.equals("New Request")) {
                New_Request(title, jsonObject2.getString("body"));
            } else if (title.equals("New Sales Order Request")) {
                New_Order_Request(title, jsonObject2.getString("body"));
            }else if (title.equals("Sales Order Approve")) {
                Sales_Order_Approve(title, jsonObject2.getString("body"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            ErrorMessage.E("MyFirebaseMessagingService Exception " + e.toString());
        }*/


    }


    private void Assign_New_Request(String title, String Content) {
        // app is in background, show the notification in notification tray
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            final String ANDROID_CHANNEL_ID = "com.pixelmarketo.ularo.ANDROID";
            Intent intent = new Intent(getApplicationContext(), UserHomeActivity.class);
            intent.putExtra("message", Content);
            intent.putExtra("Check", "");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NetworkUtileforOreao mNotificationUtils = new NetworkUtileforOreao(getApplicationContext());
            Notification.Builder nb = new Notification.Builder(getApplicationContext(), ANDROID_CHANNEL_ID).
                    setSmallIcon(R.drawable.ic_address).setContentTitle(title).setContentText(Content).setAutoCancel(true).setSound(defaultSoundUri).setContentIntent(pendingIntent);
            /*Noti ficationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)*/

            mNotificationUtils.getManager().notify(0, nb.build());
        } else {
            Intent intent = new Intent(getApplicationContext(), UserHomeActivity.class);
            intent.putExtra("message", Content);
            intent.putExtra("Check", "");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this).
                    setSmallIcon(R.drawable.app_ic).setContentTitle(title).setContentText(Content).setAutoCancel(true).setSound(defaultSoundUri).setContentIntent(pendingIntent);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, notificationBuilder.build());
        }
    }

    private void Information_message(String Title,String type) {
        if (type.equals("New_order")) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

                final String ANDROID_CHANNEL_ID = "com.pixelmarketo.ularo.ANDROID";
                Intent intent = new Intent(getApplicationContext(), BidderHomeActivity.class);
                intent.putExtra("from","Bidder");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                NetworkUtileforOreao mNotificationUtils = new NetworkUtileforOreao(getApplicationContext());
                Notification.Builder nb = new Notification.Builder(getApplicationContext(), ANDROID_CHANNEL_ID).
                        setSmallIcon(R.drawable.app_ic).setContentTitle("Ularo").setContentText(Title).setAutoCancel(true).setSound(defaultSoundUri).setContentIntent(pendingIntent);
                mNotificationUtils.getManager().notify(0, nb.build());

            } else {
                Intent intent = new Intent(getApplicationContext(), BidderHomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("from","Bidder");

                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this).
                        setSmallIcon(R.drawable.app_ic).setContentTitle("Ularo").setContentText(Title).setAutoCancel(true).setSound(defaultSoundUri).setContentIntent(pendingIntent);
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(0, notificationBuilder.build());
            }
        }
        else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                final String ANDROID_CHANNEL_ID = "com.pixelmarketo.ularo.ANDROID";
                Intent intent = new Intent(getApplicationContext(), UserHomeActivity.class);
                intent.putExtra("from","User");

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                NetworkUtileforOreao mNotificationUtils = new NetworkUtileforOreao(getApplicationContext());
                Notification.Builder nb = new Notification.Builder(getApplicationContext(), ANDROID_CHANNEL_ID).
                        setSmallIcon(R.drawable.app_ic).setContentTitle("Ularo").setContentText(Title).setAutoCancel(true).setSound(defaultSoundUri).setContentIntent(pendingIntent);
                mNotificationUtils.getManager().notify(0, nb.build());

            } else {
                Intent intent = new Intent(getApplicationContext(), UserHomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("from","User");

                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this).
                        setSmallIcon(R.drawable.app_ic).setContentTitle("Ularo").setContentText(Title).setAutoCancel(true).setSound(defaultSoundUri).setContentIntent(pendingIntent);
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(0, notificationBuilder.build());
            }
        }

    }

}



   /* private void UpdateFCMOnServer(String Tokan) {
        if (NetworkUtil.isNetworkAvailable(getApplicationContext())) {
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            ErrorMessage.E("FCM_id" + SavedData.getFCM_ID());
           *//* Call<ResponseBody> call = apiService.updatefcmtoken(UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id(), Tokan, UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_type());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                    } else {
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                }
            });*//*
        } else {

        }*/



