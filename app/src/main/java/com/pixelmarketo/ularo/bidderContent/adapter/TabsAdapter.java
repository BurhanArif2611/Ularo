package com.pixelmarketo.ularo.bidderContent.adapter;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class TabsAdapter extends FragmentPagerAdapter {
    public static int pos = 0;

    private List<Fragment> myFragments;
    private Context context;
    List<String> tabsName;

    public TabsAdapter(Context c, FragmentManager fragmentManager, List<Fragment> myFrags, List<String> tabs) {
        super(fragmentManager);
        myFragments = myFrags;
        this.context = c;
        this.tabsName = tabs;
    }

    @Override
    public Fragment getItem(int position) {
        return myFragments.get(position);

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

        TabsAdapter.pos = pos;
    }
}
