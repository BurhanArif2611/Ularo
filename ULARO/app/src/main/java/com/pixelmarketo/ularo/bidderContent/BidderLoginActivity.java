package com.pixelmarketo.ularo.bidderContent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.pixelmarketo.ularo.R;
import com.pixelmarketo.ularo.activities.BaseActivity;
import com.pixelmarketo.ularo.activities.ForgotPasswordActivity;
import com.pixelmarketo.ularo.activities.OTPVerificationActivity;
import com.pixelmarketo.ularo.utility.ErrorMessage;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BidderLoginActivity extends BaseActivity {


    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.title_txt)
    TextView titleTxt;
    @BindView(R.id.tool_barLayout)
    RelativeLayout toolBarLayout;
    @BindView(R.id.abc)
    TextView abc;
    @BindView(R.id.bid_mobile_tv)
    EditText bidMobileTv;
    @BindView(R.id.bidder_login_btn)
    Button bidderLoginBtn;
    @BindView(R.id.bid_register)
    TextView bidRegister;
    @BindView(R.id.bod_forgot_pass)
    TextView bodForgotPass;

    @Override
    protected int getContentResId() {
        return R.layout.activity_bidder_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarWithBackButton("");
        ButterKnife.bind(this);
        Bundle bundle = new Bundle();
        bundle.putString("mignon","Bidder");

        bidderLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ErrorMessage.I_clear(BidderLoginActivity.this, OTPVerificationActivity.class, bundle);

            }
        });


        bidRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ErrorMessage.I(BidderLoginActivity.this, BidderRegistrationActivity.class, null);
            }
        });

        bodForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ErrorMessage.I(BidderLoginActivity.this, ForgotPasswordActivity.class,null);
            }
        });

    }
}
