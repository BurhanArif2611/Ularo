package com.pixelmarketo.ularo.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.pixelmarketo.ularo.R;
import com.pixelmarketo.ularo.bidderContent.BidderLoginActivity;
import com.pixelmarketo.ularo.utility.ErrorMessage;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectionActivity extends AppCompatActivity {

    @BindView(R.id.bidder)
    ImageView bidder;
    @BindView(R.id.select_bidder)
    LinearLayout selectBidder;
    @BindView(R.id.select_bid_seaker)
    LinearLayout selectBidSeaker;
    @BindView(R.id.select_frienchiese)
    LinearLayout selectFrienchiese;
    @BindView(R.id.select_add)
    LinearLayout selectAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        ButterKnife.bind(this);
        Bundle bundle = new Bundle();

        selectBidder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("from", "Bidder Login");
                ErrorMessage.I(SelectionActivity.this, BidderLoginActivity.class, bundle);
            }
        });

        selectBidSeaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("from", "User Login Form");
                ErrorMessage.I(SelectionActivity.this, BidderLoginActivity.class, bundle);
            }
        });
        selectFrienchiese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ErrorMessage.I(SelectionActivity.this, FrienchieseActivity.class, null);
            }
        });
        selectAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ErrorMessage.I(SelectionActivity.this, AdvertisementActivity.class, null);
            }
        });
    }


    @Override
    public void onBackPressed() {

    }
}
