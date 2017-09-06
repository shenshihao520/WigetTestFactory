package com.example.shenshihao520.wigettestfactory.testActivity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shenshihao520.wigettestfactory.R;
import com.example.shenshihao520.wigettestfactory.utils.CommonUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * 两种截图的实现
 * Created by shenshihao520 on 2017/8/17.
 */

public class ScreenShotActivity extends Activity{
    Button screenshot;
    String sdCardPath;
    private Camera myCamera;// 相机实例


    public static final int REQUEST_MEDIA_PROJECTION = 1000;

    private ImageReader mImageReader;

    private int mScreenWidth,mScreenHeight,mScreenDensity;

    private MediaProjection mMediaProjection;  //系统级的服务

    private VirtualDisplay mVirtualDisplay;     //虚拟展现类

    SurfaceView surfaceView;
    Intent mResultData;
    Button btn_initCamera;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screenshot);
        screenshot = (Button)findViewById(R.id.screenshot);

        surfaceView = (SurfaceView)findViewById(R.id.surfaceview_recordvideo_bendi) ;
        // 获取内置SD卡路径
        sdCardPath = Environment.getExternalStorageDirectory() + "/system/screenshot1.png";
//        requestCapturePermission(ScreenShotActivity.this);
        btn_initCamera = (Button)findViewById(R.id.initCamera) ;
        btn_initCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initCamera(ScreenShotActivity.this,surfaceView);
                myCamera.startPreview();
            }
        });
        screenshot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ScreenShotActivity.this,"6666",Toast.LENGTH_SHORT).show();
//                screenshot11(sdCardPath);


//                startScreenShot();
                myCamera.takePicture(null, null, pictureCallback);

            }
        });
    }

    /**
     * 1.view的方法
     * @param path
     */
     void screenshot11(String path) {
        // 获取屏幕
        View dView = screenshot;
        dView.setDrawingCacheEnabled(true);
        dView.buildDrawingCache();
        Bitmap bmp = dView.getDrawingCache();
        if (bmp != null) {
            try {
                Log.i("666666",path);

                // 图片文件路径
                String filePath = path;

                File file = new File(filePath);
                FileOutputStream os = new FileOutputStream(file);
                bmp.compress(Bitmap.CompressFormat.PNG, 100, os);
                os.flush();
                os.close();
            } catch (Exception e) {
            }
        }
    }
    /**
     * 2.点击事件中触发截屏 使用系统方法的方法
     */
    private void startScreenShot() {


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                //获取当前屏幕内容
                startVirtual();
            }
        }, 5);

        handler.postDelayed(new Runnable() {
            public void run() {
                //生成图片保存到本地
                startCapture();

            }
        }, 30);
    }


    /**
     * 获取系统截屏权限
     * @param activity
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void requestCapturePermission(Activity activity) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            //5.0 之后才允许使用屏幕截图

            return;
        }

        MediaProjectionManager mediaProjectionManager = (MediaProjectionManager) activity.getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        activity.startActivityForResult(
                mediaProjectionManager.createScreenCaptureIntent(),
                REQUEST_MEDIA_PROJECTION);
    }


    /**
     * 创建ImageReader实例
     */
    public void createImageReader(Intent data) {

        mResultData = data;
        if(mImageReader == null)
        {
            DisplayMetrics metric = new DisplayMetrics();
            this.getWindowManager().getDefaultDisplay().getMetrics(metric);
            mScreenWidth = metric.widthPixels;  // 屏幕宽度（像素）
            mScreenHeight = metric.heightPixels;  // 屏幕高度（像素）
            mScreenDensity  = metric.densityDpi;  // 屏幕密度（0.75 / 1.0 / 1.5）

            mImageReader = ImageReader.newInstance(mScreenWidth, mScreenHeight, PixelFormat.RGBA_8888, 2);
        }
    }



    public void startVirtual() {
        if (mMediaProjection != null) {
            virtualDisplay();
        } else {
            setUpMediaProjection();
            virtualDisplay();
        }
    }
    private MediaProjectionManager getMediaProjectionManager() {

        return (MediaProjectionManager) this.getSystemService(Context.MEDIA_PROJECTION_SERVICE);
    }

    public void setUpMediaProjection() {
        if (mResultData == null) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            startActivity(intent);
        } else {
            //mResultData是在Activity中用户授权后返回的结果
            mMediaProjection = getMediaProjectionManager().getMediaProjection(Activity.RESULT_OK, mResultData);
        }
    }

    private void virtualDisplay() {
        mVirtualDisplay = mMediaProjection.createVirtualDisplay("screen-mirror",
                mScreenWidth, mScreenHeight, mScreenDensity, DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                mImageReader.getSurface(), null, null);
    }


    private void startCapture() {

        final Image image = mImageReader.acquireLatestImage();

        if (image == null) {
            startScreenShot();
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int width = image.getWidth();
                    int height = image.getHeight();
                    final Image.Plane[] planes = image.getPlanes();
                    final ByteBuffer buffer = planes[0].getBuffer();
                    //每个像素的间距
                    int pixelStride = planes[0].getPixelStride();
                    //总的间距
                    int rowStride = planes[0].getRowStride();
                    int rowPadding = rowStride - pixelStride * width;
                    Bitmap bitmap = Bitmap.createBitmap(width + rowPadding / pixelStride, height, Bitmap.Config.ARGB_8888);
                    bitmap.copyPixelsFromBuffer(buffer);
                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height);


                    // 获取内置SD卡路径
                    String sdCardPath = Environment.getExternalStorageDirectory().getPath();
                    // 图片文件路径
                    String filePath = sdCardPath + "/system/screenshot.png";

                    File f = new File(filePath);
                    if (f.exists()) {
                        f.delete();
                    }
                    try {
                        FileOutputStream out = new FileOutputStream(f);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
                        out.flush();
                        out.close();
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                    image.close();
                }
            }).start();

        }
    }

    private void tearDownMediaProjection() {
        if (mMediaProjection != null) {
            mMediaProjection.stop();
            mMediaProjection = null;
        }
    }

    private void stopVirtual() {
        if (mVirtualDisplay == null) {
            return;
        }
        mVirtualDisplay.release();
        mVirtualDisplay = null;
    }


