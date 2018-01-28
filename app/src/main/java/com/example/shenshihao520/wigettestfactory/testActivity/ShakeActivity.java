package com.example.shenshihao520.wigettestfactory.testActivity;

/**
 * Created by shenshihao520 on 2017/12/26.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.shenshihao520.wigettestfactory.R;
import com.example.shenshihao520.wigettestfactory.utils.VibratorHelper;

public class ShakeActivity extends Activity {
    private SensorManager sensorManager;
    private SensorEventListener shakeListener;
    private AlertDialog.Builder dialogBuilder;

    private boolean isRefresh = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        shakeListener = new ShakeSensorListener();

        dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setPositiveButton("确定", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                isRefresh = false;
                dialog.cancel();
            }
        }).setMessage("摇到了一个漂亮妹子！").create();
    }

    @Override
    protected void onResume() {
        sensorManager.registerListener(shakeListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_FASTEST);
        super.onResume();
    }

    @Override
    protected void onPause() {
        // acitivity后台时取消监听
        sensorManager.unregisterListener(shakeListener);

        super.onPause();
    }

    private class ShakeSensorListener implements SensorEventListener {
        private static final int ACCELERATE_VALUE = 39 ;

        @Override
        public void onSensorChanged(SensorEvent event) {

//          Log.e("zhengyi.wzy", "type is :" + event.sensor.getType());

            // 判断是否处于刷新状态(例如微信中的查找附近人)
            if (isRefresh) {
                return;
            }

            float[] values = event.values;

            /**
             * 一般在这三个方向的重力加速度达到20就达到了摇晃手机的状态 x : x轴方向的重力加速度，向右为正 y :
             * y轴方向的重力加速度，向前为正 z : z轴方向的重力加速度，向上为正
             */
            float x = Math.abs(values[0]);
            float y = Math.abs(values[1]);
            float z = Math.abs(values[2]);

            Log.e("zhengyi.wzy", "x is :" + x + " y is :" + y + " z is :" + z);
            if ( x >= ACCELERATE_VALUE && y >= ACCELERATE_VALUE ) {
                Toast.makeText(ShakeActivity.this, "accelerate speed :" + (x >= ACCELERATE_VALUE ? x : y >= ACCELERATE_VALUE ? y : z), Toast.LENGTH_SHORT).show();

                VibratorHelper.Vibrate(ShakeActivity.this, 300);
                isRefresh = true;
                dialogBuilder.show();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // TODO Auto-generated method stub
        }

    }

}
//private class ShakeSensorListener implements SensorEventListener {
//    private static final int ACCELERATE_VALUE = 30 ;
//
//    @Override
//    public void onSensorChanged(SensorEvent event) {
//
////          Log.e("zhengyi.wzy", "type is :" + event.sensor.getType());
//
//        // 判断是否处于刷新状态(例如微信中的查找附近人)
//        if (isRefresh) {
//            return;
//        }
//
//        float[] values = event.values;
//
//        /**
//         * 一般在这三个方向的重力加速度达到20就达到了摇晃手机的状态 x : x轴方向的重力加速度，向右为正 y :
//         * y轴方向的重力加速度，向前为正 z : z轴方向的重力加速度，向上为正
//         */
//        float x = Math.abs(values[0]);
//        float y = Math.abs(values[1]);
//        float z = Math.abs(values[2]);
//
//        Log.e("zhengyi.wzy", "x is :" + x + " y is :" + y + " z is :" + z);
//        if ( (x >= ACCELERATE_VALUE && y >= ACCELERATE_VALUE) || (x <= -ACCELERATE_VALUE && y <= -ACCELERATE_VALUE) ) {
//            count++;
//            Toast.makeText(MainActivity.this, "accelerate speed :" + (x >= ACCELERATE_VALUE ? x : y >= ACCELERATE_VALUE ? y : z), Toast.LENGTH_SHORT).show();
//
//            if(count == 5){
//                VibratorHelper.Vibrate(MainActivity.this, 300);
//                isRefresh = true;
//                dialogBuilder.show();
//            }
//
//        }
//    }
//
//    @Override
//    public void onAccuracyChanged(Sensor sensor, int accuracy) {
//        // TODO Auto-generated method stub
//    }
//
//}