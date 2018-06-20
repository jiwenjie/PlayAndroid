package com.example.root.playandroidtest.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.root.playandroidtest.R;
import com.example.root.playandroidtest.app.MyApplication;
import com.example.root.playandroidtest.bean.ArticleBean;
import com.example.root.playandroidtest.util.T;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Root on 2018/3/15.
 *
 * 首页数据的recycleView适配器
 */

public class HomeDataAdapter extends RecyclerView.Adapter<HomeDataAdapter.ViewHolder> {

    private List<ArticleBean> list;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View listView;
        TextView tv_author;
        TextView tv_title;
        TextView tv_time;
        TextView tv_type;

        public ViewHolder(View view) {
            super(view);
            listView = view;
            tv_author = (TextView) view.findViewById(R.id.tv_author);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            tv_type = (TextView) view.findViewById(R.id.tv_type);
        }

    }

    public HomeDataAdapter(List<ArticleBean> beanList) {
        list = beanList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_home_data_item_recycleview, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        //添加监听事件
        holder.listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                ArticleBean bean = list.get(position);
                String link = bean.getLink();
//                T.showShort("链接为：" + link);

            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ArticleBean bean = list.get(position);
        holder.tv_type.setText(bean.getChapterName());
        holder.tv_title.setText(bean.getTitle());
        holder.tv_time.setText(bean.getNiceDate());
        holder.tv_author.setText(bean.getAuthor());

        Log.d("HomeAdapter-------", "title is" + bean.getTitle());
        Log.d("HomeAdapter-------", "author is" + bean.getAuthor());
        Log.d("HomeAdapter-------", "Link is" + bean.getLink());
        //Log.d("MainActivity", "publishTime is" + publishTime);
        Log.d("HomeAdapter-------", "niceDate is" + bean.getNiceDate());
        Log.d("HomeAdapter-------", "chapterName is" + bean.getChapterName());
    }

    @Override
    public int getItemCount() {

        Log.d("HomeAdapter-------", "ItemCount is" + list.size());
        return list.size();

    }
}
