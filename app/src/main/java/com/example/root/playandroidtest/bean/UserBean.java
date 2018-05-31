package com.example.root.playandroidtest.bean;

import android.content.Intent;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Root on 2018/3/19.
 */

public class UserBean implements Serializable {

    private int userId;
    private String userName;
    private String userPass;
    private Object userIcon; //设置用户头像
    private List<Integer> colloctId;  //用户收藏文章的Id

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public Object getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(Object userIcon) {
        this.userIcon = userIcon;
    }

    public List<Integer> getColloctId() {
        return colloctId;
    }

    public void setColloctId(List<Integer> colloctId) {
        this.colloctId = colloctId;
    }
}
