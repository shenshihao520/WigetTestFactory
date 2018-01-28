package com.example.shenshihao520.wigettestfactory.java_class_test.ping_test;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.example.shenshihao520.wigettestfactory.R;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by shenshihao520 on 2018/1/22.
 */

public class PingTest extends Activity {
    private Button button3;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ping);
        button3 = (Button)findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPing();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    void startPing(){
        String delay =new String();
        Process p =null;
        try{
            p = Runtime.getRuntime().exec("/system/bin/ping -c 20 -i 0.2 "+"");

             /*为"错误输出流"单独开一个线程读取之,否则会造成标准输出流的阻塞*/
            Thread t=new Thread(new InputStreamRunnable(p.getErrorStream(),"ErrorStream"));
            t.start();

            BufferedReader buf =new BufferedReader(new InputStreamReader(p.getInputStream()));
            String str =new String();
            while((str=buf.readLine())!=null){
                System.out.println("--"+str);
                if(str.contains("avg")){
                    int i=str.indexOf("/",20);
                    int j=str.indexOf(".", i);
                    System.out.println("延迟:"+str.substring(i+1, j));
                    delay =str.substring(i+1, j);
                }
            }
            if(delay.equals("")){
//                EventBusUtils.post(newNetEvent((long)1000));
            }else{
//                EventBusUtils.post(newNetEvent(Long.parseLong(delay)));
            }
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
    /**读取InputStream的线程*/
    class InputStreamRunnable implements Runnable {
        BufferedReader bReader=null;
        String type=null;
        public InputStreamRunnable(InputStream is, String _type) {
            try {
                bReader=new BufferedReader(new InputStreamReader(new BufferedInputStream(is),"UTF-8"));
                type=_type;
            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }
        public void run() {
            String line;
            int lineNum=0;

            try {
                while((line=bReader.readLine())!=null) {
                    System.out.println("--"+line);
                    lineNum++;
                    //Thread.sleep(200);
                }
                bReader.close();
            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

}
