package com.gdi.sxba.view.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gdi.sxba.R;
import com.gdi.sxba.contract.CrawlerCallback;
import com.gdi.sxba.contract.OnItemClickLitener;
import com.gdi.sxba.contract.UIContract;
import com.gdi.sxba.model.bean.PhotoBean;
import com.gdi.sxba.presenter.PhotoPresenter;
import com.gdi.sxba.view.activity.PhotoActivity;
import com.gdi.sxba.view.adapter.PhotoAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gandi on 2017/8/21 0021.
 */

@SuppressLint("ValidFragment")
public class PhotoCategoryFragment extends Fragment implements UIContract.View<PhotoBean>, OnItemClickLitener {

    private static final String TAG = "PhotoCategoryFragment";

    public static PhotoCategoryFragment instance;
    View mView;
    String url;
    String title="";
    PhotoPresenter presenter;
    PhotoAdapter photoAdapter;
    List<PhotoBean.photoData> photoList = new ArrayList<>();

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    FloatingActionButton fab;

    // 标志位，标志已经初始化完成。
    private boolean isPrepared;
    protected boolean isVisible;

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
                        photoAdapter.notifyDataSetChanged();
                    } else if (msg.arg1 == notifyNext) {
                        photoAdapter.notifyItemRangeInserted((int) msg.obj, photoList.size() - 1);
                    }

                    break;
                case HANDLER_CRAWLER_ERROR:
                    mHandler.sendEmptyMessage(HANDLER_REFRESH_CLOSE);
                    int type = (int) msg.obj;
                    if (type== CrawlerCallback.ErrorException){
                        Toast.makeText(getActivity(),getResources().getString(R.string.jsoup_error_exception),Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getActivity(),getResources().getString(R.string.jsoup_error_nosize),Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };


    public PhotoCategoryFragment(String url) {
        this.url = url;
        presenter = new PhotoPresenter(this);
        presenter.setUrl(url);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_category, null);
            isPrepared = true;
            init();
        }
        return mView;
    }


    /**
     * 在这里实现Fragment数据的缓加载.
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        Log.i(TAG, "setUserVisibleHint: " + title + getUserVisibleHint());
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            init();
        } else {
            isVisible = false;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != mView) {
            ((ViewGroup) mView.getParent()).removeView(mView);
        }
    }


    private void init() {
        if (!isPrepared || !isVisible) {
            return;
        }

        isPrepared = false;

        swipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.sl_taohua);
        recyclerView = (RecyclerView) mView.findViewById(R.id.rv_taohua);
        fab = (FloatingActionButton) mView.findViewById(R.id.fabtn);


        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        photoAdapter = new PhotoAdapter(getActivity(), photoList);
        recyclerView.setAdapter(photoAdapter);
        photoAdapter.setOnItemClickLitener(this);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                photoList.clear();
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
    public void notifyDataSetChanged(int type, PhotoBean result) {
        int positionStart = photoList.size();
        switch (type) {
            case notifyFrist:
                photoList.clear();
                break;
            case notifyNext:
                break;
            case notifyAppoint:
                break;
        }

        photoList.addAll(result.getphotoDataList());

        Message msg = new Message();
        msg.what = HANDLER_CRAWLER_SUCCESS;
        msg.obj = positionStart;
        msg.arg1 = type;
        mHandler.sendMessage(msg);
    }

    @Override
    public void onError(int type) {
        Message msg = new Message();
        msg.what = HANDLER_CRAWLER_ERROR;
        msg.obj = type;
        mHandler.sendMessage(msg);
    }

    @Override
    public void smoothScrollToPosition(int postion) {

    }

    @Override
    public void setOnItemClickLitener(View view, int position) {
        EventBus.getDefault().postSticky(photoList.get(position));
        Intent intent = new Intent(getActivity(), PhotoActivity.class);
        startActivity(intent);
    }

}
