package com.pixelmarketo.ularo.userContent;

import android.os.Bundle;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.pixelmarketo.ularo.R;
import com.pixelmarketo.ularo.activities.BaseActivity;
import com.pixelmarketo.ularo.userContent.adapter.TabsWithFragmentAdapter;
import com.pixelmarketo.ularo.userContent.fragment.ElectricianFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TablayoutActivity extends BaseActivity {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.tabDetails)
    ViewPager tabDetails;
    @BindView(R.id.title_txt)
    TextView titleTxt;
    String from;
    private int[] imageResId = {
            R.drawable.ic_full_point1,
            R.drawable.ic_other1};

    @Override
    protected int getContentResId() {
        return R.layout.activity_tablayout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setToolbarWithBackButton("");
        Bundle bundle=getIntent().getExtras();
        from=bundle.getString("data");
        titleTxt.setText(""+bundle.getString("data"));
        List<String> tabs = new ArrayList<>();

        tabs.add(0, "Full Point");
        tabs.add(1, "Other");

        Bundle bundle1=new Bundle();
        bundle1.putString("from",from);

        List<Fragment> fragments = new ArrayList<Fragment>();

        for (int i = 0; i < tabs.size(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText("Tab name"));
            fragments.add(Fragment.instantiate(TablayoutActivity.this, ElectricianFragment.class.getName(),bundle1));

        }
        for (int i = 0; i < imageResId.length; i++) {
            tabLayout.getTabAt(i).setIcon(imageResId[i]);
        }

        TabsWithFragmentAdapter adapter = new TabsWithFragmentAdapter(TablayoutActivity.this, getSupportFragmentManager(), fragments, tabs);
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
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabDetails.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

}
