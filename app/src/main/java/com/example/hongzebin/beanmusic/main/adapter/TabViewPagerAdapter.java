package com.example.hongzebin.beanmusic.main.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class TabViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;
    private List<String> mTabList;

    public TabViewPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> tabList){
        super(fm);
        mFragments = fragments;
        mTabList = tabList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTabList.get(position);
    }
}
