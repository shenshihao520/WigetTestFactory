package com.example.shenshihao520.wigettestfactory.live;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by shenshihao520 on 2017/7/26.
 */

public class TestService extends Service{

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        android.os.Debug.waitForDebugger();

        super.onCreate();
    }
}
