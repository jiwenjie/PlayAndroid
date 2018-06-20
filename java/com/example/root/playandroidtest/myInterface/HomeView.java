package com.example.root.playandroidtest.myInterface;

import java.util.List;

/**
 * Created by Root on 2018/3/14.
 * 首页显示数据接口
 */

public interface HomeView {

    void showRefreshView(Boolean refresh);

//    void getBannerDataSuccess(List<BannerBean> data);

    void getDataError(String message);

//    void getRefreshDataSuccess(List<ArticleBean> data);
//
//    void getMoreDataSuccess(List<ArticleBean> data);

}
