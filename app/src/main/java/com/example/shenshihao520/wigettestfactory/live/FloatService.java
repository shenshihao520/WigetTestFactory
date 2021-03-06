package com.example.shenshihao520.wigettestfactory.live;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.PixelFormat;
import android.graphics.Rect;

import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.display.VirtualDisplay;
import android.media.CamcorderProfile;
import android.media.Image;
import android.media.ImageReader;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shenshihao520.wigettestfactory.R;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by shenshihao520 on 2017/7/26.
 */

public class FloatService extends Service {

    private static final int UPDATE_PIC = 0x100;
    private int statusBarHeight;// 状态栏高度
    private View view;// 透明窗体
    private TextView text = null;
    private Button hideBtn = null;
    private Button updateBtn = null;
    private HandlerUI handler = null;
    private Thread updateThread = null;
    private boolean viewAdded = false;// 透明窗体是否已经显示
    private boolean viewHide = false; // 窗口隐藏
    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;

    private SurfaceView sv_media_surface;
    private MediaRecorder mediaRecorder;
        private Camera c;
    private CameraManager mCameraManager;//摄像头管理器
    private Handler childHandler, mainHandler;
    private String mCameraID;//摄像头Id 0 为后  1 为前
    private ImageReader mImageReader;
    private CameraCaptureSession mCameraCaptureSession;
    private CameraDevice mCameraDevice;


    private Boolean isShoot = true;
    SurfaceHolder myHolder;

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        createFloatView();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        // TODO Auto-generated method stub
        super.onStart(intent, startId);
        System.out.println("------------------onStart");

        viewHide = false;
        refresh();
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        removeView();
    }

    /**
     * 关闭悬浮窗
     */
    public void removeView() {
        if (viewAdded) {
            windowManager.removeView(view);
            viewAdded = false;
        }
    }

    private void createFloatView() {
        handler = new HandlerUI();
        UpdateUI update = new UpdateUI();
        updateThread = new Thread(update);
        updateThread.start(); // 开户线程

        view = LayoutInflater.from(this).inflate(R.layout.test_float_service, null);
        sv_media_surface = (SurfaceView) view.findViewById(R.id.sv_media_surface);
        text = (TextView) view.findViewById(R.id.usage);
        hideBtn = (Button) view.findViewById(R.id.hideBtn);
        updateBtn = (Button) view.findViewById(R.id.updateBtn);
        windowManager = (WindowManager) this.getSystemService(WINDOW_SERVICE);
        /*
         * LayoutParams.TYPE_SYSTEM_ERROR：保证该悬浮窗所有View的最上层
         * LayoutParams.FLAG_NOT_FOCUSABLE:该浮动窗不会获得焦点，但可以获得拖动
         * PixelFormat.TRANSPARENT：悬浮窗透明
         */
        layoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSPARENT);
        // layoutParams.gravity = Gravity.RIGHT|Gravity.BOTTOM; //悬浮窗开始在右下角显示
        layoutParams.gravity = Gravity.LEFT | Gravity.TOP;

        /**
         * 监听窗体移动事件
         */
        view.setOnTouchListener(new View.OnTouchListener() {
            float[] temp = new float[]{0f, 0f};

            public boolean onTouch(View v, MotionEvent event) {
                layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
                int eventaction = event.getAction();
                switch (eventaction) {
                    case MotionEvent.ACTION_DOWN: // 按下事件，记录按下时手指在悬浮窗的XY坐标值
                        temp[0] = event.getX();
                        temp[1] = event.getY();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        refreshView((int) (event.getRawX() - temp[0]),
                                (int) (event.getRawY() - temp[1]));
                        break;

                }
                return true;
            }
        });

        hideBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                viewHide = true;
                removeView();
                System.out.println("----------hideBtn");
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "you click UpdateBtn",
                        Toast.LENGTH_SHORT).show();
//                System.out.println("mom "
//                        + SysInfoUtils
//                        .getUsedPercentValue(getApplicationContext()));
            }
        });
        initVideo();
