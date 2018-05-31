package com.example.root.playandroidtest.leaderPart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.AppCompatImageView;

import com.example.root.playandroidtest.R;
import com.example.root.playandroidtest.activity.BaseActivity;
import com.example.root.playandroidtest.activity.MainActivity;
import com.example.root.playandroidtest.app.AppConst;
/**
 * Created by Root on 2018/3/13.
 */

public class AppStart extends BaseActivity {

    private AppCompatImageView imageView;
    private Handler handler=new Handler();
    private boolean checkApp = true;
//    @Override


    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.layout_app_start_bg);
//        handler.postDelayed(runnable,3000);
        goToMain();
    }

    @Override
    protected void loadData() {

    }

    private void goToMain() {
        startActivity(new Intent(AppStart.this,MainActivity.class));
        finish();
    }

    Runnable runnableStart = new Runnable() {
        @Override
        public void run() {

        }
    };

}
