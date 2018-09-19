package com.example.hongzebin.beanmusic.locality.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.hongzebin.beanmusic.R;
import com.example.hongzebin.beanmusic.locality.bean.MP3Info;
import com.example.hongzebin.beanmusic.widget.IndexBar;

import java.util.Arrays;
import java.util.List;

/**
 * 本地音乐列表的适配器
 * Created By Mr.Bean
 */
public class LocRecyclerViewAdapter extends RecyclerView.Adapter<LocRecyclerViewAdapter.MusicListViewHolder> implements SectionIndexer {

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

    @Override
    public Object[] getSections() {
        return Arrays.copyOf(IndexBar.alphabets, IndexBar.alphabets.length);
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        if (sectionIndex == 26){
            return mMP3InfoList.size();
        }
        for(int i = 0; i < mMP3InfoList.size(); i++){
            if (((String)getSections()[sectionIndex]).charAt(0) == mMP3InfoList.get(i).getFirstAlphabet().charAt(0)){
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }

    class MusicListViewHolder extends RecyclerView.ViewHolder{

        private TextView mTvSongName;
        private TextView mTvSinger;
        private TextView mTvAlbum;
        private ImageButton mIbMore;

        MusicListViewHolder(View itemView) {
            super(itemView);
            mTvSongName = itemView.findViewById(R.id.locality_song_name);
            mTvSinger = itemView.findViewById(R.id.locality_singer);
            mTvAlbum = itemView.findViewById(R.id.locality_album);
            mIbMore = itemView.findViewById(R.id.locality_more);
        }
    }
}