//
    }

    /**
     * 刷新悬浮窗
     *
     * @param x 拖动后的X轴坐标
     * @param y 拖动后的Y轴坐标
     */
    private void refreshView(int x, int y) {
        // 状态栏高度不能立即取，不然得到的值是0
        if (statusBarHeight == 0) {
            View rootView = view.getRootView();
            Rect r = new Rect();
            rootView.getWindowVisibleDisplayFrame(r);
            statusBarHeight = r.top;
        }

        layoutParams.x = x;
        // y轴减去状态栏的高度，因为状态栏不是用户可以绘制的区域，不然拖动的时候会有跳动
        layoutParams.y = y - statusBarHeight;// STATUS_HEIGHT;
        refresh();
    }

    /**
     * 添加悬浮窗或者更新悬浮窗 如果悬浮窗还没添加则添加 如果已经添加则更新其位置
     */
    private void refresh() {
        // 如果已经添加了就只更新view
        if (viewAdded) {
            windowManager.updateViewLayout(view, layoutParams);
        } else {
            windowManager.addView(view, layoutParams);
            viewAdded = true;
        }
    }

    /**
     * 接受消息和处理消息
     *
     * @author Administrator
     */
    class HandlerUI extends Handler {
        public HandlerUI() {

        }

        public HandlerUI(Looper looper) {
            super(looper);
        }

        /**
         * 接收消息
         */
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            // 根据收到的消息分别处理
            if (msg.what == UPDATE_PIC) {
//                text.setText(SysInfoUtils
//                        .getUsedPercentValue(getApplicationContext())
//                        + "  t = "
//                        + SysInfoUtils.getTotalMemory(getApplicationContext())
//                        + "  a = "
//                        + SysInfoUtils
//                        .getAvailableMemoryString(getApplicationContext()));
                if (!viewHide)
                    refresh();
            } else {
                super.handleMessage(msg);
            }

        }

    }

    /**
     * 更新悬浮窗的信息
     *
     * @author Administrator
     */
    class UpdateUI implements Runnable {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            // 如果没有中断就一直运行
            while (!Thread.currentThread().isInterrupted()) {
                Message msg = handler.obtainMessage();
                msg.what = UPDATE_PIC; // 设置消息标识
                handler.sendMessage(msg);
                // 休眠1s
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    private void initVideo() {
        start();
    }

    public void start() {
        Toast.makeText(this, "开始.", Toast.LENGTH_LONG).show();

        myHolder = sv_media_surface.getHolder();
        myHolder.addCallback(new MyCallback());
    }

    public void stop() {
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

    public class MyCallback implements SurfaceHolder.Callback {
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format,
                                   int width, int height) {
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            // you need to start your drawing thread here
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        mediaRecorder = new MediaRecorder();

                        c = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
                        Camera.Parameters parameters = c.getParameters();
                        c.setDisplayOrientation(90);
                        c.setParameters(parameters);
                        c.unlock();
//                        initCamera2();
                        mediaRecorder.setPreviewDisplay(myHolder.getSurface());

                        mediaRecorder.getSurface();
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
                        mediaRecorder.setOutputFile("/sdcard/sForm／" + System.currentTimeMillis() + ".mp4");


                        mediaRecorder.prepare();
                        mediaRecorder.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            // and here you need to stop it
        }
    }
    private void prepareRecording() {
        try {
            mediaRecorder.prepare();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        final String directory = Environment.getExternalStorageDirectory() + File.separator + "Recordings";
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            Toast.makeText(this, "Failed to get External Storage", Toast.LENGTH_SHORT).show();
            return;
        }
        final File folder = new File(directory);
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdir();
        }
        String filePath;
        if (success) {
            String videoName = ("capture_" + getCurSysDate() + ".mp4");
            filePath = directory + File.separator + videoName;
        } else {
            Toast.makeText(this, "Failed to create Recordings directory", Toast.LENGTH_SHORT).show();
            return;
        }

//        int width = mDisplayMetrics.widthPixels;
//        int height = mDisplayMetrics.heightPixels;

        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setVideoEncodingBitRate(512 * 1000);
        mediaRecorder.setVideoFrameRate(30);
//        mediaRecorder.setVideoSize(width, height);
        mediaRecorder.setOutputFile(filePath);
    }
    public String getCurSysDate() {
        return new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
    }
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
////        if (requestCode != CAST_PERMISSION_CODE) {
////            // Where did we get this request from ? -_-
//////            Log.w(TAG, "Unknown request code: " + requestCode);
////            return;
////        }
////        if (resultCode != RESULT_OK) {
////            Toast.makeText(this, "Screen Cast Permission Denied :(", Toast.LENGTH_SHORT).show();
////            return;
////        }
//        mMediaProjection = mProjectionManager.getMediaProjection(resultCode, data);
//        // mMediaProjection.registerCallback(callback, null);
//        mVirtualDisplay = getVirtualDisplay();
//        mMediaRecorder.start();
//    }
//
//    private VirtualDisplay getVirtualDisplay() {
//        screenDensity = mDisplayMetrics.densityDpi;
//        int width = mDisplayMetrics.widthPixels;
//        int height = mDisplayMetrics.heightPixels;
//
//        return mMediaProjection.createVirtualDisplay(this.getClass().getSimpleName(),
//                width, height, screenDensity,
//                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
//                mMediaRecorder.getSurface(), null /*Callbacks*/, null /*Handler*/);
//    }


}