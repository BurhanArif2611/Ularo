package com.pixelmarketo.ularo.bidderContent;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pixelmarketo.ularo.R;
import com.pixelmarketo.ularo.bidderContent.adapter.BidderHomeAdapter;
import com.pixelmarketo.ularo.database.UserProfileHelper;
import com.pixelmarketo.ularo.utility.ErrorMessage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class BidderHomeActivity extends AppCompatActivity {

    @BindView(R.id.bid_home_rec_v)
    RecyclerView bidHomeRecV;
    @BindView(R.id.imageView)
    CircleImageView imageView;
    @BindView(R.id.user_name_tv)
    TextView userNameTv;
    @BindView(R.id.view_profile_tv)
    TextView viewProfileTv;
    @BindView(R.id.header_layout)
    LinearLayout headerLayout;
    @BindView(R.id.faq_tv)
    TextView faqTv;
    @BindView(R.id.history_tv)
    TextView historyTv;
    @BindView(R.id.terms_condition_Nav)
    TextView termsConditionNav;
    @BindView(R.id.logoutItemNav)
    TextView logoutItemNav;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.title_txt)
    TextView titleTxt;
    @BindView(R.id.tool_barLayout)
    RelativeLayout toolBarLayout;
    @BindView(R.id.toogle)
    ImageView toogle;
    @BindView(R.id.user_mobile_tv)
    TextView userMobileTv;
    List<String> list;
    ArrayList<Integer> images;
    @BindView(R.id.bid_state_tv)
    TextView bidStateTv;
    @BindView(R.id.help_tv)
    TextView helpTv;
    @BindView(R.id.profile_tv)
    TextView profileTv;

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bidder_home);
        ButterKnife.bind(this);

        toogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        list = new ArrayList<>();
        images = new ArrayList<>();

        list.add(0, "Architecture");
        images.add(0, R.drawable.ic_archtecture);
        list.add(1, "Contractor");
        images.add(1, R.drawable.ic_archtecture);
        list.add(2, "Electrician");
        images.add(2, R.drawable.ic_electrition);
        list.add(3, "Painter");
        images.add(3, R.drawable.ic_painter);
        list.add(4, "Railing Fighter");
        images.add(4, R.drawable.ic_railing_fitter);
        list.add(5, "Glass worker");
        images.add(5, R.drawable.ic_glass_work);
        list.add(6, "Carpenter");
        images.add(6, R.drawable.ic_carpenter);
        list.add(7, "Plumber");
        images.add(7, R.drawable.ic_plumber);
        list.add(8, "Section Fitter");
        images.add(8, R.drawable.ic_section_feeter);
        list.add(9, "POP/PVC");
        images.add(9, R.drawable.ic_poppvc_work);
        list.add(10, "Repair And Maintenance");
        images.add(10, R.drawable.ic_repair_and_maintanance);

        BidderHomeAdapter bidderHomeAdapter = new BidderHomeAdapter(BidderHomeActivity.this,list, images);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(BidderHomeActivity.this, 2);
        bidHomeRecV.setLayoutManager(gridLayoutManager);
        bidHomeRecV.setAdapter(bidderHomeAdapter);

        viewProfileTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ErrorMessage.I(BidderHomeActivity.this, BidderProfileActivity.class, null);
            }
        });


        profileTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ErrorMessage.I(BidderHomeActivity.this,BidderProfileActivity.class,null);
            }
        });


        helpTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ErrorMessage.I(BidderHomeActivity.this,BidderHelpActivity.class,null);
            }
        });

        termsConditionNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ErrorMessage.I(BidderHomeActivity.this,TermsAndConditionActivity.class,null);
            }
        });

        historyTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ErrorMessage.I(BidderHomeActivity.this,BidderOrderHistoryActivity.class,null);
            }
        });

        logoutItemNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout_PopUP();
            }
        });

        bidStateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //todo set onclick here
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bidder_home, menu);
        return true;
    }

    public void Logout_PopUP() {
        final Dialog dialog = new Dialog(BidderHomeActivity.this);
        dialog.setContentView(R.layout.logout_popup);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        final Button submit_btn = (Button) dialog.findViewById(R.id.submit_btn);
        final Button cancel_btn = (Button) dialog.findViewById(R.id.cancel_btn);


        submit_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
                UserProfileHelper.getInstance().delete();
                ErrorMessage.I_clear(BidderHomeActivity.this, BidderLoginActivity.class, null);
            }
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();

            }
        });


        dialog.show();
    }


}
