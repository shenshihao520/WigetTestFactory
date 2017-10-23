package com.example.shenshihao520.wigettestfactory.utils;

import android.content.Context;

public class ConvertUtils {

    /**
     * dip转化px
     * @param context
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, float dipValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale + 0.5f);
    }

    /**
     * px转化dip
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5f);
    }
    /**
     * 测试git
     *
     */
    /**
     * 测试git5
     *
     */
}
