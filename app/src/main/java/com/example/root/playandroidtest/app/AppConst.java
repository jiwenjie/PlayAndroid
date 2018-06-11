package com.example.root.playandroidtest.app;

import android.net.Uri;
import android.os.Environment;

import com.example.root.playandroidtest.bean.ArticleBean;

import java.util.List;

/**
 * Created by Root on 2018/3/14.
 * desc：App全局常量
 */

public class AppConst {

//    private static String homeDataList = AppConst.BASE_URL + "article/list/{page}/json";
//    private static String homeBannerData = AppConst.BASE_URL + "banner/json";
//    private static String hotKeyUrl = AppConst.BASE_URL + "hotkey/json";
//    private static String loginUrl = AppConst.BASE_URL + "user/login";
//    private static String registUrl = AppConst.BASE_URL + "user/register";
//    private static String getClollectData = AppConst.BASE_URL + "lg/collect/list/0/json";

    public static final String BASE_URL = "http://wanandroid.com/";

    public static final String IS_LOGIN_KEY = "isLogin";
    public static final String USERNAME_KEY = "userName";
    public static final String CHECK_APP = "checkApp";

    public static boolean IS_FIRST_PLAY_APP = true;  //获取标识判断是否是第一次安装使用APP

    public static boolean IS_LOGIN = false;     //标识用户是否登陆

    public static int TOTAL_COUNTER = 30;

    public static final String CACHE_ROAD = "";

    //其中可以改变数字即可以访问之后的页面，处理都是一样的  （使用page代替即可   用来替换参数多次循环访问）
    public static String getUrl(int page) {
        return "http://www.wanandroid.com/article/list/" + page + "/json";
    }


    private static String cachePath1 =  MyApplication.getContext().getExternalCacheDir().getPath();
    private static String cachePath2 =  MyApplication.getContext().getCacheDir().getPath();

    //设置缓存路径的第一种
    public static final String CACHE_PATH_ONE =  cachePath1;
    //设置缓存路径的第二种
    public static final String CACHE_PATH_TWO =  cachePath2;

    public static final String FILE_NAME = "NetData";


}
