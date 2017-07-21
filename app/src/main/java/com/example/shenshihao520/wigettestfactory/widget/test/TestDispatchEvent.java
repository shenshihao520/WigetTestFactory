package com.example.shenshihao520.wigettestfactory.widget.test;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by shenshihao520 on 2017/7/19.
 */

public class TestDispatchEvent extends android.support.v7.widget.AppCompatTextView{

    public TestDispatchEvent(Context context) {
        super(context);
    }

    public TestDispatchEvent(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestDispatchEvent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                Log.i("testShen","TextViewDispatch");
                break;

        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                Log.i("testShen","TextViewonTouchEvent");
                break;

        }
        return super.onTouchEvent(event);
    }
}
