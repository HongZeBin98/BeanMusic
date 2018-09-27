package com.example.hongzebin.beanmusic.music.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hongzebin.beanmusic.R;
import com.example.hongzebin.beanmusic.base.bean.PlayConditionStickEvent;
import com.example.hongzebin.beanmusic.base.bean.Song;
import com.example.hongzebin.beanmusic.music.MusicManager;
import com.example.hongzebin.beanmusic.util.BeanMusicApplication;
import com.example.hongzebin.beanmusic.util.LocalityMP3InfoUtil;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 底部音乐播放栏
 * Created By Mr.Bean
 */
public class BottomPlayerFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener{

    private View mView;
    private TextView mTvSongName;
    private TextView mTvSinger;
    private TextView mTvAlbum;
    private CheckBox mCbPlay;
    private CircleImageView mCivImage;
    private ImageButton mIbSongList;
    private List<Song> mSongList;
    private int mPosition;
    private MusicManager mMusicManager;

    /**
     * 新建一个底部播放栏，并且传入该播放栏的状态
     * @param playCondition 播放栏状态
     * @return 底部播放栏
     */
    public static BottomPlayerFragment newInstance(PlayConditionStickEvent playCondition){
        BottomPlayerFragment fragment = new BottomPlayerFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("Condition", playCondition);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initView(inflater, container);
        initData();
        initEvent();
        return mView;
    }

    private void initView(LayoutInflater inflater, ViewGroup container) {
        mView = inflater.inflate(R.layout.player_bottom, container, false);
        mPosition = 0;
        mTvSongName = mView.findViewById(R.id.player_bottom_song_name);
        mTvSinger = mView.findViewById(R.id.player_bottom_singer);
        mTvAlbum = mView.findViewById(R.id.player_bottom_album);
        mCbPlay = mView.findViewById(R.id.player_bottom_play_pause);
        mCivImage = mView.findViewById(R.id.player_bottom_circle_image_view);
        mIbSongList = mView.findViewById(R.id.player_bottom_song_list);
        mMusicManager = MusicManager.getInstance();
    }

    private void initData() {
        Bundle bundle = getArguments();
        if (bundle != null){
            setCondition((PlayConditionStickEvent) bundle.getSerializable("Condition"));
        }
    }

    private void initEvent() {
        mIbSongList.setOnClickListener(this);
        mCbPlay.setOnCheckedChangeListener(this);
    }

    /**
     * 返回当前底部播放栏的状态
     * @return 底部播放栏状态
     */
    public PlayConditionStickEvent getPlayerCondition(){
        return new PlayConditionStickEvent(mSongList, mPosition, mCbPlay.isChecked());
    }

    /**
     * 获取播放列表，并播放指定位置的歌
     * @param songList 播放列表
     * @param position 播放歌曲的在播放列表中的位置
     */
    public void setSongList(List<Song> songList, int position){
        Song song = songList.get(position);
        mMusicManager.setSongPlay(song.getSongAddress());
        mSongList  = songList;
        mPosition = position;
        showView(song);
    }

    /**
     * 插入歌曲到播放列表中 ,并播放
     * @param song 要插入的歌
     */
    public void insertSongToSongList(Song song){
        mMusicManager.setSongPlay(song.getSongAddress());
        showView(song);
        if(mSongList == null){
            mSongList = new ArrayList<>();
            mSongList.add(song);
            mPosition = 0;
        }else {
            mPosition++;
            mSongList.add(mPosition, song);
        }
    }

    /**
     * 设置底部播放栏的状态
     * @param condition 底部播放栏的状态
     */
    public void setCondition(PlayConditionStickEvent condition){
        if (condition != null) {
            mSongList = condition.getSongList();
            if (mSongList != null) {
                mPosition = condition.getPosition();
                Song song = mSongList.get(mPosition);
                showView(song);
            }
            mCbPlay.setChecked(condition.isPlay());
        }
    }

    /**
     * 把播放的歌曲展示在底部播放栏上
     * @param song 播放歌曲
     */
    private void showView(Song song){
        mTvSongName.setText(song.getSongName());
        mTvSinger.setText(song.getSinger());
        mTvAlbum.setText(song.getAlbum());
        if (mCbPlay.isChecked()){
            mMusicManager.playMusic();
        }else {
            mCbPlay.setChecked(true);
        }
        if (song.isLocality()) {
            Bitmap bitmap = LocalityMP3InfoUtil.getLocalityMusicBitmap(song.getSongId(), song.getSmallImageAddress(), 150);
            mCivImage.setImageBitmap(bitmap);
        }else {
            if (song.getSmallImageAddress().equals("")){
                //无法获取到专辑图片
                mCivImage.setImageResource(R.drawable.music_false);
            }else {
                Glide.with(BeanMusicApplication.getContext()).load(song.getSmallImageAddress()).into(mCivImage);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.player_bottom_song_list:
                //打开播放列表
                break;
            default:
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked){
            mMusicManager.playMusic();
        }else {
            mMusicManager.pauseMusic();
        }
    }
}
