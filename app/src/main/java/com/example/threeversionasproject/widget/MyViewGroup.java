package com.example.threeversionasproject.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Ocean on 2019/3/14.
 */

public class MyViewGroup extends ViewGroup {


    public MyViewGroup(Context context) {
        super(context);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);


        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            int width = getMaxWidth();
            int height = getTotalHeight();
            setMeasuredDimension(width, height);
        } else if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.EXACTLY) {
            setMeasuredDimension(getMaxWidth(), heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST && widthMode == MeasureSpec.EXACTLY) {
            setMeasuredDimension(widthSize, getTotalHeight());
        } else if (heightMode == MeasureSpec.EXACTLY && widthMode == MeasureSpec.EXACTLY) {
            setMeasuredDimension(widthSize, heightSize);
        }

    }


    public int getMaxWidth() {
        int count = getChildCount();
        int maxWidth = 0;
        for (int i = 0; i < count; i++) {
            View childView = getChildAt(i);
            int width = childView.getMeasuredWidth();
            if (width > maxWidth) {
                maxWidth = width;
            }
        }
        return maxWidth;
    }


    public int getTotalHeight() {
        int count = getChildCount();
        int totalHeight = 0;
        for (int i = 0; i < count; i++) {
            View childView = getChildAt(i);
            int height = childView.getMeasuredHeight();
            totalHeight += height;
        }
        return totalHeight;
    }


    @Override
    protected void onLayout(boolean flag, int l, int t, int r, int b) {
        int count = getChildCount();
        int currentHeight = 0;
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            int width = view.getMeasuredWidth();
            int height = view.getMeasuredHeight();
            view.layout(l, currentHeight, l + width, currentHeight + height);
            currentHeight += height;
        }
    }
}
