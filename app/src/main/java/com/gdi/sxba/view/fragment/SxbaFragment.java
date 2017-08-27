package com.gdi.sxba.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
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

public class SxbaFragment extends BaseFragment implements OnTabSelectListener {

    private static final String TAG = "SxbaFragment";
    
    View mView;
    private TabPagerAdapter mAdapter;

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private final String[] mTitles = {
            "自拍","自拍1","街拍","亚洲"
    };

    private final String[] mUrl = {
            SxContext.SxBaZipai, SxContext.SxBaZipaiS, SxContext.SxBaJiePai, SxContext.SxBaYaZhou
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView==null){
            mView = inflater.inflate(R.layout.fragment_sx, null);
            init();
        }
        return mView;
    }

    private void init() {
        for (String url : mUrl) {
            mFragments.add(new PhotoFragment(url));

        }

        ViewPager vp = (ViewPager) mView.findViewById(R.id.viewpager);
        mAdapter = new TabPagerAdapter(getActivity().getSupportFragmentManager(), mFragments, mTitles);
        vp.setAdapter(mAdapter);

        SlidingTabLayout tab_layout = (SlidingTabLayout) mView.findViewById(R.id.tab_layout);
        tab_layout.setViewPager(vp);
        tab_layout.setOnTabSelectListener(this);
    }

    @Override
    public void smoothScrollToPosition(int postion) {

    }

    @Override
    public void onTabSelect(int position) {
        Log.i(TAG, "onTabSelect: "+position);
    }

    @Override
    public void onTabReselect(int position) {

    }
}
