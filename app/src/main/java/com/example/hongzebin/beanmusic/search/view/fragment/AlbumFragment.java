package com.example.hongzebin.beanmusic.search.view.fragment;

import android.support.v4.app.Fragment;

import com.example.hongzebin.beanmusic.base.LazyFragment;
import com.example.hongzebin.beanmusic.search.bean.SearchAlbum;

import java.util.List;

/**
 * 搜索到的专辑页面
 * Created By Mr.Bean
 */
public class AlbumFragment extends Fragment {

    private List<SearchAlbum> mSearchAlbums;

    public void showAlbumList(List<SearchAlbum> searchAlbums){
        mSearchAlbums = searchAlbums;
    }

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
