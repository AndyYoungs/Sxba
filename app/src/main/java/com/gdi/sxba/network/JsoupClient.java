package com.gdi.sxba.network;


import android.util.Log;

import org.jsoup.Jsoup;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by gandi on 2017/8/25 0025.
 */

public class JsoupClient {

    private static final String TAG = "JsoupClient";
    String url;
    String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36";
    static JsoupClient instance;

    OkHttpClient okHttpClient;


    public static JsoupClient getInstance(String url) {
        if (instance == null) {
            instance = new JsoupClient(url);
        }
        return instance;
    }

    public JsoupClient(String url) {
        this.url = url;
        okHttpClient = new OkHttpClient();
        instance = this;
    }

    public void connet() {
        Request request = new Request.Builder().url(url).build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            Log.i(TAG, "connet: " + response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
