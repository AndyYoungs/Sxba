package com.taohua.photo.model;

import com.taohua.photo.contract.CrawlerCallback;
import com.taohua.photo.contract.TaohuaContract;
import com.taohua.photo.model.bean.TaohuaBean;
import com.taohua.photo.model.context.TaohuaContext;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gandi on 2017/8/19 0019.
 */

public class TaohuaModel implements TaohuaContract.Model {

    private static final String TAG = "TaohuaModel";

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
                TaohuaBean taohuaBean = new TaohuaBean();
                List<TaohuaBean.TaohuaData> taohuaDataList = new ArrayList<>();

                try {
                    Document doc = Jsoup.connect(url + page + TaohuaContext.Suffix).timeout(10000).get();
                    Elements ul = doc.select("ul.ml.waterfall.cl");
                    Elements li = ul.select("div.c.cl");

                    for (Element element : li) {
                        String title = element.select("a").attr("title");
                        String url = element.select("a").attr("href");
                        String img = element.select("img").attr("src");

                        TaohuaBean.TaohuaData taohuaData = new TaohuaBean.TaohuaData();
                        taohuaData.setTitle(title);
                        taohuaData.setUrl(url);
                        taohuaData.setImg(img);

                        taohuaDataList.add(taohuaData);
                    }

                    Elements pg = doc.select("div.pg").first().select("a");
                    String allPage = pg.get(pg.size() - 2).text();
                    String[] split = allPage.split(" ");

                    taohuaBean.setAllpage(split[split.length - 1]);
                    taohuaBean.setTaohuaDataList(taohuaDataList);

                } catch (IOException e) {
                    e.printStackTrace();
                }


                if (taohuaDataList.size() > 0) {
                    callback.onSuccess(taohuaBean);
                } else {
                    callback.onError();
                }
            }
        }).start();
    }

    public void setCallback(CrawlerCallback callback) {
        this.callback = callback;
    }

}
