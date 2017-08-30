package com.gdi.sxba.model.sxba;

import android.util.Log;

import com.gdi.sxba.contract.CrawlerCallback;
import com.gdi.sxba.contract.UIContract;
import com.gdi.sxba.model.bean.PhotoBean;
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

public class PhotoModel implements UIContract.Model {

    private static final String TAG = "PhotoModel";

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
                    PhotoBean photoBean = new PhotoBean();
                    List<PhotoBean.photoData> photoDataList = new ArrayList<>();

                    String connetUrl = url + page + SxContext.Suffix + "?qqdrsign=03d16?qqdrsign=0256d";
                    Log.i(TAG, "run: connetUrl------" + connetUrl);
                    Document doc = Jsoup.connect(connetUrl).userAgent(SxContext.userAgent).timeout(10000).get();
                    Elements tbody = doc.select("tbody[id]");

                    for (Element element : tbody) {
                        String img = null;

                        String title = element.select("a.s.xst").text();
                        Elements td = element.select("td.cl");
                        String url = td.select("a").attr("href");

                        Elements imgE = td.select("img");
                        if (imgE.size() > 0) {
                            img = imgE.get(0).attr("src");

                            PhotoBean.photoData photoData = new PhotoBean.photoData();
                            photoData.setTitle(title);
                            photoData.setUrl(url);
                            photoData.setImg(img);
                            photoDataList.add(photoData);
//                            Log.i(TAG, "photoData: " + photoData.toString());
                        }
                    }
                    String allPage = doc.select("a.last").first().text();
                    String[] split = allPage.split(" ");

                    photoBean.setAllpage(split[split.length - 1]);
                    photoBean.setphotoDataList(photoDataList);
                    Log.i(TAG, "photoBean: " + photoBean.toString());

                    if (photoDataList.size() > 0) {
                        callback.onSuccess(photoBean);
                    } else {
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
