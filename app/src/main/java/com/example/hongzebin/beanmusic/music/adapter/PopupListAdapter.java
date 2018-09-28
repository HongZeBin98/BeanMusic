package com.example.hongzebin.beanmusic.music.adapter;

import com.example.hongzebin.beanmusic.R;
import com.example.hongzebin.beanmusic.base.adapter.GlobalClickAdapter;
import com.example.hongzebin.beanmusic.base.adapter.GlobalViewHolder;
import com.example.hongzebin.beanmusic.base.bean.Song;

import java.util.List;

public class PopupListAdapter extends GlobalClickAdapter<Song> {

    public PopupListAdapter(List mData, int mLayoutId) {
        super(mData, mLayoutId);
    }

    @Override
    public void convert(GlobalViewHolder viewHolder, Song item) {
        viewHolder.setText(R.id.popup_window_song_name_default, item.getSongName())
                .setText(R.id.popup_window_singer_default, item.getSinger());
    }

}
