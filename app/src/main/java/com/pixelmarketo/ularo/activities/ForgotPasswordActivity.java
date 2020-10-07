package com.pixelmarketo.ularo.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;
import com.pixelmarketo.ularo.R;
import com.pixelmarketo.ularo.bidderContent.BidderHomeActivity;
import com.pixelmarketo.ularo.bidderContent.BidderLoginActivity;
import com.pixelmarketo.ularo.database.UserProfileHelper;
import com.pixelmarketo.ularo.userContent.UserHomeActivity;
import com.pixelmarketo.ularo.userContent.UserRegistrationActivity;
import com.pixelmarketo.ularo.utility.AppConfig;
import com.pixelmarketo.ularo.utility.ErrorMessage;
import com.pixelmarketo.ularo.utility.LoadInterface;
import com.pixelmarketo.ularo.utility.NetworkUtil;
import com.pixelmarketo.ularo.utility.UserAccount;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends BaseActivity {
    @BindView(R.id.tvmobile)
    TextInputEditText tvmobile;
    @BindView(R.id.etnewpassword)
    TextInputEditText etnewpassword;
    @BindView(R.id.etconfirmpassword)
    TextInputEditText etconfirmpassword;
    @BindView(R.id.btncontinue)
    Button btncontinue;
    String from;

    @Override
    protected int getContentResId() {
        return R.layout.activity_forgot_password;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setToolbarWithBackButton("Forgot Password");
        Bundle bundle = getIntent().getExtras();
        from = bundle.getString("from");
        ErrorMessage.E(from);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                tvmobile.setEnabled(false);
                send_to_server();
            }
            if (resultCode == Activity.RESULT_CANCELED) {

            }
        } else {

        }
    }

    @OnClick(R.id.btncontinue)
    public void onViewClicked() {
        if (UserAccount.isEmpty(tvmobile, etnewpassword, etconfirmpassword)) {
            if (UserAccount.isPhoneNumberLength(tvmobile)) {
                if (UserAccount.checkPasswordStats(etnewpassword.getText().toString())) {
                    if (etnewpassword.getText().toString().equalsIgnoreCase(etconfirmpassword.getText().toString())) {
                        Intent intent = new Intent(ForgotPasswordActivity.this, OtpActivity.class);
                        intent.putExtra("Contact", tvmobile.getText().toString());
                        intent.putExtra("invitaion_from", "forgot password");
                        startActivityForResult(intent, 1);
                    } else {
                        ErrorMessage.T(this, "Password mismatch!!");
                    }
                } else {
                    ErrorMessage.T(ForgotPasswordActivity.this, "please create your password which contain one uppercase one lower case and one special character and must be 8 character long");

                }
            } else {
                UserAccount.EditTextPointer.setError("Enter valid mobile number !");
                UserAccount.EditTextPointer.requestFocus();
            }

        } else {
            UserAccount.EditTextPointer.setError("This Field Can't be Empty !");
            UserAccount.EditTextPointer.requestFocus();
        }
    }

    private void send_to_server() {
        if (NetworkUtil.isNetworkAvailable(ForgotPasswordActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(ForgotPasswordActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);/*SavedData.getFCM_ID()*/
            Call<ResponseBody> call = null;
            if (from.equalsIgnoreCase("Bidder Login")) {
                call = apiService.forgot_password_bidder(tvmobile.getText().toString(), etnewpassword.getText().toString(), etconfirmpassword.getText().toString(), etconfirmpassword.getText().toString());
            } else {
                call = apiService.forgot_password_user(tvmobile.getText().toString(), etnewpassword.getText().toString(), etconfirmpassword.getText().toString(), etconfirmpassword.getText().toString());

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
                                ErrorMessage.T(ForgotPasswordActivity.this, object.getString("message"));
                                if (from.equalsIgnoreCase("Bidder Login")) {
                                    // ErrorMessage.E("comes in if cond" + object.toString());
                                    Bundle bundle=new Bundle();
                                    bundle.putString("from","Bidder Login");
                                    ErrorMessage.I_clear(ForgotPasswordActivity.this,BidderLoginActivity.class,bundle);

                                }
                                else{
                                    Bundle bundle=new Bundle();
                                    bundle.putString("from","User Login Form");
                                    ErrorMessage.I_clear(ForgotPasswordActivity.this,BidderLoginActivity.class,bundle);
                                   // ErrorMessage.T(ForgotPasswordActivity.this, object.getString("message"));
                                }

                            } else {
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(ForgotPasswordActivity.this, object.getString("message"));
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
            ErrorMessage.T(ForgotPasswordActivity.this, "No Internet");
        }
    }
}
