package com.example.shenshihao520.wigettestfactory.testActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.shenshihao520.wigettestfactory.R;
import com.example.shenshihao520.wigettestfactory.widget.customView.DrawTaiJiView;

/**
 * Created by shenshihao520 on 2017/7/19.
 */

public class TestDrawCustomView extends AppCompatActivity{
    DrawTaiJiView testDrawCustomView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_custom_view);

    }
    private void initView()
    {
        testDrawCustomView = (DrawTaiJiView)findViewById(R.id.taiji);

    }
}
