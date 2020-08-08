package com.example.threeversionasproject.envntdispatch;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Ocean on 2020/6/5.
 */

public class EventViewA extends View {
    public EventViewA(Context context) {
        super(context);
    }

    public EventViewA(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.e("", "-----------dispatchTouchEvent--------viewA");
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("", "-----------onTouchEvent--------viewA");
        return super.onTouchEvent(event);
    }



}
