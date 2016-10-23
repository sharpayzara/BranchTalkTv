package com.sumavision.branch.utils;

import android.app.Application;
import android.content.Context;
import android.support.multidex.BuildConfig;
import android.support.multidex.MultiDex;

import com.facebook.stetho.Stetho;
import com.jiongbull.jlog.JLog;

/**
 *  desc  初始化Application
 *  @author  yangjh
 *  created at  16-5-18 下午4:37
 */
public class BaseApp extends Application {
    private static BaseApp instance;
    @Override
    public void onCreate() {
        super.onCreate();

        JLog.init(this)
                .setDebug(BuildConfig.DEBUG);
        instance = (BaseApp) getApplicationContext();
        //开启leak内存检测
        //refWatcher = LeakCanary.install(this);
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());


    }

    public static Context getContext() {
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}