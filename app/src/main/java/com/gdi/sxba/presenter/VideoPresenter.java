package com.gdi.sxba.presenter;

import com.gdi.sxba.contract.CrawlerCallback;
import com.gdi.sxba.contract.UIContract;
import com.gdi.sxba.model.bean.NovelBean;
import com.gdi.sxba.model.bean.VideoBean;
import com.gdi.sxba.model.sxba.VideoModel;

/**
 * Created by gandi on 2017/8/28 0028.
 */

public class VideoPresenter implements UIContract.Presenter, CrawlerCallback<VideoBean>{

    UIContract.View iView;
    VideoModel videoModel;
    int type = -1;
    int page = 1;

    public VideoPresenter(UIContract.View iView) {
        this.iView = iView;
    }

    public void setUrl(String url) {
        videoModel = new VideoModel();
        videoModel.setUrl(url);
        videoModel.setCallback(this);
    }

    @Override
    public void onFristPage() {
        page = 1;
        type = UIContract.View.notifyFrist;
        videoModel.startCrawler(page);
    }

    @Override
    public void onNextPage() {
        page++;
        type = UIContract.View.notifyNext;
        videoModel.startCrawler(page);
    }

    @Override
    public void onAppointPage(int page) {
        this.page = page;
        type = UIContract.View.notifyAppoint;
        videoModel.startCrawler(page);
    }

    @Override
    public void onSuccess(VideoBean result) {
        iView.notifyDataSetChanged(type, result);
    }

    @Override
    public void onError(int type) {
        iView.onError(type);
    }
}
