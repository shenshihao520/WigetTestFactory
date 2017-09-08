package com.example.shenshihao520.wigettestfactory.java_class_test.wifi_control;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.example.shenshihao520.wigettestfactory.R;
import com.example.shenshihao520.wigettestfactory.java_class_test.screen_control.ScreenService;
import com.example.shenshihao520.wigettestfactory.widget.banner_image.BannerImage;
import com.example.shenshihao520.wigettestfactory.widget.banner_text.BannerText;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试Activity 亮屏息屏
 * Created by shenshihao520 on 2017/7/17.
 */

public class BannerActivity extends Activity{
    BannerImage bannerImage;
    BannerText bannerText;
    private LayoutInflater mInflater;
    int[] mImagesSrc = { R.mipmap.firstpage_banner,
            R.mipmap.firstpage_banner1 };
    WifiManager wifiManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
//        Intent intent = new Intent();
//        intent.setClass(BannerActivity.this,ScreenService.class);
//        bindService(intent,conn,BIND_AUTO_CREATE);
        initView();
        initData();

        wifiManager = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);   //初始化
        OpenWifi();
        List<WifiConfiguration> existingConfigs = wifiManager.getConfiguredNetworks();     //获取已经配置好（就是记住密码的wifi）的wifi列表
        for (int i = 0; i < existingConfigs.size() ;i++)
        {
            String ssid = existingConfigs.get(i).SSID;
            if(ssid .equals("\"D9_3635\""))
            {
                boolean is = Connect(existingConfigs.get(i));

                        break;


            }

        }


    }

    /**
     * 监听网络状态变化
     */
    BroadcastReceiver connectionReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo wifiNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if ( !wifiNetInfo.isConnected()) {
// unconnect network
            }else {
                bannerText.startScroll();
// connect network
            }
        }
    };
    private void initData() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(connectionReceiver, intentFilter);


        List scrollList = new ArrayList<String>();
        scrollList.add("热烈欢迎领导光临");
        scrollList.add("喜庆国庆欢乐大酬宾");

        bannerText.setList(scrollList);
        bannerText.startScroll();



        bannerImage.setAdapter(new BannerImage.Adapter() {
            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public View getView(int position) {
                View view = mInflater.inflate(R.layout.image_item, null);
                ImageView imageView = (ImageView) view.findViewById(R.id.image);
                imageView.setImageResource(mImagesSrc[position]);
                return view;            }

            @Override
            public int getCount() {
                return mImagesSrc.length;
            }
        });

    }

    private void initView() {
        mInflater = LayoutInflater.from(this);

        bannerText = (BannerText) findViewById(R.id.banner_text);
        bannerImage = (BannerImage)findViewById(R.id.banner_image);
    }

    /**
     * 打开wifi功能
     *
     * @return true or false
     */
    public boolean OpenWifi() {
        boolean bRet = true;
        if (!wifiManager.isWifiEnabled()) {
            bRet = wifiManager.setWifiEnabled(true);
        }
        return bRet;
    }
    /**
     * 关闭wifi
     *
     * @return
     */
    public boolean closeWifi() {
        if (!wifiManager.isWifiEnabled()) {
            return true;
        } else {
            return wifiManager.setWifiEnabled(false);
        }
    }

    /**
     * 已有配置链接
     *
     * @return
     * @param wifiConfiguration
     */
    public boolean Connect(WifiConfiguration wifiConfiguration) {
        if (!this.OpenWifi()) {
            return false;
        }
//        List<WifiConfiguration> existingConfigs = wifiManager.getConfiguredNetworks();

// 状态变成WIFI_STATE_ENABLED的时候才能执行下面的语句，即当状态为WIFI_STATE_ENABLING时，让程序在while里面跑
        while (wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLING) {
            try {
// 为了避免程序一直while循环，让它睡个100毫秒在检测……
                Thread.currentThread();
                Thread.sleep(100);
            } catch (InterruptedException ie) {
            }
        }
        boolean bRet = wifiManager.enableNetwork(wifiConfiguration.networkId, true);
        wifiManager.saveConfiguration();
        return bRet;
    }
}
