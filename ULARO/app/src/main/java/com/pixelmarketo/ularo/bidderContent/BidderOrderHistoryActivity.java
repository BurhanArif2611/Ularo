package com.pixelmarketo.ularo.bidderContent;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pixelmarketo.ularo.R;
import com.pixelmarketo.ularo.activities.BaseActivity;
import com.pixelmarketo.ularo.bidderContent.adapter.OrderHistoryAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

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


        OrderHistoryAdapter orderHistoryAdapter = new OrderHistoryAdapter(BidderOrderHistoryActivity.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BidderOrderHistoryActivity.this,RecyclerView.VERTICAL,false);
        orderListReclerView.setLayoutManager(linearLayoutManager);
        orderListReclerView.setAdapter(orderHistoryAdapter);





    }
}
