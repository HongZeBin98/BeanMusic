package com.example.hongzebin.beanmusic.search.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 搜索得到的歌手实体类
 * Created By Mr.Bean
 */
public class SearchSinger {

    @SerializedName("artist_id")
    private String singerId;
    @SerializedName("author")
    private String singer;
    @SerializedName("avatar_middle")
    private String imageAddress;
    @SerializedName("song_num")
    private int songCount;
    @SerializedName("album_num")
    private int albumCount;

    public SearchSinger(String singerId, String singer, String imageAddress
            , int songCount, int albumCount) {
        this.singerId = singerId;
        this.singer = singer;
        this.imageAddress = imageAddress;
        this.songCount = songCount;
        this.albumCount = albumCount;
    }

    public String getSingerId() {
        return singerId;
    }

    public void setSingerId(String singerId) {
        this.singerId = singerId;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getImageAddress() {
        return imageAddress;
    }

    public void setImageAddress(String imageAddress) {
        this.imageAddress = imageAddress;
    }

    public int getSongCount() {
        return songCount;
    }

    public void setSongCount(int songCount) {
        this.songCount = songCount;
    }

    public int getAlbumCount() {
        return albumCount;
    }

    public void setAlbumCount(int albumCount) {
        this.albumCount = albumCount;
    }
}
