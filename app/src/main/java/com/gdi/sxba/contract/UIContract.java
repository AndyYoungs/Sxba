package com.gdi.sxba.contract;

/**
 * Created by gandi on 2017/8/19 0019.
 */

public interface UIContract {
    interface Model {
        void setUrl(String url);

        void startCrawler(int page);
    }

    interface View<T> {
        int notifyFrist = 0;
        int notifyNext = 1;
        int notifyAppoint = 2;

        void notifyDataSetChanged(int type, T result);

        void onError();

        void smoothScrollToPosition(int postion);
    }

    interface Presenter {
        void onFristPage();

        void onNextPage();

        void onAppointPage(int page);
    }
}
