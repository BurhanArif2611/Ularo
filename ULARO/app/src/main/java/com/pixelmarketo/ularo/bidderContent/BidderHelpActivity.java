package com.pixelmarketo.ularo.bidderContent;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.pixelmarketo.ularo.R;
import com.pixelmarketo.ularo.activities.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BidderHelpActivity extends BaseActivity {

    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.title_txt)
    TextView titleTxt;
    @BindView(R.id.tool_barLayout)
    RelativeLayout toolBarLayout;

    @Override
    protected int getContentResId() {
        return R.layout.activity_bidder_help;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarWithBackButton("HELP");
       /* titleTxt.setText("Help");*/
        ButterKnife.bind(this);


    }
}
