package com.example.shenshihao520.wigettestfactory.live;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.shenshihao520.wigettestfactory.R;

import java.io.IOException;
import java.util.List;


public class WindowService extends Service {
    private final String TAG = this.getClass().getSimpleName();
    private WindowManager.LayoutParams wmParams;
    private WindowManager mWindowManager;
    private View mWindowView;
    private int mStartX;
    private int mStartY;
    private int mEndX;
    private int mEndY;
    private MyBinder myBinder;
//    private MiExToast miToast;
    //录像

    private SurfaceView sv_media_surface;
    private MediaRecorder mediaRecorder;
    private Camera c;
    private Boolean isShoot = true;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("shen", "onCreate");
        initWindowParams();
        initView();
        addWindowView2Window();
        initClick();
        initVideo();
    }

    private void initWindowParams() {
        mWindowManager = (WindowManager) getApplication().getSystemService(getApplication().WINDOW_SERVICE);
        wmParams = new WindowManager.LayoutParams();
        // 更多type：https://developer.android.com/reference/android/view/WindowManager.LayoutParams.html#TYPE_PHONE
        wmParams.type = Integer.parseInt(Build.VERSION.SDK) > 18 ? WindowManager.LayoutParams.TYPE_TOAST :
                WindowManager.LayoutParams.TYPE_PHONE;//TYPE_PHONE
        wmParams.format = PixelFormat.TRANSLUCENT;
        // 更多falgs:https://developer.android.com/reference/android/view/WindowManager.LayoutParams.html#FLAG_NOT_FOCUSABLE
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
        wmParams.width = 400;
        wmParams.height = 600;
    }

    private void initView() {
        mWindowView = LayoutInflater.from(getApplication()).inflate(R.layout.activity_screen_video, null);
        myBinder = new MyBinder();
        sv_media_surface = (SurfaceView) mWindowView.findViewById(R.id.sv_media_surface);
        //实例化媒体录制器
        mediaRecorder = new MediaRecorder();
    }

    private void addWindowView2Window() {
        mWindowManager.addView(mWindowView, wmParams);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stop();
        removeWindowView();
        stopSelf();
        Log.i(TAG, "onDestroy");
    }

    private void removeWindowView() {
        if (sv_media_surface != null) {
            //移除悬浮窗口
            Log.i(TAG, "removeView");
            mWindowManager.removeView(sv_media_surface);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    private void initClick() {
        sv_media_surface.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mStartX = (int) event.getRawX();
                        mStartY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mEndX = (int) event.getRawX();
                        mEndY = (int) event.getRawY();
                        if (needIntercept()) {
                            //getRawX是触摸位置相对于屏幕的坐标，getX是相对于按钮的坐标
                            wmParams.x = (int) event.getRawX() - sv_media_surface.getMeasuredWidth() / 2;
                            wmParams.y = (int) event.getRawY() - sv_media_surface.getMeasuredHeight() / 2;
                            mWindowManager.updateViewLayout(sv_media_surface, wmParams);
                            return true;
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (needIntercept()) {
                            return true;
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        sv_media_surface.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

                String runningActivity = activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
                Log.i("log", " activity:" + runningActivity);
                if (runningActivity != "activity:app.jiangzhe.com.animation.REC.MainActivity") {
                    Intent intent = new Intent(WindowService.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    wmParams.width = sv_media_surface.getWidth();
                    wmParams.height = sv_media_surface.getHeight();
                    mWindowManager.updateViewLayout(sv_media_surface, wmParams);
                }
            }
        });
    }

    /**
     * 是否拦截
     *
     * @return true:拦截;false:不拦截.
     */
    private boolean needIntercept() {
        if (Math.abs(mStartX - mEndX) > 30 || Math.abs(mStartY - mEndY) > 30) {
            return true;
        }
        return false;
    }

    /**
     * 判断当前应用程序处于前台还是后台
     */
    private boolean isAppAtBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    public class MyBinder extends Binder {
        public boolean isMiUI8 = false;

        public void hidFloatingWindow() {
            removeWindowView();
        }

        public void showFloatingWindow() {
            addWindowView2Window();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        public void hidwindowMiUI8() {
//            miToast.hide();
        }

        public void showFloatingWindowMiUI8() {
//            miToast.show();
        }
    }

    //---------------------------录像
    private void initVideo() {
        start();
    }

    public void start() {
        Toast.makeText(this, "开始.", Toast.LENGTH_LONG).show();
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
        mediaRecorder.setOutputFile("/mnt/sdcard/XinFu/录像" + System.currentTimeMillis() + ".mp4");

        mediaRecorder.setPreviewDisplay(sv_media_surface.getHolder().getSurface());

        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        }
        if (c != null) {
            c.release();
            c = null;
        }
    }
}
