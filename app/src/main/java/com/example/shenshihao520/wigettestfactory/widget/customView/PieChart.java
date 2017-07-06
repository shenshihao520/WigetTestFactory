package com.example.shenshihao520.wigettestfactory.widget.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.shenshihao520.wigettestfactory.R;

/**
 * Created by shenshihao520 on 2017/7/6.
 */

class PieChart extends View {
    boolean mShowText;
    int mTextPos ;
    Paint mTextPaint;
    Paint mPiePaint;
    Paint mShadowPaint;
    float mTextHeight = 0;
    int mTextColor = 0;
    float mTextWidth = 0;
    public PieChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取控件属性
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.PieChart,
                0, 0);

        try {
            mShowText = a.getBoolean(R.styleable.PieChart_showText, false);
            mTextPos = a.getInteger(R.styleable.PieChart_labelPosition, 0);
        } finally {
            a.recycle();
        }
    }
    public boolean isShowText() {
        return mShowText;
    }

    public void setShowText(boolean showText) {
        mShowText = showText;
        invalidate();
        requestLayout();
    }

    /**
     *  进行初始化画笔
     */
    private void init(){
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(mTextColor);
        if (mTextHeight == 0) {
            mTextHeight = mTextPaint.getTextSize();
        } else {
            mTextPaint.setTextSize(mTextHeight);
        }

        mPiePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPiePaint.setStyle(Paint.Style.FILL);
        mPiePaint.setTextSize(mTextHeight);

        mShadowPaint = new Paint(0);
        mShadowPaint.setColor(0xff101010);
        mShadowPaint.setMaskFilter(new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL));
    }

    /**
     *  进行对传进来的宽和高来进行控制
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Try for a width based on our minimum
        int minw = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();
        int w = resolveSizeAndState(minw, widthMeasureSpec, 1);

        // Whatever the width ends up being, ask for a height that would let the pie
        // get as big as it can
        int minh = MeasureSpec.getSize(w) - (int)mTextWidth + getPaddingBottom() + getPaddingTop();
        int h = resolveSizeAndState(MeasureSpec.getSize(w) - (int)mTextWidth, heightMeasureSpec, 0);

        setMeasuredDimension(w, h);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        // Draw the shadow
//        canvas.drawOval(
//                mShadowBounds,
//                mShadowPaint
//        );
//
//        // Draw the label text
//        canvas.drawText(mData.get(mCurrentItem).mLabel, mTextX, mTextY, mTextPaint);
//
//        // Draw the pie slices
//        for (int i = 0; i < mData.size(); ++i) {
//            Item it = mData.get(i);
//            mPiePaint.setShader(it.mShader);
//            canvas.drawArc(mBounds,
//                    360 - it.mEndAngle,
//                    it.mEndAngle - it.mStartAngle,
//                    true, mPiePaint);
//        }
//
//        // Draw the pointer
//        canvas.drawLine(mTextX, mPointerY, mPointerX, mPointerY, mTextPaint);
//        canvas.drawCircle(mPointerX, mPointerY, mPointerSize, mTextPaint);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    /**
     * 当第一次绘制或者 发生size改变时会触发的函数   代码片段
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // Account for padding
        float xpad = (float)(getPaddingLeft() + getPaddingRight());
        float ypad = (float)(getPaddingTop() + getPaddingBottom());

        // Account for the label
        if (mShowText) xpad += mTextWidth;

        float ww = (float)w - xpad;
        float hh = (float)h - ypad;

        // Figure out how big we can make the pie.
        float diameter = Math.min(ww, hh);

    }
}
