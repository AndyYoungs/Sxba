package com.gdi.sxba.presenter;

import android.util.Log;

import com.gdi.sxba.contract.CrawlerCallback;
import com.gdi.sxba.contract.UIContract;
import com.gdi.sxba.model.sxba.SxbaModel;
import com.gdi.sxba.model.bean.SxBean;

/**
 * Created by gandi on 2017/8/19 0019.
 */

public class SxPresenter implements UIContract.Presenter, CrawlerCallback<SxBean> {

    int page = 1;
    SxbaModel sxbaModel;
    UIContract.View iView;
    int type = -1;

    public SxPresenter(UIContract.View iView) {
        this.iView = iView;
    }

    public void setUrl(String url) {
        sxbaModel = new SxbaModel();
        sxbaModel.setUrl(url);
        sxbaModel.setCallback(this);
    }

    @Override
    public void onFristPage() {
        page = 1;
        type = UIContract.View.notifyFrist;
        sxbaModel.startCrawler(page);
    }

    @Override
    public void onNextPage() {
        page++;
        type = UIContract.View.notifyNext;
        sxbaModel.startCrawler(page);
    }

    @Override
    public void onAppointPage(int page) {
        this.page = page;
        type = UIContract.View.notifyAppoint;
        sxbaModel.startCrawler(page);
    }

    @Override
    public void onSuccess(SxBean result) {
        iView.notifyDataSetChanged(type, result);
    }

    @Override
    public void onError() {
        Log.i("1111111111111111111", "onError: ");
        iView.onError();
    }
}
