package com.example.hongzebin.beanmusic.songList.view;

import com.example.hongzebin.beanmusic.R;
import com.example.hongzebin.beanmusic.base.view.LazyFragment;

public class SongListFragment extends LazyFragment {

    @Override
    protected int setContentView() {
        return R.layout.fragment_songlist;
    }

    @Override
    protected void lazyLoad() {
    }
}
