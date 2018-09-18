package com.example.hongzebin.beanmusic.recommendation.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 推荐页面轮播图实体类
 * Created By Mr.Bean
 */
public class Shuffling {

    //轮播图内容类型，6是文章，7是mv，2是榜单
    @SerializedName("type")
    private int type;
    //轮播图图片地址
    @SerializedName("randpic")
    private String imageAddress;
    //轮播图内容
    @SerializedName("code")
    private String content;

    public Shuffling(int type, String imageAddress, String content) {
        this.type = type;
        this.imageAddress = imageAddress;
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getImageAddress() {
        return imageAddress;
    }

    public void setImageAddress(String imageAddress) {
        this.imageAddress = imageAddress;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
