package com.gdi.sxba.view.fragment;

import android.support.v4.app.Fragment;

/**
 * Created by gandi on 2017/8/25 0025.
 */

public abstract class BaseFragment extends Fragment {

    int SXBA = 1;
    int TAOHUA = 2;
    public abstract void smoothScrollToPosition(int postion) ;
}
