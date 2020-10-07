package com.pixelmarketo.ularo.activities;

import android.app.Dialog;
import android.app.Service;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.pixelmarketo.ularo.R;
import com.pixelmarketo.ularo.bidderContent.BidderHomeActivity;
import com.pixelmarketo.ularo.database.UserProfileHelper;
import com.pixelmarketo.ularo.database.UserProfileModel;
import com.pixelmarketo.ularo.userContent.UserHomeActivity;
import com.pixelmarketo.ularo.utility.AppConfig;
import com.pixelmarketo.ularo.utility.ErrorMessage;
import com.pixelmarketo.ularo.utility.NetworkUtil;
import com.pixelmarketo.ularo.utility.SavedData;
import com.pixelmarketo.ularo.utility.UserAccount;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.pixelmarketo.ularo.utility.NetworkUtil.*;

public class OTPVerificationActivity extends BaseActivity implements View.OnFocusChangeListener, View.OnKeyListener, TextWatcher {

    @BindView(R.id.loginLogoImg)
    ImageView loginLogoImg;
    @BindView(R.id.firstTitleTv)
    TextView firstTitleTv;
    @BindView(R.id.secondTitleTv)
    TextView secondTitleTv;
    @BindView(R.id.editTextone)
    EditText editTextone;
    @BindView(R.id.editTexttwo)
    EditText editTexttwo;
    @BindView(R.id.editTextthree)
    EditText editTextthree;
    @BindView(R.id.editTextfour)
    EditText editTextfour;
    @BindView(R.id.layout_otp)
    LinearLayout layoutOtp;
    @BindView(R.id.otpSubmitBtn)
    TextView otpSubmitBtn;
    @BindView(R.id.timer_tv)
    TextView timerTv;
    @BindView(R.id.resendOtpBtn)
    TextView resendOtpBtn;
    @BindView(R.id.otpMainLayout)
    LinearLayout otpMainLayout;
    @BindView(R.id.pin_hidden_edittext)
    EditText pinHiddenEdittext;
    private Dialog materialDialog;
    private String mobileNumStr = "";
    private String user_id;
    private String fromActivity = "";
    Bundle bundle;

    /**
     * Sets focus on a specific EditText field.
     *
     * @param editText EditText to set focus on
     */
    public static void setFocus(EditText editText) {
        if (editText == null)
            return;

        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    /**
     * Hides soft keyboard.
     *
     * @param editText EditText which has focus
     */
    public void hideSoftKeyboard(EditText editText) {
        if (editText == null)
            return;

        InputMethodManager imm = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }


    /*  @Override
        protected int getContentResId() {
            return R.layout.activity_otp_verification;
        }
    */

    @Override
    protected int getContentResId() {
        return R.layout.activity_otpverification;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);
        ButterKnife.bind(this);
        bundle = getIntent().getExtras();
        if (bundle != null) {
            mobileNumStr = bundle.getString("mob");
//          user_id=bundle.getString("user_id");
            ErrorMessage.E("mob" + mobileNumStr);
            fromActivity = bundle.getString("from");
            ErrorMessage.E("mobileNumStr" + mobileNumStr);
        } else {

        }
        setPINListeners();
        timer();
    }

    @OnClick({R.id.otpSubmitBtn, R.id.resendOtpBtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.otpSubmitBtn:
                if (UserAccount.isEmpty(editTextone, editTexttwo, editTextthree, editTextfour)) {
                    if (bundle.get("mignon").equals("Bidder")) {

                        bundle = new Bundle();

                        bundle.putString("from","Bidder");
                        ErrorMessage.I(OTPVerificationActivity.this, BidderHomeActivity.class, bundle);

                    }else if (bundle.get("mignon").equals("User")) {
                            /// user home activity
                        bundle.putString("from","User");
                        ErrorMessage.I(OTPVerificationActivity.this, UserHomeActivity.class, bundle);

                    }
  //                  otpVerifyCall();
                } else {
                    ErrorMessage.T(OTPVerificationActivity.this, "Enter Otp");
                }
                break;
            case R.id.resendOtpBtn:
//                resendOtpCall();
                break;
        }
    }

/*
    private void resendOtpCall() {
        if (NetworkUtil.isNetworkAvailable(OTPVerificationActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(OTPVerificationActivity.this);
            Call<ResponseBody> call = AppConfig.getLoadInterface().resendOtp(SavedData.getToken());
// ErrorMessage.E("fcmId" + SavedData.getFCM_ID());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        materialDialog.dismiss();
                        JSONObject jsonObject = null;
                        Gson gson = new Gson();
                        try {
                            jsonObject = new JSONObject(response.body().string());
                            ErrorMessage.E("response" + jsonObject.toString());
                            System.out.println("Login Response :" + jsonObject.toString());
                            if (jsonObject.getString("status").equals("200")) {
                                ErrorMessage.T(OTPVerificationActivity.this, jsonObject.getString("message"));
                            } else {
// ErrorMessage.setSnackBar(parentLayout, jsonObject.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
// ErrorMessage.setSnackBar(ma, "Server Error");
                            ErrorMessage.E("JSONEXCEPTION" + e.toString());
                            materialDialog.dismiss();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        ErrorMessage.T(OTPVerificationActivity.this, "Response not successful");
                        materialDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    ErrorMessage.T(OTPVerificationActivity.this, "Response Fail");
                    materialDialog.dismiss();
                }
            });
        } else {
            ErrorMessage.T(OTPVerificationActivity.this, this.getString(R.string.no_internet));
        }
    }
*/

