package com.example.root.playandroidtest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;

import com.example.root.playandroidtest.R;

/**
 * Created by Root on 2018/3/13.
 */

public class AppStart extends AppCompatActivity {

    private AppCompatImageView imageView;
    private Handler handler=new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_app_start_bg);
        handler.postDelayed(runnable,3000);
    }


    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            startActivity(new Intent(AppStart.this,AppStartLeaderFirst.class));
            finish();
        }
    };
}
