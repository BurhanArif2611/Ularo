package com.pixelmarketo.ularo.bidderContent;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.pixelmarketo.ularo.utility.ErrorMessage;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BidderRegistrationActivity extends BaseActivity {

    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.title_txt)
    TextView titleTxt;
    @BindView(R.id.tool_barLayout)
    RelativeLayout toolBarLayout;
    @BindView(R.id.app_icon_reg)
    ImageView appIconReg;
    @BindView(R.id.app_ic_demo)
    RelativeLayout appIcDemo;
    @BindView(R.id.bid_full_name)
    TextInputEditText bidFullName;
    @BindView(R.id.bid_mobile_num_reg)
    TextInputEditText bidMobileNumReg;
    @BindView(R.id.bid_address_reg)
    TextInputEditText bidAddressReg;
    @BindView(R.id.stucture_label_reg)
    TextView stuctureLabelReg;
    @BindView(R.id.structure_icn_reg)
    ImageView structureIcnReg;
    @BindView(R.id.bid_acitec_selec_reg)
    RadioButton bidAcitecSelecReg;
    @BindView(R.id.bid_contractor_selec_reg)
    RadioButton bidContractorSelecReg;
    @BindView(R.id.bid_glass_work_selec_reg)
    RadioButton bidGlassWorkSelecReg;
    @BindView(R.id.bid_electrician_selec_reg)
    RadioButton bidElectricianSelecReg;
    @BindView(R.id.bid_carpenter_selec_reg)
    RadioButton bidCarpenterSelecReg;
    @BindView(R.id.bid_railing_selec_reg)
    RadioButton bidRailingSelecReg;
    @BindView(R.id.structure_hidden_menu_one_reg)
    LinearLayout structureHiddenMenuOneReg;
    @BindView(R.id.bid_painter_selec_reg)
    RadioButton bidPainterSelecReg;
    @BindView(R.id.bid_plumber_selec_reg)
    RadioButton bidPlumberSelecReg;
    @BindView(R.id.bid_sectio_eeter_selec_reg)
    RadioButton bidSectioEeterSelecReg;
    @BindView(R.id.structure_hidden_menu_two_reg)
    LinearLayout structureHiddenMenuTwoReg;
    @BindView(R.id.bid_labour_supplier_selec_reg)
    RadioButton bidLabourSupplierSelecReg;
    @BindView(R.id.structure_hidden_menu_three_reg)
    LinearLayout structureHiddenMenuThreeReg;
    @BindView(R.id.structure_type_reg)
    LinearLayout structureTypeReg;
    @BindView(R.id.repair_label_reg)
    TextView repairLabelReg;
    @BindView(R.id.repair_icn_reg)
    ImageView repairIcnReg;
    @BindView(R.id.repair_spinner_reg)
    Spinner repairSpinnerReg;
    @BindView(R.id.bid_password_reg)
    TextInputEditText bidPasswordReg;
    @BindView(R.id.bid_conferm_password_reg)
    TextInputEditText bidConfermPasswordReg;
    @BindView(R.id.police_label_reg)
    TextView policeLabelReg;
    @BindView(R.id.police_icn_reg)
    ImageView policeIcnReg;
    @BindView(R.id.police_verification_select_file_reg)
    TextView policeVerificationSelectFileReg;
    @BindView(R.id.adhar_label_reg)
    TextView adharLabelReg;
    @BindView(R.id.adhar_icn_reg)
    ImageView adharIcnReg;
    @BindView(R.id.adhar_card_select_file_reg)
    TextView adharCardSelectFileReg;
    @BindView(R.id.submit_reg)
    Button submitReg;
    String[] yes = new String[]{"Yes", "No"};
    @BindView(R.id.showStructure)
    ImageView showStructure;
    @BindView(R.id.terms_condition_check_reg)
    CheckBox termsConditionCheckReg;
    @BindView(R.id.structure_hidden_menu_four_reg)
    LinearLayout structureHiddenMenuFourReg;


    @Override
    protected int getContentResId() {
        return R.layout.activity_bidder_registration;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setToolbarWithBackButton("Registration Page");


        submitReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ErrorMessage.I(BidderRegistrationActivity.this, BidderHomeActivity.class, null);
            }
        });

        showStructure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (structureHiddenMenuOneReg.getVisibility() == (View.VISIBLE)) {
                    structureHiddenMenuOneReg.setVisibility(View.GONE);
                    structureHiddenMenuTwoReg.setVisibility(View.GONE);
                    structureHiddenMenuThreeReg.setVisibility(View.GONE);
                    structureHiddenMenuFourReg.setVisibility(View.GONE);

                } else {
                    structureHiddenMenuOneReg.setVisibility(View.VISIBLE);
                    structureHiddenMenuTwoReg.setVisibility(View.VISIBLE);
                    structureHiddenMenuThreeReg.setVisibility(View.VISIBLE);
                    structureHiddenMenuFourReg.setVisibility(View.VISIBLE);
                }
            }
        });


        set_dropdown(repairSpinnerReg, yes);
    }

    public void set_dropdown(Spinner spinner, String[] items) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
//set the spinners adapter to the previously created one.
        spinner.setAdapter(adapter);
    }
}
