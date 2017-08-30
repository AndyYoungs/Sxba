package com.gdi.sxba.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gdi.sxba.R;
import com.gdi.sxba.contract.CrawlerCallback;
import com.gdi.sxba.model.bean.NovelBean;
import com.gdi.sxba.model.bean.VideoBean;
import com.gdi.sxba.model.sxba.NovelDetailsModel;
import com.gdi.sxba.model.sxba.VideoDetailsModel;
import com.shuyu.gsyvideoplayer.listener.StandardVideoAllCallBack;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class VideoActivity extends AppCompatActivity implements CrawlerCallback<String> {

    Toolbar toolbar;

    VideoDetailsModel videoDetailsModel;
    StandardGSYVideoPlayer gsyVideoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        init();
        EventBus.getDefault().register(this);
    }

    OrientationUtils orientationUtils;

    private void init() {
        gsyVideoPlayer = (StandardGSYVideoPlayer) findViewById(R.id.gsy_player);


        gsyVideoPlayer.setUp("http://116.196.87.67/4d60.mp4", false, null);

        //设置返回键
        gsyVideoPlayer.getBackButton().setVisibility(View.VISIBLE);
        gsyVideoPlayer.setIsTouchWiget(true);
        //关闭自动旋转
        gsyVideoPlayer.setRotateViewAuto(false);
        gsyVideoPlayer.setShowFullAnimation(false);
        //设置旋转
        orientationUtils = new OrientationUtils(this, gsyVideoPlayer);
        //设置全屏按键功能
        gsyVideoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orientationUtils.resolveByClick();
                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                gsyVideoPlayer.startWindowFullscreen(VideoActivity.this, true, true);
            }
        });
        //设置返回按键功能
        gsyVideoPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        gsyVideoPlayer.setStandardVideoAllCallBack(standardVideoAllCallBack);

    }

    @Subscribe(threadMode = ThreadMode.POSTING, sticky = true)
    public void setVideoData(VideoBean.VideoData data) {

        videoDetailsModel = new VideoDetailsModel();
        videoDetailsModel.setUrl(data.getUrl());
        videoDetailsModel.setCallback(this);
        videoDetailsModel.startCrawler(0);
    }

    @Override
    public void onSuccess(final String result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                gsyVideoPlayer.setUp(result, false, null);
            }
        });
    }

    @Override
    public void onError(final int type) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (type == CrawlerCallback.ErrorException) {
                    Toast.makeText(VideoActivity.this, getResources().getString(R.string.jsoup_error_exception), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(VideoActivity.this, getResources().getString(R.string.jsoup_error_nosize), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        GSYVideoPlayer.releaseAllVideos();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        orientationUtils.backToProtVideo();
        if (StandardGSYVideoPlayer.backFromWindowFull(VideoActivity.this)) {
            return;
        }
        super.onBackPressed();
    }


    StandardVideoAllCallBack standardVideoAllCallBack = new StandardVideoAllCallBack() {
        @Override
        public void onPrepared(String url, Object... objects) {
            orientationUtils.setEnable(true);
        }

        @Override
        public void onClickStartIcon(String url, Object... objects) {

        }

        @Override
        public void onClickStartError(String url, Object... objects) {

        }

        @Override
        public void onClickStop(String url, Object... objects) {

        }

        @Override
        public void onClickStopFullscreen(String url, Object... objects) {

        }

        @Override
        public void onClickResume(String url, Object... objects) {

        }

        @Override
        public void onClickResumeFullscreen(String url, Object... objects) {

        }

        @Override
        public void onClickSeekbar(String url, Object... objects) {

        }

        @Override
        public void onClickSeekbarFullscreen(String url, Object... objects) {

        }

        @Override
        public void onAutoComplete(String url, Object... objects) {

        }

        @Override
        public void onEnterFullscreen(String url, Object... objects) {

        }

        @Override
        public void onQuitFullscreen(String url, Object... objects) {
            orientationUtils.backToProtVideo();
        }

        @Override
        public void onQuitSmallWidget(String url, Object... objects) {

        }

        @Override
        public void onEnterSmallWidget(String url, Object... objects) {

        }

        @Override
        public void onTouchScreenSeekVolume(String url, Object... objects) {

        }

        @Override
        public void onTouchScreenSeekPosition(String url, Object... objects) {

        }

        @Override
        public void onTouchScreenSeekLight(String url, Object... objects) {

        }

        @Override
        public void onPlayError(String url, Object... objects) {

        }

        @Override
        public void onClickStartThumb(String url, Object... objects) {

        }

        @Override
        public void onClickBlank(String url, Object... objects) {

        }

        @Override
        public void onClickBlankFullscreen(String url, Object... objects) {

        }
    };

}
