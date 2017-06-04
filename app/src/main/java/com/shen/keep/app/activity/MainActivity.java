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

    private List<ImageView> tabIcons;
    private List<TextView> tabTexts;

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

        tabIcons = new ArrayList<>();
        tabIcons.add(keepMainTab1Iv);
        tabIcons.add(keepMainTab2Iv);
        tabIcons.add(keepMainTab3Iv);
        tabIcons.add(keepMainTab4Iv);

        tabTexts = new ArrayList<>();
        tabTexts.add(keepMainTab1Tv);
        tabTexts.add(keepMainTab2Tv);
        tabTexts.add(keepMainTab3Tv);
        tabTexts.add(keepMainTab4Tv);

        tabFragmentAdapter = new TabFragmentAdapter(getSupportFragmentManager(), fragments);
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
                changeTabMenuStyle(0);
                keepMainViewpager.setCurrentItem(0, false);

                break;
            case R.id.keep_main_tab2_layout:
                changeTabMenuStyle(1);
                keepMainViewpager.setCurrentItem(1, false);
                break;
            case R.id.keep_main_tab3_layout:
                changeTabMenuStyle(2);
                keepMainViewpager.setCurrentItem(2, false);
                break;
            case R.id.keep_main_tab4_layout:
                changeTabMenuStyle(3);
                keepMainViewpager.setCurrentItem(3, false);
                break;
        }
    }

    private void changeTabMenuStyle(int index){
        if(currentPosition != index){
            tabIcons.get(currentPosition).setSelected(false);
            tabTexts.get(currentPosition).setSelected(false);
            currentPosition = index;
            tabIcons.get(currentPosition).setSelected(true);
            tabTexts.get(currentPosition).setSelected(true);
            isFirstCall = true;
        }
    }

    private int currentPosition = 0;
    private boolean isFirstCall = true;

    private int leftRightIndex = 1;//向左为1，向右为-1

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        LogUtils.i("position=" + position + "当前选择的：" + currentPosition + "    positionOffset=" + positionOffset + "offsetPixels=" + positionOffsetPixels);
        if (isFirstCall && position == currentPosition) {
            isFirstCall = false;
            LogUtils.i("向左滑动");
            leftRightIndex = 1;
        }

        if (isFirstCall && currentPosition > position) {
            isFirstCall = false;
            LogUtils.i("向右滑动");
            leftRightIndex = -1;
        }

//        if(positionOffset > 0){
//
//            int currAlpha = (int) ((1 - positionOffset)*255);
//            ImageView currView = tabIcons.get(currentPosition);
//            currView.setAlpha(currAlpha);
//            TextView currTextView = tabTexts.get(currentPosition);
//            currTextView.setAlpha(currAlpha);
//
//            int moveAlpha = (int) (positionOffset * 255);
//
//            ImageView moveView = tabIcons.get(currentPosition + leftRightIndex);
//            TextView moveTextView = tabTexts.get(currentPosition + leftRightIndex);
//            moveView.setSelected(true);
//            moveView.setAlpha(moveAlpha);
//            moveTextView.setSelected(true);
//            moveTextView.setAlpha(moveAlpha);
//
//        }

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
        LogUtils.i("position=" + position);
        if(currentPosition != position){
            currentPosition = position;
            if(currentPosition < tabIcons.size()){
                tabIcons.get(currentPosition - leftRightIndex).setSelected(false);
                tabTexts.get(currentPosition - leftRightIndex).setSelected(false);

                tabIcons.get(currentPosition).setSelected(true);
                tabTexts.get(currentPosition).setSelected(true);
                isFirstCall = true;
            }
        }


    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (ViewPager.SCROLL_STATE_IDLE == state) {
            LogUtils.i("状态：idle");
            isFirstCall = true;
        }
    }
}
