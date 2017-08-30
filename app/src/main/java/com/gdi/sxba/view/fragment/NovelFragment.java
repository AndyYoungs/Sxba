package com.gdi.sxba.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.gdi.sxba.R;
import com.gdi.sxba.model.context.SxContext;
import com.gdi.sxba.view.adapter.TabPagerAdapter;

import java.util.ArrayList;

/**
 * Created by gandi on 2017/8/25 0025.
 */

public class NovelFragment extends BaseFragment implements OnTabSelectListener {

    private static final String TAG = "NovelFragment";
    View mView;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private final String[] mTitles = {
            "即时中短", "同步连载","首发作品","作家手写","电子书"
    };

    private final String[] mUrl = {
            SxContext.SxShortNovels, SxContext.SxSerialNovels,SxContext.SxLatestNovels,SxContext.SxOurWriter,SxContext.SxEbookDownload
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_novel, null);
            init();
        }
        return mView;
    }

    private void init() {

        for (String url : mUrl) {
            mFragments.add(new NovelCategoryFragment(url));
        }

        TabPagerAdapter adapter = new TabPagerAdapter(getFragmentManager(), mFragments, mTitles);
        ViewPager vp = (ViewPager) mView.findViewById(R.id.vp_novel);
        vp.setAdapter(adapter);
        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) mView.findViewById(R.id.stl_novel);
        slidingTabLayout.setViewPager(vp);
        slidingTabLayout.setOnTabSelectListener(this);

    }

    @Override
    public void smoothScrollToPosition(int postion) {

    }


    @Override
    public void onTabSelect(int position) {

    }

    @Override
    public void onTabReselect(int position) {

    }
}
