package com.pixelmarketo.ularo.bidderContent;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.pixelmarketo.ularo.R;
import com.pixelmarketo.ularo.activities.BaseActivity;
import com.pixelmarketo.ularo.bidderContent.adapter.BidderSelectionAdapeter;
import com.pixelmarketo.ularo.database.UserProfileHelper;
import com.pixelmarketo.ularo.fragment.BottomSheetFragment;
import com.pixelmarketo.ularo.model.BidListModel;
import com.pixelmarketo.ularo.model.BidListResult;
import com.pixelmarketo.ularo.utility.AppConfig;
import com.pixelmarketo.ularo.utility.ErrorMessage;
import com.pixelmarketo.ularo.utility.LoadInterface;
import com.pixelmarketo.ularo.utility.NetworkUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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

public class BidderOnCategortySelecActivity extends BaseActivity implements BottomSheetFragment.BottomSheetListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.title_txt)
    TextView titleTxt;
    @BindView(R.id.tool_barLayout)
    RelativeLayout toolBarLayout;
    @BindView(R.id.bid_categry_search_btn)
    EditText bidCategrySearchBtn;
    @BindView(R.id.bid_selecction_list_recler_view)
    RecyclerView bidSelecctionListReclerView;
    List<String> bids;
    BidderSelectionAdapeter adapeter;
    String from, id;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;
    @BindView(R.id.imgfilter)
    ImageView imgfilter;
    String area = "", story = "", filter_city = null,job="",point="",running="";

    @Override
    protected int getContentResId() {
        return R.layout.activity_bidder_on_categorty_selec;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarWithBackButton("");
        Bundle bundle = getIntent().getExtras();
        ButterKnife.bind(this);
        swiperefresh.setOnRefreshListener(BidderOnCategortySelecActivity.this);
        swiperefresh.setColorSchemeResources(R.color.colorPrimary, android.R.color.holo_green_dark, android.R.color.holo_orange_dark, android.R.color.holo_blue_dark);

        if (bundle != null) {
            id = bundle.getString("id");
            titleTxt.setText("" + bundle.getString("name"));
            from = bundle.getString("name");

        }


        bidCategrySearchBtn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                adapeter.filter(s.toString());
            }
        });

        form_submit();
        if( (id.equals("20")) ||  (id.equals("22"))||(id.equals("23")) || (id.equals("24")) || (id.equals("25")) ||(id.equals("26"))|| (id.equals("27")) ||(id.equals("28"))|| (id.equals("30"))) {
            imgfilter.setVisibility(View.VISIBLE);
        }
    }


    private void form_submit() {
        ErrorMessage.E("id" + id);
        ErrorMessage.E("area>>" + UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id());
        ErrorMessage.E("story>>" + story);
        ErrorMessage.E("running>>" + running);
        ErrorMessage.E("filter_city>>" + filter_city);
       // List<BidListResult> list = new ArrayList<BidListResult>();
        if (NetworkUtil.isNetworkAvailable(BidderOnCategortySelecActivity.this)) {
            final Dialog materialDialog = ErrorMessage.initProgressDialog(BidderOnCategortySelecActivity.this);
            LoadInterface apiService = AppConfig.getClient().create(LoadInterface.class);
            Call<ResponseBody> call = apiService.bid_list(UserProfileHelper.getInstance().getUserProfileModel().get(0).getUser_id(), id, area, story, filter_city,running);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    ErrorMessage.E("Response" + response.code());

                    if (response.isSuccessful()) {
                        JSONObject object = null;
                        Gson gson = new Gson();
                        try {
                            swiperefresh.setRefreshing(false);
                            materialDialog.dismiss();
                            object = new JSONObject(response.body().string());
                            ErrorMessage.E("comes in cond" + object.toString());
                            String responseData = object.toString();
                            if (object.getString("status").equals("200")) {
                                BidListModel bidListModel = gson.fromJson(responseData, BidListModel.class);
                                List<BidListResult> list = bidListModel.getResult();

                                if (point.equals("high_to_low")){
                                    Collections.sort(list);//high to low
                                    Collections.reverse(list);//high to low
                                }
                                else if (point.equals("low_to_high")){
                                    Collections.sort(list);//low to high
                                }
                                if (job.equals("high_to_low")) {
                                    Comparator<BidListResult> rankOrder = new Comparator<BidListResult>() {
                                        public int compare(BidListResult s1, BidListResult e2) {
                                            return s1.getTotal_job_work().compareTo(e2.getTotal_job_work());
                                        }
                                    };
                                    Collections.sort(list, rankOrder);
                                    Collections.reverse(list);//high to low
                                } else if (job.equals("low_to_high")) {
                                    Comparator<BidListResult> rankOrder = new Comparator<BidListResult>() {
                                        public int compare(BidListResult s1, BidListResult e2) {
                                            return s1.getTotal_job_work().compareTo(e2.getTotal_job_work());
                                        }
                                    };
                                    Collections.sort(list,rankOrder);//low to high
                                }
                                adapeter = new BidderSelectionAdapeter(BidderOnCategortySelecActivity.this, bidListModel.getResult(), id, from);
                                LinearLayoutManager layoutManager = new LinearLayoutManager(BidderOnCategortySelecActivity.this, RecyclerView.VERTICAL, false);
                                bidSelecctionListReclerView.setLayoutManager(layoutManager);
                                bidSelecctionListReclerView.setAdapter(adapeter);
                            }
                            else {
                                swiperefresh.setRefreshing(false);
                                ErrorMessage.E("comes in else");
                                ErrorMessage.T(BidderOnCategortySelecActivity.this, object.getString("message"));
                                materialDialog.dismiss();
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
            ErrorMessage.T(BidderOnCategortySelecActivity.this, "No Internet");
        }
    }

    @OnClick(R.id.imgfilter)
    public void onViewClicked() {
        Bundle bundle = new Bundle();
        bundle.putString("id", id);

        BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
        bottomSheetFragment.setArguments(bundle);

        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }

    @Override
    public void onRefresh() {
        form_submit();
    }

    @Override
    public void onButtonClicked(String type, String filter, String city) {
        ErrorMessage.E("type>>" + type);
        filter_city = city;
        story = "";
        area = "";
        if (type != null) {
            if (type.equals("Area")) {
                story = "";
                area = filter;

            }
            else if (type.equals("Running")){
                running=filter;
            }
            else if (type.equals("Story")){
                area = "";
                story = filter;

            }
            else if (type.equals("Job")){
              job=filter;

            }
            else if (type.equals("Point")){

               point=filter;
            }
        }
        form_submit();
    }
}
