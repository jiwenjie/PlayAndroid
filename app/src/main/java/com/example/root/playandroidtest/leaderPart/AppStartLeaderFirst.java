package com.example.root.playandroidtest.leaderPart;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.root.playandroidtest.R;
import com.example.root.playandroidtest.activity.BaseActivity;
import com.example.root.playandroidtest.activity.MainActivity;
import com.example.root.playandroidtest.view.CustomDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Root on 2018/3/14.
 */

public class AppStartLeaderFirst extends BaseActivity {

    private ViewPager viewPager1;
    private List<View> views;

    private CustomDialog mDialogWaiting;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.layout_app_start_first);

        viewPager1 = (ViewPager) findViewById(R.id.viewPager1);
        views = new ArrayList<View>();
        LayoutInflater layoutInflater = getLayoutInflater().from(this);

        View view_first = layoutInflater.inflate(R.layout.viewpager_guilde_first, null);
        View view_second = layoutInflater.inflate(R.layout.viewpager_guilde_second, null);
        View view_third = layoutInflater.inflate(R.layout.viewpager_guilde_third, null);

        view_third.findViewById(R.id.imageViewStartIndex);

        view_third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            Thread.sleep(1000);
                            showWaitingDialog("加载中");
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                    }

                }).start();

                //finish();
            }
        });

        views.add(view_first);
        views.add(view_second);
        views.add(view_third);


        loadData();
    }


    @Override
    protected void loadData() {


        viewPager1.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(views.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(views.get(position));
                return views.get(position);
            }
        });
    }
//    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.layout_app_start_first);
//
//        viewPager1 = (ViewPager) findViewById(R.id.viewPager1);
//        views = new ArrayList<View>();
//        LayoutInflater layoutInflater = getLayoutInflater().from(this);
//
//        View view_first = layoutInflater.inflate(R.layout.viewpager_guilde_first, null);
//        View view_second = layoutInflater.inflate(R.layout.viewpager_guilde_second, null);
//        View view_third = layoutInflater.inflate(R.layout.viewpager_guilde_third, null);
//
//        view_third.findViewById(R.id.imageViewStartIndex);
//        view_third.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        try {
//                            Thread.sleep(1000);
//                            showWaitingDialog("加载中");
//                        }catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                                startActivity(intent);
//                                finish();
//                            }
//                        });
//                    }
//
//                }).start();
//
//
//
//                //finish();
//            }
//        });
//
//        views.add(view_first);
//        views.add(view_second);
//        views.add(view_third);
//
//        viewPager1.setAdapter(new PagerAdapter() {
//            @Override
//            public int getCount() {
//                return views.size();
//            }
//
//            @Override
//            public boolean isViewFromObject(View view, Object object) {
//                return view == object;
//            }
//
//            @Override
//            public void destroyItem(ViewGroup container, int position, Object object) {
//                container.removeView(views.get(position));
//            }
//
//            @Override
//            public Object instantiateItem(ViewGroup container, int position) {
//                container.addView(views.get(position));
//                return views.get(position);
//            }
//        });
//    }


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

}
