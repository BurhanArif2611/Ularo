package com.pixelmarketo.ularo.bidderContent;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.pixelmarketo.ularo.R;
import com.pixelmarketo.ularo.activities.BaseActivity;
import com.pixelmarketo.ularo.bidderContent.adapter.OrderHistoryAdapter;
import com.pixelmarketo.ularo.database.UserProfileHelper;
import com.pixelmarketo.ularo.model.BidListModel;
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

public class BidderOrderHistoryActivity extends BaseActivity {

    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.title_txt)
    TextView titleTxt;
    @BindView(R.id.tool_barLayout)
    RelativeLayout toolBarLayout;
    @BindView(R.id.order_search_btn)
    EditText orderSearchBtn;
    @BindView(R.id.order_list_recler_view)
    RecyclerView orderListReclerView;
    @BindView(R.id.nodata)
    LinearLayout nodata;


    @Override
    protected int getContentResId() {
        return R.layout.activity_bidder_order_history;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setToolbarWithBackButton("");
        titleTxt.setText("ORDER HISTORY");
        Bundle bundle = getIntent().getExtras();
        bundle.getString("from");
        if (bundle.getString("from").equalsIgnoreCase("user")) {
            titleTxt.setText("Approve Bid List");

            get_history("user");
        } else {
            titleTxt.setText("ORDER HISTORY");

            get_history("bidder");
        }

    }

    private void get_history(String from) {
        if (NetworkUtil.isNetworkAvailable(BidderOrderHistoryActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(BidderOrderHistoryActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);/*SavedData.getFCM_ID()*/
            Call<ResponseBody> call;
            if (from.equalsIgnoreCase("user")) {
                call = apiService.user_order_history(UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id());

            } else {
                call = apiService.order_history(UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id());

            }
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ErrorMessage.E("Response" + response.code());
                    ErrorMessage.E("Response" + response.toString());

                    if (response.isSuccessful()) {
                        JSONObject object = null;
                        Gson gson = new Gson();

                        try {
                            materialDialog.dismiss();
                            object = new JSONObject(response.body().string());
                            ErrorMessage.E("comes in cond" + object.toString());
                            if (object.getString("status").equals("200")) {
                                ErrorMessage.T(BidderOrderHistoryActivity.this, object.getString("message"));
                                String responseData = object.toString();
                                nodata.setVisibility(View.GONE);
                                orderListReclerView.setVisibility(View.VISIBLE);
                                orderSearchBtn.setVisibility(View.VISIBLE);
                                BidListModel bidListModel = gson.fromJson(responseData, BidListModel.class);
                                OrderHistoryAdapter orderHistoryAdapter = new OrderHistoryAdapter(BidderOrderHistoryActivity.this, bidListModel.getResult(),from);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BidderOrderHistoryActivity.this, RecyclerView.VERTICAL, false);
                                orderListReclerView.setLayoutManager(linearLayoutManager);
                                orderListReclerView.setAdapter(orderHistoryAdapter);
                            } else {
                                nodata.setVisibility(View.VISIBLE);
                                orderListReclerView.setVisibility(View.GONE);
                                orderSearchBtn.setVisibility(View.GONE);
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(BidderOrderHistoryActivity.this, object.getString("message"));
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
            ErrorMessage.T(BidderOrderHistoryActivity.this, "No Internet");
        }
    }

}
