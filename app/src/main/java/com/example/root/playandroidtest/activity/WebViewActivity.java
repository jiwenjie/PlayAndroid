package com.example.root.playandroidtest.activity;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.root.playandroidtest.R;
import com.example.root.playandroidtest.app.MyApplication;
import com.example.root.playandroidtest.fragment.WebViewFragment;
import com.example.root.playandroidtest.util.ActivityUtils;
import com.example.root.playandroidtest.util.T;
import com.example.root.playandroidtest.util.WebViewSetting;
import com.example.root.playandroidtest.view.IconFontTextView;

import static com.example.root.playandroidtest.app.MyApplication.getContext;

/**
 * Created by Root on 2018/3/18.
 * 有关点击recycleView的子项跳转到网页的代码
 */

public class WebViewActivity extends BaseActivity {

    public static final String WEB_URL = "web_url";

    private String mUrl = "";

    private WebView webView;

    private IconFontTextView tv_return;
    private IconFontTextView webView_other;
    private TextView webView_title;

    //有关底部菜单的控件初始化
    private Dialog mWebViewDialog;
    private CardView btn_open_share;
    private CardView btn_copy_link;
    private CardView btn_cancel;

    private WebViewFragment mWebViewFragment;

    public static void runActivity(Context context, String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(WEB_URL, url);
        context.startActivity(intent);
    }

    @Override
    public void init() {
        super.init();
        mUrl = getIntent().getStringExtra(WEB_URL);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_webview);
        mWebViewFragment = new WebViewFragment();
        tv_return = (IconFontTextView) findViewById(R.id.webView_return);
        webView_other = (IconFontTextView) findViewById(R.id.WebView_other);
        webView_title = (TextView) findViewById(R.id.WebView_title);
        mWebViewDialog = new Dialog(this, R.style.BottomDialog);
        doClick();
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mWebViewFragment, R.id.webView_Play);
    }

    @Override
    protected void onStart() {
        super.onStart();
        webView = mWebViewFragment.getWebView();
        WebViewSetting.setWebView(webView, mUrl);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.destroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();
                return true;
            } else {
                finish();//退出程序
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    //点击响应事件
    private void doClick() {
        tv_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        webView_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDialog();
            }
        });
    }

    //底部菜单栏，弹出一个选择，提供是选择拍照还是从相册选择图片
    private void setDialog() {
//        mWebViewDialog = new Dialog(this, R.style.BottomDialog);
        LinearLayout root = (LinearLayout) LayoutInflater.from(this).inflate(
                R.layout.bottom_webview_dialog, null);
        //初始化视图
        btn_open_share = root.findViewById(R.id.btn_open_share);
        btn_copy_link = root.findViewById(R.id.btn_copy_link);
        btn_cancel = root.findViewById(R.id.btn_cancel);
        mWebViewDialog.setContentView(root);
        Window dialogWindow = mWebViewDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
//        dialogWindow.setWindowAnimations(R.style.dialogstyle); // 添加动画
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标
        lp.width = (int) getResources().getDisplayMetrics().widthPixels; // 宽度
        root.measure(0, 0);
        lp.height = root.getMeasuredHeight();

        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);
        mWebViewDialog.show();
        BottomClick();
    }

    //打开网页后点击更多按钮出来的分享底部菜单
    private void BottomClick() {
        //点击分享按钮的响应
        btn_open_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                T.showShort(getApplicationContext(), "你点击了分享按钮！");
                mWebViewDialog.dismiss();
            }
        });

        //点击复制链接的响应
        btn_copy_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String copyLink = mUrl; //获取到该网页的链接

            }
        });

        //取消按钮的响应
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击了取消按钮
                mWebViewDialog.dismiss();
            }
        });
    }

    @Override
    protected void loadData() {

    }
}
