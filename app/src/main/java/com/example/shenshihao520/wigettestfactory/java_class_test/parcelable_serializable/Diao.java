package com.example.shenshihao520.wigettestfactory.java_class_test.parcelable_serializable;

import java.io.Serializable;

/**
 * 传递接口的类
 * Created by shenshihao520 on 2017/8/2.
 */

public class Diao implements Serializable{

    Callback callback;

    public Callback getCallback() {
        return callback;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public String toString() {
        return "Diao{" +
                "callback=" + "66" +
                '}';
    }

}
