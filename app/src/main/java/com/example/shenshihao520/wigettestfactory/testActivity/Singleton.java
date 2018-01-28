package com.example.shenshihao520.wigettestfactory.testActivity;

/**
 * Created by shenshihao520 on 2017/12/28.
 */

public class Singleton{
    private static Singleton INSTANCE= null;
    Singleton (){}
    public static final Singleton getInstance(){
        if(INSTANCE == null){
            synchronized (Singleton.class){
                if(INSTANCE == null){
                    INSTANCE = new Singleton();
                }
            }
        }
        return INSTANCE;
    }
}