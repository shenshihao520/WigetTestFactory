package com.example.shenshihao520.wigettestfactory.java_class_test.parcelable_serializable;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 传递Parcelable的类
 * Created by shenshihao520 on 2017/8/2.
 */

public class Pen implements Parcelable{
    private String color;
    private int size;

    // 系统自动添加，给createFromParcel里面用
    protected Pen(Parcel in) {
        color = in.readString();
        size = in.readInt();
    }
    public static final Creator<Pen> CREATOR = new Creator<Pen>() {
        /**
         *
         * @param in
         * @return
         * createFromParcel()方法中我们要去读取刚才写出的name和age字段，
         * 并创建一个Pen对象进行返回，其中color和size都是调用Parcel的readXxx()方法读取到的，
         * 注意这里读取的顺序一定要和刚才写出的顺序完全相同。
         * 读取的工作我们利用一个构造函数帮我们完成了
         */
        @Override
        public Pen createFromParcel(Parcel in) {
            return new Pen(in); // 在构造函数里面完成了 读取 的工作
        }
        //供反序列化本类数组时调用的
        @Override
        public Pen[] newArray(int size) {
            return new Pen[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;  // 内容接口描述，默认返回0即可。
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(color);  // 写出 color
        dest.writeInt(size);  // 写出 size
    }
    // ======分割线，写写get和set
    //个人自己添加
    public Pen() {
    }
    //个人自己添加
    public Pen(String color, int size) {
        this.color = color;
        this.size = size;
    }

    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }



}
