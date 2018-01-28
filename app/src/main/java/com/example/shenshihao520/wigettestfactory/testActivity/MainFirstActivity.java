package com.example.shenshihao520.wigettestfactory.testActivity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;

import com.example.shenshihao520.wigettestfactory.R;


public class MainFirstActivity extends Activity  {

	private TextView tv_with,tv_high,tv_smallest_with;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main1);
		getEcho();
		
	}
	

	private void getEcho(){
		Configuration config = getResources().getConfiguration();
		int smallestScreenWidth = config.smallestScreenWidthDp;
		int ScreenWidth=config.screenWidthDp;
		int ScreenHeigh=config.screenHeightDp;
		tv_with=(TextView)findViewById(R.id.tv_with);
		tv_smallest_with=(TextView)findViewById(R.id.tv_smallest_with);
		tv_high=(TextView)findViewById(R.id.tv_high);
		tv_with.setText(String.valueOf(ScreenWidth));
		tv_smallest_with.setText(String.valueOf(smallestScreenWidth));
		tv_high.setText(String.valueOf(ScreenHeigh));
	}
	
}
