package com.example.threeversionasproject.application;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.taobao.sophix.SophixManager;

/**
 * Created by Ocean on 2018/7/17.
 */

public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        SophixManager.getInstance().queryAndLoadNewPatch();
        Log.i("","-------开始请求后台补丁");
        context=getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
