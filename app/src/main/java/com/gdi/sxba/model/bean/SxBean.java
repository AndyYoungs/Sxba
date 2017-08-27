package com.gdi.sxba.model.bean;

import java.util.List;

/**
 * Created by gandi on 2017/8/21 0021.
 */

public class SxBean {

    String allpage;
    List<SxData> sxDataList;

    public String getAllpage() {
        return allpage;
    }

    public void setAllpage(String allpage) {
        this.allpage = allpage;
    }

    public List<SxData> getSxDataList() {
        return sxDataList;
    }

    public void setSxDataList(List<SxData> sxDataList) {
        this.sxDataList = sxDataList;
    }

    public static class SxData{
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
            return "SxData{" +
                    "title='" + title + '\'' +
                    ", url='" + url + '\'' +
                    ", img='" + img + '\'' +
                    '}';
        }
    }


    @Override
    public String toString() {
        return "SxBean{" +
                "allpage='" + allpage + '\'' +
                ", sxDataList=" + sxDataList +
                '}';
    }
}
