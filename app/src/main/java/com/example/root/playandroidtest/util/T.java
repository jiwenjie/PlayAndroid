package com.example.root.playandroidtest.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.example.root.playandroidtest.app.MyApplication;

/**
 * Created by Root on 2018/3/14.
 *
 * Toast统一管理类
 */

public class T {

    private T() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    private static boolean isShow = true;


    /**
     * 短时间显示Toast统一管理
     * @param context
     * @param message
     */
    public static void showShort(Context context, CharSequence message) {

        if (isShow) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();;
        }
    }


    public static void showShort(CharSequence message) {

        if (isShow) {
            Toast.makeText(MyApplication.getInstance(), message, Toast.LENGTH_SHORT).show();;
        }
    }


    /**
     * 长时间显示Toast统一管理
     * @param context
     * @param message
     */
    public static void showLong(Context context, CharSequence message) {

        if (isShow) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();;
        }
    }

    /**
     * 用来显示加载中的过程显示
     * @param context
     */
    public static void showProgressDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("This is ProgressDialog");
        progressDialog.setMessage("Loading....");
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

}
