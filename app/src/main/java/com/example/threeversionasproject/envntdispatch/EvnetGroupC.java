package com.example.threeversionasproject.envntdispatch;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by Ocean on 2020/6/5.
 */

public class EvnetGroupC extends LinearLayout {

    public EvnetGroupC(Context context) {
        super(context);
    }

    public EvnetGroupC(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        Log.e("", "-----------dispatchTouchEvent--------groupC");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        Log.e("", "-----------onInterceptTouchEvent--------groupC");
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Log.e("", "-----------onTouchEvent--------groupC");
        return super.onTouchEvent(event);
    }
}