//    public void onDestroy() {
//        // to remove mFloatLayout from windowManager
//
//        stopVirtual();
//
//        tearDownMediaProjection();
//    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_MEDIA_PROJECTION:

                if (resultCode == RESULT_OK && data != null) {
//                    FloatWindowsService.setResultData(data);
//                    startService(new Intent(getApplicationContext(), FloatWindowsService.class));
                    createImageReader(data);    //创建ImageReader实例   获取完权限后裔才能在这里拿到ImageReader实例
                }

                break;
        }
    }





    /**
     * 3. 通过调用摄像头进行拍照截屏  可选择配合surfaceView
     * 初始化Camera设置   这东西需要一个surfaceView
     */
    public void initCamera(Activity activity, SurfaceView surfaceView) {
        if (myCamera == null ) {
            myCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
        }
        int LARGEST_WIDTH = activity.getWindowManager().getDefaultDisplay().getWidth();
        int LARGEST_HEIGHT = activity.getWindowManager().getDefaultDisplay().getHeight();

        try {
            Camera.Parameters myParameters = myCamera.getParameters();

            surfaceView.setLayoutParams(new LinearLayout.LayoutParams(LARGEST_WIDTH, LARGEST_HEIGHT));
            myCamera.setDisplayOrientation(90);
            myCamera.setParameters(myParameters);
            myCamera.setPreviewDisplay(surfaceView.getHolder());
            myCamera.startPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }
    } /**
     * 截屏后的最主要的返回
     */
    private Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {

                @Override
                public void onPictureTaken(final byte[] data, final Camera camera) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            UUID uuid = UUID.randomUUID();
                            Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                            String fileName = uuid + ".jpg";
                            String filePath = Environment.getExternalStorageDirectory()
                                    .toString()
                                    + File.separator
                                    + fileName;
                            save(bmp, filePath);
                            int degree = CommonUtils.readPictureDegree(filePath);


                        }
                    }).start();
                }
            };
    /**
     * 照片保存
     */
    private void save(Bitmap bitmap, String filePath) {
        File file = new File(filePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs(); // 创建文件夹
        }
        try {
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            int degree = CommonUtils.readPictureDegree(filePath);
            Bitmap bitmapRotaing = BitmapFactory.decodeFile(filePath);
            bitmapRotaing = CommonUtils.rotaingImageView(degree, bitmapRotaing);
            bos = new BufferedOutputStream(
                    new FileOutputStream(file));
            bitmapRotaing.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
        }
    }

}
