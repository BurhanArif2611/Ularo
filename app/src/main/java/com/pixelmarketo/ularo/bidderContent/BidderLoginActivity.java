package com.pixelmarketo.ularo.bidderContent;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.pixelmarketo.ularo.R;
import com.pixelmarketo.ularo.activities.BaseActivity;
import com.pixelmarketo.ularo.activities.ForgotPasswordActivity;
import com.pixelmarketo.ularo.database.UserProfileHelper;
import com.pixelmarketo.ularo.database.UserProfileModel;
import com.pixelmarketo.ularo.userContent.UserHomeActivity;
import com.pixelmarketo.ularo.userContent.UserRegistrationActivity;
import com.pixelmarketo.ularo.utility.AppConfig;
import com.pixelmarketo.ularo.utility.ErrorMessage;
import com.pixelmarketo.ularo.utility.LoadInterface;
import com.pixelmarketo.ularo.utility.NetworkUtil;
import com.pixelmarketo.ularo.utility.SavedData;
import com.pixelmarketo.ularo.utility.UserAccount;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BidderLoginActivity extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener, PaymentResultListener {


    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.title_txt)
    TextView titleTxt;
    @BindView(R.id.tool_barLayout)
    RelativeLayout toolBarLayout;
    @BindView(R.id.abc)
    TextView abc;
    @BindView(R.id.bidder_login_btn)
    Button bidderLoginBtn;
    @BindView(R.id.tvsignup)
    TextView bidRegister;
    @BindView(R.id.bod_forgot_pass)
    TextView bodForgotPass;
    String from;
    @BindView(R.id.bid_mobile_tv)
    TextInputEditText bidMobileTv;
    @BindView(R.id.etpassword)
    TextInputEditText etpassword;
    @BindView(R.id.login_button)
    LoginButton loginButton;
    @BindView(R.id.sign_in_button)
    SignInButton signInButton;
    @BindView(R.id.facebook_sign_in_button)
    Button facebookSignInButton;
    @BindView(R.id.google_sign_in_button)
    Button googleSignInButton;
    @BindView(R.id.google_facebook)
    LinearLayout googleFacebook;
    private GoogleApiClient mGoogleApiClient;
    CallbackManager callbackManager;
    int RC_SIGN_IN = 1;
    String name, profileimage, payment;
    String bidder_id="",bidder_dis="";
    @Override
    protected int getContentResId() {
        return R.layout.activity_bidder_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setToolbarWithBackButton("Login Activity");
        //  ErrorMessage.T(BidderLoginActivity.this,"Device ID: "+SavedData.getFCM_ID());
        if (SavedData.getFCM_ID().equals("")) {
            FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(BidderLoginActivity.this, new OnSuccessListener<InstanceIdResult>() {
                @Override
                public void onSuccess(InstanceIdResult instanceIdResult) {
                    String deviceToken = instanceIdResult.getToken();
                    //  ErrorMessage.T(BidderLoginActivity.this, "device"+deviceToken);
                    SavedData.saveFCM_ID(deviceToken);

                }
            });
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            from = bundle.getString("from");
            if (!from.equals(null)) {
                abc.setText(from);
            }
        }
        ErrorMessage.E("deviceid" + SavedData.getFCM_ID());

     /*   try {
            PackageInfo info = getPackageManager().getPackageInfo("com.pixelmarketo.ularo", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                ErrorMessage.E("KeyHash:" + Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }*/

        bidderLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (UserAccount.isEmpty(bidMobileTv, etpassword)) {
                    if (UserAccount.isPhoneNumberLength(bidMobileTv)) {
                        if (UserAccount.checkPasswordStats(etpassword.getText().toString())) {
                            if (from.equalsIgnoreCase("Bidder Login")) {
                                user_login("", "bidder", "");
                            } else {
                                user_login("", "user", "");
                            }
                        } else {
                            UserAccount.EditTextPointer.setError("Enter a vaild Password! Minimum 8 Character");
                            UserAccount.EditTextPointer.requestFocus();
                        }
                    } else {
                        UserAccount.EditTextPointer.setError("Enter a vaild mobile number!");
                        UserAccount.EditTextPointer.requestFocus();
                    }
                } else {
                    UserAccount.EditTextPointer.setError("This Field Can't be Empty !");
                    UserAccount.EditTextPointer.requestFocus();
                }
            }

        });


        bidRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (from.equalsIgnoreCase("Bidder Login")) {
                    ErrorMessage.I(BidderLoginActivity.this, BidderRegistrationActivity.class, null);
                } else {
                    ErrorMessage.I(BidderLoginActivity.this, UserRegistrationActivity.class, null);
                }
            }
        });

        bodForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle1 = new Bundle();
                bundle1.putString("from", from);
                ErrorMessage.I(BidderLoginActivity.this, ForgotPasswordActivity.class, bundle1);
            }
        });
        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions("email");

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //     getUserDetails(loginResult);
                ErrorMessage.E("facebook" + loginResult.toString());
                final String image = "https://graph.facebook.com/" + AccessToken.getCurrentAccessToken().getUserId() + "/picture?type=large&width=720&height=720";
                //__________Create a graph request
                GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            //_______put data into user data map
                            // CheckLogin = "social";
                            String image_url = "http://graph.facebook.com/" + Profile.getCurrentProfile().getId() + "/picture?type=large";

                            ErrorMessage.E("facebook" + object.get("name").toString());
                            name = object.get("name").toString();
                            ErrorMessage.E("facebook" + image_url);
                            ErrorMessage.E("facebook" + image_url);
                            //  profileimage=image_url;
                            ErrorMessage.E("facebook" + object.get("email"));
                            // ErrorMessage.E("facebook" + object.get("mobile"));
                            //   ErrorMessage.E("facebook" + object.get("phone"));
                            if (from.equalsIgnoreCase("Bidder Login")) {
                                user_login(object.get("email").toString(), "bidder", "social");
                            } else {
                                user_login(object.get("email").toString(), "user", "social");
                            }
                            //  user_login("social", object.get("email").toString(), object.get("name").toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                            ErrorMessage.E("facebook Exception" + e.toString());
                        }
                    }
                });
                //__________add parameters for required data
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email");
                request.setParameters(parameters);

                //__________eqecute request
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

        mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();

        // Customizing G+ button
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setScopes(gso.getScopeArray());
    }

    @OnClick({R.id.facebook_sign_in_button, R.id.sign_in_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.facebook_sign_in_button:
                loginButton.performClick();
                break;
            case R.id.sign_in_button:
                signIn();
                break;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == RC_SIGN_IN) {
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                handleSignInResult(result);
            } else {
                callbackManager.onActivityResult(requestCode, resultCode, data);
            }
        } catch (Exception e) {
            ErrorMessage.E("error" + e.getMessage());
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        GoogleSignInAccount acct = result.getSignInAccount();
        ErrorMessage.E("name" + acct.getDisplayName() + ">>" + acct.getEmail() + ">>" + acct.getPhotoUrl());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            try {
                GoogleSignInAccount acct1 = result.getSignInAccount();
                //   CheckLogin = "social";
                // user_login(acct.getEmail(), CheckLogin);
                name = acct.getDisplayName();
                if (from.equalsIgnoreCase("Bidder Login")) {
                    user_login(acct.getEmail(), "bidder", "social");
                } else {
                    user_login(acct.getEmail(), "user", "social");
                }
                ErrorMessage.E("name" + acct.getDisplayName() + ">>" + acct.getEmail());

            } catch (Exception e) {
                ErrorMessage.E("Exception" + e.toString());
            }
        } else {
            // Signed out, show unauthenticated UI.
            ErrorMessage.E("else is working Exception");
        }
    }

    private void user_login(String email, String from, String type) {
        if (NetworkUtil.isNetworkAvailable(BidderLoginActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(BidderLoginActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);/*SavedData.getFCM_ID()*/
            Call<ResponseBody> call = null;
            if (from.equalsIgnoreCase("bidder")) {
                call = apiService.bidder_login(bidMobileTv.getText().toString(), etpassword.getText().toString(), SavedData.getFCM_ID());
            } else {
                call = apiService.user_login(bidMobileTv.getText().toString(), etpassword.getText().toString(), SavedData.getFCM_ID());
            }
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ErrorMessage.E("Response" + response.code());
                    ErrorMessage.E("Response" + response.message());
                    ErrorMessage.E("Response" + response.toString());

                    if (response.isSuccessful()) {
                        JSONObject object = null;
                        try {
                            materialDialog.dismiss();
                            object = new JSONObject(response.body().string());
                            ErrorMessage.E("get" + object.toString());

                            ErrorMessage.E("comes in cond" + object.toString());
                            if (object.getString("status").equals("200")) {
                                // ErrorMessage.E("comes in if cond" + object.toString());
                                ErrorMessage.T(BidderLoginActivity.this, object.getString("message"));
                                JSONObject jsonObject1 = object.getJSONObject("user_info");
                                if (from.equalsIgnoreCase("user")) {

                                    UserProfileModel userProfileModel = new UserProfileModel();
                                    userProfileModel.setDisplayName(jsonObject1.getString("full_name"));
                                    userProfileModel.setUser_id(jsonObject1.getString("user_id"));
                                    userProfileModel.setUserPhone(jsonObject1.getString("mobile"));
                                    userProfileModel.setEmaiiId(jsonObject1.getString("address"));
                                    userProfileModel.setProfile_pic(jsonObject1.getString("profile_image"));
                                    userProfileModel.setDistrict(jsonObject1.getString("district"));
                                    userProfileModel.setCity(jsonObject1.getString("city"));
                                    UserProfileHelper.getInstance().delete();
                                    userProfileModel.setProvider("user");
                                    UserProfileHelper.getInstance().insertUserProfileModel(userProfileModel);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("from", "User");
                                    ErrorMessage.I_clear(BidderLoginActivity.this, UserHomeActivity.class, bundle);

                                } else {
                                    if (object.getString("que_sta").equalsIgnoreCase("0")) {
                                        Bundle bundle = new Bundle();
                                        bundle.putString("id", jsonObject1.getString("user_id"));
                                        ErrorMessage.I_clear(BidderLoginActivity.this, TestActivity.class, bundle);
                                    } else {
                                        UserProfileModel userProfileModel = new UserProfileModel();
                                        userProfileModel.setDisplayName(jsonObject1.getString("fname"));
                                        userProfileModel.setUser_id(jsonObject1.getString("user_id"));
                                        userProfileModel.setUserPhone(jsonObject1.getString("mobile"));
                                        userProfileModel.setEmaiiId(jsonObject1.getString("address"));
                                        userProfileModel.setProfile_pic(jsonObject1.getString("profile_image"));
                                        userProfileModel.setPolice(jsonObject1.getString("police_verification_report"));
                                        userProfileModel.setAadhar(jsonObject1.getString("adhar_card"));
                                        userProfileModel.setDistrict(jsonObject1.getString("district"));
                                        userProfileModel.setCity(jsonObject1.getString("city"));

                                        UserProfileHelper.getInstance().delete();
                                        userProfileModel.setProvider("bidder");
                                        UserProfileHelper.getInstance().insertUserProfileModel(userProfileModel);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("from", "Bidder");
                                        ErrorMessage.I_clear(BidderLoginActivity.this, BidderHomeActivity.class, bundle);
                                    }
                                }

                            } else if (object.getString("status").equals("300")) {
                                if (object.getString("bidder").equalsIgnoreCase("Normal Bidder")) {
                                    payment = "10000";
                                } else {
                                    payment = "20000";
                                }
                                bidder_id=object.getString("bidder_id");
                                bidder_dis=object.getString("bidder_dis");
                                SavedData.savePaymentInfo("Bidder"+","+object.getString("bidder_id")+","+object.getString("bidder_dis")+","+payment+","+bidMobileTv.getText().toString());
                                payment_PopUP(object.getString("bidder"));


                            } else {
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(BidderLoginActivity.this, object.getString("message"));


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
            ErrorMessage.T(BidderLoginActivity.this, "No Internet");
        }
    }

    public void payment_PopUP(String bidder) {

        final Dialog dialog = new Dialog(BidderLoginActivity.this);
        dialog.setContentView(R.layout.payment_popup);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        final Button submit_btn = (Button) dialog.findViewById(R.id.submit_btn);
        final Button cancel_btn = (Button) dialog.findViewById(R.id.cancel_btn);
        if (bidder.equalsIgnoreCase("Normal Bidder")) {
            payment = "10000";
        } else {
            payment = "20000";
        }

        submit_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
                       startPayment(payment);

            }
        });

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        dialog.show();
    }

    @Override
    public void onPaymentSuccess(String s) {
        ErrorMessage.E("onPaymentSuccess" + s);
        confirm_payment(s);
    }

    @Override
    public void onPaymentError(int i, String s) {
        ErrorMessage.E("payment" + s);
    }


    public void startPayment(String Charge) {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        ErrorMessage.E("Charge startPayment" + Charge);

        final Activity activity = this;
        final Checkout co = new Checkout();
        payment = Charge;
        try {
            JSONObject options = new JSONObject();
            options.put("name", "Ullaro");
            options.put("description", "Registartion fees");
            //You can omit the image option to fetch the image from dashboard
            options.put("currency", "INR");
            options.put("amount", payment);


            JSONObject preFill = new JSONObject();
            preFill.put("email", "squarefeethelp@gmail.com");
            preFill.put("contact", bidMobileTv.getText().toString());

            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            ErrorMessage.E("" + e.getMessage());
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void confirm_payment(String s) {
        if (NetworkUtil.isNetworkAvailable(BidderLoginActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(BidderLoginActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            if (payment.equals("10000") ) {
                payment = "100";
            } else  if (payment.equals("20000") ){
                payment = "200";
            }
            Call<ResponseBody> call = apiService.confirm_payment_registration(bidder_id, s, "Online", payment, bidder_dis);
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
                                SavedData.savePaymentInfo("");
                                // ErrorMessage.E("comes in if cond" + object.toString());
                                ErrorMessage.T(BidderLoginActivity.this, object.getString("message"));
                                Bundle bundle = new Bundle();
                                bundle.putString("from", "Bidder");
                                bundle.putString("id", bidder_id);
                                ErrorMessage.I_clear(BidderLoginActivity.this, TestActivity.class, bundle);

                            } else {
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(BidderLoginActivity.this, object.getString("message"));
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
            ErrorMessage.T(BidderLoginActivity.this, "No Internet");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
