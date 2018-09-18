package com.example.hongzebin.beanmusic.recommendation.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 推荐页面推荐歌曲板块的实体类
 * Created By Mr.Bean
 */
public class RecSong {
    //歌曲id
    @SerializedName("song_id")
    private String songId;
    //歌手id
    @SerializedName("artist_id")
    private String singerId;
    //专辑id
    @SerializedName("album_id")
    private String albumId;
    //图片地址
    @SerializedName("pic_big")
    private String image;
    //歌名
    @SerializedName("title")
    private String songName;
    //歌手
    @SerializedName("author")
    private String singer;
    //歌曲URL
    @SerializedName("url")
    private String songUrl;

    public RecSong(String songId, String singerId, String albumId, String image,
                   String songName, String singer, String songUrl) {
        this.songId = songId;
        this.singerId = singerId;
        this.albumId = albumId;
        this.image = image;
        this.songName = songName;
        this.singer = singer;
        this.songUrl = songUrl;
    }

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public String getSingerId() {
        return singerId;
    }

    public void setSingerId(String singerId) {
        this.singerId = singerId;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getSongUrl() {
        return songUrl;
    }

    public void setSongUrl(String songUrl) {
        this.songUrl = songUrl;
    }
}
