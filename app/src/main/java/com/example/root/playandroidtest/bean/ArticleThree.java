package com.example.root.playandroidtest.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Root on 2018/3/12.
 *
 * 定义了内部类（一共三层，两个内部类一起直接解析数据）
 * 解析网络数据的第二种方法
 */

public class ArticleThree implements Serializable {

    private int errorCode;
    private String errorMsg;
    private ArticleListVO data;

    public static class ArticleListVO {

        private int offset;
        private int size;
        private int total;
        private int pageCount;
        private int curPage;
        private boolean over;
        private List<ArticleBean> datas;

        public static class ArticleBean {
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


        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public int getCurPage() {
            return curPage;
        }

        public void setCurPage(int curPage) {
            this.curPage = curPage;
        }

        public boolean isOver() {
            return over;
        }

        public void setOver(boolean over) {
            this.over = over;
        }

        public void setDatas(List<ArticleBean> datas) {
            this.datas = datas;
        }

        public List<ArticleBean> getDatas() {
            return datas;
        }
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public void setData(ArticleListVO data) {
        this.data = data;
    }

    public ArticleListVO getData() {
        return data;
    }
}
