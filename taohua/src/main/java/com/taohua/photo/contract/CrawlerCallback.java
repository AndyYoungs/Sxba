package com.taohua.photo.contract;

import java.util.List;

/**
 * Created by gandi on 2017/8/21 0021.
 */

public interface CrawlerCallback<T> {

    void onSuccess(T result);
    void onError();
}
