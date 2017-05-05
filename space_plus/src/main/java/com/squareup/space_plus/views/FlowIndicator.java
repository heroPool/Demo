package com.squareup.space_plus.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.squareup.space_plus.R;


/**
 * 图片轮播的指示器View
 * Created by yao on 2016/5/6.
 */
public class FlowIndicator extends View {
    int mCount;//实心圆的个数
    int mSpace;//实心圆的间距
    int mRadius;//实心圆的半径
    int mNormalColor;//非焦点实心圆的颜色
    int mFocusColor;//焦点实心圆的颜色
    int mFocus;//焦点实心圆的下标

    Paint mPaint;

    public FlowIndicator(Context context) {
        super(context);
    }

    public FlowIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FlowIndicator);
        mCount = array.getInt(R.styleable.FlowIndicator_count, 0);
        mSpace = array.getDimensionPixelOffset(R.styleable.FlowIndicator_space, 10);
        mRadius = array.getDimensionPixelOffset(R.styleable.FlowIndicator_radius, 10);
        mNormalColor = array.getColor(R.styleable.FlowIndicator_normalColor, 0xccc);
        mFocusColor = array.getColor(R.styleable.FlowIndicator_focusColor, 0xf60);

        array.recycle();
        //创建画笔对象正确的地方
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    public void setFocus(int focus) {
        this.mFocus = focus;
        invalidate();
    }

    public int getCount() {
        return mCount;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureHeight(int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        int result = size;
        if (mode != MeasureSpec.EXACTLY) {
            size = getPaddingTop() + getPaddingBottom() + 2 * mRadius;
            result = Math.min(result, size);
        }
        return result;
    }

    private int measureWidth(int widthMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        int result = size;
        if (mode != MeasureSpec.EXACTLY) {
            size = getPaddingLeft() + getPaddingRight() + mCount * 2 * mRadius + (mCount - 1) * mSpace;
            result = Math.min(result, size);
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        mPaint = new Paint();

        for (int i = 0; i < mCount; i++) {
            int x = i * (mSpace + mRadius * 2);
            int color = i == mFocus ? mFocusColor : mNormalColor;
            mPaint.setColor(color);
            canvas.drawCircle(x + mRadius, mRadius, mRadius, mPaint);
        }
    }
}
