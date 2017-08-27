package com.taohua.photo.presenter;

import android.util.Log;

import com.taohua.photo.contract.CrawlerCallback;
import com.taohua.photo.contract.TaohuaContract;
import com.taohua.photo.model.TaohuaModel;
import com.taohua.photo.model.bean.TaohuaBean;

/**
 * Created by gandi on 2017/8/19 0019.
 */

public class TaohuaPresenter implements TaohuaContract.Presenter, CrawlerCallback<TaohuaBean> {

    int page = 1;
    TaohuaModel taohuaModel;
    TaohuaContract.View iView;
    int type = -1;

    public TaohuaPresenter(TaohuaContract.View iView) {
        this.iView = iView;
    }

    public void setUrl(String url) {
        taohuaModel = new TaohuaModel();
        taohuaModel.setUrl(url);
        taohuaModel.setCallback(this);
    }

    @Override
    public void onFristPage() {
        page = 1;
        type = TaohuaContract.View.notifyFrist;
        taohuaModel.startCrawler(page);
    }

    @Override
    public void onNextPage() {
        page++;
        type = TaohuaContract.View.notifyNext;
        taohuaModel.startCrawler(page);
    }

    @Override
    public void onAppointPage(int page) {
        this.page = page;
        type = TaohuaContract.View.notifyAppoint;
        taohuaModel.startCrawler(page);
    }

    @Override
    public void onSuccess(TaohuaBean result) {
        iView.notifyDataSetChanged(type, result);
    }

    @Override
    public void onError() {
        Log.i("1111111111111111111", "onError: ");
        iView.onError();
    }
}
