package com.pixelmarketo.ularo.bidderContent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.pixelmarketo.ularo.R;
import com.pixelmarketo.ularo.activities.AboutUsActivity;
import com.pixelmarketo.ularo.activities.AdvertisementActivity;
import com.pixelmarketo.ularo.activities.ChangePasswordActivity;
import com.pixelmarketo.ularo.activities.SelectionActivity;
import com.pixelmarketo.ularo.bidderContent.adapter.BidderHomeAdapter;
import com.pixelmarketo.ularo.database.UserProfileHelper;
import com.pixelmarketo.ularo.model.CategoryModel;
import com.pixelmarketo.ularo.utility.AppConfig;
import com.pixelmarketo.ularo.utility.ErrorMessage;
import com.pixelmarketo.ularo.utility.LoadInterface;
import com.pixelmarketo.ularo.utility.NetworkUtil;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BidderHomeActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, PaymentResultListener {

    @BindView(R.id.bid_home_rec_v)
    RecyclerView bidHomeRecV;
    @BindView(R.id.imageView)
    CircleImageView imageView;
    @BindView(R.id.user_name_tv)
    TextView userNameTv;
    @BindView(R.id.view_profile_tv)
    TextView viewProfileTv;
    @BindView(R.id.header_layout)
    LinearLayout headerLayout;
    @BindView(R.id.faq_tv)
    TextView faqTv;
    @BindView(R.id.history_tv)
    TextView historyTv;
    @BindView(R.id.terms_condition_Nav)
    TextView termsConditionNav;
    @BindView(R.id.logoutItemNav)
    TextView logoutItemNav;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.toogle)
    ImageView toogle;
    @BindView(R.id.user_mobile_tv)
    TextView userMobileTv;
    List<String> list;
    ArrayList<Integer> images;
    @BindView(R.id.bid_state_tv)
    TextView bidStateTv;
    @BindView(R.id.help_tv)
    TextView helpTv;
    @BindView(R.id.profile_tv)
    TextView profileTv;
    Boolean bidder = true;
    @BindView(R.id.tvchangepass)
    TextView tvchangepass;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;
    @BindView(R.id.drawer)
    LinearLayout drawer;
    @BindView(R.id.tvaboutus)
    TextView tvaboutus;
    Bundle bundle;

    @BindView(R.id.banner_slider)
    SliderView bannerSlider;
    String payment;

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bidder_home);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        swiperefresh.setOnRefreshListener(BidderHomeActivity.this);
        swiperefresh.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_green_dark, android.R.color.holo_orange_dark, android.R.color.holo_blue_dark);
        userNameTv.setText(UserProfileHelper.getInstance().getUserProfileModel().get(0).getDisplayName());
        userMobileTv.setText(UserProfileHelper.getInstance().getUserProfileModel().get(0).getUserPhone());
        Glide.with(this).load(UserProfileHelper.getInstance().getUserProfileModel().get(0).getProfile_pic()).placeholder(R.drawable.ic_user).into(imageView);
        historyTv.setVisibility(View.GONE);
        bannerSlider.setVisibility(View.GONE);
        if (bundle != null) {
            if (bundle.get("from").toString().equalsIgnoreCase("Bidder")) {
                bidder = true;
            } else if (bundle.get("from").toString().equalsIgnoreCase("User")) {
                bidder = false;
            }
        }
        String string = getIntent().getStringExtra("from");
        if (string != null) {
            if (bundle.get("from").toString().equalsIgnoreCase("Bidder")) {
                bidder = true;

            } else if (bundle.get("from").toString().equalsIgnoreCase("User")) {
                bidder = false;

            }
        }

        toogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
        getcategory();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bidder_home, menu);
        return true;
    }

    @OnClick({R.id.toogle, R.id.imageView, R.id.user_name_tv, R.id.user_mobile_tv, R.id.view_profile_tv, R.id.faq_tv, R.id.bid_state_tv, R.id.help_tv, R.id.profile_tv, R.id.history_tv, R.id.terms_condition_Nav, R.id.logoutItemNav, R.id.drawer_layout, R.id.tvchangepass, R.id.drawer, R.id.tvaboutus})
    public void onClick(View view) {
        bundle = new Bundle();
        switch (view.getId()) {
            case R.id.toogle:
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
                break;
            case R.id.imageView:
                break;
            case R.id.user_name_tv:
                break;
            case R.id.user_mobile_tv:
                break;
            case R.id.view_profile_tv:
                ErrorMessage.I(BidderHomeActivity.this, BidderProfileActivity.class, null);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.faq_tv:
                drawerLayout.closeDrawer(GravityCompat.START);

                break;
            case R.id.bid_state_tv:

                bundle.putString("from", "bidder");
                ErrorMessage.I(BidderHomeActivity.this, BidsStatusActivity.class, bundle);
                drawerLayout.closeDrawer(GravityCompat.START);

                break;
            case R.id.help_tv:
                ErrorMessage.I(BidderHomeActivity.this, BidderHelpActivity.class, null);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.profile_tv:
                ErrorMessage.I(BidderHomeActivity.this, BidderProfileActivity.class, null);
                drawerLayout.closeDrawer(GravityCompat.START);

                break;
            case R.id.history_tv:

                bundle.putString("from", "bidder");
                ErrorMessage.I(BidderHomeActivity.this, BidderOrderHistoryActivity.class, bundle);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.terms_condition_Nav:
                bundle.putString("from", "bidder");

                ErrorMessage.I(BidderHomeActivity.this, TermsAndConditionActivity.class, bundle);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.logoutItemNav:
                Logout_PopUP();
                break;
            case R.id.drawer_layout:
                break;

            case R.id.tvchangepass:

                bundle.putString("from", "bidder");
                ErrorMessage.I(this, ChangePasswordActivity.class, bundle);
                drawerLayout.closeDrawer(GravityCompat.START);

                break;
            case R.id.drawer:
                ErrorMessage.E("drawer clicked");

                break;
            case R.id.tvaboutus:
                ErrorMessage.I(this, AboutUsActivity.class, null);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
        }

    }

    public void Logout_PopUP() {

        final Dialog dialog = new Dialog(BidderHomeActivity.this);
        dialog.setContentView(R.layout.logout_popup);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        final Button submit_btn = (Button) dialog.findViewById(R.id.submit_btn);
        final Button cancel_btn = (Button) dialog.findViewById(R.id.cancel_btn);

        submit_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
                UserProfileHelper.getInstance().delete();
                ErrorMessage.I_clear(BidderHomeActivity.this, SelectionActivity.class, null);
            }
        });

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        dialog.show();
    }

    public void getcategory() {
        if (NetworkUtil.isNetworkAvailable(BidderHomeActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(BidderHomeActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            Call<ResponseBody> call = apiService.get_service_by_id(UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id());
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
                            swiperefresh.setRefreshing(false);

                            jsonObject = new JSONObject(response.body().string());
                            ErrorMessage.E("get" + jsonObject.toString());
                            materialDialog.dismiss();
                            if (jsonObject.getString("status").equals("200")) {
                                if (jsonObject.getString("payment_status").equals("0")){
                                    payment_PopUP(jsonObject.getString("bidder"));
                                }
                                String responseData = jsonObject.toString();
                                ErrorMessage.E("responseData" + responseData);

                                CategoryModel categoryModel = gson.fromJson(responseData, CategoryModel.class);
                                BidderHomeAdapter bidderHomeAdapter = new BidderHomeAdapter(BidderHomeActivity.this, categoryModel.getResult(), responseData, bidder);
                                GridLayoutManager gridLayoutManager = new GridLayoutManager(BidderHomeActivity.this, 2);
                                bidHomeRecV.setLayoutManager(gridLayoutManager);
                                bidHomeRecV.setAdapter(bidderHomeAdapter);
                            } else {
                                materialDialog.dismiss();
                                ErrorMessage.T(BidderHomeActivity.this, jsonObject.getString("message"));
                            }
                        } catch (JSONException e) {
                            swiperefresh.setRefreshing(false);

                            e.printStackTrace();
                            materialDialog.dismiss();
                            ErrorMessage.E("JsonException" + e);
                        } catch (Exception e) {
                            swiperefresh.setRefreshing(false);

                            e.printStackTrace();
                            materialDialog.dismiss();
                            ErrorMessage.E("Exceptions" + e);
                        }
                    } else {
                        swiperefresh.setRefreshing(false);

                        materialDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    swiperefresh.setRefreshing(false);

                    t.printStackTrace();
                    ErrorMessage.E("Falure login" + t);
                    materialDialog.dismiss();
                }
            });
        } else {
            ErrorMessage.T(BidderHomeActivity.this, "No Internet");
        }
    }

    @Override
    public void onRefresh() {
        getcategory();
    }

    public void payment_PopUP(String bidder) {

        final Dialog dialog = new Dialog(BidderHomeActivity.this);
        dialog.setContentView(R.layout.payment_popup);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        final Button submit_btn = (Button) dialog.findViewById(R.id.submit_btn);
        final Button cancel_btn = (Button) dialog.findViewById(R.id.cancel_btn);
        //cancel_btn.setClickable(t);
        cancel_btn.setEnabled(false);

        if (bidder.equalsIgnoreCase("Normal Bidder")){
            payment="10000";
        }
        else {
            payment="20000";
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
        dialog.setCancelable(false);

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
            options.put("description", "Rehgistartion fees");
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
        if (NetworkUtil.isNetworkAvailable(BidderHomeActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(BidderHomeActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            Call<ResponseBody> call = apiService.confirm_payment_registration(UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id(), payment, "Online", s, UserProfileHelper.getInstance().getUserProfileModel().get(0).getDistrict());

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
                                ErrorMessage.T(BidderHomeActivity.this, object.getString("message"));


                            } else {
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(BidderHomeActivity.this, object.getString("message"));
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
            ErrorMessage.T(BidderHomeActivity.this, "No Internet");
        }
    }


}
