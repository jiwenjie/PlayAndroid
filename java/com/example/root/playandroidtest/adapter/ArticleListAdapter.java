package com.example.root.playandroidtest.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.root.playandroidtest.R;
import com.example.root.playandroidtest.activity.WebViewActivity;
import com.example.root.playandroidtest.app.AppConst;
import com.example.root.playandroidtest.app.MyApplication;
import com.example.root.playandroidtest.bean.ArticleBean;
import com.example.root.playandroidtest.util.T;


import java.util.List;


public class ArticleListAdapter extends BaseQuickAdapter<ArticleBean, BaseViewHolder> {
    //public class ArticleListAdapter extends BaseQuickAdapter<ArticleBean, > {
    private Context mContext;

    public ArticleListAdapter(Context context, @Nullable List<ArticleBean> data) {
        super(R.layout.layout_home_data_item_recycleview, data);
        mContext = context;
    }

    @Override
    protected void convert(final BaseViewHolder holder, final ArticleBean bean) {
        holder.setText(R.id.tv_title, Html.fromHtml(bean.getTitle()))
                .setText(R.id.tv_author, bean.getAuthor())
                .setText(R.id.tv_time, bean.getNiceDate())
                .setText(R.id.tv_type, bean.getChapterName());

        TextView tvCollect = (TextView) holder.getView(R.id.tv_collect);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = bean.getLink();
                WebViewActivity.runActivity(MyApplication.getContext(), link);

            }
        });

    }

}