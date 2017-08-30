package com.gdi.sxba.presenter;

import com.gdi.sxba.contract.CrawlerCallback;
import com.gdi.sxba.contract.UIContract;
import com.gdi.sxba.model.sxba.PhotoModel;
import com.gdi.sxba.model.bean.PhotoBean;

/**
 * Created by gandi on 2017/8/19 0019.
 */

public class PhotoPresenter implements UIContract.Presenter, CrawlerCallback<PhotoBean> {

    int page = 1;
    PhotoModel photoModel;
    UIContract.View iView;
    int type = -1;

    public PhotoPresenter(UIContract.View iView) {
        this.iView = iView;
    }

    public void setUrl(String url) {
        photoModel = new PhotoModel();
        photoModel.setUrl(url);
        photoModel.setCallback(this);
    }

    @Override
    public void onFristPage() {
        page = 1;
        type = UIContract.View.notifyFrist;
        photoModel.startCrawler(page);
    }

    @Override
    public void onNextPage() {
        page++;
        type = UIContract.View.notifyNext;
        photoModel.startCrawler(page);
    }

    @Override
    public void onAppointPage(int page) {
        this.page = page;
        type = UIContract.View.notifyAppoint;
        photoModel.startCrawler(page);
    }

    @Override
    public void onSuccess(PhotoBean result) {
        iView.notifyDataSetChanged(type, result);
    }

    @Override
    public void onError(int type) {
        iView.onError(type);
    }
}
