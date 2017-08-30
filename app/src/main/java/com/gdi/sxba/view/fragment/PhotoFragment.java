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

public class PhotoFragment extends BaseFragment implements OnTabSelectListener {

    private static final String TAG = "PhotoFragment";

    View mView;

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private final String[] mTitles = {
            "生活自拍", "性爱自拍", "街拍",
            "欧亚套图", "国模套图", "动漫套图",
            "亚洲性爱", "欧美性爱", "亚洲裸女", "欧美裸女", "裸模艺术",
            "极致诱惑", "明星专区", "动漫专区"
    };

    private final String[] mUrl = {
            SxContext.SxBaZipai, SxContext.SxBaZipaiS, SxContext.SxBaJiePai,
            SxContext.SxBaOuYaT, SxContext.SxBaGuoMoT, SxContext.SxBaDongManT,
            SxContext.SxBaYaZhou, SxContext.SxBaOuMei, SxContext.SxBaYaZhouL, SxContext.SxBaOuMeiL, SxContext.SxBaLYiShu,
            SxContext.SxBaYouHuo, SxContext.SxBaMingXing, SxContext.SxBaDongMan
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_photo, null);
            init();
        }
        return mView;
    }

    private void init() {

        for (String url : mUrl) {
            mFragments.add(new PhotoCategoryFragment(url));
        }

        TabPagerAdapter adapter = new TabPagerAdapter(getFragmentManager(), mFragments, mTitles);
        ViewPager vp = (ViewPager) mView.findViewById(R.id.vp_photo);
        vp.setAdapter(adapter);
        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) mView.findViewById(R.id.stl_photo);
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
