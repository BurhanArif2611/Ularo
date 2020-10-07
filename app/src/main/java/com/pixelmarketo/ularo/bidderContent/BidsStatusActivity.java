package com.pixelmarketo.ularo.bidderContent;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.pixelmarketo.ularo.R;
import com.pixelmarketo.ularo.activities.BaseActivity;
import com.pixelmarketo.ularo.bidderContent.adapter.TabsAdapter;
import com.pixelmarketo.ularo.bidderContent.fragment.BidStatusFragment;
import com.pixelmarketo.ularo.utility.ErrorMessage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BidsStatusActivity extends BaseActivity {

    @BindView(R.id.title_txt)
    TextView titleTxt;
    @BindView(R.id.bid_categry_search_btn)
    EditText bidCategrySearchBtn;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.tabDetails)
    ViewPager tabDetails;
    int position1;
    String tabname,from;


    @Override
    protected int getContentResId() {
        return R.layout.activity_bids_status;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setToolbarWithBackButton("");
        Bundle bundle1=getIntent().getExtras();
        from= bundle1.getString("from");
        ErrorMessage.E("bunddle"+bundle1);
        if (from.equalsIgnoreCase("user")){
            titleTxt.setText("bid history");

        }
        else {
            titleTxt.setText("bid status");
        }
        List<String> tabs = new ArrayList<>();


        tabs.add(0, "Pending Bid List");
        tabs.add(1, "Approve Bid List");

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabDetails.setCurrentItem(tab.getPosition());
                position1 = tab.getPosition();
                tabname = String.valueOf(tab.getText());
                String filter = "thisIsForMyFragment";
                Intent intent = new Intent(filter);
                intent.putExtra("extra", tab.getPosition());//If you need extra, add: intent.putExtra("extra","something");
                LocalBroadcastManager.getInstance(BidsStatusActivity.this).sendBroadcast(intent);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        List<Fragment> fragments = new ArrayList<Fragment>();

        for (int i = 0; i < tabs.size(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText("Tab name"));
            Bundle bundle = new Bundle();
            bundle.putInt("position", position1);
            bundle.putString("tab", tabname);
            bundle.putString("from", from);
            fragments.add(Fragment.instantiate(BidsStatusActivity.this, BidStatusFragment.class.getName(), bundle));

        }
        TabsAdapter adapter = new TabsAdapter(BidsStatusActivity.this, getSupportFragmentManager(), fragments, tabs);
        tabDetails.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        if (tabDetails != null) {
            tabLayout.setupWithViewPager(tabDetails);
        }


        tabDetails.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.getTabAt(position).select();

            }
        });
    }
}
