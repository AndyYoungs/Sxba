package com.gdi.sxba.model.bean;

import java.util.List;

/**
 * Created by gandi on 2017/8/21 0021.
 */

public class PhotoBean {

    String allpage;
    List<photoData> photoDataList;

    public String getAllpage() {
        return allpage;
    }

    public void setAllpage(String allpage) {
        this.allpage = allpage;
    }

    public List<photoData> getphotoDataList() {
        return photoDataList;
    }

    public void setphotoDataList(List<photoData> photoDataList) {
        this.photoDataList = photoDataList;
    }

    public static class photoData{
        String title;
        String url;
        String img;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        @Override
        public String toString() {
            return "photoData{" +
                    "title='" + title + '\'' +
                    ", url='" + url + '\'' +
                    ", img='" + img + '\'' +
                    '}';
        }
    }


    @Override
    public String toString() {
        return "PhotoBean{" +
                "allpage='" + allpage + '\'' +
                ", photoDataList=" + photoDataList +
                '}';
    }
}
