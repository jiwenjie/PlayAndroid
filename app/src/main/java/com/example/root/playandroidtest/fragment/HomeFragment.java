package com.example.root.playandroidtest.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.root.playandroidtest.R;
import com.example.root.playandroidtest.adapter.HomeDataAdapter;
import com.example.root.playandroidtest.app.AppConst;
import com.example.root.playandroidtest.app.MyApplication;
import com.example.root.playandroidtest.bean.ArticleBean;
import com.example.root.playandroidtest.util.GetHomeData;
import com.example.root.playandroidtest.util.ParserJsonWebData;
import com.example.root.playandroidtest.util.T;
import com.example.root.playandroidtest.util.WebConnectUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Root on 2018/3/13.
 * 首页 Fragment
 */

public class HomeFragment extends Fragment {

    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView recyclerView;
    private List<ArticleBean> beanList;
//    HomeDataAdapter homeDataAdapter;

    TextView index_home_textView;

    private String homeData = "";

    public HomeFragment() {  }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View rootView = inflater.inflate(R.layout.fragment_home,container,false);
        initViewContent(rootView);
        doClick();

        return rootView;
        //return inflater.inflate(R.layout.fragment_home, container, false);
    }

    private void doClick(){

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

    }

    private void refresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    showRefreshView(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

//    private void runUi() {
//        swipeRefresh.setRefreshing(false);
//    }

    private void initViewContent(View rootView) {
        swipeRefresh = (SwipeRefreshLayout)rootView.findViewById(R.id.swipe_refresh);
//        index_home_textView = (TextView) rootView.findViewById(R.id.index_home_text);
        swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        recyclerView = (RecyclerView) rootView.findViewById(R.id.index_home_recycleView_data);
        LinearLayoutManager manager = new LinearLayoutManager(MyApplication.getContext());
        recyclerView.setLayoutManager(manager);
//        recyclerView = (RecyclerView) getView().findViewById(R.id.index_home_recycleView_data);
        GetRefreshData();

        //获取数据

//        GetHomeData getHomeData = new GetHomeData();
//        String homeData = getHomeData.getUIData();
//        List<ArticleBean> getDataList = ParserJsonWebData.getBeanListTwo(homeData);
//        List<ArticleBean> getDataList = ParserJsonWebData.getBeanListOne(homeData);
//        homeDataAdapter = new HomeDataAdapter(getDataList);
//        recyclerView.setAdapter(homeDataAdapter);
//        beanList = AppConst.Const_list;
//        homeDataAdapter = new HomeDataAdapter(beanList);
//        recyclerView.setAdapter(homeDataAdapter);
    }

    //获取首页数据
    private void GetRefreshData() {

//        final String refreshData = "";
        //获取数据
        WebConnectUtil.sendRequestWidthOkHttp("http://www.wanandroid.com/article/list/0/json", new okhttp3.Callback() {

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //获取服务器返回的具体内容
                final String responseData = response.body().string();
//                final List<ArticleBean> getDataList = ParserJsonWebData.parseJsonWithGSON(responseData);
//                beanList = getDataList;

                getActivity().runOnUiThread(
                        new Runnable() {
                    @Override
                    public void run() {
//                        beanList = getDataList;
                        List<ArticleBean> getDataList = ParserJsonWebData.parseJsonWithGSON(responseData);
//                       List<ArticleBean> getDataList = ParserJsonWebData.parseJSONWITHJSONObject(responseData);
                        if (getDataList==null) {
                            T.showShort(MyApplication.getContext(), "首页数据对象为空");
                        }


                        HomeDataAdapter homeDataAdapter = new HomeDataAdapter(getDataList);
                        recyclerView.setAdapter(homeDataAdapter);
//                        index_home_textView.setText(responseData);
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {
                T.showShort(MyApplication.getContext(), "没有获取到网络数据");
            }
        });
    }

    //展示是否需要隐藏刷新图标
    public void showRefreshView(final Boolean refresh) {
        swipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                swipeRefresh.setRefreshing(refresh);
            }
        });
    }

}
