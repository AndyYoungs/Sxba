package com.gdi.sxba.model.sxba;

import android.text.TextUtils;
import android.util.Log;

import com.gdi.sxba.contract.CrawlerCallback;
import com.gdi.sxba.contract.UIContract;
import com.gdi.sxba.model.context.SxContext;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gandi on 2017/8/27 0027.
 */

public class PhotoDetailsModel implements UIContract.Model {

    private static final String TAG = "PhotoDetailsModel";
    
    String url;
    CrawlerCallback callback;

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void startCrawler(int page) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<String> photoList = new ArrayList<>();
                try {
                    Document doc = Jsoup.connect(SxContext.SxBaDomainName + url ).userAgent(SxContext.userAgent).timeout(10000).get();
                    Elements imgs = doc.select("img.zoom");
                    for (Element element : imgs) {
                        String img = null;

                        img = element.attr("file");
//                        if (TextUtils.isEmpty(img)){
//                            img = element.attr("src");
//                        }
                        Log.i(TAG, "run: "+img);
                        if (!TextUtils.isEmpty(img)){
                            photoList.add(img);
                        }

                    }
                    if (photoList.size() > 0) {
                        callback.onSuccess(photoList);
                    }else{
                        callback.onError(CrawlerCallback.ErrorNoSize);
                    }
                }catch (Exception e){
                    callback.onError(CrawlerCallback.ErrorException);
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void setCallback(CrawlerCallback callback) {
        this.callback = callback;
    }
}
