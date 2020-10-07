package com.pixelmarketo.ularo.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pixelmarketo.ularo.R;
import com.pixelmarketo.ularo.bidderContent.BidderHomeActivity;

import com.pixelmarketo.ularo.bidderContent.BidderLoginActivity;
import com.pixelmarketo.ularo.bidderContent.TestActivity;
import com.pixelmarketo.ularo.database.UserProfileHelper;
import com.pixelmarketo.ularo.userContent.UserHomeActivity;
import com.pixelmarketo.ularo.utility.AppConfig;
import com.pixelmarketo.ularo.utility.ErrorMessage;
import com.pixelmarketo.ularo.utility.LoadInterface;
import com.pixelmarketo.ularo.utility.NetworkUtil;
import com.pixelmarketo.ularo.utility.SavedData;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements PaymentResultListener {

    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
    final int MY_REQUEST_CODE = 1000;
    String Bidder_type = "";
    String user_id = "";
    String district_name = "";
    String Amount = "";
    String Mobile = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (UserProfileHelper.getInstance().getUserProfileModel().size() > 0) {
            ErrorMessage.E("from" + UserProfileHelper.getInstance().getUserProfileModel().get(0).getProvider());
        }

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= 23) {
                    permissioncheck();
                } else {
                    LaunchApp();
                }
            }
        }, 4000);


    }

    private void permissioncheck() {
        List<String> permissionsNeeded = new ArrayList<String>();
        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.READ_EXTERNAL_STORAGE))
            permissionsNeeded.add("READ");
        if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            permissionsNeeded.add("WRITE");
        if (!addPermission(permissionsList, Manifest.permission.CAMERA))
            permissionsNeeded.add("CAMERA");
        if (!addPermission(permissionsList, Manifest.permission.ACCESS_FINE_LOCATION))
            permissionsNeeded.add("FINE LOCATION");

        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                // Need Rationale
                String message = "You need to grant access to " + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);
                showMessageOKCancel(message, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (Build.VERSION.SDK_INT >= 23) {
                            // Marshmallow+
                            requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                        } else {
                            // Pre-Marshmallow
                        }

                    }
                });
                return;
            }

            if (Build.VERSION.SDK_INT >= 23) {
                // Marshmallow+
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);


            } else {
                // Pre-Marshmallow

            }

            return;
        } else {
            // Toast.makeText(this,"Permission",Toast.LENGTH_LONG).show();
            LaunchApp();
        }

        //insertDummyContact();
    }


    private boolean addPermission(List<String> permissionsList, String permission) {
        Boolean cond;
        if (Build.VERSION.SDK_INT >= 23) {
            // Marshmallow+
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(permission);
                // Check for Rationale Option
                if (!shouldShowRequestPermissionRationale(permission))
                    //  return false;
                    cond = false;
            }
            //  return true;
            cond = true;
        } else {
            // Pre-Marshmallow
            cond = true;
        }
        return cond;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this).setMessage(message).setPositiveButton("OK", okListener).setNegativeButton("Cancel", null).create().show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //Checking the request code of our request
        if (requestCode == 23) {
            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Permission Needed To Run The App", Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS) {
            Map<String, Integer> perms = new HashMap<String, Integer>();
            // Initial
            perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
            perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
            perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
            perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
            // perms.put(Manifest.permission.READ_PHONE_STATE, PackageManager.PERMISSION_GRANTED);
            perms.put(Manifest.permission.INTERNET, PackageManager.PERMISSION_GRANTED);
//            perms.put(Manifest.permission.CALL_PHONE, PackageManager.PERMISSION_GRANTED);
//           perms.put(Manifest.permission.SEND_SMS, PackageManager.PERMISSION_GRANTED);
           /* perms.put(Manifest.permission.RECEIVE_SMS, PackageManager.PERMISSION_GRANTED);
            perms.put(Manifest.permission.READ_SMS, PackageManager.PERMISSION_GRANTED);
            perms.put(Manifest.permission.READ_PHONE_STATE, PackageManager.PERMISSION_GRANTED);*/
            //Toast.makeText(SplashActivityScreen.this, " Permissions are jddddd", Toast.LENGTH_SHORT).show();
            // Fill with results
            for (int i = 0; i < permissions.length; i++)
                perms.put(permissions[i], grantResults[i]);
            // Check for ACCESS_FINE_LOCATION
            if (perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && perms.get(Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
                // All Permissions Granted
                // insertDummyContact();
                //Toast.makeText(SplashActivityScreen.this, " Permissions are l", Toast.LENGTH_SHORT).show();
                LaunchApp();
            } else {
                // Permission Denied
                Toast.makeText(MainActivity.this, "Some Permission is Denied", Toast.LENGTH_SHORT).show();
                LaunchApp();
               /* final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                        finish();
                    }
                }, 3000);*/
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_REQUEST_CODE) {
            if (resultCode != RESULT_OK) {
                Log.e("Update flow failed! :", "" + resultCode);
                // If the update is cancelled or fails,
                // you can request to start the update again.
                finish();
            }
        }
    }


    private void LaunchApp() {

        if (UserProfileHelper.getInstance().getUserProfileModel().size() > 0) {
            if (UserProfileHelper.getInstance().getUserProfileModel().get(0).getProvider() != null) {
                if (UserProfileHelper.getInstance().getUserProfileModel().get(0).getProvider().contains("user")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("from", "User");
                    ErrorMessage.I_clear(MainActivity.this, UserHomeActivity.class, bundle);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("from", "Bidder");
                    ErrorMessage.I_clear(MainActivity.this, BidderHomeActivity.class, bundle);

                }

            } else {
                ErrorMessage.I_clear(MainActivity.this, SelectionActivity.class, null);

            }
        } else {
            if (SavedData.getPaymentInfo().equals("")) {
                ErrorMessage.I_clear(MainActivity.this, SelectionActivity.class, null);
            } else {
                //SavedData.savePaymentInfo("Bidder"+","+user_id+","+district_name+","+"20000"+","+etmobile.getText().toString());
                try {
                    String[] namesList = SavedData.getPaymentInfo().split(",");
                    Bidder_type = namesList[0];
                    user_id = namesList[1];
                    if (Bidder_type.equals("Bidder")) {
                        district_name = namesList[2];
                        Amount = namesList[3];
                        Mobile = namesList[4];
                        payment_PopUP(Amount, Mobile);
                    } else if (Bidder_type.equals("freinchiese")) {
                        Mobile = namesList[4];
                        popup();
                    }
                } catch (Exception e) {
                }
            }

        }
    }

    public void payment_PopUP(String Amount, String Mobile) {

        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.payment_popup);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        final Button submit_btn = (Button) dialog.findViewById(R.id.submit_btn);
        final Button cancel_btn = (Button) dialog.findViewById(R.id.cancel_btn);


        submit_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
                startPayment(Amount, Mobile);


            }
        });

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });

        dialog.show();
    }

    public void startPayment(String Charge, String Mobile) {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        ErrorMessage.E("Charge startPayment" + Charge);

        final Activity activity = this;
        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Ullaro");
            options.put("description", "Registartion fees");
            //You can omit the image option to fetch the image from dashboard
            options.put("currency", "INR");
            options.put("amount", Charge);


            JSONObject preFill = new JSONObject();
            preFill.put("email", "squarefeethelp@gmail.com");
            preFill.put("contact", Mobile);

            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            ErrorMessage.E("" + e.getMessage());
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void confirm_payment(String s) {
        //  ErrorMessage.E("dis" + UserProfileHelper.getInstance().getUserProfileModel().get(0).getDistrict());
        if (NetworkUtil.isNetworkAvailable(MainActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(MainActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            Call<ResponseBody> call = null;
            if (Amount.equals("20000")) {
                call = apiService.confirm_payment_registration(user_id, s, "Online", "200", district_name);
            } else if (Amount.equals("10000")) {
                call = apiService.confirm_payment_registration(user_id, s, "Online", "100", district_name);
            }
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ErrorMessage.E("Response" + response.code());

                    if (response.isSuccessful()) {
                        JSONObject object = null;
                        try {
                            materialDialog.dismiss();
                            object = new JSONObject(response.body().string());
                            ErrorMessage.E("comes in cond" + object.toString());
                            if (object.getString("status").equals("200")) {
                                // ErrorMessage.E("comes in if cond" + object.toString());
                                SavedData.savePaymentInfo("");
                                ErrorMessage.T(MainActivity.this, object.getString("message"));
                                Bundle bundle = new Bundle();
                                bundle.putString("from", "Bidder");
                                bundle.putString("id", user_id);
                                ErrorMessage.I_clear(MainActivity.this, TestActivity.class, bundle);

                            } else {
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(MainActivity.this, object.getString("message"));
                                materialDialog.dismiss();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            materialDialog.dismiss();
                            ErrorMessage.E("JsonException" + e);
                        } catch (Exception e) {
                            e.printStackTrace();
                            materialDialog.dismiss();
                            ErrorMessage.E("Exceptions" + e);
                        }
                    } else {
                        materialDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    ErrorMessage.E("Falure login" + t);
                    materialDialog.dismiss();
                }
            });
        } else {
            ErrorMessage.T(MainActivity.this, "No Internet");
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        if (Bidder_type.equals("Bidder")) {
            confirm_payment(s);
        } else if (Bidder_type.equals("freinchiese")) {
            freinchiese_confirm_payment(s);
        }
    }

    @Override
    public void onPaymentError(int i, String s) {

    }

    public void popup() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.frienchiese_popup);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        final Button btnsubmit = (Button) dialog.findViewById(R.id.btnsubmit);
        final TextView tvnetamount = (TextView) dialog.findViewById(R.id.tvnetamount);
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPayment("1699900");

            }
        });


        dialog.show();
    }


    public void startPayment(String Charge) {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        ErrorMessage.E("Charge startPayment" + Charge);

        final Activity activity = this;
        final Checkout co = new Checkout();
        try {
            JSONObject options = new JSONObject();
            options.put("name", "Ullaro");
            options.put("description", "Frienchiese Partner");
            //You can omit the image option to fetch the image from dashboard
            options.put("currency", "INR");
            options.put("amount", Charge);

            JSONObject preFill = new JSONObject();
            preFill.put("email", "squarefeethelp@gmail.com");
            preFill.put("contact", Mobile);

            options.put("prefill", preFill);
            co.open(activity, options);
        } catch (Exception e) {
            ErrorMessage.E("" + e.getMessage());
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void freinchiese_confirm_payment(String s) {
        if (NetworkUtil.isNetworkAvailable(MainActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(MainActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);

            Call<ResponseBody> call = apiService.frienchiese_partner_confirm_payment(Integer.parseInt(user_id), s, "online", "2000", "14999", "16999");
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ErrorMessage.E("Response" + response.toString());
                    if (response.isSuccessful()) {
                        JSONObject object = null;
                        try {
                            materialDialog.dismiss();
                            object = new JSONObject(response.body().string());
                            ErrorMessage.E("comes in cond" + object.toString());
                            if (object.getString("status").equals("200")) {
                                ErrorMessage.T(MainActivity.this, object.getString("message"));
                                SavedData.savePaymentInfo("");
                                ErrorMessage.I_clear(MainActivity.this, ThankuActivity.class, null);

                            } else {
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(MainActivity.this, object.getString("message"));
                                materialDialog.dismiss();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            materialDialog.dismiss();
                            ErrorMessage.E("JsonException" + e);
                        } catch (Exception e) {
                            e.printStackTrace();
                            materialDialog.dismiss();
                            ErrorMessage.E("Exceptions" + e);
                        }
                    } else {
                        materialDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    ErrorMessage.E("Falure login" + t);
                    materialDialog.dismiss();
                }
            });

        } else {
            ErrorMessage.T(MainActivity.this, "No Internet");
        }
    }
}
