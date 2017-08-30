package com.gdi.sxba.presenter;

import com.gdi.sxba.contract.CrawlerCallback;
import com.gdi.sxba.contract.UIContract;
import com.gdi.sxba.model.bean.NovelBean;
import com.gdi.sxba.model.bean.PhotoBean;
import com.gdi.sxba.model.sxba.NovelModel;

/**
 * Created by gandi on 2017/8/28 0028.
 */

public class NovelPresenter implements UIContract.Presenter, CrawlerCallback<NovelBean>{

    UIContract.View iView;
    NovelModel novelModel;
    int type = -1;
    int page = 1;

    public NovelPresenter(UIContract.View iView) {
        this.iView = iView;
    }

    public void setUrl(String url) {
        novelModel = new NovelModel();
        novelModel.setUrl(url);
        novelModel.setCallback(this);
    }

    @Override
    public void onFristPage() {
        page = 1;
        type = UIContract.View.notifyFrist;
        novelModel.startCrawler(page);
    }

    @Override
    public void onNextPage() {
        page++;
        type = UIContract.View.notifyNext;
        novelModel.startCrawler(page);
    }

    @Override
    public void onAppointPage(int page) {
        this.page = page;
        type = UIContract.View.notifyAppoint;
        novelModel.startCrawler(page);
    }

    @Override
    public void onSuccess(NovelBean result) {
        iView.notifyDataSetChanged(type, result);
    }

    @Override
    public void onError(int type) {
        iView.onError(type);
    }
}
