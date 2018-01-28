package com.example.shenshihao520.wigettestfactory.utils;

import android.os.Handler;
import android.os.Message;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * android ping的操作
 * Created by shenshihao520 on 2018/1/23.
 */

public class PingUtils   {
    //获取ping的延时
    public static final int PING_STREAM = 2100;

    //ping是发生了错误
    public static final int ERROR_STREAM = 2200;

    //io 错误
    public static final int IO_ERROR = 2300;
    public void startPing(Handler handler){

        Process p =null;
        try{
            p = Runtime.getRuntime().exec("/system/bin/ping -c 20 "+"117.141.138.101");

             /*为"错误输出流"单独开一个线程读取之,否则会造成标准输出流的阻塞*/
            Thread errorThread=new Thread(new InputStreamRunnable(p.getErrorStream(),"ErrorStream",handler));
            errorThread.start();

            Thread pingThread=new Thread(new InputStreamRunnable(p.getInputStream(),"PingStream",handler));
            pingThread.start();

        }catch(IOException e) {
            e.printStackTrace();
            handler.sendEmptyMessage(IO_ERROR);

        }
    }

    /**读取InputStream的线程*/
    class InputStreamRunnable implements Runnable {
        BufferedReader bReader=null;
        String type=null;
        Handler handler;
        public InputStreamRunnable(InputStream is, String _type, Handler _handler) {
            try {
                bReader=new BufferedReader(new InputStreamReader(new BufferedInputStream(is),"UTF-8"));
                type=_type;
                handler = _handler;
            }
            catch(IOException e) {
                e.printStackTrace();
                handler.sendEmptyMessage(IO_ERROR);

            }
        }
        public void run() {
            String line;
            String delay = "";
            String errorMsg = "";
            Message message = new Message();
            try {
                while((line=bReader.readLine())!=null) {
                    System.out.println("--"+line);
                    if(line.contains("avg")){
                        int i=line.indexOf("/",20);
                        int j=line.indexOf(".", i);
                        System.out.println("延迟:"+line.substring(i+1, j));
                        delay =line.substring(i+1, j);
                    }
                }
                bReader.close();
            }
            catch(IOException e) {
                e.printStackTrace();
                handler.sendEmptyMessage(IO_ERROR);
            }
            if(type.equals("PingStream")){
                message.what = PING_STREAM;
                message.obj = delay;
                handler.sendMessage(message);
            }
            else{
                message.what =  ERROR_STREAM;
                message.obj = errorMsg;
                handler.sendMessage(message);
            }

        }
    }

}
