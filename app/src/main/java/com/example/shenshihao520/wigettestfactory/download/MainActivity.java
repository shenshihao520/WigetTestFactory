package com.example.shenshihao520.wigettestfactory.download;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
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
    DownloadPDFCallback countService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        mContext = this;
        initView();


    }
    private ServiceConnection conn = new ServiceConnection() {
        /** 获取服务对象时的操作 */
        public void onServiceConnected(ComponentName name, IBinder service) {
            countService = ((DownloadPDFService.ServiceBinder) service).getService();

        }

        /** 无法获取到服务对象时的操作 */
        public void onServiceDisconnected(ComponentName name) {
            countService = null;
        }

    };
    //利用bindService的方法 获取Service对象再通过接口调用service方法 可以减轻boardcast的重量，但是生命周期将降低，activity被杀死  也将销毁
    //根据需求吧  可以切换方法  下面注释部分是boardcast方法
    private void initView() {
        download = (Button)findViewById(R.id.download);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,DownloadPDFService.class);
                bindService(intent,conn,Context.BIND_AUTO_CREATE);
//                startService(intent);

            }
        });
        check = (Button)findViewById(R.id.check);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setAction("android.intent.action.CheckDownloadStatus");
//                mContext.sendBroadcast(intent);
                countService.getPDFStatus();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unbindService(conn);

    }
}
