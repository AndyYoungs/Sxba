package com.gdi.sxba.model.bean;

import java.util.List;

/**
 * Created by gandi on 2017/8/28 0028.
 */

public class NovelBean {

    String allPage;
    List<NovelData> novelData;

    public String getAllPage() {
        return allPage;
    }

    public void setAllPage(String allPage) {
        this.allPage = allPage;
    }

    public List<NovelData> getNovelData() {
        return novelData;
    }

    public void setNovelData(List<NovelData> novelData) {
        this.novelData = novelData;
    }

    public static class NovelData{
        String url;
        String title;
        String classify;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getClassify() {
            return classify;
        }

        public void setClassify(String classify) {
            this.classify = classify;
        }

        @Override
        public String toString() {
            return "NovelData{" +
                    "url='" + url + '\'' +
                    ", title='" + title + '\'' +
                    ", classify='" + classify + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "NovelBean{" +
                "allPage=" + allPage +
                ", novelData=" + novelData +
                '}';
    }
}
