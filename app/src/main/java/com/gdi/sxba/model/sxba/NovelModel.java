package com.gdi.sxba.model.sxba;

import android.util.Log;

import com.gdi.sxba.contract.CrawlerCallback;
import com.gdi.sxba.contract.UIContract;
import com.gdi.sxba.model.bean.NovelBean;
import com.gdi.sxba.model.context.SxContext;
import com.gdi.sxba.network.JsoupClient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gandi on 2017/8/28 0028.
 */

public class NovelModel implements UIContract.Model {

    private static final String TAG = "NovelModel";
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
                    NovelBean novelBean = new NovelBean();
                    List<NovelBean.NovelData> novelDataList = new ArrayList<>();

                    String connetUrl = url + page + SxContext.Suffix;
                    Log.i(TAG, "run: connetUrl------" + connetUrl);
                    Document doc = Jsoup.connect(connetUrl).userAgent(SxContext.userAgent).timeout(10000).get();
                    Elements th = doc.select("th.new");
                    for (Element element : th) {
                        String classify = element.select("em a").text();
                        if (!classify.equals("版块公告")) {
                            String title = element.select("a.s.xst").text();
                            String url = element.select("a.s.xst").attr("href");

                            NovelBean.NovelData novelData = new NovelBean.NovelData();
                            novelData.setClassify(classify);
                            novelData.setTitle(title);
                            novelData.setUrl(url);
                            novelDataList.add(novelData);
                        }
                    }

                    String allPage = doc.select("a.last").first().text();
                    if (allPage!=null){
                        String[] split = allPage.split(" ");
                        novelBean.setAllPage(split[split.length - 1]);
                    }
                    novelBean.setNovelData(novelDataList);
                    Log.i(TAG, "run: " + novelBean.toString());

                    if (novelDataList.size() > 0) {
                        callback.onSuccess(novelBean);
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
