package com.taohua.photo.model;

import android.text.TextUtils;
import android.util.Log;

import com.taohua.photo.contract.CrawlerCallback;
import com.taohua.photo.contract.TaohuaContract;
import com.taohua.photo.model.context.TaohuaContext;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gandi on 2017/8/23 0023.
 */

public class TaohuaPhotoModel implements TaohuaContract.Model {

    private static final String TAG = "TaohuaPhotoModel";

    String url;
    CrawlerCallback callback;

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void startCrawler(final int page) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<String> photoList = new ArrayList<>();
                try {
                    Document doc = Jsoup.connect(TaohuaContext.DomainName + url ).timeout(10000).get();
                    Elements img = doc.select("img.zoom");
                    for (Element element : img) {
                        String src = element.attr("file");
                        if (TextUtils.isEmpty(src)){
                            src = element.attr("src");
                        }
                        Log.i(TAG, "run: " + src);
                        photoList.add(src);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (photoList.size()>0){
                    callback.onSuccess(photoList);
                }else{
                    callback.onError();
                }
            }
        }).start();
    }

    public void setCallback(CrawlerCallback callback) {
        this.callback = callback;
    }
}
