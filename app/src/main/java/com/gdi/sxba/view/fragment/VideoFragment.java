package com.gdi.sxba.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gdi.sxba.R;

/**
 * Created by gandi on 2017/8/25 0025.
 */

public class VideoFragment extends BaseFragment {

    View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_sx, null);
        }
        return mView;
    }

    @Override
    public void smoothScrollToPosition(int postion) {

    }
}
