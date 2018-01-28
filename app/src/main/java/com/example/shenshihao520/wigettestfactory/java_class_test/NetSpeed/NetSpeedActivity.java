package com.example.shenshihao520.wigettestfactory.java_class_test.NetSpeed;

import android.app.Activity;
import android.net.TrafficStats;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.example.shenshihao520.wigettestfactory.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 检查当前网速
 * Created by shenshihao520 on 2017/12/12.
 */

public class NetSpeedActivity extends Activity{

    private long lastTotalRxBytes = 0;
    private long lastTimeStamp = 0;
    Button btn_start;
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_speed);
        btn_start = (Button)findViewById(R.id.btn_start);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastTotalRxBytes = getTotalRxBytes();
                lastTimeStamp = System.currentTimeMillis();
                new Timer().schedule(task, 1000, 2000); // 1s后启动任务，每2s执行一次
            }
        });
    }

    private void showNetSpeed() {

        long nowTotalRxBytes = getTotalRxBytes();
        long nowTimeStamp = System.currentTimeMillis();
        long speed = ((nowTotalRxBytes - lastTotalRxBytes) * 1000 / (nowTimeStamp - lastTimeStamp));//毫秒转换

        lastTimeStamp = nowTimeStamp;
        lastTotalRxBytes = nowTotalRxBytes;

        Message msg = mHandler.obtainMessage();
        msg.what = 100;
        msg.obj = String.valueOf(speed) + " kb/s";

        mHandler.sendMessage(msg);//更新界面
    }

    private long getTotalRxBytes() {
        return TrafficStats.getUidRxBytes(getApplicationInfo().uid)==TrafficStats.UNSUPPORTED ? 0 :(TrafficStats.getTotalRxBytes()/1024);//转为KB
    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            showNetSpeed();
        }
    };
}
