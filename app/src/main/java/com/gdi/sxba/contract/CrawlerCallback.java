package com.gdi.sxba.contract;

/**
 * Created by gandi on 2017/8/21 0021.
 */

public interface CrawlerCallback<T> {

    int ErrorException = 1;
    int ErrorNoSize = 2;

    void onSuccess(T result);
    void onError(int type);
}
