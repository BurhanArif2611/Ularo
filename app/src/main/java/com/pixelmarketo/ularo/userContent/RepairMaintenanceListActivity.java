package com.pixelmarketo.ularo.userContent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pixelmarketo.ularo.R;
import com.pixelmarketo.ularo.activities.BaseActivity;
import com.pixelmarketo.ularo.database.UserProfileHelper;
import com.pixelmarketo.ularo.database.UserProfileModel;
import com.pixelmarketo.ularo.model.BidListModel;
import com.pixelmarketo.ularo.model.RepairListModel;
import com.pixelmarketo.ularo.userContent.adapter.BidsSubmittedAdapter;
import com.pixelmarketo.ularo.userContent.adapter.RepairListAdapter;
import com.pixelmarketo.ularo.utility.AppConfig;
import com.pixelmarketo.ularo.utility.ErrorMessage;
import com.pixelmarketo.ularo.utility.LoadInterface;
import com.pixelmarketo.ularo.utility.NetworkUtil;
import com.pixelmarketo.ularo.utility.SavedData;
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

public class RepairMaintenanceListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, PaymentResultListener {
    @BindView(R.id.title_txt)
    TextView titleTxt;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.nodata)
    LinearLayout nodata;
    @BindView(R.id.tvmsg)
    TextView tvmsg;
    String payment;

    @Override
    protected int getContentResId() {
        return R.layout.activity_repair_maintenance_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setToolbarWithBackButton("");
        titleTxt.setText("repair and maintenance");
        //swiperefesh.setOnRefreshListener(RepairMaintenanceListActivity.this);
       // swiperefesh.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_green_dark, android.R.color.holo_orange_dark, android.R.color.holo_blue_dark);
       form_submit();

    }

    private void form_submit() {

        ErrorMessage.E("user_id" + SavedData.getChekcData());
        ErrorMessage.E("city" + UserProfileHelper.getInstance().getUserProfileModel().get(0).getCity());
        if (NetworkUtil.isNetworkAvailable(RepairMaintenanceListActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(RepairMaintenanceListActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            Call<ResponseBody> call = apiService.repair_list(SavedData.getChekcData(),UserProfileHelper.getInstance().getUserProfileModel().get(0).getCity());

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ErrorMessage.E("Response" + response.code());

                    if (response.isSuccessful()) {
                        JSONObject object = null;
                        Gson gson = new Gson();

                        try {
                         //   swiperefesh.setRefreshing(false);
                            materialDialog.dismiss();
                            object = new JSONObject(response.body().string());
                            ErrorMessage.E("comes in cond" + object.toString());
                            if (object.getString("status").equals("200")) {
                                String responseData = object.toString();
                                nodata.setVisibility(View.GONE);
                                recyclerview.setVisibility(View.VISIBLE);
                                RepairListModel repairListModel = gson.fromJson(responseData, RepairListModel.class);
                                tvmsg.setText(object.getString("message"));

                                    RepairListAdapter adapter = new RepairListAdapter(RepairMaintenanceListActivity.this, repairListModel.getResult());
                                    LinearLayoutManager layoutManager = new LinearLayoutManager(RepairMaintenanceListActivity.this, RecyclerView.VERTICAL, false);
                                    recyclerview.setLayoutManager(layoutManager);
                                    recyclerview.setAdapter(adapter);

                            }
                            else if (object.getString("status").equals("300")){
                                nodata.setVisibility(View.VISIBLE);
                                recyclerview.setVisibility(View.GONE);
                                tvmsg.setText(object.getString("message"));
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(RepairMaintenanceListActivity.this, object.getString("message"));
                                startPayment(String.valueOf(5000));
                                materialDialog.dismiss();
                            }
                            else {
                                nodata.setVisibility(View.VISIBLE);
                                recyclerview.setVisibility(View.GONE);
                                tvmsg.setText(object.getString("message"));
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(RepairMaintenanceListActivity.this, object.getString("message"));
                                materialDialog.dismiss();
                            }
                        } catch (JSONException e) {
                          //  swiperefesh.setRefreshing(false);

                            e.printStackTrace();
                            materialDialog.dismiss();
                            ErrorMessage.E("JsonException" + e);
                        } catch (Exception e) {
                         //   swiperefesh.setRefreshing(false);

                            e.printStackTrace();
                            materialDialog.dismiss();
                            ErrorMessage.E("Exceptions" + e);
                        }
                    } else {
                      //  swiperefesh.setRefreshing(false);

                        materialDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                   // swiperefesh.setRefreshing(false);


                    t.printStackTrace();
                    ErrorMessage.E("Falure login" + t);
                    materialDialog.dismiss();
                }
            });
        } else {
            ErrorMessage.T(RepairMaintenanceListActivity.this, "No Internet");
        }
    }

    @Override
    public void onRefresh() {
        // form_submit();
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
        payment=Charge;
        try {
            JSONObject options = new JSONObject();
            options.put("name", "Ullaro");
            options.put("description", "Repair and Maintenance fee");
            //You can omit the image option to fetch the image from dashboard
            options.put("currency", "INR");
            options.put("amount", payment);


            JSONObject preFill = new JSONObject();
            preFill.put("email", "squarefeethelp@gmail.com");
            preFill.put("contact", UserProfileHelper.getInstance().getUserProfileModel().get(0).getUserPhone());

            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            ErrorMessage.E("" + e.getMessage());
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    private void confirm_payment(String s) {
        if (NetworkUtil.isNetworkAvailable(RepairMaintenanceListActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(RepairMaintenanceListActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            Call<ResponseBody> call = apiService.bid_response(SavedData.getChekcData(), "50", "Online", s,UserProfileHelper.getInstance().getUserProfileModel().get(0).getDistrict());

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
                                ErrorMessage.T(RepairMaintenanceListActivity.this, object.getString("message"));
                                ErrorMessage.I(RepairMaintenanceListActivity.this,RepairMaintenanceListActivity.class,null);

                            } else {
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(RepairMaintenanceListActivity.this, object.getString("message"));
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
            ErrorMessage.T(RepairMaintenanceListActivity.this, "No Internet");
        }
    }
}
