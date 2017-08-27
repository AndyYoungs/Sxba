package com.taohua.photo.view.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.taohua.photo.R;
import com.taohua.photo.contract.OnItemClickLitener;
import com.taohua.photo.contract.TaohuaContract;
import com.taohua.photo.model.bean.TaohuaBean;
import com.taohua.photo.presenter.TaohuaPresenter;
import com.taohua.photo.view.activity.TaohuaPhotoActivity;
import com.taohua.photo.view.adapter.TaohuaAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gandi on 2017/8/21 0021.
 */

@SuppressLint("ValidFragment")
public class TaohuaFragment extends Fragment implements TaohuaContract.View<TaohuaBean>, OnItemClickLitener {

    private static final String TAG = "TaohuaFragment";

    public static TaohuaFragment instance;
    View mView;
    String url;
    TaohuaPresenter presenter;
    TaohuaAdapter taohuaAdapter;
    List<TaohuaBean.TaohuaData> taohuaList = new ArrayList<>();

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    FloatingActionButton fab;

    final int HANDLER_REFRESH_CLOSE = 1;
    final int HANDLER_CRAWLER_SUCCESS = 2;
    final int HANDLER_CRAWLER_ERROR = 3;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HANDLER_REFRESH_CLOSE:
                    if (swipeRefreshLayout.isRefreshing())
                        swipeRefreshLayout.setRefreshing(false);
                    break;
                case HANDLER_CRAWLER_SUCCESS:
                    mHandler.sendEmptyMessage(HANDLER_REFRESH_CLOSE);
                    if (msg.arg1 == notifyFrist) {
                        taohuaAdapter.notifyDataSetChanged();
                    } else if (msg.arg1 == notifyNext) {
                        taohuaAdapter.notifyItemRangeInserted((int) msg.obj, taohuaList.size() - 1);
                    }

                    break;
                case HANDLER_CRAWLER_ERROR:
                    mHandler.sendEmptyMessage(HANDLER_REFRESH_CLOSE);
                    break;
            }
        }
    };


    public TaohuaFragment(String url) {
        this.url = url;
        presenter = new TaohuaPresenter(this);
        presenter.setUrl(url);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView==null){
            mView = inflater.inflate(R.layout.fragment_taohua, null);
            init();
        }
        return mView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != mView) {
            ((ViewGroup) mView.getParent()).removeView(mView);
        }
    }

    private void init() {
        swipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.sl_taohua);
        recyclerView = (RecyclerView) mView.findViewById(R.id.rv_taohua);
        fab = (FloatingActionButton) mView.findViewById(R.id.fabtn);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        taohuaAdapter = new TaohuaAdapter(getActivity(), taohuaList);
        recyclerView.setAdapter(taohuaAdapter);
        taohuaAdapter.setOnItemClickLitener(this);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                taohuaList.clear();
                presenter.onFristPage();
                mHandler.sendEmptyMessageDelayed(HANDLER_REFRESH_CLOSE, 6000);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isSlideToBottom(recyclerView)) {
                    presenter.onNextPage();
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //回到顶部
                if (recyclerView != null) {
                    recyclerView.smoothScrollToPosition(0);
                }
            }
        });

        presenter.onFristPage();
    }

    /**
     * 是否滑动到底部
     *
     * @param recyclerView
     * @return
     */
    public static boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }

    @Override
    public void notifyDataSetChanged(int type, TaohuaBean result) {
        int positionStart = taohuaList.size();
        switch (type) {
            case notifyFrist:
                taohuaList.clear();
                break;
            case notifyNext:
                break;
            case notifyAppoint:
                break;
        }

        taohuaList.addAll(result.getTaohuaDataList());

        Message msg = new Message();
        msg.what = HANDLER_CRAWLER_SUCCESS;
        msg.obj = positionStart;
        msg.arg1 = type;
        mHandler.sendMessage(msg);
    }

    @Override
    public void onError() {
        mHandler.sendEmptyMessage(HANDLER_CRAWLER_ERROR);
    }

    @Override
    public void setOnItemClickLitener(View view, int position) {
        EventBus.getDefault().postSticky(taohuaList.get(position));
        Intent intent = new Intent(getActivity(), TaohuaPhotoActivity.class);
        startActivity(intent);
    }

}
