package com.example.shenshihao520.wigettestfactory.java_class_test.screen_control;

import android.content.Context;
import android.content.Intent;

/**
 * Created by shenshihao520 on 2017/9/5.
 */

public class BroadcastReceiver extends android.content.BroadcastReceiver {
    private String action = null;
    @Override
    public void onReceive(Context context, Intent intent) {
        action = intent.getAction();
        if (Intent.ACTION_SCREEN_ON.equals(action)) {
            // 开屏
        } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
            // 锁屏
        } else if (Intent.ACTION_USER_PRESENT.equals(action)) {
            // 解锁
        }
    }

}
