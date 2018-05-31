package com.example.root.playandroidtest.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.view.animation.FastOutLinearInInterpolator;

import com.example.root.playandroidtest.app.MyApplication;

/**
 * Created by Root on 2018/3/17.
 * 判断网络情况
 *
 */

public class NetWorkStateReceiver extends BroadcastReceiver {

//    boolean isConnect = false;
    public static final String NET_CHANGE = "net_change";
    //标记当前网络状态，0为无可用网络状态，1表示有。
    public static final String NET_TYPE = "net_type";

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("网络状态发生变化");
        //检测API是否小于23，因为23之后getNetworkInfo(int networkType)方法被废弃
        if (Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {

            //获取ConnectivityManager对象
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            //获取ConnectivityManager对象对应的NetworkInfo对象
            //获取WIFI连接的信息
            NetworkInfo wifiNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            //获取移动数据连接的信息
            NetworkInfo dataNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if ((wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) || (wifiNetworkInfo.isConnected() && !dataNetworkInfo.isConnected()) || (!wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected())) {
                isConnectSuccess();
                return;
            } else {
                //            网络状态全部不可用
                isConnectFail();
                return;
            }
            //API大于23时使用下面的方式进行网络监听
        } else {
            System.out.println("API level 大于23");
            //获得ConnectivityManager对象
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            //获取所有网络连接的信息
            Network[] networks = connMgr.getAllNetworks();
            //用于存放网络连接信息
            StringBuilder sb = new StringBuilder();
            //通过循环将网络信息逐个取出来
            for (int i=0; i < networks.length; i++){
                //获取ConnectivityManager对象对应的NetworkInfo对象
                NetworkInfo networkInfo = connMgr.getNetworkInfo(networks[i]);

                if (networkInfo.isConnected()) {
                    //说明网络连接成功
                    isConnectSuccess();
                    return;
                } else {
                    //网络没有连接
                    //            网络状态全部不可用
                    isConnectFail();
                    return;
                }

//                sb.append(networkInfo.getTypeName() + " connect is " + networkInfo.isConnected());
            }
//            Toast.makeText(context, sb.toString(),Toast.LENGTH_SHORT).show();
            T.showShort(context, sb.toString());
        }
    }

    private void isConnectSuccess() {
        Intent netIntent = new Intent(NET_CHANGE);
        netIntent.putExtra(NET_TYPE,1);
        MyApplication.getContext().sendBroadcast(netIntent);
        T.showShort(MyApplication.getContext(), "网络已经连接");
    }

    private void isConnectFail() {
        Intent netIntent = new Intent(NET_CHANGE);
        netIntent.putExtra(NET_TYPE,0);
        MyApplication.getContext().sendBroadcast(netIntent);
        T.showShort(MyApplication.getContext(), "WIFI 已断开， 移动数据已断开");
    }
}
