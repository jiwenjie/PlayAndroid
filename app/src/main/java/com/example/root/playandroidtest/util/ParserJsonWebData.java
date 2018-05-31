package com.example.root.playandroidtest.util;

import android.util.Log;

import com.example.root.playandroidtest.app.AppConst;
import com.example.root.playandroidtest.app.MyApplication;
import com.example.root.playandroidtest.bean.ArticleBean;
import com.example.root.playandroidtest.bean.ArticleThree;
import com.example.root.playandroidtest.bean.TypeChildrenBean;
import com.example.root.playandroidtest.bean.TypeTagVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Root on 2018/3/15.
 * 实际解析网络数据的方法封装
 */

public class ParserJsonWebData {
    //使用JSON来解析数据  (使用把三层包装分开分别定义的方式)
    public static List<ArticleBean> parseJSONWITHJSONObject(String jsonData) {
        List<ArticleBean> listsJson = null;
        try {
            JSONObject object = new JSONObject(jsonData); //把传递来的第一层数据包装成一个JSONObject对象
            String data = object.getString("data"); //获取第二层的json数据
            JSONObject second = new JSONObject(data); //把第二层的数据包装成一个JSONObject对象
            String thirdData = second.getString("datas"); //获取第三层的json数据
            JSONArray third = new JSONArray(thirdData); //第三层是一个数组的形式，所以用jsonArray来包装
            //String title = third.getString("author");

            for (int i = 0; i < third.length(); i++) {
                    ArticleBean beans = new ArticleBean();
                    JSONObject jsonObject = third.getJSONObject(i);
                    beans.setTitle(jsonObject.getString("title"));
                    beans.setAuthor(jsonObject.getString("author"));
                    beans.setLink(jsonObject.getString("link"));
                    beans.setChapterName(jsonObject.getString("chapterName"));
                    beans.setNiceDate(jsonObject.getString("niceDate"));
                    listsJson.add(beans);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listsJson;

    }

    //通过GSON方法来解析网络数据（使用内部类的实体类）
    public static List<ArticleBean> parseJsonWithGSON(String responseData) {

//        FinallyTest jsonBean = gson.fromJson(responseData,
//                new TypeToken<List<FinallyTest>>(){}.getType());
        Gson gson = new Gson();
        List<ArticleBean> GsonLists = new ArrayList<>();

        java.lang.reflect.Type type = new TypeToken<ArticleThree>() {}.getType();
        ArticleThree jsonBean = gson.fromJson(responseData, type);

        int size = jsonBean.getData().getDatas().size();

        for (int i = 0; i < size; i++) {
            ArticleBean bean = new ArticleBean();

            bean.setAuthor(jsonBean.getData().getDatas().get(i).getAuthor());
            bean.setTitle(jsonBean.getData().getDatas().get(i).getTitle());
            bean.setLink(jsonBean.getData().getDatas().get(i).getLink());
            //publishTime = jsonBean.getData().getDatas().get(i).getPublishTime();
            bean.setNiceDate(jsonBean.getData().getDatas().get(i).getNiceDate());
            bean.setChapterName(jsonBean.getData().getDatas().get(i).getChapterName());

            GsonLists.add(bean);
            //打印日志信息
            Log.d("MainActivity", "title is" + bean.getTitle());
            Log.d("MainActivity", "author is" + bean.getAuthor());
            Log.d("MainActivity", "Link is" + bean.getLink());
            //Log.d("MainActivity", "publishTime is" + publishTime);
            Log.d("MainActivity", "niceDate is" + bean.getNiceDate());
            Log.d("MainActivity", "chapterName is" + bean.getChapterName());
        }
        return GsonLists;
    }


    //解析第二部分的Tab标签数据
    //使用json解析数据
    public static List<TypeTagVO> parseTagFirstWithJSON(String responseData) {
//        List<TypeChildrenBean> beanList = new ArrayList<>();
        List<TypeTagVO> voList = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(responseData); //把传递来的第一层数据包装成一个JSONObject对象
            String data = object.getString("data"); //获取第二层的json数据
            JSONArray second = new JSONArray(data);
            for (int i = 0; i < second.length(); i ++) {
                JSONObject secondObject = second.getJSONObject(i);
                TypeTagVO vo = new TypeTagVO();
                vo.setName(secondObject.getString("name"));
                Log.d("MainActivity", "Name is" + vo.getName());
//                String thirdChild = secondObject.getString("children");
//                JSONArray fuChild = new JSONArray(thirdChild);
//                for (int j = 0; j < fuChild.length(); j ++) {
//                    JSONObject thirdObject = fuChild.getJSONObject(j);
//                    TypeChildrenBean childrenBean = new TypeChildrenBean();
//                    childrenBean.setName(thirdObject.getString("name"));
//                    Log.d("MainActivity", "Name is--------------" + childrenBean.getName());
//                    beanList.add(childrenBean);
//                }
                voList.add(vo);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return voList;
    }

    //解析Tab标签下的二级标签数据
    public static Map<Integer,JSONArray> parseTagSecondWithJSON(String responseData) {
//        List<TypeChildrenBean> beanList = new ArrayList<>();
        Map<Integer,JSONArray> map = new HashMap<>();
//        List<TypeTagVO> voList = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(responseData); //把传递来的第一层数据包装成一个JSONObject对象
            String data = object.getString("data"); //获取第二层的json数据
            JSONArray second = new JSONArray(data);
            for (int i = 0; i < second.length(); i ++) {
                JSONObject secondObject = second.getJSONObject(i);
//                TypeTagVO vo = new TypeTagVO();
//                vo.setName(secondObject.getString("name"));
//                Log.d("MainActivity", "Name is" + vo.getName());

                String thirdChild = secondObject.getString("children");
                JSONArray fuChild = new JSONArray(thirdChild);
//                for (int j = 0; j < fuChild.length(); j ++) {
//                    JSONObject thirdObject = fuChild.getJSONObject(j);
//                    TypeChildrenBean childrenBean = new TypeChildrenBean();
//                    childrenBean.setName(thirdObject.getString("name"));
//                    Log.d("MainActivity", "Name is--------------" + childrenBean.getName());
//                    beanList.add(childrenBean);
//                }
                map.put(i, fuChild);
//                voList.add(vo);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


    //通过第一种方法解析数据
    public static List<ArticleBean> getBeanListOne(String data) {
        return parseJSONWITHJSONObject(data);
    }

    //通过第二种方法解析数据
    public static List<ArticleBean> getBeanListTwo(String data) {
//        List<ArticleBean> parseList = parseJsonWithGSON(data);
        return parseJsonWithGSON(data);
    }
}