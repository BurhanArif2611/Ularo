package com.pixelmarketo.ularo.userContent;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.pixelmarketo.ularo.R;
import com.pixelmarketo.ularo.activities.AboutUsActivity;
import com.pixelmarketo.ularo.activities.ChangePasswordActivity;
import com.pixelmarketo.ularo.activities.SelectionActivity;
import com.pixelmarketo.ularo.bidderContent.BidderHelpActivity;
import com.pixelmarketo.ularo.bidderContent.BidderOrderHistoryActivity;
import com.pixelmarketo.ularo.bidderContent.TermsAndConditionActivity;
import com.pixelmarketo.ularo.bidderContent.adapter.BidderHomeAdapter;
import com.pixelmarketo.ularo.database.UserProfileHelper;
import com.pixelmarketo.ularo.model.CategoryModel;
import com.pixelmarketo.ularo.userContent.adapter.ViewPagerAdapter;
import com.pixelmarketo.ularo.utility.AppConfig;
import com.pixelmarketo.ularo.utility.ErrorMessage;
import com.pixelmarketo.ularo.utility.LoadInterface;
import com.pixelmarketo.ularo.utility.NetworkUtil;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserHomeActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.toogle)
    ImageView toogle;
    @BindView(R.id.bid_home_rec_v)
    RecyclerView bidHomeRecV;
    @BindView(R.id.imageView)
    CircleImageView imageView;
    @BindView(R.id.user_name_tv)
    TextView userNameTv;
    @BindView(R.id.user_mobile_tv)
    TextView userMobileTv;
    @BindView(R.id.view_profile_tv)
    TextView viewProfileTv;
    @BindView(R.id.header_layout)
    LinearLayout headerLayout;
    @BindView(R.id.faq_tv)
    TextView faqTv;
    @BindView(R.id.bid_state_tv)
    TextView bidStateTv;
    @BindView(R.id.help_tv)
    TextView helpTv;
    @BindView(R.id.profile_tv)
    TextView profileTv;
    @BindView(R.id.history_tv)
    TextView historyTv;
    @BindView(R.id.terms_condition_Nav)
    TextView termsConditionNav;
    @BindView(R.id.logoutItemNav)
    TextView logoutItemNav;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    Boolean bidder = true;
    @BindView(R.id.tvchangepass)
    TextView tvchangepass;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;
    @BindView(R.id.drawer)
    LinearLayout drawer;
    @BindView(R.id.tvaboutus)
    TextView tvaboutus;
    @BindView(R.id.banner_slider)
    SliderView bannerSlider;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bidder_home);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        userNameTv.setText(UserProfileHelper.getInstance().getUserProfileModel().get(0).getDisplayName());
        userMobileTv.setText(UserProfileHelper.getInstance().getUserProfileModel().get(0).getUserPhone());
        Glide.with(this).load(UserProfileHelper.getInstance().getUserProfileModel().get(0).getProfile_pic()).placeholder(R.drawable.ic_user).into(imageView);
        bidStateTv.setText("Request Submitted");
        historyTv.setText("Bid History");
        // ErrorMessage.E("bundle" + bundle.getString("from"));
        String string = getIntent().getStringExtra("from");
        if (string != null) {
            ErrorMessage.E("bundle" + string);
            if (string.equalsIgnoreCase("Bidder")) {
                bidder = true;
            } else if (string.equalsIgnoreCase("User")) {
                bidder = false;
            }
        }


        if (bundle != null) {
            ErrorMessage.E("bundle" + bundle.getString("from"));
            if (bundle.getString("from").equalsIgnoreCase("Bidder")) {
                bidder = true;
            } else if (bundle.getString("from").equalsIgnoreCase("User")) {
                bidder = false;
            }
        }
        swiperefresh.setOnRefreshListener(UserHomeActivity.this);
        swiperefresh.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_green_dark, android.R.color.holo_orange_dark, android.R.color.holo_blue_dark);

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
                ErrorMessage.I(this, UserProfileActivity.class, null);
                drawerLayout.closeDrawer(GravityCompat.START);

                break;
            case R.id.faq_tv:
                drawerLayout.closeDrawer(GravityCompat.START);

                break;
            case R.id.bid_state_tv:
                ErrorMessage.I(UserHomeActivity.this, BidsSubmittedCategoryActivity.class, null);
                drawerLayout.closeDrawer(GravityCompat.START);

                break;
            case R.id.help_tv:
                ErrorMessage.I(UserHomeActivity.this, BidderHelpActivity.class, null);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.profile_tv:
                break;
            case R.id.history_tv:
                Bundle bundle1 = new Bundle();
                bundle1.putString("from", "user");

                ErrorMessage.I(UserHomeActivity.this, BidderOrderHistoryActivity.class, bundle1);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.terms_condition_Nav:
                Bundle bundle2 = new Bundle();
                bundle2.putString("from", "user");
                ErrorMessage.I(UserHomeActivity.this, TermsAndConditionActivity.class, bundle2);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.logoutItemNav:
                Logout_PopUP();
                break;
            case R.id.drawer_layout:
                break;
            case R.id.tvchangepass:
                Bundle bundle = new Bundle();
                bundle.putString("from", "user");
                ErrorMessage.I(this, ChangePasswordActivity.class, bundle);
                drawerLayout.closeDrawer(GravityCompat.START);

                break;
            case R.id.drawer:
                break;
            case R.id.tvaboutus:
                ErrorMessage.I(this, AboutUsActivity.class, null);
                drawerLayout.closeDrawer(GravityCompat.START);

                break;

        }


    }

    public void Logout_PopUP() {

        final Dialog dialog = new Dialog(UserHomeActivity.this);
        dialog.setContentView(R.layout.logout_popup);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        final Button submit_btn = (Button) dialog.findViewById(R.id.submit_btn);
        final Button cancel_btn = (Button) dialog.findViewById(R.id.cancel_btn);

        submit_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
                UserProfileHelper.getInstance().delete();
                Bundle bundle = new Bundle();
                bundle.putString("from", "user");
                ErrorMessage.I_clear(UserHomeActivity.this, SelectionActivity.class, bundle);
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
        if (NetworkUtil.isNetworkAvailable(UserHomeActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(UserHomeActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            ErrorMessage.E("" + UserProfileHelper.getInstance().getUserProfileModel().get(0).getDistrict());

            Call<ResponseBody> call = apiService.get_category(UserProfileHelper.getInstance().getUserProfileModel().get(0).getDistrict());


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
                                String responseData = jsonObject.toString();
                                ErrorMessage.E("responseData" + responseData);

                                CategoryModel categoryModel = gson.fromJson(responseData, CategoryModel.class);
                                if (categoryModel.getResult().size() > 0) {

                                    BidderHomeAdapter bidderHomeAdapter = new BidderHomeAdapter(UserHomeActivity.this, categoryModel.getResult(), responseData, bidder);
                                    GridLayoutManager gridLayoutManager = new GridLayoutManager(UserHomeActivity.this, 2);
                                    bidHomeRecV.setLayoutManager(gridLayoutManager);
                                    bidHomeRecV.setAdapter(bidderHomeAdapter);
                                }
                                if (categoryModel.getAd().size() > 0) {

                                    ViewPagerAdapter sliderAdapter = new ViewPagerAdapter(UserHomeActivity.this, categoryModel.getAd());
                                    bannerSlider.setSliderAdapter(sliderAdapter);
                                }
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
            ErrorMessage.T(UserHomeActivity.this, "No Internet");
        }
    }


    @Override
    public void onRefresh() {
        getcategory();
    }
}