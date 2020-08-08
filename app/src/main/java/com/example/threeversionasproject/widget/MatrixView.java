package com.example.threeversionasproject.widget;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Ocean on 2019/3/21.
 */

public class MatrixView extends View {

    private Paint mPaint;
    private Paint mNeedlePaint;
    private int mCenterX;
    private int mCenterY;

    private float mCanvasMaxRotateDegree = 20;
    private float mCanvasRotateX = 0;
    private float mCanvasRotateY = 0;
    private Camera mCamera;
    private Matrix mMatrix;

    private float mTouchY;
    private float mTouchX;
    private double alpha;

    public MatrixView(Context context) {
        super(context);
    }

    public MatrixView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setColor(Color.GRAY);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(1);

        mNeedlePaint = new Paint();
        mNeedlePaint.setColor(Color.parseColor("#FF4081"));
        mNeedlePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mNeedlePaint.setStrokeWidth(1);

        mMatrix = new Matrix();
        mCamera = new Camera();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = measureSize(widthMeasureSpec);
        int height = measureSize(heightMeasureSpec);
        if (width < height) {
            height = width;
        } else {
            width = height;
        }
        setMeasuredDimension(width, height);
    }

    public int measureSize(int measureSpec) {
        int defaultSize = 800;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch (mode) {
            case MeasureSpec.AT_MOST:
                Log.i("", "----Width------AT_MOST:");
                return Math.min(size, defaultSize);
            case MeasureSpec.EXACTLY:
                Log.i("", "----Width------EXACTLY:");
                return size;
            case MeasureSpec.UNSPECIFIED:
                Log.i("", "----Width------UNSPECIFIED:");
                return defaultSize;
            default:
                return defaultSize;
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mCenterX = getWidth() / 2;
        mCenterY = getHeight() / 2;


        Log.e("wing", alpha + "");
        canvas.rotate((float) alpha, mCenterX, mCenterY);

        alpha = Math.atan((mTouchX - mCenterX) / (mCenterY - mTouchY));
        alpha = Math.toDegrees(alpha);
        if (mTouchY > mCenterY) {
            alpha = alpha + 180;
        }


        mMatrix.reset();
        mCamera.save();
        mCamera.rotateX(mCanvasRotateX);
        mCamera.rotateY(mCanvasRotateY);
        mCamera.getMatrix(mMatrix);
        mCamera.restore();
        mMatrix.preTranslate(-mCenterX, -mCenterY);
        mMatrix.postTranslate(mCenterX, mCenterY);
        //将矩阵作用于整个canvas
        canvas.concat(mMatrix);
        int radius = Math.min(getWidth() - getPaddingLeft() - getPaddingRight(), getHeight() - getPaddingTop() - getPaddingBottom()) / 2;
        canvas.drawCircle(mCenterX, mCenterY, radius, mPaint);
        Path path = new Path();
        path.moveTo(mCenterX, getPaddingTop());
        path.lineTo(mCenterX - radius * 0.2f, mCenterY);
        path.lineTo(mCenterX, getHeight() - getPaddingBottom() - radius * 0.2f);
        path.lineTo(mCenterX + radius * 0.2f, mCenterY);
        path.close();
        canvas.drawPath(path, mNeedlePaint);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();


        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                rotateCanvasWhenMove(x, y);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                //这里将camera旋转的变量赋值
                mTouchX = x;
                mTouchY = y;
                rotateCanvasWhenMove(x, y);
                invalidate();
                break;
            }
            case MotionEvent.ACTION_UP: {
                mTouchX = 0;
                mTouchY = 0;
                //这里将camera旋转的变量赋值
                mCanvasRotateY = 0;
                mCanvasRotateX = 0;
                invalidate();

                break;
            }
        }
        return true;
    }


    private void rotateCanvasWhenMove(float x, float y) {
        float dx = x - mCenterX;
        float dy = y - mCenterY;

        float percentX = dx / mCenterX;
        float percentY = dy / mCenterY;

        if (percentX > 1f) {
            percentX = 1f;
        } else if (percentX < -1f) {
            percentX = -1f;
        }
        if (percentY > 1f) {
            percentY = 1f;
        } else if (percentY < -1f) {
            percentY = -1f;
        }

        mCanvasRotateY = mCanvasMaxRotateDegree * percentX;
        mCanvasRotateX = -(mCanvasMaxRotateDegree * percentY);
    }

}
