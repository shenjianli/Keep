package com.shen.keep.app.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shen.keep.R;
import com.shen.keep.app.KeepDataManager;
import com.shen.keep.app.adapter.TabFragmentAdapter;
import com.shen.keep.app.fragment.HomeTabFragment;
import com.shen.keep.core.base.BaseFragment;
import com.shen.netclient.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {


    @Bind(R.id.keep_main_tab1_iv)
    ImageView keepMainTab1Iv;
    @Bind(R.id.keep_main_tab1_tv)
    TextView keepMainTab1Tv;
    @Bind(R.id.keep_main_tab2_iv)
    ImageView keepMainTab2Iv;
    @Bind(R.id.keep_main_tab2_tv)
    TextView keepMainTab2Tv;
    @Bind(R.id.keep_main_tab3_iv)
    ImageView keepMainTab3Iv;
    @Bind(R.id.keep_main_tab3_tv)
    TextView keepMainTab3Tv;
    @Bind(R.id.keep_main_tab4_iv)
    ImageView keepMainTab4Iv;
    @Bind(R.id.keep_main_tab4_tv)
    TextView keepMainTab4Tv;
    @Bind(R.id.keep_main_bottom_layout)
    LinearLayout keepMainBottomLayout;

    @Bind(R.id.keep_main_viewpager)
    ViewPager keepMainViewpager;
    @Bind(R.id.keep_main_tab1_layout)
    LinearLayout keepMainTab1Layout;
    @Bind(R.id.keep_main_tab2_layout)
    LinearLayout keepMainTab2Layout;
    @Bind(R.id.keep_main_tab3_layout)
    LinearLayout keepMainTab3Layout;
    @Bind(R.id.keep_main_tab4_layout)
    LinearLayout keepMainTab4Layout;

    private TabFragmentAdapter tabFragmentAdapter;
    private List<BaseFragment> fragments;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        fragments = new ArrayList<>();
        fragments.add(HomeTabFragment.newInstance(1));
        fragments.add(HomeTabFragment.newInstance(2));
        fragments.add(HomeTabFragment.newInstance(3));
        fragments.add(HomeTabFragment.newInstance(4));

        tabFragmentAdapter = new TabFragmentAdapter(getSupportFragmentManager(),fragments);
        keepMainViewpager.setAdapter(tabFragmentAdapter);

        keepMainViewpager.addOnPageChangeListener(this);

        keepMainTab1Iv.setSelected(true);
        keepMainTab1Tv.setSelected(true);
    }

    private void updateKeepData() {
        KeepDataManager.getKeepDataManager().startUpdateData();
    }


    private void initView() {
        //设置ViewPager的最大缓存页面
        keepMainViewpager.setOffscreenPageLimit(3);
    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @OnClick({R.id.keep_main_tab1_layout, R.id.keep_main_tab2_layout, R.id.keep_main_tab3_layout, R.id.keep_main_tab4_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.keep_main_tab1_layout:
                keepMainViewpager.setCurrentItem(0, false);
                break;
            case R.id.keep_main_tab2_layout:
                keepMainViewpager.setCurrentItem(1, false);
                break;
            case R.id.keep_main_tab3_layout:
                keepMainViewpager.setCurrentItem(2, false);
                break;
            case R.id.keep_main_tab4_layout:
                keepMainViewpager.setCurrentItem(3, false);
                break;
        }
    }

    private int currentPosition = 0;
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        LogUtils.i("position=" + position + "    positionOffset=" + positionOffset);

        switch (position){
            case 0:
                if(currentPosition != position){
                    currentPosition = position;
                    restoreTabStyle();
                    keepMainTab1Iv.setSelected(true);
                    keepMainTab1Tv.setSelected(true);
                }
                break;
            case 1:
                if(currentPosition != position){
                    currentPosition = position;
                    restoreTabStyle();
                    keepMainTab2Iv.setSelected(true);
                    keepMainTab2Tv.setSelected(true);
                }
                break;
            case 2:
                if(currentPosition != position){
                    currentPosition = position;
                    restoreTabStyle();
                    keepMainTab3Iv.setSelected(true);
                    keepMainTab3Tv.setSelected(true);
                }
                break;
            case 3:
                if(currentPosition != position){
                    currentPosition = position;
                    restoreTabStyle();
                    keepMainTab4Iv.setSelected(true);
                    keepMainTab4Tv.setSelected(true);
                }
                break;
            default:
                break;
        }
    }

    private void restoreTabStyle() {
        keepMainTab1Iv.setSelected(false);
        keepMainTab1Tv.setSelected(false);
        keepMainTab2Iv.setSelected(false);
        keepMainTab2Tv.setSelected(false);
        keepMainTab3Iv.setSelected(false);
        keepMainTab3Tv.setSelected(false);
        keepMainTab4Iv.setSelected(false);
        keepMainTab4Tv.setSelected(false);
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
