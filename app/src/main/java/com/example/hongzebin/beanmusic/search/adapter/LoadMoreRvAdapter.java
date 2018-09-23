package com.example.hongzebin.beanmusic.search.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.hongzebin.beanmusic.R;
import com.example.hongzebin.beanmusic.base.GlobalAdapter;
import com.example.hongzebin.beanmusic.base.GlobalViewHolder;
import com.example.hongzebin.beanmusic.search.bean.SearchSong;

import java.util.List;

/**
 * 能加载更多的RecyclerViewAdapter
 * Created By Mr.Bean
 */
public class LoadMoreRvAdapter extends GlobalAdapter<SearchSong> {

    @Override
    public void convert(GlobalViewHolder viewHolder, SearchSong item) {
        viewHolder.setText(R.id.song_name, item.getSongName())
                .setText(R.id.song_singer, item.getSinger())
                .setText(R.id.song_album, item.getAlbum());
    }

    public LoadMoreRvAdapter(List<SearchSong> mData, int mLayoutId, RecyclerView recyclerView){
        super(mData, mLayoutId , recyclerView);
    }
}