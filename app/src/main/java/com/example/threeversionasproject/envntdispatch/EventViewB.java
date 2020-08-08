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

public class EventViewB extends View {
    public EventViewB(Context context) {
        super(context);
    }

    public EventViewB(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.e("", "-----------dispatchTouchEvent--------viewB");
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("", "-----------onTouchEvent--------viewB");
        return super.onTouchEvent(event);
    }


}
