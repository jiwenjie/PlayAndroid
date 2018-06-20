package com.example.root.playandroidtest.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.root.playandroidtest.R;
import com.example.root.playandroidtest.activity.MainActivity;
import com.example.root.playandroidtest.adapter.ArticleListAdapter;
import com.example.root.playandroidtest.api.WanService;
import com.example.root.playandroidtest.app.AppConst;
import com.example.root.playandroidtest.app.MyApplication;
import com.example.root.playandroidtest.bean.ArticleBean;
import com.example.root.playandroidtest.bean.Book;
import com.example.root.playandroidtest.bean.TypeChildrenBean;
import com.example.root.playandroidtest.bean.TypeTagVO;
import com.example.root.playandroidtest.util.ParserJsonWebData;
import com.example.root.playandroidtest.util.T;
import com.example.root.playandroidtest.util.Util;
import com.example.root.playandroidtest.util.WebConnectUtil;
import com.google.android.flexbox.FlexboxLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

import static com.example.root.playandroidtest.app.AppConst.TOTAL_COUNTER;

/**
 * Created by Root on 2018/3/13.
 */

public class SearchFragment extends Fragment {

    private TabLayout mTabLayout;
    private FlexboxLayout flexboxLayout;
    private TextView no_Data;
    private RecyclerView recycleView;
    private int int_page = 0;  //访问网络数据的当前代表页（从0开始）
    private int mCurrentCounter = 0;

    private List<ArticleBean> beans;  //存放搜索到的数据

    public SearchFragment() { }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        initViews(rootView);

