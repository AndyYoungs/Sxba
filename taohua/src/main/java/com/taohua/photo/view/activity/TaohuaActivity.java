package com.taohua.photo.view.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.taohua.photo.R;
import com.taohua.photo.model.TaohuaPhotoModel;
import com.taohua.photo.model.bean.TaohuaBean;
import com.taohua.photo.model.context.TaohuaContext;
import com.taohua.photo.view.adapter.TabPagerAdapter;
import com.taohua.photo.view.fragment.TaohuaFragment;

import java.util.ArrayList;

public class TaohuaActivity extends AppCompatActivity implements OnTabSelectListener {

    private static final String TAG = "TaohuaActivity";
    private TabPagerAdapter mAdapter;

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private final String[] mTitles = {
            "自拍", "欧美", "艺术", "街拍", "美图", "漫画"
    };

    private final String[] mUrl = {
            TaohuaContext.ZiPai, TaohuaContext.OuMei, TaohuaContext.YiShu, TaohuaContext.JiePai, TaohuaContext.MeiTu, TaohuaContext.ManHua
    };

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_taohua);

        for (String url : mUrl) {
            mFragments.add(new TaohuaFragment(url));
        }

        ViewPager vp = (ViewPager) findViewById(R.id.viewpager);
        mAdapter = new TabPagerAdapter(getSupportFragmentManager(), mFragments, mTitles);
        vp.setAdapter(mAdapter);

        SlidingTabLayout tab_layout = (SlidingTabLayout) findViewById(R.id.tab_layout);
        tab_layout.setViewPager(vp);


    }

    @Override
    public void onTabSelect(int position) {
        Toast.makeText(this, "onTabSelect&position--->" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTabReselect(int position) {

    }
}
