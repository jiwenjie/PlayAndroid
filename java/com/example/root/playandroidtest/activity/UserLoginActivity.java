package com.example.root.playandroidtest.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.playandroidtest.R;
import com.example.root.playandroidtest.app.AppConst;
import com.example.root.playandroidtest.app.MyApplication;
import com.example.root.playandroidtest.bean.UserBean;
import com.example.root.playandroidtest.util.T;
import com.example.root.playandroidtest.view.IconFontTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by Root on 2018/3/20.
 */

public class UserLoginActivity extends BaseActivity {

    private EditText et_userName;
    private EditText et_userPassword;
    private Button btn_login;
    private TextView tv_rigster;
    private IconFontTextView it_close;

    private SharedPreferences share;

    private UserBean bean = new UserBean();

    /**
     * 重写onStart方法，进行一些处理判断
     */
    @Override
    protected void onStart() {
        super.onStart();
        // EventBus.getDefault().register(this);
        share = getSharedPreferences("config", 0);
        bean = (UserBean) getIntent().getSerializableExtra("user");
        if (null != bean) {
            et_userName.setText(bean.getUserName());
            et_userPassword.setText(bean.getUserPass());
        } else {
            et_userName.setText(share.getString("username", ""));
            et_userPassword.setText(share.getString("password", ""));
        }

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);

        et_userName = findViewById(R.id.et_name);
        et_userPassword = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);
        tv_rigster = findViewById(R.id.register);

        it_close = findViewById(R.id.ic_close);
        it_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "返回", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        doClick();
    }

    private void doClick() {
        //点击登陆按钮响应事件
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = et_userName.getText().toString();
                String userPass = et_userPassword.getText().toString();
                //比较输入的信息是否匹配

               // Toast.makeText(getApplicationContext(), "点击事件", Toast.LENGTH_SHORT).show();
                if (TextUtils.isEmpty(userName)) {
                    T.showShort(getApplicationContext(), "用户名不能为空");
                } else if (TextUtils.isEmpty(userPass)) {
                    T.showShort(getApplicationContext(),"密码不能为空");
                } else {
                    //查询是否存在
                    List<UserBean> list = DataSupport.findAll(UserBean.class);
//                    List<UserBean> list = DataSupport
//                            .where("username=" + userName)
//                            .find(UserBean.class);
                    if (list.size() <= 0) {     //没有数据
                        // T.showShort("Size的数量为：" + list.size());
                        T.showShort(getApplicationContext(),"登陆失败");
                        et_userName.setText("");
                        et_userPassword.setText("");
                    } else {
                        for (UserBean users: list) {
                            if (users.getUserName().equals(userName) && users.getUserPass().equals(userPass)) {
                                //登陆成功
                                T.showShort(getApplicationContext(),"登陆成功, 即将跳转主界面");
                                AppConst.IS_LOGIN = true;       //登陆标志改为true
                                sharedPref(userName, userPass);
                                gotoMain();
                            }
                        }
                    }
                }
            }
        });

        //注册按钮的响应
        tv_rigster.setOnClickListener( view -> {
            Intent intent = new Intent(UserLoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       // EventBus.getDefault().unregister(this);
    }

    //序列化本地操作
    private void sharedPref(String username, String password) {
        SharedPreferences.Editor editor = share.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.apply();
    }

    private void gotoMain() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(UserLoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
            }
        }, 2000);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setEditTextMessage(UserBean userBean) {
        if (userBean != null) {
            et_userName.setText(userBean.getUserName());
            et_userPassword.setText(userBean.getUserPass());
            T.showShort(getApplicationContext(), "赋值成功");
        }
    }
}
