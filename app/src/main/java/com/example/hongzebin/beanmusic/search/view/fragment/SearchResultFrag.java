package com.example.hongzebin.beanmusic.search.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hongzebin.beanmusic.R;
import com.example.hongzebin.beanmusic.base.view.BaseNoLazyFragment;
import com.example.hongzebin.beanmusic.search.adapter.ViewPagerAdapter;
import com.example.hongzebin.beanmusic.search.bean.SearchAlbum;
import com.example.hongzebin.beanmusic.search.bean.SearchSinger;
import com.example.hongzebin.beanmusic.search.bean.SearchSong;
import com.example.hongzebin.beanmusic.search.contract.SearchMVPContract;
import com.example.hongzebin.beanmusic.search.presenter.SearchPresenter;
import com.example.hongzebin.beanmusic.search.view.activity.SearchEventBusActivity;
import com.example.hongzebin.beanmusic.base.bean.Song;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索结果通过viewPager对不同类型进行展示
 * Created By Mr.Bean
 */
public class SearchResultFrag extends BaseNoLazyFragment<SearchMVPContract.View, SearchPresenter> implements SearchMVPContract.View{

    private ViewPager mViewPager;
    private PagerTabStrip mPagerTabStrip;
    private List<Fragment> mFragmentList;
    private List<String> mTagList;
    private List<SearchAlbum> mAlbumList;
    private List<SearchSinger> mSingerList;
    private List<SearchSong> mSongList;
    private String mKeyWord;
    private SongFragment mSongFragment;
    private SingerFragment mSingerFragment;
    private AlbumFragment mAlbumFragment;
    private View mView;
    private int mPagerNo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initView(inflater, container);
        initData();
        initEvent();
        return mView;
    }


    private void initView(LayoutInflater inflater, ViewGroup container) {
        mView = inflater.inflate(R.layout.fragment_search_result_main, container, false);
        mViewPager = mView.findViewById(R.id.search_result_view_pager);
        mPagerTabStrip = mView.findViewById(R.id.search_result_tag);
        mPagerNo = 1;
        mFragmentList = new ArrayList<>();
        mTagList = new ArrayList<>();
        mSongFragment = new SongFragment();
        mSingerFragment = new SingerFragment();
        mAlbumFragment = new AlbumFragment();
    }

    private void initData() {
        mFragmentList.add(mSongFragment);
        mFragmentList.add(mSingerFragment);
        mFragmentList.add(mAlbumFragment);

        mTagList.add("歌曲");
        mTagList.add("歌手");
        mTagList.add("专辑");
    }

    private void initEvent() {
        mPagerTabStrip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18); //设置滚动标题字大小
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager(), mFragmentList, mTagList);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(2);
        mPresenter.getSearchResult(getActivity(), mKeyWord, mPagerNo);
    }

    @Override
    protected SearchPresenter createPresenter() {
        return new SearchPresenter(this);
    }

    @Override
    public void returnTag() {
    }

    @Override
    public void showResult(List<SearchAlbum> albumList, List<SearchSinger> singerList, List<SearchSong> songList, boolean loadMoreFlag) {
        mAlbumList = albumList;
        mSongList = songList;
        mSingerList = singerList;
        mSongFragment.showSongList(this, mSongList, loadMoreFlag);
    }

    @Override
    public void returnSong(Song song) {
        ((SearchEventBusActivity) getActivity()).getSong(song);
    }

    /**
     * 搜索结果
     * @param keyWord 搜索的关键字
     */
    public void getKeyWord(String keyWord){
        mKeyWord = keyWord;
    }

    /**
     * 获取更多数据
     */
    public void getMoreData(){
        mPagerNo++;
        mPresenter.getSearchResult(getActivity(), mKeyWord, mPagerNo);
    }

    /**
     * 获取点击的是第几首歌曲
     * @param searchSong 点击的歌曲
     */
    public void getClickSearchSong(SearchSong searchSong){
        mPresenter.getSong(searchSong);
    }
}
