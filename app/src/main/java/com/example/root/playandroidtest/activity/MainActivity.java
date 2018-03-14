package com.example.root.playandroidtest.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.example.root.playandroidtest.R;
import com.example.root.playandroidtest.fragment.HomeFragment;
import com.example.root.playandroidtest.fragment.SearchFragment;
import com.example.root.playandroidtest.fragment.UserFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RadioGroup indexRadioGroup;
    private ViewPager viewPagerIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initViews();
        ContentView();

//        indexRadioGroup = (RadioGroup) findViewById(R.id.index_radioGroup);
////        viewPagerIndex = (ViewPager) findViewById(R.id.)
//
//        indexRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int item_id) {
//                switch (item_id) {
//                    case R.id.index_home_radio:
//                        viewPagerIndex.setCurrentItem(0);
//                        break;
//                    case R.id.index_search_radio:
//                        viewPagerIndex.setCurrentItem(1);
//                        break;
//                    case R.id.index_user_radio:
//                        viewPagerIndex.setCurrentItem(2);
//                        break;
//                    default:
//                        break;
//                }
//            }
//        });
//
//        viewPagerIndex = (ViewPager) findViewById(R.id.viewPagerIndex);
//        final List<Fragment> fragments = new ArrayList<Fragment>();
//        HomeFragment homeFragment = new HomeFragment();
//        SearchFragment searchFragment = new SearchFragment();
//        UserFragment userFragment = new UserFragment();
//
//        fragments.add(homeFragment);
//        fragments.add(searchFragment);
//        fragments.add(userFragment);
//
//        FragmentManager fragmentManager;
//        fragmentManager = getSupportFragmentManager();
//        viewPagerIndex.setAdapter(new FragmentPagerAdapter(fragmentManager) {
//            @Override
//            public Fragment getItem(int position) {
//                return fragments.get(position);
//            }
//
//            @Override
//            public int getCount() {
//                return fragments.size();
//            }
//        });
//
//        viewPagerIndex.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                switch (position) {
//                    case 0:
//                        indexRadioGroup.check(R.id.index_home_radio);
//                        break;
//                    case 1:
//                        indexRadioGroup.check(R.id.index_search_radio);
//                        break;
//                    case 2:
//                        indexRadioGroup.check(R.id.index_user_radio);
//                        break;
//                    default:
//                        break;
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });

    }

    //初始化各个控件
    private void initViews() {
        indexRadioGroup = (RadioGroup) findViewById(R.id.index_radioGroup);
        viewPagerIndex = (ViewPager) findViewById(R.id.viewPagerIndex);
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
    }
}
