package com.example.shenshihao520.wigettestfactory.java_class_test.parcelable_serializable;

import java.io.Serializable;

/**
 * 传递Serializable的类
 * Created by shenshihao520 on 2017/8/2.
 */

public class Person implements Serializable{
    private static final long serialVersionUID = 7382351359868556980L;
    private String name;
    private int age;
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

}
