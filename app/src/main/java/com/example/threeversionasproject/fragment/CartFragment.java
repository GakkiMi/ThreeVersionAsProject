package com.example.threeversionasproject.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.threeversionasproject.R;

/**
 * Created by Ocean on 2018/4/16.
 */

public class CartFragment extends Fragment {

    TextView tv;
    View view;

    String TAG="CartFragment";

    public static CartFragment newInstance(String param1) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cart, container, false);
        initView();
//        Bundle bundle = getArguments();
//        String text = bundle.getString("agrs1");
//        tv.setText(text);
        return view;

    }

    public void initView() {
        tv = (TextView) view.findViewById(R.id.fm_cart_tv);
    }

    public void update(){
        Log.i("","-----------更新CartFragment");
    }

}
