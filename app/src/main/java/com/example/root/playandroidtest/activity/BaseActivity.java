package com.example.root.playandroidtest.activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.root.playandroidtest.R;
import com.example.root.playandroidtest.app.MyApplication;
import com.example.root.playandroidtest.util.NetWorkStateReceiver;
import com.example.root.playandroidtest.util.T;
import com.example.root.playandroidtest.view.CustomDialog;

/**
 * Created by Root on 2018/3/17.
 *
 * 添加动态注册广播检测网络书否连接
 */

public abstract class BaseActivity extends AppCompatActivity {

    NetWorkStateReceiver netWorkStateReceiver;
    private CustomDialog mDialogWaiting;
    private BroadcastReceiver netReceiver;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        initVariables();
        MyApplication.activities.add(this);
        init();

        initViews(savedInstanceState);
        loadData();
        initReceiver();
    }

    //      监听广播
    private void initReceiver() {
        netReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
//                判断当前网络状态
                if(intent.getIntExtra(NetWorkStateReceiver.NET_TYPE,0)==0){
                    T.showShort(getApplicationContext(), "网络没有连接，应该读取缓存数据");
                }else {
                    T.showShort(getApplicationContext(), "网络已经连接，应该优先取缓存数据");
                }
            }
        };

        IntentFilter filter = new IntentFilter(NetWorkStateReceiver.NET_CHANGE);
        registerReceiver(netWorkStateReceiver,filter);
    }

    //在onResume()方法注册
    @Override
    protected void onResume() {
        if (netWorkStateReceiver == null) {
            netWorkStateReceiver = new NetWorkStateReceiver();
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(netWorkStateReceiver, filter);
        System.out.println("注册");
        super.onResume();
    }

    //onPause()方法注销
    @Override
    protected void onPause() {
        unregisterReceiver(netWorkStateReceiver);
        System.out.println("注销");
        super.onPause();
    }

    /**
     * 显示等待提示框
     */
    public Dialog showWaitingDialog(String tip) {
        hideWaitingDialog();
        View view = View.inflate(this, R.layout.dialog_waiting, null);
        if (!TextUtils.isEmpty(tip))
            ((TextView) view.findViewById(R.id.tvTip)).setText(tip);
        mDialogWaiting = new CustomDialog(this, view, R.style.MyDialog);
        mDialogWaiting.show();
        mDialogWaiting.setCancelable(false);
        return mDialogWaiting;
    }

    /**
     * 隐藏等待提示框
     */
    public void hideWaitingDialog() {
        if (mDialogWaiting != null) {
            mDialogWaiting.dismiss();
            mDialogWaiting = null;
        }
    }

    //在setContentView之前调用，可以做一些设置
    public void init() {

    }

//    protected abstract void initVariables();
    protected abstract void initViews(Bundle savedInstanceState);
    protected abstract void loadData();

}
