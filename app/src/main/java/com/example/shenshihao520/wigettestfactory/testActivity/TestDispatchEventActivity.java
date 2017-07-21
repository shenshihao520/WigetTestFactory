package com.example.shenshihao520.wigettestfactory.testActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.shenshihao520.wigettestfactory.R;
import com.example.shenshihao520.wigettestfactory.widget.test.TestDispatchEvent;

/**
 * 事件分发测试Activity
 * Created by shenshihao520 on 2017/7/19.
 */

public class TestDispatchEventActivity extends AppCompatActivity{
    private TestDispatchEvent testDispatchEvent ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_dispatch);
        testDispatchEvent = (TestDispatchEvent)findViewById(R.id.tv);
        testDispatchEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("testShen","onClick");
            }
        });
        testDispatchEvent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("testShen","onTouch");

                return false;
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                Log.i("testShen","ActivityDispatch");
                break;

        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                Log.i("testShen","ActivityTouch");
                break;

        }
        return super.onTouchEvent(event);
    }


}
