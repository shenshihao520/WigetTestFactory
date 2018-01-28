package com.example.shenshihao520.wigettestfactory.java_class_test.network_situation;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.shenshihao520.wigettestfactory.R;

/**
 * Created by shenshihao520 on 2018/1/22.
 */

public class NetworkSituationTest extends Activity implements ConnectionClassManager.ConnectionClassStateChangeListener{
    private Button button3;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ping);
        button3 = (Button)findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeviceBandwidthSampler.getInstance().startSampling();

                DeviceBandwidthSampler.getInstance().stopSampling();

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ConnectionClassManager.getInstance().register(NetworkSituationTest.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ConnectionClassManager.getInstance().remove(NetworkSituationTest.this);
    }

    @Override
    public void onBandwidthStateChange(ConnectionQuality bandwidthState) {
        Log.e("onBandwidthStateChange", bandwidthState.toString());
    }
}
