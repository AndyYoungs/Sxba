package com.gdi.sxba.view.activity;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gdi.sxba.R;
import com.gdi.sxba.contract.CrawlerCallback;
import com.gdi.sxba.model.bean.NovelBean;
import com.gdi.sxba.model.bean.PhotoBean;
import com.gdi.sxba.model.sxba.NovelDetailsModel;
import com.gdi.sxba.model.sxba.PhotoDetailsModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class NovelActivity extends AppCompatActivity implements CrawlerCallback<String>{

    Toolbar toolbar;
    TextView tvTitle;
    TextView tvContext;

    NovelDetailsModel novelDetailsModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novel);

        init();
        EventBus.getDefault().register(this);
    }



    private void init(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvTitle = (TextView) findViewById(R.id.tv_novel_title);
        tvContext = (TextView) findViewById(R.id.tv_novel_context);

        tvContext.setMovementMethod(ScrollingMovementMethod.getInstance());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.mipmap.icon_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.POSTING,sticky = true)
    public void setNovelData(NovelBean.NovelData data){
        tvTitle.setText(data.getTitle());

        novelDetailsModel = new NovelDetailsModel();
        novelDetailsModel.setUrl(data.getUrl());
        novelDetailsModel.setCallback(this);
        novelDetailsModel.startCrawler(0);
    }

    @Override
    public void onSuccess(final String result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvContext.setText(result);
            }
        });
    }

    @Override
    public void onError(final int type) {
        runOnUiThread(new Runnable() {
            @SuppressLint("WrongConstant")
            @Override
            public void run() {
                if (type== CrawlerCallback.ErrorException){
                    Toast.makeText(NovelActivity.this,getResources().getString(R.string.jsoup_error_exception),Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(NovelActivity.this,getResources().getString(R.string.jsoup_error_nosize),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
