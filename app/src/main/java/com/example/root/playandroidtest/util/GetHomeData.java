package com.example.root.playandroidtest.util;

import android.support.v7.app.AppCompatActivity;

import com.example.root.playandroidtest.app.MyApplication;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Root on 2018/3/16.
 */

public class GetHomeData extends AppCompatActivity {

    public String data;


    public void getData() {

        WebConnectUtil.sendRequestWidthOkHttp("http://www.wanandroid.com/article/list/0/json", new okhttp3.Callback() {
            String responseData;
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //获取服务器返回的具体内容
                responseData = response.body().string();
//                             beanList = getDataList;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        data = responseData;
                    }
                });

            }

            @Override
            public void onFailure(Call call, IOException e) {
                T.showShort(MyApplication.getContext(), "没有获取到网络数据");
            }
        });
    }

    public String getUIData() {
        getData();
        return data;
    }


}
