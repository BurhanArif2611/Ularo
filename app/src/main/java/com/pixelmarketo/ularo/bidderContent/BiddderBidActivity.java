package com.pixelmarketo.ularo.bidderContent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.pixelmarketo.ularo.R;
import com.pixelmarketo.ularo.activities.BaseActivity;

public class BiddderBidActivity extends BaseActivity {

    @Override
    protected int getContentResId() {
        return R.layout.activity_biddder_bid;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarWithBackButton("Bid Status");
    }
}
