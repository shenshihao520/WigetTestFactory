package com.example.shenshihao520.wigettestfactory.java_class_test.parcelable_serializable;

/**
 * 一个测试用的接口
 * Created by shenshihao520 on 2017/8/2.
 */

public interface Callback  {

    /**
     * Schedule javascript function execution represented by this {@link Callback} instance
     *
     * @param args arguments passed to javascript callback method via bridge
     */
    public void invoke(Object... args);

}