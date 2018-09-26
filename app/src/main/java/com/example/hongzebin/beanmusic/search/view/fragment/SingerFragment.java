package com.example.hongzebin.beanmusic.search.view.fragment;

import android.support.v4.app.Fragment;

import com.example.hongzebin.beanmusic.search.bean.SearchSinger;

import java.util.List;

/**
 * 搜索到的歌手页面
 * Created By Mr.Bean
 */
public class SingerFragment extends Fragment {

    private List<SearchSinger> mSearchSingers;

    public void showSingerList(List<SearchSinger> searchSingers){
        mSearchSingers = searchSingers;
    }
//
//    @Override
//    protected int setContentView() {
//        return 0;
//    }
//
//    @Override
//    protected void lazyLoad() {
//
//    }
}
