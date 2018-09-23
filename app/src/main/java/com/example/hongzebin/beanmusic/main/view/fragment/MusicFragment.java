package com.example.hongzebin.beanmusic.main.view.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.hongzebin.beanmusic.R;
import com.example.hongzebin.beanmusic.base.LazyFragment;
import com.example.hongzebin.beanmusic.main.adapter.TabViewPagerAdapter;
import com.example.hongzebin.beanmusic.rankingList.view.RankingListFragment;
import com.example.hongzebin.beanmusic.recommendation.view.RecFragment;
import com.example.hongzebin.beanmusic.singer.view.SingerFragment;
import com.example.hongzebin.beanmusic.songList.view.SongListFragment;

import java.util.ArrayList;
import java.util.List;

public class MusicFragment extends LazyFragment {

    private List<String> mTabList;
    private List<Fragment> mFragments;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected int setContentView() {
        return R.layout.fragment_music;
    }

    @Override
    protected void lazyLoad() {
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        View view = getContentView();
        mTabList = new ArrayList<>();
        mFragments = new ArrayList<>();
        mViewPager = view.findViewById(R.id.music_viewpager);
        mTabLayout = view.findViewById(R.id.music_tab);
    }

    private void initData() {
        mFragments.add(new RecFragment());
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
