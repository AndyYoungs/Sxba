package com.taohua.photo.model.bean;

import java.util.List;

/**
 * Created by gandi on 2017/8/21 0021.
 */

public class TaohuaBean {

    String allpage;
    List<TaohuaData> taohuaDataList;

    public String getAllpage() {
        return allpage;
    }

    public void setAllpage(String allpage) {
        this.allpage = allpage;
    }

    public List<TaohuaData> getTaohuaDataList() {
        return taohuaDataList;
    }

    public void setTaohuaDataList(List<TaohuaData> taohuaDataList) {
        this.taohuaDataList = taohuaDataList;
    }

    public static class TaohuaData{
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
    }











}
