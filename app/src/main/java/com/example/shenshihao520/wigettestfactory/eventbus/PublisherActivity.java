package com.example.shenshihao520.wigettestfactory.eventbus;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.shenshihao520.wigettestfactory.R;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by shenshihao520 on 2017/8/1.
 */

public class PublisherActivity extends Activity implements OnClickListener{
    Button publish;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventbus_publisher);
        publish = (Button)findViewById(R.id.publish);
        publish.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.publish:
                EventBus.getDefault().post(new MessageEvent("Hello everyone!"));

                break;

        }
    }
}
