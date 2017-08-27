package com.gdi.sxba.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import com.gdi.sxba.R;
import com.gdi.sxba.contract.CrawlerCallback;
import com.gdi.sxba.contract.OnItemClickLitener;
import com.gdi.sxba.model.sxba.SxbaPhotoModel;
import com.gdi.sxba.model.bean.SxBean;
import com.gdi.sxba.view.adapter.PhotoAdapter;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.ArrayList;
import java.util.List;

public class PhotoActivity extends AppCompatActivity implements CrawlerCallback<List<String>>,OnItemClickLitener{

    SxbaPhotoModel photoModel;

    Toolbar toolbar;
    TextView tvTitle;
    RecyclerView recyclerView;

    PhotoAdapter photoAdapter;
    List<String> photoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        init();
        EventBus.getDefault().register(this);
    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvTitle = (TextView) findViewById(R.id.tv_photo_title);
        recyclerView = (RecyclerView) findViewById(R.id.rv_photo);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        photoAdapter = new PhotoAdapter(this,photoList);
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
    public void setSxMessage(SxBean.SxData data){
        tvTitle.setText(data.getTitle());

        photoModel = new SxbaPhotoModel();
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
