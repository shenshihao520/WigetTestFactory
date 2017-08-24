package com.example.shenshihao520.wigettestfactory.java_class_test.hugo_aop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.shenshihao520.wigettestfactory.R;

import hugo.weaving.DebugLog;

/**
 * Created by shenshihao520 on 2017/8/23.
 */

public class HugoActivity extends AppCompatActivity{
    Button button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hugo);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    @DebugLog
    void clickHugo()
    {
       //TODO whatever you do it will print Log
    }
}
