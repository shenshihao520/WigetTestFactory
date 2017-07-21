package com.example.shenshihao520.wigettestfactory.animation.viewpager;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

/**
 * Created by shenshihao520 on 2017/7/17.
 */

public class Test3DPageTransformer  implements ViewPager.PageTransformer {
    @Override
    public void transformPage(View page, float position) {
        int width = page.getWidth();
        Log.i("shen1", page + " , " + position + "");


        int pivotX = 0;
        if (position <= 1 && position > 0) {// right scrolling
            pivotX = 0;
        } else if (position == 0) {

        } else if (position < 0 && position >= -1) {// left scrolling
            pivotX = width;
        }
        //设置x轴的锚点
        page.setPivotX(pivotX);
        //设置绕Y轴旋转的角度
        page.setRotationY(90f * position);
    }
}
