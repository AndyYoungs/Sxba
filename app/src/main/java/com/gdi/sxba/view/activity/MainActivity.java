package com.gdi.sxba.view.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.gdi.sxba.R;
import com.gdi.sxba.model.sxba.SxbaModel;
import com.gdi.sxba.view.fragment.BaseFragment;
import com.gdi.sxba.view.fragment.NovelFragment;
import com.gdi.sxba.view.fragment.SxbaFragment;
import com.gdi.sxba.view.fragment.VideoFragment;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView tvTitle;
    SxbaModel sxbaPhotoModel;

    private BaseFragment[] fragments;
    private int index;
    private int currentTabIndex;
    SxbaFragment sxbaFragment;
    VideoFragment videoFragment;
    NovelFragment novelFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    index = 0;
                    tvTitle.setText(getResources().getString(R.string.title_photo));
                    break;
                case R.id.navigation_dashboard:
                    index = 1;
                    break;
                case R.id.navigation_notifications:
                    index = 2;
                    break;
            }
            if (currentTabIndex != index) {
                FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
                trx.hide(fragments[currentTabIndex]);
                if (!fragments[index].isAdded()) {
                    trx.add(R.id.fragment_container, fragments[index]);
                }
                trx.show(fragments[index]).commit();
            }else{
                fragments[currentTabIndex].smoothScrollToPosition(0);
            }
            currentTabIndex = index;
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        init();

    }

    private void init() {
        sxbaFragment = new SxbaFragment();
        videoFragment = new VideoFragment();
        novelFragment = new NovelFragment();
        fragments = new BaseFragment[]{ sxbaFragment,videoFragment, novelFragment};
        // add and show first fragment
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,sxbaFragment )
                .add(R.id.fragment_container, videoFragment).hide(videoFragment).show(sxbaFragment)
                .commit();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvTitle = (TextView) findViewById(R.id.tv_tab_title);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tvTitle.setText(getResources().getString(R.string.title_photo));
    }

}
