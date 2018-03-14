package com.example.root.playandroidtest.api;

import com.example.root.playandroidtest.app.AppConst;

/**
 * Created by Root on 2018/3/14.
 * desc：玩Android提供的api接口
 * www.wanandroid.com
 *
 */


public class WanService {

    private static String homeDataList = AppConst.BASE_URL + "article/list/{page}/json";
    private static String homeBannerData = AppConst.BASE_URL + "banner/json";
    private static String hotKeyUrl = AppConst.BASE_URL + "hotkey/json";
    private static String loginUrl = AppConst.BASE_URL + "user/login";
    private static String registUrl = AppConst.BASE_URL + "user/register";
    private static String getClollectData = AppConst.BASE_URL + "lg/collect/list/0/json";

}
