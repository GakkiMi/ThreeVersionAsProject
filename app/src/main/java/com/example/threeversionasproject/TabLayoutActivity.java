package com.example.threeversionasproject;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.threeversionasproject.adapter.TabViewPagerAdapter;
import com.example.threeversionasproject.fragment.CartFragment;
import com.example.threeversionasproject.fragment.HomeFragment;
import com.example.threeversionasproject.fragment.MineFragment;

import java.util.ArrayList;
import java.util.List;

public class TabLayoutActivity extends AppCompatActivity implements MineFragment.UpdateBadgeImpl{

    TabLayout tabLayout;
    ViewPager viewPager;

    FragmentManager fm;


    Fragment currentShowFragment;
    List<Fragment> fragmentList;

    int num=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);
        initView();
//        initFragment();
//        showFragment(0);
    }

    public void initView() {

        fm=getSupportFragmentManager();

        String titles[] = {"Tab1", "TabTab2","Tab3", "TabTabTab4","Tab5", "Tab6"};
        int images[] = {R.drawable.ic_menu_camera, R.drawable.tab_icon_selector,
                R.drawable.tab_icon_selector, R.drawable.tab_icon_selector,
                R.drawable.tab_icon_selector, R.drawable.tab_icon_selector};

        tabLayout = (TabLayout) findViewById(R.id.activity_tab_layout_tablayout);
        viewPager=(ViewPager) findViewById(R.id.activity_tab_layout_view_pager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View view=tab.getCustomView();
                if(view!=null){
                    view.findViewById(R.id.tab_item_badge).setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                if (view != null) {
                    view.setScaleX(1f);
                    view.setScaleY(1f);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new CartFragment());
        fragments.add(new MineFragment());
        fragments.add(new CartFragment());
        fragments.add(new MineFragment());
        fragments.add(new CartFragment());
        fragments.add(new MineFragment());
        TabViewPagerAdapter adapter=new TabViewPagerAdapter(this,getSupportFragmentManager(),fragments,titles,images);
        viewPager.setAdapter(adapter);
//        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));


        for (int i = 0; i < 6; i++) {
//            tabLayout.addTab(tabLayout.newTab().setText("Tab"+i).setIcon(R.drawable.tab_icon_selector));
            //添加自定义view
//            tabLayout.addTab(tabLayout.newTab().setCustomView(setTabIcon(titles[i], R.drawable.tab_icon_selector)));
        }


        for (int i = 0; i < titles.length; i++) {
//            mTitleIcons[i]和mTitleNames[i]是放图片和文字的资源的数组
//            tabLayout.getTabAt(i).setIcon(images[i]);
//           如果设置了text，则适配器中的getePageTitle()也可以不写
//            tabLayout.getTabAt(i).setText(titles[i]);
            tabLayout.getTabAt(i).setCustomView(setTabIcon(titles[i], R.drawable.tab_icon_selector));
        }
    }
    
    public void updateBadgeView(){
        num++;
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab=tabLayout.getTabAt(i);
            View customView=tab.getCustomView();
            if(customView!=null){
                TextView tvBadgeView=(TextView) customView.findViewById(R.id.tab_item_badge);
                tvBadgeView.setText(num+"");
            }
        }
    }



    public View setTabIcon(String text, int icon) {
        View view = LayoutInflater.from(this).inflate(R.layout.tab_item_view, null);
        TextView textView = (TextView) view.findViewById(R.id.tab_item_text);
        ImageView imageView = (ImageView) view.findViewById(R.id.tab_item_icon);
        TextView tvBadge=(TextView) view.findViewById(R.id.tab_item_badge);
        tvBadge.setText("99");
        textView.setText(text);
        imageView.setImageResource(icon);
        return view;
    }

    public void initFragment(){
        fragmentList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            fragmentList.add(HomeFragment.newInstance("这个第"+i+"个Fragment"));
        }
    }

    public void showFragment(int position){
        Fragment fragment=fragmentList.get(position);
        FragmentTransaction ft=fm.beginTransaction();
        if(currentShowFragment!=null){
            ft.hide(currentShowFragment);
        }
        if(!fragment.isAdded()){
            ft.add(R.id.activity_tab_layout_framelayout,fragment);
        }else{
            ft.show(fragment);
        }
        currentShowFragment=fragment;
        ft.commit();
    }


    @Override
    public void update() {
        updateBadgeView();
    }
}