    /*private void otpVerifyCall() {
        String otpPinStr = editTextone.getText().toString().trim() + editTexttwo.getText().toString().trim() + editTextthree.getText().toString().trim()
                + editTextfour.getText().toString().trim();
        if (isNetworkAvailable(OTPVerificationActivity.this)) {
//            final Dialog materialDialog = ErrorMessage.initProgressDialog(OTPVerificationActivity.this);
            Call<ResponseBody> call = AppConfig.getLoadInterface().otpVerification(otpPinStr, SavedData.getToken());
// ErrorMessage.E("fcmId" + SavedData.getFCM_ID());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        JSONObject jsonObject = null;
                        Gson gson = new Gson();
                        try {
                            jsonObject = new JSONObject(response.body().string());
                            ErrorMessage.E("response" + jsonObject.toString());
                            System.out.println("Login Response :" + jsonObject.toString());
                            if (jsonObject.getString("status").equals("200")) {
                                String responseData = jsonObject.toString();
//                                SavedData.getInstance().edit().putString("LOGIN_RESPONSE", responseData).commit();
                                SavedData.getInstance().edit().putBoolean("IS_LOGIN", true).commit();

//                                JSONObject jsonObject1 = jsonObject.getJSONObject("result");
     UserProfileModel userProfileModel = new UserProfileModel();
                                userProfileModel.setUser_id(jsonObject1.getString("auth_token"));
                                userProfileModel.setName(jsonObject1.getString("name"));
                                userProfileModel.setContact(jsonObject1.getString("contact"));
                                userProfileModel.setEmail(jsonObject1.getString("email"));
                                userProfileModel.setImage(jsonObject1.getString("profile_image"));
                                UserProfileHelper.getInstance().insertUserProfileModel(userProfileModel);

                                ErrorMessage.T(OTPVerificationActivity.this, jsonObject.getString("message"));

                                if (jsonObject.getString("profile_complete").equals("uncomplete")){
                                    Bundle bundle=new Bundle();
                                    bundle.putString("fromActivity","Otp");
//                                    ErrorMessage.I_clear(OTPVerificationActivity.this,UpdateProfileActivity.class,bundle);
                                }

                                ErrorMessage.I_clear(OTPVerificationActivity.this, DashboardActivity.class, null);

                            } else {
// ErrorMessage.setSnackBar(parentLayout, jsonObject.getString("message"));

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
// ErrorMessage.setSnackBar(ma, "Server Error");
                            ErrorMessage.E("JSONEXCEPTION" + e.toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        ErrorMessage.T(OTPVerificationActivity.this, "Response not successful");
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    ErrorMessage.T(OTPVerificationActivity.this, "Response Fail");
                }
            });
        } else {
            ErrorMessage.T(OTPVerificationActivity.this, this.getString(R.string.no_internet));
        }
    }*/


