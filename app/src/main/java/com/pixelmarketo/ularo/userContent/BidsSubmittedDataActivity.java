package com.pixelmarketo.ularo.userContent;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.pixelmarketo.ularo.fragment.BottomSheetFragment;
import com.pixelmarketo.ularo.model.BidListModel;
import com.pixelmarketo.ularo.model.BidListResult;
import com.pixelmarketo.ularo.userContent.adapter.BidsSubmittedAdapter;
import com.pixelmarketo.ularo.userContent.fragment.BottomSheetFragmentUser;
import com.pixelmarketo.ularo.utility.AppConfig;
import com.pixelmarketo.ularo.utility.ErrorMessage;
import com.pixelmarketo.ularo.utility.LoadInterface;
import com.pixelmarketo.ularo.utility.NetworkUtil;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BidsSubmittedDataActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, PaymentResultListener, BottomSheetFragmentUser.BottomSheetListener {

    @BindView(R.id.title_txt)
    TextView titleTxt;
    @BindView(R.id.recyclerbidssubmitted)
    RecyclerView recyclerbidssubmitted;
    @BindView(R.id.swiperefesh)
    SwipeRefreshLayout swiperefesh;
    String id = "123", payment = "1234",actual_payment="";
    @BindView(R.id.nodata)
    LinearLayout nodata;
    @BindView(R.id.tvmsg)
    TextView tvmsg;
    @BindView(R.id.imgfilter)
    ImageView imgfilter;
    String price = "", rating = "", quick_mode, end = "";

    @Override
    protected int getContentResId() {
        return R.layout.activity_bids_submitted_data;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        setToolbarWithBackButton("");
        titleTxt.setText("" + bundle.getString("category"));
        id = bundle.getString("id");
        quick_mode = bundle.getString("quick_mode");
        swiperefesh.setOnRefreshListener(BidsSubmittedDataActivity.this);
        swiperefesh.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_green_dark, android.R.color.holo_orange_dark, android.R.color.holo_blue_dark);
        form_submit();
    }

    @OnClick(R.id.imgfilter)
    public void onViewClicked() {
        BottomSheetFragmentUser bottomSheetFragment = new BottomSheetFragmentUser();
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }

    private void form_submit() {
        ErrorMessage.E("price>>>" + price);
        ErrorMessage.E("rating>>>" + rating);
        ErrorMessage.E("end>>>" + end);
        ErrorMessage.E("user_id" + UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id());
        if (NetworkUtil.isNetworkAvailable(BidsSubmittedDataActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(BidsSubmittedDataActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            Call<ResponseBody> call = apiService.user_bid_list(UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id(), id, price, rating, end);

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
                                nodata.setVisibility(View.GONE);
                                recyclerbidssubmitted.setVisibility(View.VISIBLE);
                                BidListModel bidListModel = gson.fromJson(responseData, BidListModel.class);
                                tvmsg.setText(object.getString("message"));
                                //  ErrorMessage.T(BidsSubmittedDataActivity.this, object.getString("message"));
                                List<BidListResult> list = bidListModel.getResult();

                                if (end.equals("high_to_low")) {
                                    Comparator<BidListResult> rankOrder = new Comparator<BidListResult>() {
                                        public int compare(BidListResult s1, BidListResult e2) {
                                            return s1.getEnd_date().compareTo(e2.getEnd_date());
                                        }
                                    };
                                    Collections.sort(list, rankOrder);
                                    Collections.reverse(list);//high to low
                                } else if (end.equals("low_to_high")) {
                                    Comparator<BidListResult> rankOrder = new Comparator<BidListResult>() {
                                        public int compare(BidListResult s1, BidListResult e2) {
                                            return s1.getEnd_date().compareTo(e2.getEnd_date());
                                        }
                                    };
                                    Collections.sort(list,rankOrder);//low to high
                                }
                                BidsSubmittedAdapter adapter = new BidsSubmittedAdapter(BidsSubmittedDataActivity.this, bidListModel.getResult());
                                LinearLayoutManager layoutManager = new LinearLayoutManager(BidsSubmittedDataActivity.this, RecyclerView.VERTICAL, false);
                                recyclerbidssubmitted.setLayoutManager(layoutManager);
                                recyclerbidssubmitted.setAdapter(adapter);

                            } else {
                                swiperefesh.setRefreshing(false);
                                nodata.setVisibility(View.VISIBLE);
                                recyclerbidssubmitted.setVisibility(View.GONE);
                                tvmsg.setText(object.getString("message"));

                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(BidsSubmittedDataActivity.this, object.getString("message"));
                                materialDialog.dismiss();
                            }
                        } catch (JSONException e) {
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
            ErrorMessage.T(BidsSubmittedDataActivity.this, "No Internet");
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        ErrorMessage.E("payment" + s);
        confirm_payment(s);
    }

    @Override
    public void onPaymentError(int i, String s) {
        ErrorMessage.E("payment" + s);
    }


    public void startPayment(String Charge, String image, String bid_id,String actual_amount) {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        ErrorMessage.E("Charge startPayment" + Charge);
        ErrorMessage.E("bid_id" + bid_id);
        ErrorMessage.E("actual_amount" + actual_amount);
        id = bid_id;
        payment = Charge;
        actual_payment=actual_amount;
        final Activity activity = this;
        final Checkout co = new Checkout();
        try {
            JSONObject options = new JSONObject();
            options.put("name", "Ullaro");
            options.put("description", "Bid Approval");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", image);
            options.put("currency", "INR");
            options.put("amount", Charge);


            JSONObject preFill = new JSONObject();
            preFill.put("email", "squarefeethelp@gmail.com");
            preFill.put("contact", UserProfileHelper.getInstance().getUserProfileModel().get(0).getUserPhone());

            options.put("prefill", preFill);


            co.open(activity, options);
           // confirm_payment("123456");
        } catch (Exception e) {
            ErrorMessage.E("" + e.getMessage());
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void confirm_payment(String s) {
        ErrorMessage.E("district:" + UserProfileHelper.getInstance().getUserProfileModel().get(0).getDistrict());
        ErrorMessage.E("id:" + id);
        ErrorMessage.E("pay:" + payment);
        ErrorMessage.E("tran:" + s);
        ErrorMessage.E("actual_payment:" + actual_payment);
        if (NetworkUtil.isNetworkAvailable(BidsSubmittedDataActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(BidsSubmittedDataActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            Call<ResponseBody> call = apiService.bid_response(id, actual_payment, "Online", s, UserProfileHelper.getInstance().getUserProfileModel().get(0).getDistrict());

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
                                ErrorMessage.T(BidsSubmittedDataActivity.this, object.getString("message"));

                            } else {
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(BidsSubmittedDataActivity.this, object.getString("message"));
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
            ErrorMessage.T(BidsSubmittedDataActivity.this, "No Internet");
        }
    }

    @Override
    public void onRefresh() {
        form_submit();
    }

    @Override
    public void onButtonClicked(String type, String filter) {
        ErrorMessage.E("type>>" + type);
        if (type.equals("Bid Amount")) {
            price = filter;
            rating = "";
        } else if (type.equals("Rating")) {
            rating = filter;
            price = "";
        } else {
            end = filter;
        }
        form_submit();
    }
}
