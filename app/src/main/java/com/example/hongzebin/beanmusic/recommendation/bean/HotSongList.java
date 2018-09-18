package com.example.hongzebin.beanmusic.recommendation.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 推荐页面热门歌单板块的实体类
 * Created By Mr.Bean
 */
public class HotSongList {
    //歌单id
    @SerializedName("listid")
    private String songListId;
    //图片地址
    @SerializedName("pic")
    private String image;
    //题目
    @SerializedName("title")
    private String title;
    //收听次数
    @SerializedName("listenum")
    private String listenNum;

    public HotSongList(String songListId, String image, String title, String listenNum) {
        this.songListId = songListId;
        this.image = image;
        this.title = title;
        this.listenNum = listenNum;
    }

    public String getSongListId() {
        return songListId;
    }

    public void setSongListId(String songListId) {
        this.songListId = songListId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getListenum() {
        return listenNum;
    }

    public void setListenum(String listenNum) {
        this.listenNum = listenNum;
    }
}
