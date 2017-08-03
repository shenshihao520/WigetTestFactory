package com.example.shenshihao520.wigettestfactory.java_class_test.handler_thread;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.shenshihao520.wigettestfactory.R;

/**
 * Created by shenshihao520 on 2017/8/2.
 */

public class HandlerThreadTestActivity extends AppCompatActivity implements View.OnClickListener{
    private HandlerThread mHandlerThread;
    Button start_handler_thread;
    Button sent_msg;
    Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_thread);
        initView();
        initData();
    }

    private void initData() {
        mHandlerThread = new HandlerThread("HandlerThread");


        mHandlerThread.start();

        handler  = new Handler(mHandlerThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                Log.i("6666666666","收到消息");

            }
        };
    }

    private void initView() {
        start_handler_thread = (Button)findViewById(R.id.start_handler_thread);
        sent_msg = (Button)findViewById(R.id.sent_msg);

        start_handler_thread.setOnClickListener(this);
        sent_msg.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.start_handler_thread:

                break;

            case R.id.sent_msg:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);//模拟耗时操作
                            Log.i("6666666666","发送消息");

                            handler.sendEmptyMessage(0);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandlerThread.quit();
    }
}
