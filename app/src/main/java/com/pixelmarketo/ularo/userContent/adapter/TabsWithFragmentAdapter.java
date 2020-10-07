package com.pixelmarketo.ularo.userContent.adapter;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.google.android.material.tabs.TabLayout;
import com.pixelmarketo.ularo.userContent.fragment.ElectricianFragment;
import com.pixelmarketo.ularo.userContent.fragment.OtherFragment;

import java.util.List;

public class TabsWithFragmentAdapter extends FragmentPagerAdapter {
    public static int pos = 0;

    private List<Fragment> myFragments;
    private Context context;
    List<String> tabsName;
    public TabsWithFragmentAdapter(Context c, FragmentManager fragmentManager, List<Fragment> myFrags, List<String> tabs) {
        super(fragmentManager);
        myFragments = myFrags;
        this.context = c;
        this.tabsName = tabs;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ElectricianFragment Fragment = new ElectricianFragment();
                return Fragment;
            case 1:
                OtherFragment otherFragment = new OtherFragment();
                return otherFragment;

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
      return myFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        setPos(position);
        return tabsName.get(position);
    }

    public static int getPos() {
        return pos;
    }

    public void add(Class<Fragment> c, String title, Bundle b) {
        myFragments.add(Fragment.instantiate(context,c.getName(),b));
        tabsName.add(title);
    }

    public static void setPos(int pos) {

        TabsWithFragmentAdapter.pos = pos;
    }
}
