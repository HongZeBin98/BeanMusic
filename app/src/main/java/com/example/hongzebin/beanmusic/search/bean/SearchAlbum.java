package com.example.hongzebin.beanmusic.search.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 搜索得到的专辑实体类
 * Created By Mr.Bean
 */
public class SearchAlbum {

    @SerializedName("album_id")
    private String albumId;
    @SerializedName("title")
    private String album;
    @SerializedName("author")
    private String singer;
    @SerializedName("pic_small")
    private String imageAddress;

    public SearchAlbum(String albumId, String album, String singer, String imageAddress) {
        this.albumId = albumId;
        this.album = album;
        this.singer = singer;
        this.imageAddress = imageAddress;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
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
}
