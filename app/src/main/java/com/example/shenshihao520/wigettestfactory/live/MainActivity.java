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
    private Button video_reduce,recording;
    private MyServiceConnection serviceConnection;
    private Intent service;
    private static final int REQUEST_PERMISSION_CODE = 708;

    private static final int RECORD_REQUEST_CODE  = 101;
    private static final int STORAGE_REQUEST_CODE = 102;
    private static final int AUDIO_REQUEST_CODE   = 103;

    private MediaProjectionManager projectionManager;
    private MediaProjection mediaProjection;
    private RecordService recordService = new RecordService();


    private SurfaceView sv_media_surface;
    private MediaRecorder mediaRecorder;
    private Camera c;
    private Boolean isShoot =true;
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
                Log.i("log","点击了q！！！！");
                showFloatingWindow();
//                initScreen();
//                initVideo();
            }
        });
    }

    private void showFloatingWindow() {
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
    private WindowService.MyBinder mybinder ;
    public class MyServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mybinder = (WindowService.MyBinder) iBinder;
        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onResume() {
        super.onResume();
        if (mybinder != null) {
                mybinder.hidFloatingWindow();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解绑服务
        if (serviceConnection != null) {
            Log.i("log","serviceConnection:"+serviceConnection);
            unbindService(serviceConnection);
        }
        unbindService(connection);
        MainActivity.this.stopService(new Intent(this,WindowService.class));
    }
    //---------------------------录屏
    private void initScreen(){
        if (recordService.isRunning()) {
            recordService.stopRecord();
        } else {
            Intent captureIntent = projectionManager.createScreenCaptureIntent();
            startActivityForResult(captureIntent, RECORD_REQUEST_CODE);
        }
        Intent intent = new Intent(this, RecordService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
    }
    class RecordService extends Service {
        private MediaProjection mediaProjection;
        private MediaRecorder mediaRecorderS= new MediaRecorder();
        private VirtualDisplay virtualDisplay;

        private boolean running;
        private int width = 720;
        private int height = 1080;
        private int dpi;


        @Override
        public IBinder onBind(Intent intent) {
            return new RecordBinder();
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            return START_STICKY;
        }

        @Override
        public void onCreate() {
            super.onCreate();
            acquirePermissions();
            HandlerThread serviceThread = new HandlerThread("service_thread",
                    android.os.Process.THREAD_PRIORITY_BACKGROUND);
            serviceThread.start();
            running = false;
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
        }

        public void setMediaProject(MediaProjection project) {
            mediaProjection = project;
        }

        public boolean isRunning() {
            return running;
        }

        public void setConfig(int width, int height, int dpi) {
            this.width = width;
            this.height = height;
            this.dpi = dpi;
        }

        public boolean startRecord() {
            if (mediaProjection == null || running) {
                return false;
            }

            initRecorder();
            createVirtualDisplay();
            mediaRecorderS.start();
            running = true;
            return true;
        }

        public boolean stopRecord() {
            if (!running) {
                return false;
            }
            running = false;
            mediaRecorderS.stop();
            mediaRecorderS.reset();
            virtualDisplay.release();
            mediaProjection.stop();

            return true;
        }

        private void createVirtualDisplay() {
            virtualDisplay = mediaProjection.createVirtualDisplay("MainScreen", 720, 1080, 360,
                    DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR, mediaRecorderS.getSurface(), null, null);
        }

        private void initRecorder() {
            mediaRecorderS.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorderS.setVideoSource(MediaRecorder.VideoSource.SURFACE);
            mediaRecorderS.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorderS.setOutputFile(getsaveDirectory() +"录屏"+System.currentTimeMillis() + ".mp4");
            mediaRecorderS.setVideoSize(width, height);
            mediaRecorderS.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
            mediaRecorderS.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorderS.setVideoEncodingBitRate(5 * 1024 * 1024);
            mediaRecorderS.setVideoFrameRate(30);
            try {
                mediaRecorderS.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public String getsaveDirectory() {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String rootDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "XinFu" + "/";

                File file = new File(rootDir);
                if (!file.exists()) {
                    if (!file.mkdirs()) {
                        return null;
                    }
                }
                return rootDir;
            } else {
                return null;
            }
        }

        class RecordBinder extends Binder {
            public RecordService getRecordService() {
                return RecordService.this;
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECORD_REQUEST_CODE && resultCode == RESULT_OK) {
            mediaProjection = projectionManager.getMediaProjection(resultCode, data);
            recordService.setMediaProject(mediaProjection);
            recordService.startRecord();
        }
    }
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            RecordService.RecordBinder binder = (RecordService.RecordBinder) service;
            recordService = binder.getRecordService();
            recordService.setConfig(metrics.widthPixels, metrics.heightPixels, metrics.densityDpi);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {}
    };
    private void initVideo(){
        start();
    }
    private void acquirePermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            String[]permissions = {
                    Manifest.permission.READ_CONTACTS,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA};

            for(String value : permissions)
            {
                if(ContextCompat.checkSelfPermission(this,value) != PackageManager.PERMISSION_GRANTED) {

                    requestPermissions(new String[]{value}, REQUEST_PERMISSION_CODE);
                }

            }
        }
    }
    public void start(){
        mediaRecorder = new MediaRecorder();

        Toast.makeText(this,"开始.",Toast.LENGTH_LONG).show();
        c = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
        Camera.Parameters parameters = c.getParameters();
        c.setDisplayOrientation(90);
        c.setParameters(parameters);
        c.unlock();
        mediaRecorder.setCamera(c);
        mediaRecorder.setOrientationHint(180);
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_QVGA));

        //设置格式
//        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
//
//        mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H263);
//        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        //设置保存路径
        mediaRecorder.setOutputFile("/mnt/sdcard/XinFu/录像"+System.currentTimeMillis()+".mp4");

        mediaRecorder.setPreviewDisplay(sv_media_surface.getHolder().getSurface());

        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void stop(){
        if(mediaRecorder!=null){
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder=null;
        }
        if(c!=null){
            c.release();
            c = null;
        }
    }
}
