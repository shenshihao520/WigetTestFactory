package com.example.shenshihao520.wigettestfactory.widget.banner_text;

import java.util.List;


import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shenshihao520.wigettestfactory.R;

/**
 * 文字轮播图
 */
public class BannerText extends LinearLayout {
	 
	  private TextView mBannerTV1;
	  private TextView mBannerTV2;
	  private Handler handler;
	  private boolean isShow;
	  private int startY1, endY1, startY2, endY2;
	  private Runnable runnable;
	  private List<String> list;
	  private int position = 0;
	  private int offsetY = 100;
	 
	 
	  public BannerText(Context context) {
	    this(context, null);
	  }
	 
	  public BannerText(Context context, AttributeSet attrs) {
	    this(context, attrs, 0);
	  }
	 
	  public BannerText(Context context, AttributeSet attrs, int defStyleAttr) {
	    super(context, attrs, defStyleAttr);
	    View view = LayoutInflater.from(context).inflate(R.layout.view_scroll_banner, this);
	    mBannerTV1 = (TextView) view.findViewById(R.id.tv_banner1);
	    mBannerTV2 = (TextView) view.findViewById(R.id.tv_banner2);
	 
	    handler = new Handler();
	 
	    runnable = new Runnable() {
	      @Override
	      public void run() {
	        isShow = !isShow;
	 
	        if (position == list.size())
	          position = 0;
	 
	        if (isShow) {
	          mBannerTV1.setText(list.get(position++));
	        } else {
	          mBannerTV2.setText(list.get(position++));
	        }
	 
	        startY1 = isShow ? 0 : offsetY;
	        endY1 = isShow ? -offsetY : 0;
	 
	 
	        ObjectAnimator.ofFloat(mBannerTV1, "translationY", startY1, endY1).setDuration(300).start();
	 
	        startY2 = isShow ? offsetY : 0;
	        endY2 = isShow ? 0 : -offsetY;
	        ObjectAnimator.ofFloat(mBannerTV2, "translationY", startY2, endY2).setDuration(300).start();
	 
	        handler.postDelayed(runnable, 5000);
	      }
	    };
	 
	  }
	 
	 
	  public List<String> getList() {
	    return list;
	  }
	 
	  public void setList(List<String> list) {
	    this.list = list;
	  }
	 
	  public void startScroll() {
	    handler.post(runnable);
	  }
	 
	  public void stopScroll() {
	    handler.removeCallbacks(runnable);
	  }
	}