package com.example.shenshihao520.wigettestfactory.java_class_test.proxy_static;

/**
 * Created by shenshihao520 on 2017/8/28.
 */

/**
 * 明星实体类
 */
public class Star implements IStar {
    private String mName;

    public Star(String name) {
        mName = name;
    }

    @Override
    public void attendTheShow() {
        System.out.print( this.mName + " 参加演出 \n");
    }

    @Override
    public void loveWife() {
        System.out.print(this.mName + " 照顾了妻子");
    }
}