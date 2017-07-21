package com.example.shenshihao520.wigettestfactory.widget.customView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 自定义ViewGroup
 * Created by shenshihao520 on 2017/7/19.
 */

public class TestCustomViewGroup extends ViewGroup {
    public TestCustomViewGroup(Context context) {
        super(context);
    }

    public TestCustomViewGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestCustomViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int height = 0;
        int width = 0;
        int count = getChildCount();
        View child;
        for(int i = 0 ;i < count;i++) {
            child = getChildAt(i);
            int left = width;
            int top = 0;
            int right = child.getMeasuredWidth() + width;
            int bottom = child.getMeasuredHeight();
            child.layout(left, top,right ,bottom);
            width += child.getMeasuredWidth();

        }
    }
}
