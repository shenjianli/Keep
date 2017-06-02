package com.shen.keep.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shen.keep.core.base.BaseFragment;

import java.util.List;

/**
 * Created by edianzu on 2017/6/2.
 */
public class TabFragmentAdapter extends FragmentPagerAdapter{

    private List<BaseFragment> mFragments;

    public TabFragmentAdapter(FragmentManager fm,List<BaseFragment>  fragments) {
        super(fm);
        this.mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments != null ? mFragments.size() : 0;
    }
}
