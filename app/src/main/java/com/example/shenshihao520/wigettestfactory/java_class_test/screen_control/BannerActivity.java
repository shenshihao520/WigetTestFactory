package com.example.shenshihao520.wigettestfactory.java_class_test.screen_control;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.example.shenshihao520.wigettestfactory.R;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        Intent intent = new Intent();
        intent.setClass(BannerActivity.this,ScreenService.class);
        bindService(intent,conn,BIND_AUTO_CREATE);
        initView();
        initData();
    }
    private ServiceConnection conn = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        //client 和service连接意外丢失时，会调用该方法
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.v("hjz","onServiceDisconnected  A");
        }
    };
    private void initData() {
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
}
