package com.example.root.playandroidtest.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.root.playandroidtest.R;
import com.example.root.playandroidtest.adapter.ArticleListAdapter;
import com.example.root.playandroidtest.app.AppConst;
import com.example.root.playandroidtest.app.MyApplication;
import com.example.root.playandroidtest.bean.ArticleBean;
//import com.example.root.playandroidtest.data.DataServer;
import com.example.root.playandroidtest.util.CacheDataUtil;
import com.example.root.playandroidtest.util.NetWorkStateReceiver;
import com.example.root.playandroidtest.util.ParserJsonWebData;
import com.example.root.playandroidtest.util.T;
import com.example.root.playandroidtest.util.WebConnectUtil;

import java.io.File;
import java.io.IOException;
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
    private BroadcastReceiver netReceiver;
    private TextView tv_notGetData;
    private int int_page = 0;  //访问网络数据的当前代表页（从0开始）
    private static final int TOTAL_COUNTER = 78;
    private static final int PAGE_SIZE = 6;
    private int mCurrentCounter = 0;
    private int mNextRequestPage = 1;

    public HomeFragment() {  }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        initViewContent(rootView);
        doClick();
        return rootView;
    }

    //刷新事件的响应
    private void doClick(){
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    //模拟下拉刷新动作
    private void refresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    //不隐藏刷新图标
                    swipeRefresh.setRefreshing(false);
                    //重新请求网络数据
                    String url = AppConst.getUrl(int_page+1);
                    GetRefreshData(url);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //初始化各个控件
    private void initViewContent(View rootView) {
        swipeRefresh = (SwipeRefreshLayout)rootView.findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));

        recyclerView = (RecyclerView) rootView.findViewById(R.id.index_home_recycleView_data);
        LinearLayoutManager manager = new LinearLayoutManager(MyApplication.getContext());
        recyclerView.setLayoutManager(manager);
        tv_notGetData = (TextView) rootView.findViewById(R.id.not_get_data);

        if (NetWorkStateReceiver.NET_TYPE.equals(0)){
            //说明没网络
            //判断是否从本地读取数据
            getDataFromCache();
        } else {
            String url = AppConst.getUrl(int_page);
            GetRefreshData(url);
        }
    }

    //获取首页数据
    private void GetRefreshData(String address) {
//        final String refreshData = "";
        //获取数据
        WebConnectUtil.sendRequestWidthOkHttp(address, new okhttp3.Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //获取服务器返回的具体内容
                final String responseData = response.body().string();
                //把数据写入Cache
                WriteDataToCache(responseData);
                getActivity().runOnUiThread(
                        new Runnable() {
                    @Override
                    public void run() {
                        List<ArticleBean> getDataList = ParserJsonWebData.parseJsonWithGSON(responseData);
//                       List<ArticleBean> getDataList = ParserJsonWebData.parseJSONWITHJSONObject(responseData);
                        if (getDataList==null) {
                            T.showShort(getContext(),"首页数据对象为空");
                        }
                        //找出当前的一共多少个对象（List中有多少个对象）
                        int size = getDataList.size();
//                        T.showShort(MyApplication.getContext(), "一共有" + size + "个对象");
                        ArticleListAdapter adapter = new ArticleListAdapter(MyApplication.getContext(), getDataList);

//                        adapter.setEmptyView(emptyView);
                        //设置item的动画效果
                        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
                        //设置重复执行动画
                        adapter.isFirstOnly(false);
                        //预加载功能，设置滑倒第几个item的时候开始回掉接口
                        adapter.setAutoLoadMoreSize(5);
                        loadMore(adapter);
                        //设置适配器
                        recyclerView.setAdapter(adapter);
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {
                //获取网络数据失败之后检查是否有网络连接
                T.showShort(getContext(),"获取网络数据失败");
                recyclerView.setVisibility(View.GONE);
                tv_notGetData.setVisibility(View.VISIBLE);
            }
        });

    }

    //调用了接口回掉方法（加载更多）
    private void loadMore(ArticleListAdapter listAdapter) {

        listAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            boolean isErr = true;

            @Override public void onLoadMoreRequested() {
            recyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mCurrentCounter >= TOTAL_COUNTER) {
                        //一个界面的数据加载完毕
                        listAdapter.loadMoreEnd();
                    } else {
                        if (isErr) {
                            getIndexMoreData(listAdapter);
                            mCurrentCounter = listAdapter.getData().size();
                            //主动调用加载完成，停止加载
                            listAdapter.loadMoreComplete();

                        } else {
                            //获取更多数据失败
                            isErr = false;
                            listAdapter.loadMoreFail();
                        }

                    }
                }

            }, 2000);
            }
        });
    }
            // 网络监听广播
    private void initReceiver() {
        netReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
//               判断当前网络状态
                if (intent.getIntExtra(NetWorkStateReceiver.NET_TYPE, 0) == 0) {
//                0表示无网络连接,
                    T.showShort(getContext(),"网络没有连接，应该读取缓存数据");
                    recyclerView.setVisibility(View.GONE);  //设置隐藏RecycleView
                    tv_notGetData.setVisibility(View.VISIBLE); //设置显示错误界面
                    getDataFromCache();
                // 这里填你想要执行的东西
                } else {
//                    T.showShort(MyApplication.getContext(), "网络已经连接，应该优先取缓存数据");
                    getDataFromCache();
                }
            }
        };

        IntentFilter filter = new IntentFilter(NetWorkStateReceiver.NET_CHANGE);
        getActivity().registerReceiver(netReceiver, filter);
    }


    //加载更多数据的时候调用该方法
    protected void getIndexMoreData(ArticleListAdapter listAdapter) {
        int_page = 1 + int_page;
        String address = AppConst.getUrl(int_page);
        WebConnectUtil.sendRequestWidthOkHttp(address, new okhttp3.Callback(){
            @Override
            public void onResponse(Call call, Response response) throws IOException {

                //获取服务器返回的具体内容
                final String responseData = response.body().string();
                List<ArticleBean> getDataList = ParserJsonWebData.parseJsonWithGSON(responseData);
//                       List<ArticleBean> getDataList = ParserJsonWebData.parseJSONWITHJSONObject(responseData);
                if (getDataList==null) {
                    T.showShort(getContext(),"首页数据对象为空");
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (getDataList.size() != 0) {
                            listAdapter.addData(getDataList);
                            listAdapter.loadMoreComplete();
                        }else {
                            listAdapter.loadMoreEnd();
                        }
                    }
                });

            }

            @Override
            public void onFailure(Call call, IOException e) {
                //获取网络数据失败之后检查是否有网络连接
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initReceiver();
                        listAdapter.loadMoreFail();
                        T.showShort(getContext(), "没有获取到网络数据");
                    }
                });
            }
        });
    }

    //判断是否从本地读取数据的方法调用
    private void getDataFromCache() {
        /**
         * 先检测是否有本地数据，如果有，则应先读取本地数据，之后再获取网络数据
         * 如果没有本地数据则直接获取网络数据
         */
        //首先检查是否有此文件
        File netDataFile = CacheDataUtil.getFile(AppConst.FILE_NAME);
        if (netDataFile.exists()) {
            boolean isFail = CacheDataUtil.isCacheDataFailure(MyApplication.getContext(), AppConst.FILE_NAME);
            //true为过期，false为没过期
            if (isFail) {
                //清除所有的缓存
                CacheDataUtil.clearAllCache(MyApplication.getContext());
            } else {
                //没有过期
                //读取出所有的数据
                String responseNetData = CacheDataUtil.readNetForFile(AppConst.FILE_NAME);
                List<ArticleBean> getCacheDataList = ParserJsonWebData.parseJsonWithGSON(responseNetData);
                ArticleListAdapter adapter = new ArticleListAdapter(MyApplication.getContext(), getCacheDataList);
                //设置item的动画效果
                adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
                //设置重复执行动画
                adapter.isFirstOnly(false);
                //预加载功能，设置滑倒第几个item的时候开始回掉接口
                adapter.setAutoLoadMoreSize(5);
                //获取上拉加载更多
                loadMore(adapter);
                //设置适配器
                recyclerView.setAdapter(adapter);
                //读取完数据后加载网络数据
                String url = AppConst.getUrl(int_page);
                GetRefreshData(url);
            }
        } else {
            //如果文件不存在，直接从网络读取数据
            recyclerView.setVisibility(View.GONE);
            tv_notGetData.setVisibility(View.VISIBLE);
        }
    }

    private void WriteDataToCache(String responseData) {
        //把获取到的数据写入文件中
        File netDataFile = CacheDataUtil.getFile(AppConst.FILE_NAME);
        if (netDataFile.exists()) {
            //清除所有的缓存
            netDataFile.delete();
            CacheDataUtil.WriteStringToFile2(AppConst.FILE_NAME, responseData);
        } else {
            netDataFile.mkdir();
            CacheDataUtil.WriteStringToFile2(AppConst.FILE_NAME, responseData);
        }
    }

}