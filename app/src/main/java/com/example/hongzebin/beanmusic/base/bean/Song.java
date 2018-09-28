package com.example.hongzebin.beanmusic.base.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import java.io.Serializable;

/**
 * 播放歌曲实体类
 * Created By Mr.Bean
 */
public class Song implements Serializable{

    private String songAddress;
    private long songTime;
    private String smallImageAddress;
    @Nullable
    private String largeImageAddress;
    private String songName;
    private String singer;
    private String album;
    @Nullable
    private String songId;
    @Nullable
    private String singerId;
    @Nullable
    private String albumId;
    @Nullable
    private String lyric;
    private boolean locality;

    public Song(String songAddress, long songTime, String smallImageAddress, @Nullable String largeImageAddress, String songName, String singer
            , String album, @Nullable String songId, @Nullable String singerId, @Nullable String albumId, @Nullable String lyric, boolean locality) {
        this.songAddress = songAddress;
        this.songTime = songTime;
        this.smallImageAddress = smallImageAddress;
        this.largeImageAddress = largeImageAddress;
        this.songName = songName;
        this.singer = singer;
        this.album = album;
        this.songId = songId;
        this.singerId = singerId;
        this.albumId = albumId;
        this.lyric = lyric;
        this.locality = locality;
    }

    protected Song(Parcel in) {
        songAddress = in.readString();
        songTime = in.readLong();
        smallImageAddress = in.readString();
        largeImageAddress = in.readString();
        songName = in.readString();
        singer = in.readString();
        album = in.readString();
        songId = in.readString();
        singerId = in.readString();
        albumId = in.readString();
        lyric = in.readString();
        locality = in.readByte() != 0;
    }

    public String getSongAddress() {
        return songAddress;
    }

    public void setSongAddress(String songAddress) {
        this.songAddress = songAddress;
    }

    public long getSongTime() {
        return songTime;
    }

    public void setSongTime(long songTime) {
        this.songTime = songTime;
    }

    public String getSmallImageAddress() {
        return smallImageAddress;
    }

    public void setSmallImageAddress(String smallImageAddress) {
        this.smallImageAddress = smallImageAddress;
    }

    @Nullable
    public String getLargeImageAddress() {
        return largeImageAddress;
    }

    public void setLargeImageAddress(@Nullable String largeImageAddress) {
        this.largeImageAddress = largeImageAddress;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    @Nullable
    public String getSongId() {
        return songId;
    }

    public void setSongId(@Nullable String songId) {
        this.songId = songId;
    }

    @Nullable
    public String getSingerId() {
        return singerId;
    }

    public void setSingerId(@Nullable String singerId) {
        this.singerId = singerId;
    }

    @Nullable
    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(@Nullable String albumId) {
        this.albumId = albumId;
    }

    @Nullable
    public String getLyric() {
        return lyric;
    }

    public void setLyric(@Nullable String lyric) {
        this.lyric = lyric;
    }

    public boolean isLocality() {
        return locality;
    }

    public void setLocality(boolean locality) {
        this.locality = locality;
    }
}
