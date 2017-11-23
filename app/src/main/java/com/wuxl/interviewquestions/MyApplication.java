package com.wuxl.interviewquestions;

import android.app.Application;

import com.tencent.bugly.crashreport.CrashReport;
import com.zhuge.analysis.stat.ZhugeSDK;

import cn.bmob.v3.Bmob;


/**
 * Application
 * Created by wuxianglong on 2016/9/8.
 */
public class MyApplication extends Application {

    private static final String APPLICATION_ID = "2c90d494b667e7749a1bdd8d9f9ac110";
    private static final String BUGLY_ID = "cb84a618dc";

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化Bmob
        Bmob.initialize(getApplicationContext(), APPLICATION_ID);
        //初始化分析跟踪
        ZhugeSDK.getInstance().init(getApplicationContext());
        //初始化Bugly
        CrashReport.initCrashReport(getApplicationContext(), BUGLY_ID, false);
    }
}
