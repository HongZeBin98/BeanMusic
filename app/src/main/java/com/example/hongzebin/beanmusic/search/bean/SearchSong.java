package com.example.hongzebin.beanmusic.search.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 搜索得到的歌曲实体类
 * Created By Mr.Bean
 */
public class SearchSong {

    @SerializedName("song_id")
    private String songId;
    @SerializedName("album_id")
    private String albumId;
    @SerializedName("artist_id")
    private String singerId;
    @SerializedName("title")
    private String songName;
    @SerializedName("album_title")
    private String album;
    @SerializedName("author")
    private String singer;
    @SerializedName("pic_small")
    private String imageAddress;
    @SerializedName("lrclink")
    private String lyric;

    public SearchSong(String songId, String albumId, String singerId, String songName
            , String album, String singer, String imageAddress, String lyric) {
        this.songId = songId;
        this.albumId = albumId;
        this.singerId = singerId;
        this.songName = songName;
        this.album = album;
        this.singer = singer;
        this.imageAddress = imageAddress;
        this.lyric = lyric;
    }

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getSingerId() {
        return singerId;
    }

    public void setSingerId(String singerId) {
        this.singerId = singerId;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
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

    public String getLyric() {
        return lyric;
    }

    public void setLyric(String lyric) {
        this.lyric = lyric;
    }
}
