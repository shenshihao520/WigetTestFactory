package com.example.shenshihao520.wigettestfactory.eventbus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.shenshihao520.wigettestfactory.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by shenshihao520 on 2017/8/1.
 */

public class SubscriberActivity extends Activity implements View.OnClickListener{
    Button subscribe;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventbus_subscriber);
        EventBus.getDefault().register(this);

        subscribe = (Button)findViewById(R.id.subscribe);
        subscribe.setOnClickListener(this);

    }
    @Subscribe
    public void onMessageEvent(MessageEvent event){
        Toast.makeText(this, event.message, Toast.LENGTH_SHORT).show();
    }

    // This method will be called when a SomeOtherEvent is posted
    @Subscribe
    public void handleSomethingElse(MessageEvent event){
        doSomethingWith(event);
    }

    private void doSomethingWith(MessageEvent event) {
        subscribe.setText(event.message);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

    public void onClick(View v) {
        switch (v.getId())
        {

            case R.id.subscribe:
//                EventBus.getDefault().post(new MessageEvent("Hello everyone!"));
                Intent intent = new Intent();
                intent.setClass(SubscriberActivity.this,PublisherActivity.class);
                startActivity(intent);
                break;
        }
    }
}
