package com.example.root.playandroidtest.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;

import com.example.root.playandroidtest.R;
import com.example.root.playandroidtest.app.AppConst;

/**
 * Created by Root on 2018/3/13.
 */

public class AppStart extends AppCompatActivity {

    private AppCompatImageView imageView;
    private Handler handler=new Handler();
    private boolean checkApp = true;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_app_start_bg);
//        handler.postDelayed(runnable,3000);
        checkAppIsFirst();
    }


    private void checkAppIsFirst() {

        /// / AppConst.IS_FIRST_PLAY_APP

        SharedPreferences pref = getSharedPreferences(AppConst.CHECK_APP, MODE_PRIVATE);
        checkApp = pref.getBoolean("isFirst", true);

        if (checkApp) {
            SharedPreferences.Editor editor = getSharedPreferences(AppConst.CHECK_APP, MODE_PRIVATE).edit();
            editor.putBoolean("isFirst", false);
            editor.apply();
            handler.postDelayed(runnableFirst,3000);
        } else {
            handler.postDelayed(runnableStart,3000);
        }


    }

    Runnable runnableFirst = new Runnable() {
        @Override
        public void run() {
            startActivity(new Intent(AppStart.this,AppStartLeaderFirst.class));
            finish();
        }
    };


    Runnable runnableStart = new Runnable() {
        @Override
        public void run() {
            startActivity(new Intent(AppStart.this,MainActivity.class));
            finish();
        }
    };
}
