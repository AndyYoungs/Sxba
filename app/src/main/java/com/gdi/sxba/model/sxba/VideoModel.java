package com.gdi.sxba.model.sxba;

import android.util.Log;

import com.gdi.sxba.contract.CrawlerCallback;
import com.gdi.sxba.contract.UIContract;
import com.gdi.sxba.model.bean.PhotoBean;
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

public class VideoModel implements UIContract.Model {

    private static final String TAG = "VideoModel";

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
                    VideoBean videoBean = new VideoBean();
                    List<VideoBean.VideoData> videoDataList = new ArrayList<>();

                    String connetUrl = url + page + SxContext.Suffix + "?qqdrsign=03d16?qqdrsign=0256d";
                    Log.i(TAG, "run: connetUrl------" + connetUrl);
                    Document doc = Jsoup.connect(connetUrl).userAgent(SxContext.userAgent).timeout(10000).get();
                    Elements th = doc.select("th.new");

                    for (Element element : th) {
                        String classify = element.select("em a").text();

                        String title = element.select("a.s.xst").text();
                        String url = element.select("a.s.xst").attr("href");
                        VideoBean.VideoData videoData = new VideoBean.VideoData();
                        videoData.setClassify(classify);
                        videoData.setTitle(title);
                        videoData.setUrl(url);
                        videoDataList.add(videoData);

                    }

                    String allPage = doc.select("a.last").first().text();
                    String[] split = allPage.split(" ");

                    videoBean.setAllPage(split[split.length - 1]);
                    videoBean.setVideoData(videoDataList);
                    Log.i(TAG, "photoBean: " + videoBean.toString());

                    if (videoDataList.size() > 0) {
                        callback.onSuccess(videoBean);
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
