package com.example.shenshihao520.wigettestfactory.java_class_test.loader;

import android.app.ApplicationErrorReport;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.shenshihao520.wigettestfactory.R;

import java.util.List;

/**
 * Loader功能练习
 * Created by shenshihao520 on 2017/8/22.
 */

public class LoaderActivity extends AppCompatActivity{
    public static final int LOADER_ID = 1000;
    DataLoader dataLoader ;
    DummyItem dummyItem;
    Button button ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader);
        initView();

        initData();
    }


    private void initView() {
        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                People p = new People();
                p.setName("wanger");
                dummyItem.save(p,dataLoader);
                Log.i("shen","数据为save");


            }
        });
    }
    private void initData() {
        LoaderManager loaderManager = getSupportLoaderManager();

        dummyItem = new DummyItem();
        dataLoader = new DataLoader(this,dummyItem);

        loaderManager.initLoader(LOADER_ID, null, new DataLoaderCallback());

//        dataLoader.loadInBackground()
    }

    class DataLoaderCallback implements LoaderManager.LoaderCallbacks<List<People>> {
        @Override
        public Loader<List<People>> onCreateLoader(int id, Bundle args) {
            Log.i("shen","数据为onCreateLoader");

            return dataLoader;
        }

        @Override
        public void onLoadFinished(Loader<List<People>> loader, List<People> data) {
            if (data != null) {
                Log.i("shen","数据为"+data.toString());
            } else {
                Log.i("shen","数据为null");
            }
        }

        @Override
        public void onLoaderReset(Loader<List<People>> loader) {
            Log.i("shen","数据为onLoaderReset");

        }
    }
}
