package com.example.hongzebin.beanmusic.base.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 音乐底部播放栏的状态
 * Created By Mr.Bean
 */
public class PlayerCondition implements Parcelable{

    private List<Song> songList;
    private int position;
    private boolean isPlay;

    public PlayerCondition(List<Song> songList, int position, boolean isPlay) {
        this.songList = songList;
        this.position = position;
        this.isPlay = isPlay;
    }

    private PlayerCondition(Parcel in) {
        position = in.readInt();
        isPlay = in.readByte() != 0;
    }

    public static final Creator<PlayerCondition> CREATOR = new Creator<PlayerCondition>() {
        @Override
        public PlayerCondition createFromParcel(Parcel in) {
            return new PlayerCondition(in);
        }

        @Override
        public PlayerCondition[] newArray(int size) {
            return new PlayerCondition[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(position);
        dest.writeByte((byte) (isPlay ? 1 : 0));
    }
}

