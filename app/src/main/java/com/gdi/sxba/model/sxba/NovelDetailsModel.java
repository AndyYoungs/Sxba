package com.gdi.sxba.model.sxba;

import android.util.Log;

import com.gdi.sxba.contract.CrawlerCallback;
import com.gdi.sxba.contract.UIContract;
import com.gdi.sxba.model.bean.NovelBean;
import com.gdi.sxba.model.context.SxContext;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gandi on 2017/8/28 0028.
 */

public class NovelDetailsModel implements UIContract.Model {

    private static final String TAG = "NovelDetailsModel";
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


                    String connetUrl = SxContext.SxBaDomainName + url;
                    Log.i(TAG, "run: connetUrl------" + connetUrl);
                    Document doc = Jsoup.connect(connetUrl).userAgent(SxContext.userAgent).timeout(10000).get();
                    String html = doc.html().replace("<br />", "$");
                    doc = Jsoup.parse(html);

                    String text = doc.select("td.t_f").text();
                    String[] split = text.split("最大的动力！");

                    if (split.length == 1) {
                        text = split[split.length - 1].replace("$", "\n");
                    } else {
                        text = "";
                        for (int i = 1; i < split.length; i++) {
                            text += split[i].replace("$", "\n");
                        }
                    }
                    text = text.substring(4, text.length() - 1);
                    Log.i(TAG, "run: " + split.length + text);

                    if (text != null) {
                        callback.onSuccess(text);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void setCallback(CrawlerCallback callback) {
        this.callback = callback;
    }
}
