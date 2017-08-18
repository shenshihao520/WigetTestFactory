package com.example.shenshihao520.wigettestfactory.testActivity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;

import com.example.shenshihao520.wigettestfactory.R;

/**
 * Created by shenshihao520 on 2017/8/11.
 */

public class ScreenActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);

        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;  // 屏幕宽度（像素）
        int height = metric.heightPixels;  // 屏幕高度（像素）
        float density = metric.density;  // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）



        Log.i("123123屏幕宽度（像素）",width+"");
        Log.i("123123屏幕高度（像素）",height+"");
        Log.i("123123density）",density+"");
        Log.i("123123densityDpi",densityDpi+"");

        Log.i("123123屏幕宽度（dp）",px2dip(this,width)+"");
        Log.i("123123屏幕高度（dp）",px2dip(this,height)+"");

    }
    public static int dip2px(Context context, float dipValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale + 0.5f);
    }
    public static int px2dip(Context context, float pxValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5f);
    }

}
