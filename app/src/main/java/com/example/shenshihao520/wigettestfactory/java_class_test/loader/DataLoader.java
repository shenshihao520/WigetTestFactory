package com.example.shenshihao520.wigettestfactory.java_class_test.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenshihao520 on 2017/8/22.
 */

public class DataLoader extends AsyncTaskLoader<List<People>> implements DummyItem.Change{

    public DataLoader(Context context) {
        super(context);
    }
    DummyItem testData ;
    public DataLoader(Context context,DummyItem testData) {
        super(context);
        this.testData = testData;
    }
    @Override
    public List<People> loadInBackground() {
        Log.i("shen","数据为loadInBackground");

        List<People> data = testData.getPeopleList();
        List<People> data2 = new ArrayList<>();
        data2.addAll(data);
        return data2;
    }
    @Override
    protected void onReset() {
        Log.i("shen","数据为onReset");

        onStopLoading();
    }
    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        Log.i("shen","数据为onStartLoading");

        deliverResult(testData.getPeopleList());
        if (takeContentChanged()) {
            forceLoad();
        }
    }
    @Override
    protected void onStopLoading() {
        super.onStopLoading();
        Log.i("shen","数据为onStopLoading");
        cancelLoad();
    }
    @Override
    public void deliverResult(List<People> data) {
        Log.i("shen","数据为deliverResult");

        if (isReset()) {
            return;
        }
        if (isStarted()) {
            super.deliverResult(data);
        }
    }

    @Override
    public void onChange() {
        if (isStarted()) {
            forceLoad();
        }
    }

}
