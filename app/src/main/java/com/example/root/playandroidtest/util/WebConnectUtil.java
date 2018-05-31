package com.example.root.playandroidtest.util;

import android.util.Log;

import com.example.root.playandroidtest.app.AppConst;
import com.example.root.playandroidtest.app.MyApplication;
import com.example.root.playandroidtest.bean.ArticleBean;
import com.example.root.playandroidtest.bean.ResponseData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Root on 2018/3/15.
 * 使用okhttp3框架链接网络获取数据
 */

public class WebConnectUtil {
    //提出网络请求，获取所得的数据(返回数据)
//   static String WebresponseData = "";
    public static void sendRequestWidthOkHttp(String url, okhttp3.Callback callback) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(callback);
    }

}