package com.example.threeversionasproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.threeversionasproject.adapter.TabViewPagerAdapter;
import com.example.threeversionasproject.fragment.CartFragment;
import com.example.threeversionasproject.fragment.InfoFragment;
import com.example.threeversionasproject.fragment.MineFragment;

import java.util.ArrayList;
import java.util.List;

public class MyDrawerLayoutActivity extends AppCompatActivity{

    TabLayout tabLayout;
    ViewPager viewPager;

    CartFragment cartFragment;
    MineFragment mineFragment;


    int num=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_drawer_layout);
        initView();
        initData();
    }

    public void initView() {
        cartFragment = new CartFragment();
        mineFragment = new MineFragment();
        tabLayout = (TabLayout) findViewById(R.id.my_draw_layout_tab_layout);
        viewPager = (ViewPager) findViewById(R.id.my_draw_layout_viewpager);
    }

    public void initData() {
               String titles[] = {"补丁一般病人", "补丁关注病人","补丁一般病人", "补丁关注病人","补丁一般病人", "补丁关注病人"};
        int images[] = {R.drawable.ic_menu_camera, R.drawable.tab_icon_selector,
                R.drawable.tab_icon_selector, R.drawable.tab_icon_selector,
                R.drawable.tab_icon_selector, R.drawable.tab_icon_selector};

        for (int i = 0; i < titles.length; i++) {
            tabLayout.addTab(tabLayout.newTab().setCustomView(setTabIcon(titles[i],images[i])));
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.i("", "onTabSelected:" + tab.getText());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
//        tabLayout.setupWithViewPager(viewPager);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new CartFragment());
        fragments.add(new MineFragment());
        fragments.add(new CartFragment());
        fragments.add(new MineFragment());
        fragments.add(new CartFragment());
        fragments.add(new MineFragment());
        TabViewPagerAdapter adapter = new TabViewPagerAdapter(this, getSupportFragmentManager(), fragments, titles, images);
        viewPager.setAdapter(adapter);

        //Tablayout自定义view绑定ViewPager 自定义view时使用 tabLayout.setupWithViewPager(viewPager)方法关联无效，通过以下方法进行viewpager和tablayout的关联
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
//        for (int i = 0; i < titles.length; i++) {
//            tabLayout.getTabAt(i).setCustomView(setTabIcon(titles[i],images[i]));
//        }
    }


    public static void startDrawActivity(Context context) {
        context.startActivity(new Intent(context, MyDrawerLayoutActivity.class));
    }

    public View setTabIcon(String text, int icon) {
        View view = LayoutInflater.from(this).inflate(R.layout.tab_item_view, null);
        TextView textView = (TextView) view.findViewById(R.id.tab_item_text);
        ImageView imageView = (ImageView) view.findViewById(R.id.tab_item_icon);
        textView.setText(text);
        imageView.setImageResource(icon);
        return view;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        cartFragment.update();
        mineFragment.update();
    }

   /* @Override
    public void update() {
        num++;
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab=tabLayout.getTabAt(i);
            View customView=tab.getCustomView();
            if(customView!=null){
                TextView tvBadgeView=(TextView) customView.findViewById(R.id.tab_item_badge);
                tvBadgeView.setText(num+"");
            }
        }
    }*/
}
