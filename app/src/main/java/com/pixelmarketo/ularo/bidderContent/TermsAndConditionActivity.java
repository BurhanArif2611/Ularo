package com.pixelmarketo.ularo.bidderContent;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;
import com.pixelmarketo.ularo.R;
import com.pixelmarketo.ularo.activities.BaseActivity;
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

public class TermsAndConditionActivity extends BaseActivity {

    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.title_txt)
    TextView titleTxt;
    @BindView(R.id.tool_barLayout)
    RelativeLayout toolBarLayout;
    @BindView(R.id.tvdetail)
    TextView tvdetail;
    @BindView(R.id.web)
    WebView web;


    @Override
    protected int getContentResId() {
        return R.layout.activity_terms_and_condition;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarWithBackButton("Terms And Condition");
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();

      //  bundle.g("from");
        getterms(bundle.getString("from"));
    }

    public void getterms(String from) {
        ErrorMessage.E("" + from);
        if (NetworkUtil.isNetworkAvailable(TermsAndConditionActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(TermsAndConditionActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            Call<ResponseBody> call = null;
            if (from.equalsIgnoreCase("user")) {
                call = apiService.term_condition_user();
            } else if (from.equalsIgnoreCase("bidder")) {
                call = apiService.term_condition_bidder();
            } else {
                call = apiService.term_condition_frienchiese();
            }

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
                                String responseData = jsonObject.getString("term_condition");
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
            ErrorMessage.T(TermsAndConditionActivity.this, "No Internet");
        }
    }


}