    private void setPINListeners() {
        pinHiddenEdittext.addTextChangedListener(this);
        editTextone.setOnFocusChangeListener(this);
        editTexttwo.setOnFocusChangeListener(this);
        editTextthree.setOnFocusChangeListener(this);
//        mPinForthDigitEditText.setOnFocusChangeListener(this);
        editTextfour.setOnFocusChangeListener(this);
        editTextone.setOnKeyListener(this);
        editTexttwo.setOnKeyListener(this);
        editTextthree.setOnKeyListener(this);
//        mPinForthDigitEditText.setOnKeyListener(this);
        editTextfour.setOnKeyListener(this);
//        pinHiddenEdittext.setOnKeyListener(this);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        final int id = v.getId();
        switch (id) {
            case R.id.editTextone:
                if (hasFocus) {
                    setFocus(pinHiddenEdittext);
                    showSoftKeyboard(pinHiddenEdittext);
                }
                break;
            case R.id.editTexttwo:
                if (hasFocus) {
                    setFocus(pinHiddenEdittext);
                    showSoftKeyboard(pinHiddenEdittext);
                }
                break;
            case R.id.editTextthree:
                if (hasFocus) {
                    setFocus(pinHiddenEdittext);
                    showSoftKeyboard(pinHiddenEdittext);
                }
                break;
           /* case R.id.pin_forth_edittext:
                if (hasFocus) {
                    setFocus(pinHiddenEdittext);
                    showSoftKeyboard(pinHiddenEdittext);
                }
                break;*/
            case R.id.editTextfour:
                if (hasFocus) {
                    setFocus(pinHiddenEdittext);
                    showSoftKeyboard(pinHiddenEdittext);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            final int id = v.getId();
            switch (id) {
                case R.id.pin_hidden_edittext:
                    if (keyCode == KeyEvent.KEYCODE_DEL) {
                        if (pinHiddenEdittext.getText().length() == 4)
                            editTextone.setText("");
                        else if (pinHiddenEdittext.getText().length() == 3)
                            editTexttwo.setText("");
                        else if (pinHiddenEdittext.getText().length() == 2)
                            editTexttwo.setText("");
                        else if (pinHiddenEdittext.getText().length() == 1)
                            editTextthree.setText("");

                        if (pinHiddenEdittext.length() > 0)
                            pinHiddenEdittext.setText(pinHiddenEdittext.getText().subSequence(0, pinHiddenEdittext.length() - 1));

                        return true;
                    }

                    break;

                default:
                    return false;
            }
        }

        return false;
    }


    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
      /*  setDefaultPinBackground(editTextone);
        setDefaultPinBackground(editTexttwo);
        setDefaultPinBackground(editTextthree);

        setDefaultPinBackground(editTextfour);*/

        if (s.length() == 0) {
//            setFocusedPinBackground(editTextone);
            editTextone.setText("");
        } else if (s.length() == 1) {
//            setFocusedPinBackground(editTexttwo);
            editTextone.setText(s.charAt(0) + "");
            editTexttwo.setText("");
            editTextthree.setText("");

            editTextfour.setText("");
        } else if (s.length() == 2) {
//            setFocusedPinBackground(editTextthree);
            editTexttwo.setText(s.charAt(1) + "");
            editTextthree.setText("");
            editTextfour.setText("");
        } else if (s.length() == 3) {
//            setFocusedPinBackground(editTextfour);
            editTextthree.setText(s.charAt(2) + "");
            editTextfour.setText("");
        } else if (s.length() == 4) {
//            setDefaultPinBackground(editTextfour);
            editTextfour.setText(s.charAt(3) + "");
            hideSoftKeyboard(editTextfour);
        }/* else if (s.length() == 5) {
            setDefaultPinBackground(mPinFifthDigitEditText);
            mPinFifthDigitEditText.setText(s.charAt(4) + "");

            hideSoftKeyboard(mPinFifthDigitEditText);
        }*/
    }

    /**
     * Sets focused PIN field background.
     *
     * @param editText edit text to change
     */
   /* private void setFocusedPinBackground(EditText editText) {
        setViewBackground(editText, getResources().getDrawable(R.drawable.line_color));
    }*/

    /**
     * Sets listeners for EditText fields.
     */

    /**
     * Sets background of the view.
     * This method varies in implementation depending on Android SDK version.
     *
     * @param view       View to which set background
     * @param background Background to set to view
     */
    @SuppressWarnings("deprecation")
    public void setViewBackground(View view, Drawable background) {
        if (view == null || background == null)
            return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(background);
        } else {
            view.setBackgroundDrawable(background);
        }
    }

    /**
     * Shows soft keyboard.
     *
     * @param editText EditText which has focus
     */
    public void showSoftKeyboard(EditText editText) {
        if (editText == null)
            return;

        InputMethodManager imm = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, 0);
    }

    /**
     * Custom LinearLayout with overridden onMeasure() method
     * for handling software keyboard show and hide events.
     */
/*
    public class MainLayout extends LinearLayout {

        public MainLayout(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.activity_buy_now, this);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            final int proposedHeight = MeasureSpec.getSize(heightMeasureSpec);
            final int actualHeight = getHeight();

            Log.d("TAG", "proposed: " + proposedHeight + ", actual: " + actualHeight);

            if (actualHeight >= proposedHeight) {
                // Keyboard is shown
              */
/*  if (pinHiddenEdittext.length() == 0)
                    setFocusedPinBackground(mPinFirstDigitEditText);
                else
                    setDefaultPinBackground(mPinFirstDigitEditText);*//*

            }

            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
*/
    private void timer() {
        CountDownTimer yourCountDownTimer = new CountDownTimer(60000 * 2, 1000) { // adjust the milli seconds here
            public void onTick(long millisUntilFinished) {
                timerTv.setVisibility(View.VISIBLE);
                resendOtpBtn.setVisibility(View.GONE);
                timerTv.setText("" + String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished), TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                timerTv.setText("00:00");
                resendOtpBtn.setVisibility(View.VISIBLE);
                timerTv.setVisibility(View.GONE);
                //   dialog.show();
               /* onBackPressed();
                finish();
        */
            }
        }.start();
    }

}
