package com.example.hongzebin.beanmusic.search.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * ViewPager的Adapter，ViewPager和能和Tag联动
 * Created By Mr.Bean
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragments;
    private List<String> mTagList;

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> tagList) {
        super(fm);
        mFragments = fragments;
        mTagList = tagList;
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
        return mTagList.get(position);
    }
}
