package com.example.shenshihao520.wigettestfactory.java_class_test.parcelable_serializable;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.shenshihao520.wigettestfactory.R;

/**
 * Created by shenshihao520 on 2017/8/2.
 */

public class MainActivity extends AppCompatActivity implements OnClickListener{
    Button btn_send_S;
    Button btn_send_P;
    Button btn_send_test;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serializable_main);
        initView();
    }

    private void initView() {
        btn_send_S = (Button)findViewById(R.id.send_S);
        btn_send_S.setOnClickListener(this);
        btn_send_P = (Button)findViewById(R.id.send_P);
        btn_send_P.setOnClickListener(this);
        btn_send_test = (Button)findViewById(R.id.send_test);
        btn_send_test.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.send_S:
                /**
                 *  使用Intent Serializable 传序列化 obj
                 */
                Intent open = new Intent(MainActivity.this,SecondActivity.class);

                Person person = new Person();
                person.setName("一去二三里");
                person.setAge(18);
                // 传输方式一，intent直接调用putExtra
                // public Intent putExtra(String name, Serializable value)
                open.putExtra("put_ser_test", person);
                // 传输方式二，intent利用putExtras（注意s）传入bundle
                /**
                 Bundle bundle = new Bundle();
                 bundle.putSerializable("bundle_ser",person);
                 open.putExtras(bundle);
                 */
                startActivity(open);
                break;
            case R.id.send_P:
                /**
                 *  使用Intent Parcelable 传序列化 obj
                 */
                Intent mTvOpenThird = new Intent(MainActivity.this,SecondActivity.class);
                Pen tranPen = new Pen();
                tranPen.setColor("big red");
                tranPen.setSize(98);
                // public Intent putExtra(String name, Parcelable value)
                mTvOpenThird.putExtra("parcel_test",tranPen);
                startActivity(mTvOpenThird);

                break;
            case R.id.send_test:
                Intent open2 = new Intent(MainActivity.this,SecondActivity.class);

                Diao diao = new Diao();
                Callback callback = new CallbackImplement();
                diao.setCallback(callback);
                CustomOutputStream customOutputStream = new CustomOutputStream(Environment.getExternalStorageDirectory()+"/PDF/banner",diao);

                startActivity(open2);
                break;
        }
    }
}
