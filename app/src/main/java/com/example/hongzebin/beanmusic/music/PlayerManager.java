package com.example.hongzebin.beanmusic.music;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.hongzebin.beanmusic.R;
import com.example.hongzebin.beanmusic.music.bean.PlayMode;
import com.example.hongzebin.beanmusic.music.view.IPlayModeChangeListener;
import com.example.hongzebin.beanmusic.music.view.MusicPlayerActivity;
import com.example.hongzebin.beanmusic.music.view.SongListPopupWindow;

import java.util.ArrayList;
import java.util.List;

public class PlayerManager implements IPlayModeChangeListener
        , MusicManager.PlayFinishCallBack{

    private SongListPopupWindow mPopupWindow;
    private int mModeSelected;
    private int mPosition;
    private MusicManager mMusicManager;
    private List<NextSongListener> mListenerList;
    private List<Integer> mPlayMode;
    private List<String> mPlayModeText;
    private MusicPlayerActivity mPlayerActivity;

    public interface NextSongListener{
        void nextSong(int position);
    }

    private PlayerManager(){
       initView();
       initData();
       initEvent();
    }

    public static PlayerManager getInstance(){
        return PlayerManagerHolder.INSTANCE;
    }

    private static class PlayerManagerHolder{
        @SuppressLint("StaticFieldLeak")
        private static final PlayerManager INSTANCE = new PlayerManager();
    }

    private void initView() {
        mPosition = 0;
        mModeSelected = 0;
        mListenerList = new ArrayList<>();
        mPopupWindow = SongListPopupWindow.getInstance();
        mMusicManager = MusicManager.getInstance();
        mPlayMode = new ArrayList<>();
        mPlayModeText = new ArrayList<>();
    }

    private void initData() {
        mPlayMode.add(R.drawable.loop_play);
        mPlayMode.add(R.drawable.random_play);
        mPlayMode.add(R.drawable.single_play);
        mPlayModeText.add("列表循环");
        mPlayModeText.add("随机播放");
        mPlayModeText.add("单曲循环");
    }

    private void initEvent() {
        mPopupWindow.setPlayModeChangeListener(this);
        mMusicManager.setPlayFinishCallBack(this);
    }

    @Override
    public void modeChange() {
        mModeSelected++;
        if (mModeSelected >= 3){
            mModeSelected = 0;
        }

    }

    @Override
    public void onPlayFinish() {
        int songListSize = mPopupWindow.getSongList().size();
        if (mModeSelected == 0){
            //列表循环
            mMusicManager.setLooping(false);
            if (mPosition + 1 == songListSize){
                mPosition = 0;
            }else {
                mPosition = -1;
            }
            nextSongCallBack();
        } else if (mModeSelected == 1) {
            //随机播放
            mMusicManager.setLooping(false);
            mPosition = (int) (Math.random() * songListSize);
            nextSongCallBack();
        }else {
            //单曲循环
            mMusicManager.setLooping(true);
        }
    }

    private void nextSongCallBack(){
        for (NextSongListener listener : mListenerList){
            /*
              mPosition == -1 就是按顺序往下播下一首歌
              mPosition >= 0 就是播放播放列表中响应次序的歌曲
             */
            listener.nextSong(mPosition);
        }
    }

    public PlayMode getPlayModeView(){
        return new PlayMode(mPlayMode.get(mModeSelected), mPlayModeText.get(mModeSelected));
    }

    public void addNextSongListener(NextSongListener listener){
        mListenerList.add(listener);
    }

    public int getPlayMode(){
       return mModeSelected;
    }

    public void setPlayActivity(MusicPlayerActivity activity){
        mPlayerActivity = activity;
        mPlayerActivity.setPlayModeChangeListener(this);
    }
}
