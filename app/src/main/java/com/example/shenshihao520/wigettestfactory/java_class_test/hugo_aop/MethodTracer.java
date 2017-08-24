package com.example.shenshihao520.wigettestfactory.java_class_test.hugo_aop;

/**
 * Created by shenshihao520 on 2017/8/23.
 */

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * 可以进行class类编译后织入代码从而埋点
 */
@Aspect
public class MethodTracer {

    private static final String TAG = MethodTracer.class.getSimpleName();

    @Before("execution (protected void com.example.shenshihao520.wigettestfactory.java_class_test.hugo_aop.AspectJActivity.onCreate(android.os.Bundle))")
    public void adviceOnCreate(JoinPoint joinPoint) {
        Log.v(TAG, joinPoint.toString());
    }
}

