package com.example.shenshihao520.wigettestfactory.live;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.VirtualDisplay;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.example.shenshihao520.wigettestfactory.R;

import java.io.File;

/**
 * Created by shenshihao520 on 2017/7/28.
 */

public class Camera2Service extends Service{

//    private static final int CAST_PERMISSION_CODE = 22;
//    private DisplayMetrics mDisplayMetrics;
//    private MediaProjection mMediaProjection;
//    private VirtualDisplay mVirtualDisplay;
//    private MediaRecorder mMediaRecorder;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        mMediaRecorder = new MediaRecorder();
//
//        mProjectionManager = (MediaProjectionManager) getSystemService
//                (Context.MEDIA_PROJECTION_SERVICE);
//
//        getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
//
//        prepareRecording();
//    }
//
//    private void startRecording() {
//        // If mMediaProjection is null that means we didn't get a context, lets ask the user
//        if (mMediaProjection == null) {
//            // This asks for user permissions to capture the screen
//            startActivityForResult(mProjectionManager.createScreenCaptureIntent(), CAST_PERMISSION_CODE);
//            return;
//        }
//        mVirtualDisplay = createVirtualDisplay();
//        mMediaRecorder.start();
//    }
//
//    private void stopRecording() {
//        if (mMediaRecorder != null) {
//            mMediaRecorder.stop();
//            mMediaRecorder.reset();
//        }
//        if (mVirtualDisplay != null) {
//            mVirtualDisplay.release();
//        }
//        if (mMediaProjection != null) {
//            mMediaProjection.stop();
//        }
//        prepareRecording();
//    }
//
//    public String getCurSysDate() {
//        return new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
//    }
//
//    private void prepareRecording() {
//        try {
//            mMediaRecorder.prepare();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return;
//        }
//
//        final String directory = Environment.getExternalStorageDirectory() + File.separator + "Recordings";
//        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
//            Toast.makeText(this, "Failed to get External Storage", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        final File folder = new File(directory);
//        boolean success = true;
//        if (!folder.exists()) {
//            success = folder.mkdir();
//        }
//        String filePath;
//        if (success) {
//            String videoName = ("capture_" + getCurSysDate() + ".mp4");
//            filePath = directory + File.separator + videoName;
//        } else {
//            Toast.makeText(this, "Failed to create Recordings directory", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        int width = mDisplayMetrics.widthPixels;
//        int height = mDisplayMetrics.heightPixels;
//
//        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
//        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
//        mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
//        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
//        mMediaRecorder.setVideoEncodingBitRate(512 * 1000);
//        mMediaRecorder.setVideoFrameRate(30);
//        mMediaRecorder.setVideoSize(width, height);
//        mMediaRecorder.setOutputFile(filePath);
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode != CAST_PERMISSION_CODE) {
//            // Where did we get this request from ? -_-
//            Log.w(TAG, "Unknown request code: " + requestCode);
//            return;
//        }
//        if (resultCode != RESULT_OK) {
//            Toast.makeText(this, "Screen Cast Permission Denied :(", Toast.LENGTH_SHORT).show();
//            return;
//        }
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
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