        return rootView;
    }

    private void initViews(View rootView) {
        mTabLayout = (TabLayout) rootView.findViewById(R.id.test_tabLayout);
        flexboxLayout = (FlexboxLayout) rootView.findViewById(R.id.flexbox_layout);
        recycleView = (RecyclerView) rootView.findViewById(R.id.search_recycleView);
        no_Data = (TextView) rootView.findViewById(R.id.not_get_data);
        LinearLayoutManager manager = new LinearLayoutManager(MyApplication.getContext());
        recycleView.setLayoutManager(manager);
        String url = AppConst.getUrl(int_page);

        getSearchItemData(url);
        getTagFirstData();


//        TabLayoutClick();
    }

    private void getTagFirstData() {
        WebConnectUtil.sendRequestWidthOkHttp(WanService.TagSearchData, new okhttp3.Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //获取服务器返回的具体内容
                final String responseData = response.body().string();
                List<TypeTagVO> voList = ParserJsonWebData.parseTagFirstWithJSON(responseData);
                Map<Integer, JSONArray> beanList = ParserJsonWebData.parseTagSecondWithJSON(responseData);

                if (voList.size() <= 0 || beanList.size() <= 0) {
                    T.showShort(getContext(),"获取网络数据失败");
                    recycleView.setVisibility(View.GONE);
                    no_Data.setVisibility(View.VISIBLE);
                    return;
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setTagData(voList);
                        TabLayoutClick(beanList);
//                        T.showShort(getContext(), "一级标签设置完成");
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {
                T.showShort(getContext(),"获取网络数据失败");
                recycleView.setVisibility(View.GONE);
                no_Data.setVisibility(View.VISIBLE);
            }
        });
    }

    //调用该方法，设置Tag一级标签
    private void setTagData(List<TypeTagVO> list) {
        for (TypeTagVO vo : list) {
            mTabLayout.addTab(mTabLayout.newTab().setText(vo.getName()));
        }
    }

    //TabLayout的点击事件实现
    private void TabLayoutClick(Map<Integer, JSONArray> list) {
        //实现点击事件的监听
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            if (tab == null) return;
            //这里使用到反射，拿到Tab对象后获取Class
            Class c = tab.getClass();
            try {
                //Filed “字段、属性”的意思,c.getDeclaredField 获取私有属性。
                //"mView"是Tab的私有属性名称(可查看TabLayout源码),类型是 TabView,TabLayout私有内部类。
                Field field = c.getDeclaredField("mView");
                //值为 true 则指示反射的对象在使用时应该取消 Java 语言访问检查。值为 false 则指示反射的对象应该实施 Java 语言访问检查。
                //如果不这样会报如下错误
                // java.lang.IllegalAccessException:
                //Class com.test.accessible.Main
                //can not access
                //a member of class com.test.accessible.AccessibleTest
                //with modifiers "private"
                field.setAccessible(true);
                final View view = (View) field.get(tab);
                if (view == null) return;
                view.setTag(i);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = (int) view.getTag();
                        //这里就可以根据业务需求处理点击事件了
                        JSONArray array = list.get(position);
                        flexboxLayout.removeAllViews();
                        doClick(array);
                        flexboxLayout.setVisibility(View.VISIBLE);

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void doClick(JSONArray array) {
        for (int i = 0; i < array.length(); i ++) {
            try {
                JSONObject thirdObject = array.getJSONObject(i);
                Book model = new Book();
                model.setId(i);
                model.setName(thirdObject.getString("name"));
                flexboxLayout.addView(createNewFlexItemTextView(model));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    /**
     * 动态创建TextView
     * @param book
     * @return
     */
    private TextView createNewFlexItemTextView(final Book book) {
        TextView textView = new TextView(getContext());
        textView.setGravity(Gravity.CENTER);
        textView.setText(book.getName());
        textView.setTextSize(15);
        textView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        textView.setBackgroundResource(R.drawable.background);
        textView.setTag(book.getId());
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.e("MainActivity", book.getName());
                //逻辑处理
//                getSearchItemData(book.getName());
                flexboxLayout.setVisibility(View.GONE);
                //调用搜索方法，匹配相同位类别相同，chapter Name
                String url = AppConst.getUrl(int_page);
                getSearchItemData(url);
                Toast.makeText(getContext(), book.getName(), Toast.LENGTH_SHORT).show();


            }
        });
        int padding = Util.dpToPixel(getContext(), 4);
        int paddingLeftAndRight = Util.dpToPixel(getContext(), 8);
        ViewCompat.setPaddingRelative(textView, paddingLeftAndRight, padding, paddingLeftAndRight, padding);
        FlexboxLayout.LayoutParams layoutParams = new FlexboxLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        int margin = Util.dpToPixel(getContext(), 6);
        int marginTop = Util.dpToPixel(getContext(), 16);
        layoutParams.setMargins(margin, marginTop, margin, 0);
        textView.setLayoutParams(layoutParams);
        return textView;
    }

    //根据Tag的类别和文章做出比较做出比较，一样的加载出来
    private void getSearchItemData(String address) {
        WebConnectUtil.sendRequestWidthOkHttp(address, new okhttp3.Callback(){
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //获取服务器返回的具体内容
                final String responseData = response.body().string();
                getActivity().runOnUiThread(
                        new Runnable() {
                            @Override
                            public void run() {
                                List<ArticleBean> getDataList = ParserJsonWebData.parseJsonWithGSON(responseData);
//                       List<ArticleBean> getDataList = ParserJsonWebData.parseJSONWITHJSONObject(responseData);
                                if (getDataList==null) {
                                    T.showShort(getContext(),"搜索数据对象为空");
                                }
                                //找出当前的一共多少个对象（List中有多少个对象）
//                                int size = getDataList.size();
//                        T.showShort(MyApplication.getContext(), "一共有" + size + "个对象");
//                                for (ArticleBean bean : getDataList) {
//                                    if (bean.getChapterName().equals(chapterName));
//                                    beans.add(bean);
//                                }

                                ArticleListAdapter adapter = new ArticleListAdapter(MyApplication.getContext(), getDataList);
                                //设置item的动画效果
                                adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
                                //设置重复执行动画
                                adapter.isFirstOnly(false);
                                //预加载功能，设置滑倒第几个item的时候开始回掉接口
                                adapter.setAutoLoadMoreSize(5);
                                loadMore(adapter);
                                //设置适配器
                                recycleView.setAdapter(adapter);
                            }
                        });
            }


            @Override
            public void onFailure(Call call, IOException e) {
                T.showShort(getContext(),"获取网络数据失败");
                recycleView.setVisibility(View.GONE);
                no_Data.setVisibility(View.VISIBLE);
            }
        });


    }

    //调用了接口回掉方法（加载更多）
    private void loadMore(ArticleListAdapter listAdapter) {

        listAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            boolean isErr = true;

            @Override public void onLoadMoreRequested() {
                recycleView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mCurrentCounter >= TOTAL_COUNTER) {
                            //一个界面的数据加载完毕
                            listAdapter.loadMoreEnd();
                        } else {
                            if (isErr) {
                                getIndexMoreData(listAdapter);
                                mCurrentCounter = listAdapter.getData().size();
                                //主动调用加载完成，停止加载
                                listAdapter.loadMoreComplete();

                            } else {
                                //获取更多数据失败
                                isErr = false;
                                listAdapter.loadMoreFail();
                            }

                        }
                    }

                }, 2000);
            }
        });
    }

    //加载更多数据的时候调用该方法
    protected void getIndexMoreData(ArticleListAdapter listAdapter) {
        int_page = 1 + int_page;
        String address = AppConst.getUrl(int_page);
        WebConnectUtil.sendRequestWidthOkHttp(address, new okhttp3.Callback(){
            @Override
            public void onResponse(Call call, Response response) throws IOException {

                //获取服务器返回的具体内容
                final String responseData = response.body().string();
                List<ArticleBean> getDataList = ParserJsonWebData.parseJsonWithGSON(responseData);
//                       List<ArticleBean> getDataList = ParserJsonWebData.parseJSONWITHJSONObject(responseData);
                if (getDataList==null) {
                    T.showShort(getContext(),"搜索数据对象为空");
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (getDataList.size() != 0) {
                            listAdapter.addData(getDataList);
                            listAdapter.loadMoreComplete();
                        }else {
                            listAdapter.loadMoreEnd();
                        }
                    }
                });

            }

            @Override
            public void onFailure(Call call, IOException e) {
                //获取网络数据失败之后检查是否有网络连接
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        initReceiver();
                        listAdapter.loadMoreFail();
                        T.showShort(getContext(),"没有获取到网络数据");
                    }
                });
            }
        });
    }

}
