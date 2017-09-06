package com.example.shenshihao520.wigettestfactory.java_class_test.battery;

import android.app.ApplicationErrorReport;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.shenshihao520.wigettestfactory.R;

/**
 * Created by shenshihao520 on 2017/8/24.
 */

public class BatteryActivity extends AppCompatActivity{
    ApplicationErrorReport applicationErrorReport;
    ApplicationErrorReport.BatteryInfo batteryInfo;
    Button button ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader);
        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


        ApplicationErrorReport report = new ApplicationErrorReport();

    }
}
