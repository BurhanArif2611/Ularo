package com.pixelmarketo.ularo.userContent;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.pixelmarketo.ularo.R;
import com.pixelmarketo.ularo.activities.BaseActivity;
import com.pixelmarketo.ularo.database.UserProfileHelper;
import com.pixelmarketo.ularo.model.BidListModel;
import com.pixelmarketo.ularo.userContent.adapter.BidsCategoryAdapter;
import com.pixelmarketo.ularo.utility.AppConfig;
import com.pixelmarketo.ularo.utility.ErrorMessage;
import com.pixelmarketo.ularo.utility.LoadInterface;
import com.pixelmarketo.ularo.utility.NetworkUtil;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BidsSubmittedCategoryActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.title_txt)
    TextView titleTxt;
    @BindView(R.id.tool_barLayout)
    RelativeLayout toolBarLayout;
    @BindView(R.id.recyclerbidssubmitted)
    RecyclerView recyclerbidssubmitted;
    BidsCategoryAdapter adapter;
    String id, payment;
    @BindView(R.id.swiperefesh)
    SwipeRefreshLayout swiperefesh;
    @BindView(R.id.bid_categry_search_btn)
    EditText bidCategrySearchBtn;

    @Override
    protected int getContentResId() {
        return R.layout.activity_bids_submitted;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setToolbarWithBackButton("");
        titleTxt.setText("Request Submitted");
        swiperefesh.setOnRefreshListener(BidsSubmittedCategoryActivity.this);
        swiperefesh.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_green_dark, android.R.color.holo_orange_dark, android.R.color.holo_blue_dark);
        bidCategrySearchBtn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter.filter(s.toString());
            }
        });
        form_submit();
    }

    private void form_submit() {
        ErrorMessage.E("user_id"+UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id());
        if (NetworkUtil.isNetworkAvailable(BidsSubmittedCategoryActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(BidsSubmittedCategoryActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            Call<ResponseBody> call = apiService.user_request_bid_list(UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id());

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ErrorMessage.E("Response" + response.code());

                    if (response.isSuccessful()) {
                        JSONObject object = null;
                        Gson gson = new Gson();

                        try {
                            swiperefesh.setRefreshing(false);
                            materialDialog.dismiss();
                            object = new JSONObject(response.body().string());
                            ErrorMessage.E("comes in cond" + object.toString());
                            if (object.getString("status").equals("200")) {
                                String responseData = object.toString();

                                BidListModel bidListModel = gson.fromJson(responseData, BidListModel.class);
                                ErrorMessage.T(BidsSubmittedCategoryActivity.this, object.getString("message"));
                                adapter = new BidsCategoryAdapter(BidsSubmittedCategoryActivity.this, bidListModel.getResult());
                                LinearLayoutManager layoutManager = new LinearLayoutManager(BidsSubmittedCategoryActivity.this, RecyclerView.VERTICAL, false);
                                recyclerbidssubmitted.setLayoutManager(layoutManager);
                                recyclerbidssubmitted.setAdapter(adapter);

                            } else {
                                swiperefesh.setRefreshing(false);

                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(BidsSubmittedCategoryActivity.this, object.getString("message"));
                                materialDialog.dismiss();
                            }
                        }  catch (JSONException e) {
                            swiperefesh.setRefreshing(false);

                            e.printStackTrace();
                            materialDialog.dismiss();
                            ErrorMessage.E("JsonException" + e);
                        } catch (Exception e) {
                            swiperefesh.setRefreshing(false);

                            e.printStackTrace();
                            materialDialog.dismiss();
                            ErrorMessage.E("Exceptions" + e);
                        }
                    } else {
                        swiperefesh.setRefreshing(false);

                        materialDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    swiperefesh.setRefreshing(false);


                    t.printStackTrace();
                    ErrorMessage.E("Falure login" + t);
                    materialDialog.dismiss();
                }
            });
        } else {
            ErrorMessage.T(BidsSubmittedCategoryActivity.this, "No Internet");
        }
    }



    @Override
    public void onRefresh() {
        form_submit();
    }
}
