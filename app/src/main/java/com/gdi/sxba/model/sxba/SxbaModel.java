package com.gdi.sxba.model.sxba;

import android.util.Log;

import com.gdi.sxba.contract.CrawlerCallback;
import com.gdi.sxba.contract.UIContract;
import com.gdi.sxba.model.bean.SxBean;
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

public class SxbaModel implements UIContract.Model {

    private static final String TAG = "SxbaModel";

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
                    SxBean sxBean = new SxBean();
                    List<SxBean.SxData> sxDataList = new ArrayList<>();

                    String connetUrl = url + page + SxContext.Suffix+"?qqdrsign=03d16";
                    Log.i(TAG, "run: connetUrl------" + connetUrl);
//                    JsoupClient jsoupClient = JsoupClient.getInstance("http://s8zhibo.com/forum-158-2.html");
//                    Document doc = jsoupClient.connect();
                    Document doc = Jsoup.connect(connetUrl).timeout(10000).get();
                    Elements tbody = doc.select("tbody[id]");

                    for (Element element : tbody) {
                        String img = null;

                        String title = element.select("a.s.xst").text();
                        Elements td = element.select("td.cl");
                        String url = td.select("a").attr("href");

                        Elements imgE = td.select("img");
                        if (imgE.size() > 0) {
                            img = imgE.get(0).attr("src");

                            SxBean.SxData sxData = new SxBean.SxData();
                            sxData.setTitle(title);
                            sxData.setUrl(url);
                            sxData.setImg(img);
                            sxDataList.add(sxData);
                            Log.i(TAG, "sxData: " + sxData.toString());
                        }
                    }
                    String allPage = doc.select("a.last").first().text();
                    String[] split = allPage.split(" ");

                    sxBean.setAllpage(split[split.length - 1]);
                    sxBean.setSxDataList(sxDataList);
                    Log.i(TAG, "sxBean: " + sxBean.toString());

                    if (sxDataList.size() > 0) {
                        callback.onSuccess(sxBean);
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
