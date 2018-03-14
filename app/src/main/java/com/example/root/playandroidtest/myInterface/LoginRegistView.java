package com.example.root.playandroidtest.myInterface;


/**
 * user：Root
 * desc：登录注册
 */

public interface LoginRegistView {


    void showProgress(String tipString);

    void hideProgress();

//    void loginSuccess(UserBean user);
//
//    void registerSuccess(UserBean user);

    void loginFail();

    void registerFail();

}
