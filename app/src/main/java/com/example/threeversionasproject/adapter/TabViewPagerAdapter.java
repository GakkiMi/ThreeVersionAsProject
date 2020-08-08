package com.example.threeversionasproject.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Ocean on 2018/7/3.
 */

public class TabViewPagerAdapter extends FragmentPagerAdapter {


    private Context context;
    private List<Fragment> fragmentList;
    private FragmentManager fragmentManager;
    private String titles[];
    private int images[];



    public TabViewPagerAdapter(Context mContext,FragmentManager fm, List<Fragment> fragmentList, String titles[], int images[]) {
        super(fm);
        this.context=mContext;
        this.fragmentList=fragmentList;
        this.fragmentManager=fm;
        this.titles=titles;
        this.images=images;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }






    /*@Override
    public CharSequence getPageTitle(int position) {
//        Drawable image = context.getResources().getDrawable(images[position]);
//        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
//        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
//        SpannableString ss = new SpannableString("      "+titles[position]);
//        ss.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return titles[position];
    }*/
}
