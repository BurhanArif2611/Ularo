package com.pixelmarketo.ularo.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;
import com.pixelmarketo.ularo.R;
import com.pixelmarketo.ularo.bidderContent.BidderHomeActivity;
import com.pixelmarketo.ularo.database.UserProfileHelper;
import com.pixelmarketo.ularo.userContent.UserHomeActivity;
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

public class ChangePasswordActivity extends BaseActivity {


    @BindView(R.id.bidder_login_btn)
    Button bidderLoginBtn;
    String from;
    @BindView(R.id.etcurrentpassword)
    TextInputEditText etcurrentpassword;
    @BindView(R.id.etnewpassword)
    TextInputEditText etnewpassword;
    @BindView(R.id.etconfirmpassword)
    TextInputEditText etconfirmpassword;

    @Override
    protected int getContentResId() {
        return R.layout.activity_change_password;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setToolbarWithBackButton("Change Password");
        Bundle bundle = getIntent().getExtras();
        from = bundle.getString("from");
        ErrorMessage.E("from>>" + from);
    }

    @OnClick(R.id.bidder_login_btn)
    public void onViewClicked() {
        if (UserAccount.isEmpty(etcurrentpassword, etnewpassword, etconfirmpassword)) {
            if (UserAccount.checkPasswordStats(etnewpassword.getText().toString())) {
            if (etnewpassword.getText().toString().equalsIgnoreCase(etconfirmpassword.getText().toString())) {
                change_password();
            } else {
                ErrorMessage.T(this, "Password mismatch!!");
            }
            } else {
                ErrorMessage.T(ChangePasswordActivity.this, "please create your password which contain one uppercase one lower case and one special character and must be 8 character long");

            }
        } else {
            UserAccount.EditTextPointer.setError("This Field Can't be Empty !");
            UserAccount.EditTextPointer.requestFocus();
        }
    }

    private void change_password() {
        if (NetworkUtil.isNetworkAvailable(ChangePasswordActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(ChangePasswordActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);/*SavedData.getFCM_ID()*/
            Call<ResponseBody> call = null;
            if (from.equalsIgnoreCase("user")) {
                call = apiService.change_password(UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id(), etcurrentpassword.getText().toString(), etnewpassword.getText().toString(), etconfirmpassword.getText().toString());
            } else {
                call = apiService.change_password_bidder(UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id(), etcurrentpassword.getText().toString(), etnewpassword.getText().toString(), etconfirmpassword.getText().toString());
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
                                ErrorMessage.T(ChangePasswordActivity.this, object.getString("message"));
                                if (from.equalsIgnoreCase("user")) {
                                    ErrorMessage.I_clear(ChangePasswordActivity.this, UserHomeActivity.class, null);
                                } else {
                                    ErrorMessage.I_clear(ChangePasswordActivity.this, BidderHomeActivity.class, null);

                                }
                            } else {
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(ChangePasswordActivity.this, object.getString("message"));
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
            ErrorMessage.T(ChangePasswordActivity.this, "No Internet");
        }
    }
}
