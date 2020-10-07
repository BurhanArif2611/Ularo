package com.pixelmarketo.ularo.userContent.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;

import com.pixelmarketo.ularo.R;
import com.pixelmarketo.ularo.model.AdvertisementModel;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    List<AdvertisementModel> model;

    public ViewPagerAdapter(Context context, List<AdvertisementModel> model ) {
        this.context = context;
        this.model=model;

    }




    @Override
    public int getCount() {
        return model.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }


    @Override
    public Object instantiateItem(View container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.custom_layout,null);
        ImageView imageView=(ImageView) view.findViewById(R.id.imageView);
        AdvertisementModel bannerResult = model.get(position);
        Glide.with(context).load(bannerResult.getAdv_image()).into(imageView);

        ViewPager vp=(ViewPager)container;
        vp.addView(view,0);
        return view;


    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager vp=(ViewPager)container;
        View view=(View)object;
        vp.removeView(view);

    }
}


