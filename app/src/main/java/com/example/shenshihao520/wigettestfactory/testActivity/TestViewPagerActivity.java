package com.example.shenshihao520.wigettestfactory.testActivity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shenshihao520.wigettestfactory.R;
import com.example.shenshihao520.wigettestfactory.animation.viewpager.DepthPageTransformer;
import com.example.shenshihao520.wigettestfactory.animation.viewpager.Test3DPageTransformer;
import com.example.shenshihao520.wigettestfactory.animation.viewpager.ZoomOutPageTransformer;
import com.example.shenshihao520.wigettestfactory.testData.TestBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewPager实验
 * Created by shenshihao520 on 2017/7/4.
 */

public class TestViewPagerActivity extends Activity {
    private ViewPager mViewPager;

    private ViewPagerAdapter mPagerAdapter;

    private List<View> mListViews = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_view_pager);
        initView();
        initData();
    }


    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        initVIewPager();

    }

    private void initVIewPager() {
        LayoutInflater inflater = getLayoutInflater();
        View view1 = inflater.inflate(R.layout.item_view_pager, null);
        ((ImageView) view1.findViewById(R.id.tv)).setImageResource(R.mipmap.firstpage_banner);
        View view2 = inflater.inflate(R.layout.item_view_pager, null);
        ((ImageView) view2.findViewById(R.id.tv)).setImageResource(R.mipmap.firstpage_banner1);
        View view3 = inflater.inflate(R.layout.item_view_pager, null);
        ((ImageView) view3.findViewById(R.id.tv)).setImageResource(R.mipmap.firstpage_banner);

        mListViews.add(view1);
        mListViews.add(view2);
        mListViews.add(view3);

        mPagerAdapter = new ViewPagerAdapter(mListViews);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setPageTransformer(true,new Test3DPageTransformer());

    }

    private void initData() {

    }

    class ViewPagerAdapter extends PagerAdapter {
        private List<View> mViews;

        public ViewPagerAdapter(List<View> views) {
            super();
            mViews = views;
        }

        @Override
        public int getCount() {
            return mViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup viewGroup, int position, Object object) {
            viewGroup.removeView(mViews.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup viewGroup, int position) {

            viewGroup.addView(mViews.get(position), 0);
            return mViews.get(position);
        }
    }

}
