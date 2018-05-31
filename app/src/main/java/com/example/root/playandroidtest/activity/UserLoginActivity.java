package com.example.root.playandroidtest.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.root.playandroidtest.R;
import com.example.root.playandroidtest.app.MyApplication;
import com.example.root.playandroidtest.util.T;
import com.example.root.playandroidtest.view.IconFontTextView;

/**
 * Created by Root on 2018/3/20.
 */

public class UserLoginActivity extends BaseActivity {

    private EditText et_userName;
    private EditText et_userPassword;
    private Button btn_login;
    private Button btn_regist;
    private IconFontTextView it_close;

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        et_userName = (EditText) findViewById(R.id.et_name);
        et_userPassword = (EditText) findViewById(R.id.et_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_regist = (Button) findViewById(R.id.btn_regist);
        doClick();
    }

    private void doClick() {
        //点击登陆按钮响应事件
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = et_userName.getText().toString();
                String userPass = et_userPassword.getText().toString();
                //比较输入的信息是否匹配

            }
        });

        //注册按钮的响应
        btn_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_userName.getText().toString().equals("") || et_userPassword.getText().toString().equals("")) {
                    T.showShort(MyApplication.getContext(), "用户名或密码不能为空");
                } else if (et_userPassword.getText().toString().length() < 6) {
                    T.showShort(MyApplication.getContext(), "密码输入不合法，不能小于6位");
                }
            }
        });
    }

    @Override
    protected void loadData() {

    }
}
