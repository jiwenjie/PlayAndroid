package com.example.root.playandroidtest.app;

import android.net.Uri;

import com.example.root.playandroidtest.bean.ArticleBean;

import java.util.List;

/**
 * Created by Root on 2018/3/14.
 * desc：App全局常量
 */

public class AppConst {


    public static final String BASE_URL = "http://wanandroid.com/";

    public static final String IS_LOGIN_KEY = "isLogin";
    public static final String USERNAME_KEY = "userName";
    public static final String CHECK_APP = "checkApp";

    public static List<ArticleBean> Const_list = null;

    //其中可以改变数字即可以访问之后的页面，处理都是一样的  （使用page代替即可   用来替换参数多次循环访问）
    //private static String url = "http://www.wanandroid.com/article/list/" + page + "/json";

    public static boolean IS_FIRST_PLAY_APP = true;  //获取标识判断是否是第一次安装使用APP


    //其中可以改变数字即可以访问之后的页面，处理都是一样的  （使用page代替即可   用来替换参数多次循环访问）
    public static String getUrl(int page) {
        return "http://www.wanandroid.com/article/list/0/json";
    }

    public static String Const_data = "";

}
