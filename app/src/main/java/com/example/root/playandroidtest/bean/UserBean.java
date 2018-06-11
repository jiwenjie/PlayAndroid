package com.example.root.playandroidtest.bean;

import android.content.Intent;

import org.litepal.crud.DataSupport;
import org.w3c.dom.NameList;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Root on 2018/3/19.
 *
 * 继承DataSupport，可以更便捷的完成增删改查操作
 */

public class UserBean extends DataSupport implements Serializable {

    //private int userId;
    private String userName;
    private String userPass;
    private Object userIcon; //设置用户头像
    private List<Integer> colloctId;  //用户收藏文章的Id

    public UserBean() {

    }

    public UserBean(String name, String pass) {
        this.userName = name;
        this.userPass = pass;
    }

//    public UserBean(int id, String name, String pass) {
//        this.userId = id;
//        this.userName = name;
//        this.userPass = pass;
//    }

    public UserBean(String name, String pass, Object icon, List<Integer> colloctId) {
        this.colloctId = colloctId;
        this.userIcon = icon;
        //this.userId = id;
        this.userName = name;
        this.userPass = pass;
    }

//    public int getUserId() {
//        return userId;
//    }
//
//    public void setUserId(int userId) {
//        this.userId = userId;
//    }

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
