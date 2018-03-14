package com.example.root.playandroidtest.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.root.playandroidtest.R;

/**
 * Created by Root on 2018/3/13.
 */

public class HomeFragment extends Fragment {

    private TextView index_home_textView;
    private SwipeRefreshLayout swipeRefresh;

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
//        index_home_textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Intent intent = new Intent(getContext(), TestLink.class);
////                startActivity(intent);
//            }
//        });
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
        index_home_textView = (TextView) rootView.findViewById(R.id.index_home_text);
        swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        // onRefresh();
    }

    //获取数据
    private void onRefresh() {

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
