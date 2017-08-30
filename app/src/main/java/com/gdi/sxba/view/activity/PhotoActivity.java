package com.gdi.sxba.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gdi.sxba.R;
import com.gdi.sxba.contract.CrawlerCallback;
import com.gdi.sxba.contract.OnItemClickLitener;
import com.gdi.sxba.model.bean.PhotoBean;
import com.gdi.sxba.model.sxba.PhotoDetailsModel;
import com.gdi.sxba.view.adapter.PhotoDetailsAdapter;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.ArrayList;
import java.util.List;

public class PhotoActivity extends AppCompatActivity implements CrawlerCallback<List<String>>,OnItemClickLitener{

    PhotoDetailsModel photoDetailsModel;

    Toolbar toolbar;
    TextView tvTitle;
    RecyclerView recyclerView;

    PhotoDetailsAdapter adapter;
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
        adapter = new PhotoDetailsAdapter(this,photoList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickLitener(this);

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
    public void setPhotoData(PhotoBean.photoData data){
        tvTitle.setText(data.getTitle());

        photoDetailsModel = new PhotoDetailsModel();
        photoDetailsModel.setUrl(data.getUrl());
        photoDetailsModel.setCallback(this);
        photoDetailsModel.startCrawler(0);
    }


    @Override
    public void onSuccess(List<String> result) {
        photoList.addAll(result);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
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
                    Toast.makeText(PhotoActivity.this,getResources().getString(R.string.jsoup_error_exception),Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(PhotoActivity.this,getResources().getString(R.string.jsoup_error_nosize),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void setOnItemClickLitener(View view, int position) {
        EventBus.getDefault().postSticky(photoList.get(position));
        Intent intent = new Intent(this,PhotoViewActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
