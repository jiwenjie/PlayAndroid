package com.example.root.playandroidtest.util;

import android.content.Context;
import android.widget.Toast;

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

    public static boolean isShow = true;


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

}
