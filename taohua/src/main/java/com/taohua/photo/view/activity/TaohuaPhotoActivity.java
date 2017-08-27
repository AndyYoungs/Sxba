package com.taohua.photo.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.taohua.photo.R;
import com.taohua.photo.contract.CrawlerCallback;
import com.taohua.photo.contract.OnItemClickLitener;
import com.taohua.photo.contract.TaohuaContract;
import com.taohua.photo.model.TaohuaPhotoModel;
import com.taohua.photo.model.bean.TaohuaBean;
import com.taohua.photo.view.adapter.TaohuaPhotoAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class TaohuaPhotoActivity extends AppCompatActivity implements CrawlerCallback<List<String>>,OnItemClickLitener{

    TaohuaPhotoModel photoModel;

    Toolbar toolbar;
    TextView tvTitle;
    RecyclerView recyclerView;

    TaohuaPhotoAdapter photoAdapter;
    List<String> photoList = new ArrayList<>();
    TaohuaBean.TaohuaData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taohua_photo);

        init();
        EventBus.getDefault().register(this);
    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvTitle = (TextView) findViewById(R.id.tv_photo_title);
        recyclerView = (RecyclerView) findViewById(R.id.rv_photo);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        photoAdapter = new TaohuaPhotoAdapter(this,photoList);
        recyclerView.setAdapter(photoAdapter);
        photoAdapter.setOnItemClickLitener(this);

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
    public void setTaohuaMessage(TaohuaBean.TaohuaData data){
        tvTitle.setText(data.getTitle());

        photoModel = new TaohuaPhotoModel();
        photoModel.setUrl(data.getUrl());
        photoModel.setCallback(this);
        photoModel.startCrawler(0);
    }


    @Override
    public void onSuccess(List<String> result) {
        photoList.addAll(result);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                photoAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onError() {

    }

    @Override
    public void setOnItemClickLitener(View view, int position) {
        EventBus.getDefault().postSticky(photoList.get(position));
        Intent intent = new Intent(this,PhotoViewActivity.class);
        startActivity(intent);
    }
}
