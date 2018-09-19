package com.example.hongzebin.beanmusic.locality.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.hongzebin.beanmusic.R;
import com.example.hongzebin.beanmusic.locality.bean.MP3Info;

import java.util.List;

public class LocRecyclerViewAdapter extends RecyclerView.Adapter<LocRecyclerViewAdapter.MusicListViewHolder> {

    private List<MP3Info> mMP3InfoList;

    public LocRecyclerViewAdapter(List<MP3Info> mp3InfoList){
        mMP3InfoList = mp3InfoList;
    }

    @NonNull
    @Override
    public MusicListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.locality_music_list_item, parent, false);
        return new MusicListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicListViewHolder holder, int position) {
        MP3Info mp3Info = mMP3InfoList.get(position);
        holder.mTvSongName.setText(mp3Info.getSongName());
        holder.mTvSinger.setText(mp3Info.getSinger());
        holder.mTvAlbum.setText(mp3Info.getAlbum());
    }

    @Override
    public int getItemCount() {
        return mMP3InfoList.size();
    }

    class MusicListViewHolder extends RecyclerView.ViewHolder{

        private TextView mTvSongName;
        private TextView mTvSinger;
        private TextView mTvAlbum;
        private ImageButton mIbMore;

        public MusicListViewHolder(View itemView) {
            super(itemView);
            mTvSongName = itemView.findViewById(R.id.locality_song_name);
            mTvSinger = itemView.findViewById(R.id.locality_singer);
            mTvAlbum = itemView.findViewById(R.id.locality_album);
            mIbMore = itemView.findViewById(R.id.locality_more);
        }
    }
}
