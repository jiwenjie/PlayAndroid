package com.example.root.playandroidtest.leaderPart;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.root.playandroidtest.R;
import com.example.root.playandroidtest.activity.BaseActivity;
import com.example.root.playandroidtest.activity.MainActivity;
import com.example.root.playandroidtest.view.CustomDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Root on 2018/3/14.
 */

public class AppStartLeaderFirst extends AppCompatActivity {

   private Handler handler = new Handler();

    //设置开启页面动画的图片数组
    private static final int[] SPLASH_ARRAY = {
            R.drawable.splash0,
            R.drawable.splash1,
            R.drawable.splash2,
            R.drawable.splash3,
            R.drawable.splash4,
            R.drawable.splash6,
            R.drawable.splash7,
            R.drawable.splash8,
            R.drawable.splash9,
            R.drawable.splash10,
            R.drawable.splash11,
            R.drawable.splash12,
            R.drawable.splash13,
            R.drawable.splash14,
            R.drawable.splash15,
            R.drawable.splash16,

    };

    private ImageView mIv_splash;
    private TextView tv_splashAppName;
    private TextView tv_splashSlogan;
    private TextView tv_splashCopyRight;
    private TextView tv_splashVersionName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//窗口透明的状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//窗口透明的导航栏

        setContentView(R.layout.layout_app_start_bg);
        //初始化各个控件
        initViews();
        //获取系统时间的参数，根据随机数来确定当前应该是那一张图
        Random random = new Random(SystemClock.elapsedRealtime());
        mIv_splash.setImageResource(SPLASH_ARRAY[random.nextInt(SPLASH_ARRAY.length)]);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                gotoMain();
            }
        }, 2000);
    }

    private void initViews() {
        mIv_splash = (ImageView) findViewById(R.id.iv_splash);
        tv_splashAppName = (TextView) findViewById(R.id.splash_app_name);
        tv_splashSlogan = (TextView) findViewById(R.id.splash_slogan);
        tv_splashCopyRight = (TextView) findViewById(R.id.splash_copyright);
        tv_splashVersionName = (TextView) findViewById(R.id.splash_version_name);
    }


    private void gotoMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

}
