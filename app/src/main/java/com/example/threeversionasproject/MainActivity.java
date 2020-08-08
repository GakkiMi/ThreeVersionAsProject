package com.example.threeversionasproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.ShapeBadgeItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;
import com.example.threeversionasproject.envntdispatch.RSASecurityServerUtil;
import com.example.threeversionasproject.fragment.CartFragment;
import com.example.threeversionasproject.fragment.HomeFragment;
import com.example.threeversionasproject.fragment.InfoFragment;
import com.example.threeversionasproject.fragment.MineFragment;
import com.example.threeversionasproject.widget.DownloadSuccessView;
import com.example.threeversionasproject.widget.ShapeTextView;
import com.gengchao.chat.activity.myscroltextview.scroll.ScrollActivity;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

public class MainActivity extends AppCompatActivity implements InfoFragment.InfoCallback, View.OnClickListener {

    ArrayList<Fragment> fragmentArrayList;

    BottomNavigationBar bottom_bar;
    FrameLayout frameLayout;

    TextBadgeItem numberBadgeItem;
    ShapeBadgeItem shapeBadgeItem;

    private Fragment mFrag;

    FragmentManager fm;
    FragmentTransaction ft;

    DownloadSuccessView dsView;
    ShapeTextView shapeTv;
    ShapeTextView shapeTvLine;

    Button btIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
        setFragmentList();
        showFragment(0);

        try {
            RSASecurityServerUtil.generateKeyPair();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void setFragmentList() {
        fragmentArrayList = new ArrayList<>();
        fragmentArrayList.add(HomeFragment.newInstance("Home"));
        fragmentArrayList.add(CartFragment.newInstance("Cart"));
        fragmentArrayList.add(InfoFragment.newInstance("Info"));
        fragmentArrayList.add(MineFragment.newInstance("mine"));


       /* String json="[\"DZQG12042019040802\",\"DZXD12112019040801\",\"DZXD12112019040803\"]";
        try {
            JSONArray jsonArray=new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                Log.i("","-------"+jsonArray.get(i).toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
    }

    public void showFragment(int position) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        Fragment fragment = fragmentArrayList.get(position);
        if (mFrag != null) {
            ft.hide(mFrag);
        }
        if (!fragment.isAdded()) {
            ft.add(R.id.main_framlayout, fragment);
        } else {
            ft.show(fragment);
        }
        mFrag = fragment;
        ft.commit();
    }


    private void initEvent() {
        bottom_bar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                showFragment(position);
                switch (position) {
                    case 0:
                        break;
                    case 1:
//                        numberBadgeItem.show();
                        break;
                    case 2:
//                        numberBadgeItem.hide();
                        break;
                    case 3:
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {
                Fragment fragment = fragmentArrayList.get(position);
                if (fragment instanceof InfoFragment) {
                    InfoFragment infoFragment = (InfoFragment) fragment;
                    infoFragment.reload();
                }
            }
        });
    }

    private void initView() {
        bottom_bar = (BottomNavigationBar) findViewById(R.id.bottom_bar);
        shapeTv = (ShapeTextView) findViewById(R.id.main_shape_tv);
        shapeTvLine = (ShapeTextView) findViewById(R.id.main_shape_tv_line);

        frameLayout = (FrameLayout) findViewById(R.id.bottom_bar);
        dsView = (DownloadSuccessView) findViewById(R.id.main_ds_view);

        btIntent = (Button) findViewById(R.id.bt_scroll_tv);

        bottom_bar.setMode(BottomNavigationBar.MODE_FIXED);
        bottom_bar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottom_bar.setBarBackgroundColor(R.color.colorWhite)
                .setActiveColor(R.color.colorPrimaryDark)
                .setInActiveColor(R.color.colorGray); //

        numberBadgeItem = new TextBadgeItem();
        numberBadgeItem.setBackgroundColor(Color.RED).setText("" + 0)
                .setTextColor(Color.WHITE).setHideOnSelect(false);


        shapeBadgeItem = new ShapeBadgeItem();
        shapeBadgeItem.setShape(ShapeBadgeItem.SHAPE_OVAL).setShapeColor(Color.RED)
                .setHideOnSelect(true);

        bottom_bar.addItem(new BottomNavigationItem(R.mipmap.tab_home_passed, getString(R.string.home)))
                .addItem(new BottomNavigationItem(R.mipmap.tab_cart_passed, getString(R.string.cart)))
                .addItem(new BottomNavigationItem(R.mipmap.tab_info_passed, getString(R.string.info))
                        .setBadgeItem(numberBadgeItem))
                .addItem(new BottomNavigationItem(R.mipmap.tab_mine_passed, getString(R.string.mine))
                        .setBadgeItem(shapeBadgeItem))
                .initialise();
        dsView.setOnClickListener(this);
        shapeTv.setOnClickListener(this);
        shapeTvLine.setOnClickListener(this);
        btIntent.setOnClickListener(this);
    }


    @Override
    public void updateBadgeCount(int num) {
        numberBadgeItem.setText("" + num);
        new SlideInLeftAnimator();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_ds_view:
                dsView.reset();
                break;
            case R.id.main_shape_tv:
                break;
            case R.id.main_shape_tv_line:

                break;
            case R.id.bt_scroll_tv:
                Intent intent = new Intent(MainActivity.this, ScrollActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
