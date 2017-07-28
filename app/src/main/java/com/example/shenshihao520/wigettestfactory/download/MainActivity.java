package com.example.shenshihao520.wigettestfactory.download;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.example.shenshihao520.wigettestfactory.R;

/**
 * Created by shenshihao520 on 2017/7/27.
 */

public class MainActivity extends Activity{
    Button download;
    Button check;
    Context mContext;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        mContext = this;
        initView();


    }

    private void initView() {
        download = (Button)findViewById(R.id.download);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,DownloadPDFService.class);
                startService(intent);

            }
        });
        check = (Button)findViewById(R.id.check);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.CheckDownloadStatus");
                mContext.sendBroadcast(intent);
            }
        });
    }
}
