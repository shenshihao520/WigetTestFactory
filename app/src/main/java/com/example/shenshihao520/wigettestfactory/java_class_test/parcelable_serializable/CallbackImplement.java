package com.example.shenshihao520.wigettestfactory.java_class_test.parcelable_serializable;

import java.io.Serializable;

/**
 * 如果想序列化接口  就必须序列化其实现类
 * Created by shenshihao520 on 2017/8/3.
 */

public class CallbackImplement implements Callback,Serializable{
    @Override
    public void invoke(Object... args) {

    }
}
