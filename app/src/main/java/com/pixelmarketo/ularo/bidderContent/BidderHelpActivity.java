package com.pixelmarketo.ularo.bidderContent;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.pixelmarketo.ularo.R;
import com.pixelmarketo.ularo.activities.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BidderHelpActivity extends BaseActivity {

    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.title_txt)
    TextView titleTxt;
    @BindView(R.id.tool_barLayout)
    RelativeLayout toolBarLayout;
    @BindView(R.id.tvfirstcl)
    TextView tvfirstcl;
    @BindView(R.id.tvsecondcl)
    TextView tvsecondcl;
    @BindView(R.id.tvthirdcl)
    TextView tvthirdcl;
    @BindView(R.id.email)
    TextView email;

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

    @OnClick({R.id.tvfirstcl, R.id.tvsecondcl, R.id.tvthirdcl, R.id.email})
    public void onViewClicked(View view) {
        Intent callIntent;

        switch (view.getId()) {

            case R.id.tvfirstcl:
                callIntent = new Intent(Intent.ACTION_VIEW);
                callIntent.setData(Uri.parse("tel:" + "7423491920"));
                startActivity(callIntent);
                break;
            case R.id.tvsecondcl:
                callIntent = new Intent(Intent.ACTION_VIEW);
                callIntent.setData(Uri.parse("tel:" + "9109825271"));
                startActivity(callIntent);
                break;
            case R.id.tvthirdcl:
                callIntent = new Intent(Intent.ACTION_VIEW);
                callIntent.setData(Uri.parse("tel:" + "9109825272"));
                startActivity(callIntent);
                break;
            case R.id.email:
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "ularoconstructionshelp@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
                break;
        }
    }
}
