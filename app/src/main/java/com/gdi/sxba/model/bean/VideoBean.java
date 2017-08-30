package com.gdi.sxba.model.bean;

import java.util.List;

/**
 * Created by gandi on 2017/8/28 0028.
 */

public class VideoBean {

    String allPage;
    List<VideoData> videoData;

    public String getAllPage() {
        return allPage;
    }

    public void setAllPage(String allPage) {
        this.allPage = allPage;
    }

    public List<VideoData> getVideoData() {
        return videoData;
    }

    public void setVideoData(List<VideoData> videoData) {
        this.videoData = videoData;
    }

    public static class VideoData{
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
        return "VideoBean{" +
                "allPage='" + allPage + '\'' +
                ", videoData=" + videoData +
                '}';
    }
}
