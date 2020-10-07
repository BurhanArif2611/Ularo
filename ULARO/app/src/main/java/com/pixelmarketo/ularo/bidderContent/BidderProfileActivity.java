package com.pixelmarketo.ularo.bidderContent;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.pixelmarketo.ularo.R;
import com.pixelmarketo.ularo.activities.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class BidderProfileActivity extends BaseActivity {

    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.title_txt)
    TextView titleTxt;
    @BindView(R.id.tool_barLayout)
    RelativeLayout toolBarLayout;
    @BindView(R.id.profile_pic_bid)
    CircleImageView profilePicBid;
    @BindView(R.id.bid_full_name)
    TextInputEditText bidFullName;
    @BindView(R.id.bid_mobile_num)
    TextInputEditText bidMobileNum;
    @BindView(R.id.bid_address)
    TextInputEditText bidAddress;
    @BindView(R.id.stucture_label)
    TextView stuctureLabel;
    @BindView(R.id.structure_icn)
    ImageView structureIcn;
    @BindView(R.id.bid_acitec_selec)
    RadioButton bidAcitecSelec;
    @BindView(R.id.bid_contractor_selec)
    RadioButton bidContractorSelec;
    @BindView(R.id.bid_glass_work_selec)
    RadioButton bidGlassWorkSelec;
    @BindView(R.id.bid_electrician_selec)
    RadioButton bidElectricianSelec;
    @BindView(R.id.bid_carpenter_selec)
    RadioButton bidCarpenterSelec;
    @BindView(R.id.bid_railing_selec)
    RadioButton bidRailingSelec;
    @BindView(R.id.structure_hidden_menu_one)
    LinearLayout structureHiddenMenuOne;
    @BindView(R.id.bid_painter_selec)
    RadioButton bidPainterSelec;
    @BindView(R.id.bid_plumber_selec)
    RadioButton bidPlumberSelec;
    @BindView(R.id.bid_sectio_eeter_selec)
    RadioButton bidSectioEeterSelec;
    @BindView(R.id.structure_hidden_menu_two)
    LinearLayout structureHiddenMenuTwo;
    @BindView(R.id.bid_labour_supplier_selec)
    RadioButton bidLabourSupplierSelec;
    @BindView(R.id.structure_hidden_menu_three)
    LinearLayout structureHiddenMenuThree;
    @BindView(R.id.structure_type)
    LinearLayout structureType;
    @BindView(R.id.repair_label)
    TextView repairLabel;
    @BindView(R.id.repair_icn)
    ImageView repairIcn;
    @BindView(R.id.repair_spinner)
    Spinner repairSpinner;
    @BindView(R.id.bid_password)
    TextInputEditText bidPassword;
    @BindView(R.id.bid_conferm_password)
    TextInputEditText bidConfermPassword;
    @BindView(R.id.police_label)
    TextView policeLabel;
    @BindView(R.id.police_icn)
    ImageView policeIcn;
    @BindView(R.id.police_verification_select_file)
    TextView policeVerificationSelectFile;
    @BindView(R.id.adhar_label)
    TextView adharLabel;
    @BindView(R.id.adhar_icn)
    ImageView adharIcn;
    @BindView(R.id.adhar_card_select_file)
    TextView adharCardSelectFile;
    @BindView(R.id.showStructure)
    ImageView showStructure;
    @BindView(R.id.structure_hidden_menu_four)
    LinearLayout structureHiddenMenuFour;

    @Override
    protected int getContentResId() {
        return R.layout.activity_bidder_profile;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarWithBackButton(null);
        ButterKnife.bind(this);
        titleTxt.setText("Profile");

        showStructure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (structureHiddenMenuOne.getVisibility() == (View.VISIBLE)) {
                    structureHiddenMenuOne.setVisibility(View.GONE);
                    structureHiddenMenuTwo.setVisibility(View.GONE);
                    structureHiddenMenuThree.setVisibility(View.GONE);
                    structureHiddenMenuFour.setVisibility(View.GONE);

                } else {
                    structureHiddenMenuOne.setVisibility(View.VISIBLE);
                    structureHiddenMenuTwo.setVisibility(View.VISIBLE);
                    structureHiddenMenuThree.setVisibility(View.VISIBLE);
                    structureHiddenMenuFour.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
