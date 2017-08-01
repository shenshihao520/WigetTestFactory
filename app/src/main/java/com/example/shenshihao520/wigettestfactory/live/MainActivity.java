package com.example.shenshihao520.wigettestfactory.live;

import android.Manifest;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.HandlerThread;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.shenshihao520.wigettestfactory.R;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private Button video_reduce, recording;
    private MyServiceConnection serviceConnection;
    private Intent service;
    private static final int REQUEST_PERMISSION_CODE = 708;

    private static final int RECORD_REQUEST_CODE = 101;
    private static final int STORAGE_REQUEST_CODE = 102;
    private static final int AUDIO_REQUEST_CODE = 103;

    private MediaProjectionManager projectionManager;
    private MediaProjection mediaProjection;


    private SurfaceView sv_media_surface;
    private MediaRecorder mediaRecorder;
    private Camera c;
    private Boolean isShoot = true;
    private MyServiceConnection connection = new MyServiceConnection();
    private ServiceDemo.LocalBinder binder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_video2);
        initView();

    }

    private void initView() {
//        sv_media_surface = (SurfaceView)findViewById(R.id.ac_surfaceview) ;
        video_reduce = (Button) findViewById(R.id.video_reduce);
        recording = (Button) findViewById(R.id.recording);
        serviceConnection = new MyServiceConnection();
        projectionManager = (MediaProjectionManager) getSystemService(MEDIA_PROJECTION_SERVICE);
        video_reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("log", "点击了q！！！！");
                showFloatingWindow();
//                initScreen();
//                initVideo();
            }
        });
    }

    private void showFloatingWindow() {
//        FangFa();
         service = new Intent(MainActivity.this, FloatService.class);
//        if (mybinder != null&& !mybinder.isMiUI8) {
//            mybinder.showFloatingWindow();
//        Log.i("log","showFloatingWindow");
//        } else {
//            Log.i("log","11111111showFloatingWindow:"+mybinder);
            startService(service);
//            bindService(service, serviceConnection, Service.BIND_AUTO_CREATE);
//            if (mybinder!=null) {
//                Log.i("log","2222222showFloatingWindow");
//                mybinder.showFloatingWindowMiUI8();
//            }
//        }
    }

    private void FangFa() {
        Intent intent = new Intent(this, ServiceDemo.class);
        startService(intent);
//        bindService(intent, connection, Service.BIND_AUTO_CREATE);
    }

    public class MyServiceConnection implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            binder = (ServiceDemo.LocalBinder) iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }




}
