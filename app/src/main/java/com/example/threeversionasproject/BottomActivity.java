package com.example.threeversionasproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class BottomActivity extends AppCompatActivity implements View.OnClickListener {


    RadioGroup radioGroup;
    RadioButton rbOne, rbTwo, rbThree, rbFour;

    LinearLayout llNormal, llSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom);
        initView();

        rbOne.setOnClickListener(this);
        rbTwo.setOnClickListener(this);
        rbThree.setOnClickListener(this);
        rbFour.setOnClickListener(this);
        llNormal.setVisibility(View.VISIBLE);
    }

    public void initView() {
        radioGroup = (RadioGroup) findViewById(R.id.bottom_rg);
        rbOne = (RadioButton) findViewById(R.id.radio_button_one);
        rbTwo = (RadioButton) findViewById(R.id.radio_button_two);
        rbThree = (RadioButton) findViewById(R.id.radio_button_three);
        rbFour = (RadioButton) findViewById(R.id.radio_button_four);
        llNormal = (LinearLayout) findViewById(R.id.bottom_ll_normal);
        llSelect = (LinearLayout) findViewById(R.id.bottom_ll_select);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.radio_button_one:
                select();
                break;
            case R.id.radio_button_two:
                normal();
                break;
            case R.id.radio_button_three:
                normal();
                break;
            case R.id.radio_button_four:
                normal();
                break;
        }
    }

    public void normal() {
        llNormal.setVisibility(View.VISIBLE);
        llSelect.setVisibility(View.GONE);
        radioGroup.setBackgroundColor(getResources().getColor(R.color.colorWhite));
    }

    public void select() {
        llNormal.setVisibility(View.GONE);
        llSelect.setVisibility(View.VISIBLE);
        radioGroup.setBackgroundResource(R.drawable.main_chick_bg_bottom);
    }

}
