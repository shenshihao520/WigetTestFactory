package com.example.shenshihao520.wigettestfactory.widget.spiner;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;


import com.example.shenshihao520.wigettestfactory.R;

import java.util.List;

/**
 * 下拉菜单的列表
 */
public class SpinerPopWindow extends PopupWindow implements OnItemClickListener{

	private Context mContext;
	private ListView mListView;
	private SpinerAdapter mAdapter;
	private IOnItemSelectListener mItemSelectListener;
	int window_layout;
	int item_layout;
	
	public SpinerPopWindow(Context context, int window_layout, int item_layout)
	{
		super(context);
		mContext = context;
		this.window_layout = window_layout;
		this.item_layout = item_layout;
		init();
	}
	
	
	public void setItemListener(IOnItemSelectListener listener){
		mItemSelectListener = listener;
	}

	
	private void init()
	{
		View view = LayoutInflater.from(mContext).inflate(window_layout, null);
		setContentView(view);		
		setWidth(LayoutParams.WRAP_CONTENT);
		setHeight(LayoutParams.WRAP_CONTENT);
		
		setFocusable(true);
    	ColorDrawable dw = new ColorDrawable(0x00);
		setBackgroundDrawable(dw);
	
		
		mListView = (ListView) view.findViewById(R.id.listview);
		

		mAdapter = new SpinerAdapter(mContext,item_layout);
		mListView.setAdapter(mAdapter);	
		mListView.setOnItemClickListener(this);

	}
	
	
	public void refreshData(List<String> list, int selIndex)
	{
		if (list != null && selIndex  != -1)
		{
			mAdapter.refreshData(list, selIndex);
		}
	}


	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int pos, long arg3) {
		dismiss();
		if (mItemSelectListener != null){
			mItemSelectListener.onItemClick(pos);
		}
	}


	
}
