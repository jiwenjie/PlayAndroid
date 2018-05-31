package com.example.root.playandroidtest.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Root on 2018/3/19.
 *
 * 有关SharedPreferences的方法封装
 */

//用以获取真假值，判断用户是否登陆等等
public class PreferUtils {

    public static final String PREF_NAME = "config";

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, defaultValue);
    }

    public static void setBoolean(Context context, String key, boolean Value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.putBoolean(key,Value).apply();
    }

    public static String getString(Context context, String key, String defaultValue) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getString(PREF_NAME, defaultValue);
    }

    public static void setString(Context context, String key, String Value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(PREF_NAME, Value);
    }

}
