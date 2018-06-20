package com.example.root.playandroidtest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.root.playandroidtest.R;
import com.example.root.playandroidtest.bean.UserBean;
import com.example.root.playandroidtest.util.T;

import org.greenrobot.eventbus.EventBus;

public class RegisterActivity extends BaseActivity {

    private EditText et_userName;
    private EditText et_pass;
    private EditText et_confirmPass;
    private Button btn_register;
    private UserBean userBean = new UserBean();

    private Handler handler;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
        handler = new Handler();
        initControl();
    }

    private void initControl() {
        et_userName = findViewById(R.id.et_name);
        et_pass = findViewById(R.id.et_password);
        et_confirmPass = findViewById(R.id.et_confirmPass);
        btn_register = findViewById(R.id.btn_register);

        T.showShort("注册测试部分");
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //T.showLong(getBaseContext(), "点击事件");
                String userName = et_userName.getText().toString();
                String password = et_pass.getText().toString();
                String confirm_pass = et_confirmPass.getText().toString();

                if (TextUtils.isEmpty(userName)) {
                    T.showShort(getBaseContext(),"用户名不能为空");
                } else if (TextUtils.isEmpty(password)) {
                    T.showShort(getBaseContext(),"密码不能为空");
                } else if (TextUtils.isEmpty(confirm_pass)) {
                    T.showShort(getBaseContext(),"还没有确认信息");
                } else if (!password.equals(confirm_pass)) {
                    T.showShort(getBaseContext(),"两次密码输入不同");
                } else {
                    registerUser(userName, password);
                }
            }
        });

        //doOnclick();
    }

    @Override
    protected void loadData() {

    }

    //进行用户注册的操作（完成之后跳转登陆界面）
    private void registerUser(String name, String pass) {
        userBean = new UserBean();
        userBean.setUserName(name);
        userBean.setUserPass(pass);
        userBean.save();      //存储失败之后抛出一个异常

        T.showShort(getApplicationContext(), "注册成功，两秒后跳转登陆界面");

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(RegisterActivity.this, UserLoginActivity.class);
                intent.putExtra("user", userBean);
                startActivity(intent);
                //EventBus.getDefault().post(userBean);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }, 2000);
    }

}
