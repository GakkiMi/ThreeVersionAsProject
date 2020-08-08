package com.example.threeversionasproject.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.threeversionasproject.R;

/**
 * Created by Ocean on 2019/3/14.
 */

public class MyView extends View {


    private Paint mPaint;
    private int defaultSize;

    public MyView(Context context) {
        this(context, null);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(R.styleable.MyView);
        defaultSize = a.getDimensionPixelOffset(R.styleable.MyView_defaultSize, 100);
        int x = a.getDimensionPixelSize(R.styleable.MyView_defaultSize, 100);
        float y = a.getDimension(R.styleable.MyView_defaultSize, 100);

        Log.i("YViewWidth", "----defaultSize:" + defaultSize + "----x:" + x + "---y:" + y);

        a.recycle();
        init();
    }


    public void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(2);
        mPaint.setAntiAlias(true);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMyWidth(widthMeasureSpec);
        int height = getMyHeight(heightMeasureSpec);
        if (width < height) {
            height = width;
        } else {
            width = height;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        Rect rect = new Rect(0,0,getMeasuredWidth(),getMeasuredHeight());
//        canvas.drawRect(rect,mPaint);
//        float cx=getMeasuredWidth()/2;
//        float cy=getMeasuredHeight()/2;
//        float radius=cx-getPaddingLeft();
//        canvas.drawCircle(cx,cy,radius,mPaint);

        drawClock(canvas);

    }

    public int getMyWidth(int widthMeasureSpec) {
        int width = defaultSize;
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);

        Log.i("YViewWidth", "----Width--------size:" + size);

        switch (mode) {
            case MeasureSpec.AT_MOST:
                Log.i("YViewWidth", "----Width------AT_MOST:");
                if (width > size) {
                    width = size;
                }
                break;
            case MeasureSpec.EXACTLY:
                Log.i("YViewWidth", "----Width------EXACTLY:");
                width = size;
                break;
            case MeasureSpec.UNSPECIFIED:
                Log.i("YViewWidth", "----Width------UNSPECIFIED:");
                if (width > size) {
                    width = size;
                }
                break;
        }
        return width;
    }


    public int getMyHeight(int widthMeasureSpec) {
        int height = defaultSize;
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);

        Log.i("YViewHeight", "----Height-------size:" + size);

        switch (mode) {
            case MeasureSpec.AT_MOST:
                Log.i("YViewHeight", "----Height------AT_MOST:");
                if (height > size) {
                    height = size;
                }
                break;
            case MeasureSpec.EXACTLY:
                Log.i("YViewHeight", "----Height------EXACTLY:");
                height = size;
                break;
            case MeasureSpec.UNSPECIFIED:
                Log.i("YViewHeight", "----Height------UNSPECIFIED:");
                if (height > size) {
                    height = size;
                }
                break;
        }
        return height;
    }


    /*绘制钟表*/
    private void drawClock(Canvas canvas) {
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.translate(canvas.getWidth() / 2, canvas.getHeight() / 2); //将位置移动画纸的坐标点:150,150
        canvas.drawCircle(0, 0, 100, mPaint); //画圆圈
        //使用path绘制路径文字
        canvas.save();
        canvas.translate(-75, -75);
        Path path = new Path();
        path.addArc(new RectF(0, 0, 150, 150), -135, 90);
        canvas.drawRect(new RectF(0, 0, 150, 150), mPaint);
        Paint citePaint = new Paint(mPaint);
        citePaint.setTextSize(14);
        citePaint.setStrokeWidth(1);
        canvas.drawTextOnPath("http://www.", path, 28, 0, citePaint);
        canvas.restore();
        Paint tmpPaint = new Paint(mPaint); //小刻度画笔对象
        tmpPaint.setStrokeWidth(1);
        float y = 100;
        int count = 60; //总刻度数
        for (int i = 0; i < count; i++) {
            if (i % 5 == 0) {
                canvas.drawLine(0f, y, 0, y + 12f, mPaint);
                canvas.drawText(String.valueOf(i / 5 + 1), -4f, y + 25f, tmpPaint);
            } else {
                canvas.drawLine(0f, y, 0f, y + 5f, tmpPaint);
            }
            canvas.rotate(360 / count, 0f, 0f); //旋转画纸
        }
        //绘制指针
        tmpPaint.setColor(Color.GRAY);
        tmpPaint.setStrokeWidth(4);
        canvas.drawCircle(0, 0, 7, tmpPaint);
        tmpPaint.setStyle(Paint.Style.FILL);
        tmpPaint.setColor(Color.YELLOW);
        canvas.drawCircle(0, 0, 5, tmpPaint);
        canvas.drawLine(0, 10, 0, -65, mPaint);
    }
}
