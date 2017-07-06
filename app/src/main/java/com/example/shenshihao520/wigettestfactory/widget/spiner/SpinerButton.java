package com.example.shenshihao520.wigettestfactory.widget.spiner;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.shenshihao520.wigettestfactory.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenshihao520 on 2017/6/17.
 * 下拉菜单按钮
 */

public class SpinerButton extends RelativeLayout implements IOnItemSelectListener {
    private TextView mTView;
    private TextView mTitleView;
    private ImageView mBtnDropDown;
    private SpinerPopWindow mSpinerPopWindow;
    private List<String> nameList = new ArrayList<>();
    Context mContext;
    boolean isShowPop = false;

    public SpinerButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.SpinerButton);

        try {
            int buttonLayout = attributes.getResourceId(R.styleable.SpinerButton_spiner_button_layout, R.layout.spiner_button_layout);

            LayoutInflater.from(context).inflate(buttonLayout, this, true);

            mTView = (TextView) findViewById(R.id.tv_value);
            mBtnDropDown = (ImageView) findViewById(R.id.bt_dropdown);
            mTitleView  = (TextView)findViewById(R.id.tv_valueTitle);

            //设置背景
            int background = attributes.getResourceId(R.styleable.SpinerButton_background_color, R.drawable.xf_third_circle_ego);
            setBackgroundResource(background);

            //设置右边箭头是否可见
            boolean rightButtonVisible = attributes.getBoolean(R.styleable.SpinerButton_right_image_visible, true);
            if (rightButtonVisible) {
                mBtnDropDown.setVisibility(View.VISIBLE);
            } else {
                mBtnDropDown.setVisibility(View.INVISIBLE);
            }

            //设置显示的文字
            String valueText = attributes.getString(R.styleable.SpinerButton_value_text);
            if (!TextUtils.isEmpty(valueText)) {
                mTView.setText(valueText);
                int textColor = attributes.getColor(R.styleable.SpinerButton_value_text_color, getResources().getColor(R.color.xf_third_black));

                mTView.setTextColor(textColor);
            } else {
                mTView.setText("");
            }

            String valueTextTitle = attributes.getString(R.styleable.SpinerButton_value_text_title);
            if (!TextUtils.isEmpty(valueTextTitle)) {
                mTitleView.setText(valueTextTitle);
            }
            //设置右边箭头
            int rightArrow = attributes.getResourceId(R.styleable.SpinerButton_right_image_drawable, R.drawable.xfthirddownarraw);
            mBtnDropDown.setBackgroundResource(rightArrow);

            //设置下拉布局
            int window_layout = attributes.getResourceId(R.styleable.SpinerButton_spiner_window_layout, R.layout.spiner_window_layout);

            //设置下拉布局item
            int item_layout = attributes.getResourceId(R.styleable.SpinerButton_spiner_item_layout, R.layout.spiner_item_layout);

            mSpinerPopWindow = new SpinerPopWindow(mContext, window_layout, item_layout);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showSpinWindow();
                mBtnDropDown.setBackgroundResource(R.drawable.xfthirduparrow);
            }
        });
        mSpinerPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mBtnDropDown.setBackgroundResource(R.drawable.xfthirddownarraw);
            }
        });
        }
        finally {
            attributes.recycle();
        }

    }

    public void setListData(List<String> list) {
        nameList = list;

        mSpinerPopWindow.refreshData(nameList, 0);
        mSpinerPopWindow.setItemListener(this);
    }

    private void showSpinWindow() {

        mSpinerPopWindow.setWidth(getWidth());
        mSpinerPopWindow.showAsDropDown(this);
    }

    private void setHero(int pos) {
        if (pos >= 0 && pos <= nameList.size()) {
            String value = nameList.get(pos);

            mTView.setText(value);
        }
    }

    @Override
    public void onItemClick(int pos) {
        setHero(pos);
    }
}
