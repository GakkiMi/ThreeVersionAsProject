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

public class EvnetGroupB extends LinearLayout {

    public EvnetGroupB(Context context) {
        super(context);
    }

    public EvnetGroupB(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        Log.e("", "-----------dispatchTouchEvent--------groupB");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        Log.e("", "-----------onInterceptTouchEvent--------groupB");
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Log.e("", "-----------onTouchEvent--------groupB");
        return super.onTouchEvent(event);
    }
}
