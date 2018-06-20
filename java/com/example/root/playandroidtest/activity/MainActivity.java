package com.example.root.playandroidtest.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageButton;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.playandroidtest.R;
import com.example.root.playandroidtest.app.AppConst;
import com.example.root.playandroidtest.fragment.HomeFragment;
import com.example.root.playandroidtest.fragment.SearchFragment;
import com.example.root.playandroidtest.fragment.UserFragment;
import com.example.root.playandroidtest.util.T;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private RadioGroup indexRadioGroup;
    private ViewPager viewPagerIndex;
    private DrawerLayout drawerLayout;
    private NavigationView navView;

    private SharedPreferences preferences;

    private AppCompatImageButton homeImageButton;
    private Handler handler;
    private TextView tv_userName;

    //初始化各个控件
    public void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        preferences = getSharedPreferences("config", 0);    //保持可编辑的状态

        initFragment();
        check_is_login();

        hideWaitingDialog();
        loadData();

    }

    private void initFragment() {
        indexRadioGroup = findViewById(R.id.index_radioGroup);
        viewPagerIndex = findViewById(R.id.viewPagerIndex);
        drawerLayout = findViewById(R.id.drawer_layout);
        homeImageButton = findViewById(R.id.index_home_button);
        navView = findViewById(R.id.nav_view);
        View headView = navView.inflateHeaderView(R.layout.nav_header);
        tv_userName = headView.findViewById(R.id.username);

        final List<Fragment> fragments = new ArrayList<Fragment>();
        HomeFragment homeFragment = new HomeFragment();
        SearchFragment searchFragment = new SearchFragment();
        UserFragment userFragment = new UserFragment();

        fragments.add(homeFragment);
        fragments.add(searchFragment);
        fragments.add(userFragment);

        FragmentManager fragmentManager;
        fragmentManager = getSupportFragmentManager();
        viewPagerIndex.setAdapter(new FragmentPagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
    }

    @Override
    protected void loadData() {
        ContentView();
    }

    //把各个控件的点击事件实现
    private void ContentView(){
        indexRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int item_id) {
                switch (item_id) {
                    case R.id.index_home_radio:
                        viewPagerIndex.setCurrentItem(0);
                        break;
                    case R.id.index_search_radio:
                        viewPagerIndex.setCurrentItem(1);
                        break;
                    case R.id.index_user_radio:
                        viewPagerIndex.setCurrentItem(2);
                        break;
                    default:
                        break;
                }
            }
        });

        viewPagerIndex.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        indexRadioGroup.check(R.id.index_home_radio);
                        break;
                    case 1:
                        indexRadioGroup.check(R.id.index_search_radio);
                        break;
                    case 2:
                        indexRadioGroup.check(R.id.index_user_radio);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        homeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //这里可以实现跳转不同的界面
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    /**
     * 检查是否登陆，没有登陆则做出跳转动作，否则不做处理
     */
    private void check_is_login() {
        handler = new Handler();    //初始化（防止程序直接卡死）

        if (AppConst.IS_LOGIN) {    //用户已经登陆

            T.showShort("您已经登陆");

            tv_userName.setText(preferences.getString("username", "用户名"));

            return;
        } else {
            //没有登陆,跳转登陆界面
            T.showShort(getApplicationContext(), "您还没有登陆，请先登陆");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(MainActivity.this, UserLoginActivity.class);
                    startActivity(intent);
                    //finish();
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                }
            }, 2000);
        }
    }
}
