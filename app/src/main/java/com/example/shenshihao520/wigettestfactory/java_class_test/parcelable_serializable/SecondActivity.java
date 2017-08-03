package com.example.shenshihao520.wigettestfactory.java_class_test.parcelable_serializable;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.shenshihao520.wigettestfactory.R;

/**
 * Created by shenshihao520 on 2017/8/2.
 */

public class SecondActivity extends AppCompatActivity{
    TextView tv_getPerson;
    TextView tv_getPen;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serializable_second);
        initView();
    }

    private void initView() {
        tv_getPerson = (TextView) findViewById(R.id.getPerson);

        tv_getPen = (TextView) findViewById(R.id.getPen);
//        Intent intent = getIntent();
//        // 关键方法：getSerializableExtra ，我们的类是实现了Serializable接口的，所以写这个方法获得对象
//        // public class Person implements Serializable
//        Person per = (Person) intent.getSerializableExtra("put_ser_test");
//        //Person per = (Person)intent.getSerializableExtra("bundle_ser");
//        tv_getPerson.setText("名字：" + per.getName() + "\\n"
//                + "年龄：" + per.getAge());


//        Pen pen = (Pen) getIntent().getParcelableExtra("parcel_test");
//
//        tv_getPen.setText("颜色:" + pen.getColor() + "\\n"
//                + "大小:" + pen.getSize());
        CustomInputStream customInputStream = new CustomInputStream();
        Diao diao = (Diao)customInputStream.CustomInputStreamBuild(Environment.getExternalStorageDirectory()+"/PDF/banner");
        Log.i("66",diao.toString());
    }



}
