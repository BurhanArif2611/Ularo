package com.pixelmarketo.ularo.activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.pixelmarketo.ularo.R;
import com.pixelmarketo.ularo.utility.AppConfig;
import com.pixelmarketo.ularo.utility.ErrorMessage;
import com.pixelmarketo.ularo.utility.LoadInterface;
import com.pixelmarketo.ularo.utility.NetworkUtil;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutUsActivity extends BaseActivity {

    @BindView(R.id.tvdetail)
    TextView tvdetail;
    @BindView(R.id.web)
    WebView web;

    @Override
    protected int getContentResId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setToolbarWithBackButton("About Us");
        getterms();
    }

    public void getterms() {
        if (NetworkUtil.isNetworkAvailable(AboutUsActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(AboutUsActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);

            Call<ResponseBody> call = apiService.about_us();


            call.enqueue(new Callback<ResponseBody>() {
                @SuppressLint("NewApi")
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ErrorMessage.E("Response" + response.code());
                    if (response.isSuccessful()) {
                        JSONObject jsonObject = null;
                        Gson gson = new Gson();
                        try {
                            jsonObject = new JSONObject(response.body().string());
                            ErrorMessage.E("get" + jsonObject.toString());
                            materialDialog.dismiss();
                            if (jsonObject.getString("status").equals("200")) {
                                String responseData = jsonObject.getString("service_req_dtl");
                                ErrorMessage.E("responseData" + responseData);
                                String plain = Html.fromHtml(responseData).toString();
                                ErrorMessage.E("responseData" + plain);
                                tvdetail.setText(plain);
                                final String mimeType = "text/html";
                                final String encoding = "UTF-8";
                                web.loadDataWithBaseURL("", responseData, mimeType, encoding, "");
//                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                                    tvdetail.setText(Html.fromHtml(responseData, Html.FROM_HTML_MODE_COMPACT));
//                                } else {
//                                    tvdetail.setText(Html.fromHtml(responseData));
//                                }
                                // tvdetail.setText(plain);

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
            ErrorMessage.T(AboutUsActivity.this, "No Internet");
        }
    }

}
