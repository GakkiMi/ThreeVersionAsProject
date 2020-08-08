package com.example.threeversionasproject.envntdispatch;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import java.util.logging.Logger;

/**
 * Created by Ocean on 2020/6/5.
 */

public class EvnetGroupA extends LinearLayout {

    public EvnetGroupA(Context context) {
        super(context);
    }

    public EvnetGroupA(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        Log.e("", "-----------dispatchTouchEvent--------groupA");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        Log.e("", "-----------onInterceptTouchEvent--------groupA");
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Log.e("", "-----------onTouchEvent--------groupA");
        return super.onTouchEvent(event);
    }
}
