package com.example.root.playandroidtest.bean;

import java.io.Serializable;

/**
 * user：Root
 * desc：
 */

public class ArticleBean implements Serializable {
    /**
     * id : 2207
     "apkLink":"",
     "author":" jokermonn",
     "chapterId":100,
     "chapterName":"RecyclerView",
     "collect":false,
     "courseId":13,
     "desc":"",
     "envelopePic":"",
     "id":2473,
     "link":"https://juejin.im/post/5a5d3d9b518825734216e1e8",
     "niceDate":"5小时前",
     "origin":"",
     "projectLink":"",
     "publishTime":1520819459000,
     "superChapterId":151,
     "superChapterName":"5.+高新技术",
     "title":"手摸手第二弹，可视化 RecyclerView 缓存机制",
     "visible":1,
     "zan":0
     *
     */

    private int id;
    private String title;
    private int chapterId;
    private String chapterName;
    private Object envelopePic;
    private String link;
    private String author;
    private Object origin;
    private int originId;
    private long publishTime;
    private Object zan;
    private Object desc;
    private int visible;
    private String niceDate;
    private int courseId;
    private boolean collect;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public Object getEnvelopePic() {
        return envelopePic;
    }

    public void setEnvelopePic(Object envelopePic) {
        this.envelopePic = envelopePic;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Object getOrigin() {
        return origin;
    }

    public void setOrigin(Object origin) {
        this.origin = origin;
    }

    public int getOriginId() {
        return originId;
    }

    public void setOriginId(int originId) {
        this.originId = originId;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }

    public Object getZan() {
        return zan;
    }

    public void setZan(Object zan) {
        this.zan = zan;
    }

    public Object getDesc() {
        return desc;
    }

    public void setDesc(Object desc) {
        this.desc = desc;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public String getNiceDate() {
        return niceDate;
    }

    public void setNiceDate(String niceDate) {
        this.niceDate = niceDate;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public boolean isCollect() {
        return collect;
    }

    public void setCollect(boolean collect) {
        this.collect = collect;
    }
}
