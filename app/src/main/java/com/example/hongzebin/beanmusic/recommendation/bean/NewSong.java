package com.example.hongzebin.beanmusic.recommendation.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 推荐页面新歌首发板块的实体类
 * Created By Mr.Bean
 */
public class NewSong {
    //专辑id
    @SerializedName("album_id")
    private String albumId;
    //标题
    @SerializedName("title")
    private String title;
    //歌手
    @SerializedName("author")
    private String author;
    //发行时间
    @SerializedName("publishtime")
    private String time;
    //图片网址
    @SerializedName("pic_big")
    private String imageAddress;

    public NewSong(String albumId, String title, String author, String time, String imageAddress) {
        this.albumId = albumId;
        this.title = title;
        this.author = author;
        this.time = time;
        this.imageAddress = imageAddress;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImageAddress() {
        return imageAddress;
    }

    public void setImageAddress(String imageAddress) {
        this.imageAddress = imageAddress;
    }
}
