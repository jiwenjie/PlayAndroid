package com.example.root.playandroidtest.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import org.litepal.LitePalApplication;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Root on 2018/3/14.
 * 自己继承实现的Application，用以随时退出整个程序
 *
 * 继承类改为支持LitePal，这样就不要每次需要context了
 */

public class MyApplication extends LitePalApplication {

    public static List<Activity> activities = new LinkedList<>();

    private static MyApplication cloudReaderApplication;

    public static MyApplication getInstance() {
        // if语句下是不会走的，Application本身已单例
        if (cloudReaderApplication == null) {
            synchronized (MyApplication.class) {
                if (cloudReaderApplication == null) {
                    cloudReaderApplication = new MyApplication();
                }
            }
        }
        return cloudReaderApplication;
    }

    @SuppressWarnings("unused")
    @Override
    public void onCreate() {
        super.onCreate();
        cloudReaderApplication = this;

    }

    /**
     * 退出程序
     */
    public static void exit() {
        for (Activity activity : activities) {
            activity.finish();
        }
    }


}
