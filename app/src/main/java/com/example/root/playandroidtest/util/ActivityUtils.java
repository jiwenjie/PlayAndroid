package com.example.root.playandroidtest.util;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

/**
 * Created by Root on 2018/3/18.
 * 用于动态添加Fragment到活动中去
 */

public class ActivityUtils {

    private static final String TAG = "ActivityUtils";

    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId) {
        addFragmentToActivity(fragmentManager, fragment, frameId, true);
    }

    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId, boolean addToBackStack) {
        if (fragment.isAdded()) {
            Log.w(TAG, "addFragmentToActivity: fragment is added:" + fragment.getClass().getName());
            return;
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

}
