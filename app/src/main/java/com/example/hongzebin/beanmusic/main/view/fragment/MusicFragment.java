package com.example.hongzebin.beanmusic.main.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hongzebin.beanmusic.R;
import com.example.hongzebin.beanmusic.base.LazyFragment;
import com.example.hongzebin.beanmusic.main.adapter.TabViewPagerAdapter;
import com.example.hongzebin.beanmusic.rankingList.view.RankingListFragment;
import com.example.hongzebin.beanmusic.recommendation.view.RecommendationFragment;
import com.example.hongzebin.beanmusic.singer.view.SingerFragment;
import com.example.hongzebin.beanmusic.songList.view.SongListFragment;

import java.util.ArrayList;
import java.util.List;

public class MusicFragment extends LazyFragment {

    private List<String> mTabList;
    private List<Fragment> mFragments;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private View mView;

    @Override
    protected int setContentView() {
        return R.layout.fragment_music;
    }

    @Override
    protected void lazyLoad() {
        Log.e("music lazy", "????????????" );
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        mView = getContentView();
        mTabList = new ArrayList<>();
        mFragments = new ArrayList<>();
        mViewPager = mView.findViewById(R.id.music_viewpager);
        mTabLayout = mView.findViewById(R.id.music_tab);

    }

    private void initData() {
        mFragments.add(new RecommendationFragment());
        mFragments.add(new SongListFragment());
        mFragments.add(new RankingListFragment());
        mFragments.add(new SingerFragment());

        mTabList.add("推荐");
        mTabList.add("歌单");
        mTabList.add("榜单");
        mTabList.add("歌手");
    }

    private void initEvent() {
        mViewPager.setAdapter(new TabViewPagerAdapter(getChildFragmentManager(), mFragments, mTabList));
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
