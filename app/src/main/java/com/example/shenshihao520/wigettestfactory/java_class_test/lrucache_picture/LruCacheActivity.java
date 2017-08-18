package com.example.shenshihao520.wigettestfactory.java_class_test.lrucache_picture;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.LruCache;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.shenshihao520.wigettestfactory.utils.CommonUtils;

import static android.R.attr.bitmap;

/**
 * Created by shenshihao520 on 2017/8/8.
 */

public class LruCacheActivity extends AppCompatActivity{
    private LruCache<String, Bitmap> mMemoryCache;
    private Bitmap bitmap ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initCache();
    }

    /**
     * 退出时一定要记住执行 回收内存操作
     */
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        bitmap.recycle();
        mMemoryCache = null;

    }

    void initCache() {
        // 获取到可用内存的最大值，使用内存超出这个值会引起OutOfMemory异常。
        // LruCache通过构造函数传入缓存值，以KB为单位。
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        // 使用最大可用内存值的1/8作为缓存的大小。
        int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize)
//        {
//            @Override
//            protected int sizeOf(String key, Bitmap bitmap) {
//                // 重写此方法来衡量每张图片的大小，默认返回图片数量。
//                return bitmap.getByteCount() / 1024;
//            }
//        }
        ;
    }


    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }


    /**
     * 加载图片方法
     * @param resId  id的方式
     * @param imageView
     * @param height
     * @param width
     */
    public void loadBitmap(int resId, ImageView imageView, int height, int width) {
        final String imageKey = String.valueOf(resId);
        bitmap = getBitmapFromMemCache(imageKey);
        if (bitmap != null) {
            if (height == 100) {
                imageView.setBackground(new BitmapDrawable(bitmap));
            } else {
                imageView.setImageBitmap(bitmap);
            }
        } else {
            // imageView.setImageResource(R.drawable.image_placeholder);
//			BitmapWorkerTask task = new BitmapWorkerTask(imageView, height, width);
//			task.execute(resId);
            bitmap = CommonUtils.convertToBitmap(getResources(), resId, height, width);
            addBitmapToMemoryCache(String.valueOf(resId), bitmap);
            if (height != 100) {
                imageView.setImageBitmap(bitmap);
            } else {
                imageView.setBackground(new BitmapDrawable(bitmap));
            }
        }
    }

    /**
     *  加载图片的方法
     * @param path  文件路径的方式
     * @param imageView
     * @param height
     * @param width
     */
    public void loadBitmap(String path, ImageView imageView, int height, int width) {
        final String imageKey = path;
        bitmap = getBitmapFromMemCache(imageKey);
        if (bitmap != null) {
            if (height == 100) {
                imageView.setBackground(new BitmapDrawable(bitmap));
            } else {
                imageView.setImageBitmap(bitmap);
            }
        } else {
            // imageView.setImageResource(R.drawable.image_placeholder);
//			BitmapWorkerTaskPath task = new BitmapWorkerTaskPath(imageView, height, width);
//			task.execute(path);

            bitmap = CommonUtils.convertToBitmap(path, height, width);
            addBitmapToMemoryCache(path, bitmap);
            if (height != 100) {
                imageView.setImageBitmap(bitmap);
            } else {
                imageView.setBackground(new BitmapDrawable(bitmap));
            }
        }
    }

}
