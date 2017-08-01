package com.example.shenshihao520.wigettestfactory.download;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;
import android.webkit.MimeTypeMap;

import java.io.File;

/**
 * Created by shenshihao520 on 2017/7/27.
 */

public class DownloadPDFService extends Service implements DownloadPDFCallback{
    static DownloadManager downloadManager;
    Context mContext;
    static Long mTaskId;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        IBinder result = null;
        if ( null == result )
            result = new ServiceBinder() ;
        return result;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {

        downloadAPK("http://10.2.100.83:8080/source_app/riskinfo/other/Spring.pdf","Spring.pdf");

        return super.onStartCommand(intent, flags, startId);
    }

    //使用系统下载器下载
    private void downloadAPK(String versionUrl, String versionName) {
        //创建下载任务
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(versionUrl));
        request.setAllowedOverRoaming(false);//漫游网络是否可以下载

        //设置文件类型，可以在下载结束后自动打开该文件
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        String mimeString = mimeTypeMap.getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(versionUrl));
        request.setMimeType(mimeString);

        //在通知栏中显示，默认就是显示的
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setVisibleInDownloadsUi(true);

        //sdcard的目录下的download文件夹，必须设置
        request.setDestinationInExternalPublicDir("/download/", versionName);
        //request.setDestinationInExternalFilesDir(),也可以自己制定下载路径

        //将下载请求加入下载队列
        downloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        //加入下载队列后会给该任务返回一个long型的id，
        //通过该id可以取消任务，重启任务等等，看上面源码中框起来的方法
        mTaskId = downloadManager.enqueue(request);


        //注册广播接收者，监听下载状态
        mContext.registerReceiver(receiver,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }
    //广播接受者，接收下载状态
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            checkDownloadStatus();//检查下载状态
        }
    };
    //检查下载状态
    private static void checkDownloadStatus() {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(11111);//筛选下载任务，传入任务ID，可变参数
        Cursor c = downloadManager.query(query);

        if (c.moveToFirst()) {
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status) {
                case DownloadManager.STATUS_PAUSED:
                    Log.i("shen",">>>下载暂停");
                case DownloadManager.STATUS_PENDING:
                    Log.i("shen",">>>下载延迟");
                case DownloadManager.STATUS_RUNNING:
                    Log.i("shen",">>>正在下载");
                    break;
                case DownloadManager.STATUS_SUCCESSFUL:
                    Log.i("shen",">>>下载完成");
                    //下载完成安装APK
                    //downloadPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator + versionName;
                    //installAPK(new File(downloadPath));
                    break;
                case DownloadManager.STATUS_FAILED:
                    Log.i("shen",">>>下载失败");
                    break;
            }
        }
    }

    @Override
    public void getPDFStatus() {
        Log.i("shen",">>>下载失败11");
    }

    //下载查看状态广播
    public static class DownloadBroadcastReceiver extends  BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            checkDownloadStatus();
        }
    }

    public class ServiceBinder extends Binder {

        public DownloadPDFService getService() {
            return DownloadPDFService.this;
        }
    }

}
