package com.gdi.sxba.network;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Created by gandi on 2017/8/25 0025.
 */

public class JsoupClient {

    String url;
    String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36";
    static JsoupClient instance;

    public static JsoupClient getInstance(String url){
        if (instance==null){
            instance = new JsoupClient(url);
        }
        return instance;
    }

    public JsoupClient(String url) {
        this.url = url;
        instance = this;
    }

    public Document connect() {
        Document doc = null;
        synchronized (this) {
            try {
//
//                Connection conn = Jsoup.connect(url);
//                // 修改http包中的header,伪装成浏览器进行抓取
//                doc =  conn.header("User-Agent", userAgent)
//                        .timeout(3000)//设置超时
//                        .cookie("guide", "1")//一个坑
//                        .followRedirects(false)//是否跳转
//                        .execute().parse();//执行
                doc = Jsoup.connect(url).timeout(10000).get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return doc;
    }
}
