package com.example.hongzebin.beanmusic.base.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 底部播放栏状态粘性事件，用于EventBus
 * Created By Mr.Bean
 */
public class PlayConditionStickEvent implements Serializable{

    private List<Song> songList;
    private int position;
    private boolean isPlay;

    public PlayConditionStickEvent(List<Song> songList, int position, boolean isPlay) {
        this.songList = songList;
        this.position = position;
        this.isPlay = isPlay;
    }

    public List<Song> getSongList() {
        return songList;
    }

    public void setSongList(List<Song> songList) {
        this.songList = songList;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isPlay() {
        return isPlay;
    }

    public void setPlay(boolean play) {
        isPlay = play;
    }
}

