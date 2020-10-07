package com.pixelmarketo.ularo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.pixelmarketo.ularo.R;
import com.pixelmarketo.ularo.bidderContent.BidderHomeActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        ButterKnife.bind(this);


        selectBidder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),BidderLoginActivity.class));
            }
        });

        selectBidSeaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
