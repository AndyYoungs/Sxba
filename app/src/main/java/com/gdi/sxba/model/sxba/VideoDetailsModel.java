package com.gdi.sxba.model.sxba;

import android.text.TextUtils;
import android.util.Log;

import com.gdi.sxba.contract.CrawlerCallback;
import com.gdi.sxba.contract.UIContract;
import com.gdi.sxba.model.bean.VideoBean;
import com.gdi.sxba.model.context.SxContext;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by gandi on 2017/8/24 0024.
 */

public class VideoDetailsModel implements UIContract.Model {

    private static final String TAG = "VideoDetailsModel";

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
                try {

                    String connetUrl = SxContext.SxBaDomainName+url  + "?qqdrsign=03d16?qqdrsign=0256d";
                    Log.i(TAG, "run: connetUrl------" + connetUrl);
                    Document doc = Jsoup.connect(connetUrl).userAgent(SxContext.userAgent).timeout(10000).get();

                    String src = doc.select("video").attr("src");
                    Log.i(TAG, "run: "+src);
                    if (!TextUtils.isEmpty(src)){
                        callback.onSuccess(src);
                    }else {
                        callback.onError(CrawlerCallback.ErrorNoSize);
                    }

                } catch (Exception e) {
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
