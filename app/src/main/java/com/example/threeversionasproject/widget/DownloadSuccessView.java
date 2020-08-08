package com.example.threeversionasproject.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Ocean on 2019/3/22.
 */

public class DownloadSuccessView extends View {

    //普通画笔
    private Paint mPaint;
    //圆圈画笔
    private Paint mCirclePaint;
    //小圆点画笔
    private Paint mPointPaint;

    //圆心坐标 x,y
    private int centerX, centerY;

    //线的宽度
    private float lineWidth;
    //线长度的二分之一
    private float halfLineLength;
    //圆圈的半径
    private float mCircleRadius;
    //小圆点的半径
    private float mPointRadius;


    //绘制箭头的进度
    float arrowPercent = 0;
    //绘制竖直线的进度
    float linePercent = 0;
    //绘制小圆点的进度
    float pointPercent = 0;
    //绘制圆圈的进度
    float circlePercent = 0;
    //绘制对勾的进度
    float successPercent = 0;


    //用来标识是否正在绘制
    boolean isDraw = true;
    //追踪Path的坐标
    private PathMeasure mPathMeasure;
    private Path mDst;

    public DownloadSuccessView(Context context) {
        super(context);
    }

    public DownloadSuccessView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setDither(true);//防抖动
        mPaint.setStrokeCap(Paint.Cap.ROUND);//末端为圆角
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.STROKE);

        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setColor(Color.parseColor("#80FFFFFF"));
        mCirclePaint.setStyle(Paint.Style.STROKE);

        mPointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPointPaint.setColor(Color.WHITE);
        mPointPaint.setStyle(Paint.Style.FILL);

        mPathMeasure = new PathMeasure();
        mDst = new Path();
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

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;

        mCircleRadius = Math.min(getWidth() - getPaddingLeft() - getPaddingRight(), getHeight() - getPaddingTop() - getPaddingBottom()) / 2 - 6;
        lineWidth = mCircleRadius * 0.1f;
        mPointRadius = lineWidth * 0.75f;
        halfLineLength = mCircleRadius * 0.5f;


        mPaint.setStrokeWidth(lineWidth);
        mCirclePaint.setStrokeWidth(lineWidth);
    }

    public int measureSize(int measureSpec) {
        int defaultSize = 400;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch (mode) {
            case MeasureSpec.AT_MOST:
                Log.i("", "----------AT_MOST:");
                return Math.min(size, defaultSize);
            case MeasureSpec.EXACTLY:
                Log.i("", "----------EXACTLY:");
                return size;
            case MeasureSpec.UNSPECIFIED:
                Log.i("", "----------UNSPECIFIED:");
                return defaultSize;
            default:
                return defaultSize;
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isDraw) {
            canvas.drawCircle(centerX, centerY, mCircleRadius, mCirclePaint);
            if (linePercent <= 100) {
                drawLine(canvas);
            } else {
                if (arrowPercent <= 100) {
                    drawArrow(canvas);
                } else {
                    if (pointPercent <= 100) {
                        drawPoint(canvas);
                    } else {
                        if (circlePercent <= 100) {
                            drawCircle(canvas);
                        } else {
                            if (successPercent <= 100) {
                                drawSuccess(canvas);
                            } else {
                                isDraw = false;
                                canvas.drawCircle(centerX, centerY, mCircleRadius, mPaint);

                                Path successPath = drawSuccessPath();
                                canvas.drawPath(successPath, mPaint);
                            }
                        }
                    }
                }
            }
            if (isDraw) {
                //通过延迟刷新达到一种动画的效果
                postInvalidateDelayed(10);
            }
        }
    }

    //再次绘制
    public void reset() {
        if (isDraw == false) {
            arrowPercent = 0;
            linePercent = 0;
            pointPercent = 0;
            circlePercent = 0;
            successPercent = 0;
            isDraw = true;
            invalidate();
        }
    }


    public void drawLine(Canvas canvas) {
        if (linePercent <= 100) {
            canvas.drawLine(centerX, centerY - halfLineLength * (1f - linePercent / 100f), centerX, centerY + halfLineLength * (1f - linePercent / 100f), mPaint);
            Path path = new Path();
            path.moveTo(centerX - halfLineLength, centerY);
            path.lineTo(centerX, centerY + halfLineLength);
            path.lineTo(centerX + halfLineLength, centerY);
            canvas.drawPath(path, mPaint);
            linePercent += 5;
        }
    }

    public void drawArrow(Canvas canvas) {
        if (arrowPercent <= 100) {
            Path path = new Path();
            path.moveTo(centerX - halfLineLength, centerY);
            path.lineTo(centerX, centerY + halfLineLength * (1f - arrowPercent / 100f));
            path.lineTo(centerX + halfLineLength, centerY);
            canvas.drawPath(path, mPaint);
            canvas.drawCircle(centerX, centerY, mPointRadius, mPointPaint);
            arrowPercent += 5;
        }
    }


    public void drawPoint(Canvas canvas) {
        canvas.drawCircle(centerX, centerY - mCircleRadius * 0.8f * (pointPercent / 100f), mPointRadius, mPointPaint);
        canvas.drawLine(centerX - halfLineLength, centerY, centerX + halfLineLength, centerY, mPaint);
        pointPercent += 7;
    }


    public void drawCircle(Canvas canvas) {
        mDst.reset();
        // 硬件加速的BUG
//        mDst.lineTo(0, 0);

        Path path = new Path();
        path.addCircle(centerX, centerY, mCircleRadius, Path.Direction.CW);
        mPathMeasure.setPath(path, false);
        mPathMeasure.getSegment(0, mPathMeasure.getLength() * circlePercent / 100f, mDst, true);
        mDst.rLineTo(0,0);
        canvas.drawPath(mDst, mPaint);

        circlePercent += 5;
    }


    public void drawSuccess(Canvas canvas) {
        canvas.drawCircle(centerX, centerY, mCircleRadius, mPaint);
        mDst.reset();
        // 硬件加速的BUG
//        mDst.lineTo(0, 0);
        Path successPath = drawSuccessPath();
        mPathMeasure.nextContour();
        mPathMeasure.setPath(successPath, false);
        mPathMeasure.getSegment(0, successPercent / 100f * mPathMeasure.getLength(), mDst, true);
        mDst.rLineTo(0,0);
        canvas.drawPath(mDst, mPaint);
        successPercent += 5;
    }

    //绘制对勾的路径
    public Path drawSuccessPath() {
        Path successPath = new Path();
        successPath.moveTo(centerX - mCircleRadius * 0.4f, centerY);
        successPath.lineTo(centerX - mCircleRadius * 0.1f, centerY + mCircleRadius * 0.3f);
        successPath.lineTo(centerX + mCircleRadius * 0.4f, centerY - mCircleRadius * 0.3f);
        return successPath;
    }
}


