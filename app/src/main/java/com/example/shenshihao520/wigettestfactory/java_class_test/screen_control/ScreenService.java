package com.example.shenshihao520.wigettestfactory.java_class_test.screen_control;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;

/**
 * 控制手机亮屏，息屏 服务
 * Created by shenshihao520 on 2017/9/5.
 */

public class ScreenService extends Service {

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        /* 注册屏幕唤醒时的广播 */
        IntentFilter mScreenOnFilter = new IntentFilter("android.intent.action.SCREEN_ON");
        ScreenService.this.registerReceiver(mScreenOReceiver, mScreenOnFilter);

        /* 注册机器锁屏时的广播 */
        IntentFilter mScreenOffFilter = new IntentFilter("android.intent.action.SCREEN_OFF");
        ScreenService.this.registerReceiver(mScreenOReceiver, mScreenOffFilter);
    }

    public void onDestroy() {
        super.onDestroy();
        ScreenService.this.unregisterReceiver(mScreenOReceiver);
    }

    /**
     * 锁屏的管理类叫KeyguardManager，
     * 通过调用其内部类KeyguardLockmKeyguardLock的对象的disableKeyguard方法可以取消系统锁屏，
     * newKeyguardLock的参数用于标识是谁隐藏了系统锁屏
     */
    private BroadcastReceiver mScreenOReceiver = new BroadcastReceiver() {
        SharedPreferences sp;

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            sp = getSharedPreferences("Users_Screen", MODE_PRIVATE);

            if (action.equals("android.intent.action.SCREEN_ON")) {
                System.out.println("—— SCREEN_ON ——");
                long screen_off_time = sp.getLong("SCREEN_OFF",0);
                long now_time = System.currentTimeMillis();
                if(now_time-screen_off_time>0)
                {

                }

            } else if (action.equals("android.intent.action.SCREEN_OFF")) {
                System.out.println("—— SCREEN_OFF ——");
                SharedPreferences.Editor editor = sp.edit();
                editor.putLong("SCREEN_OFF", System.currentTimeMillis());
                editor.apply();
            }
        }

    };

}