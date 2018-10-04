package com.example.hongzebin.beanmusic.music.bean;

import android.support.annotation.NonNull;

/**
 * 歌词bean
 * Created By Mr.Bean
 */
public class LrcBean implements Comparable<LrcBean> {

    //该行歌词要开始播放的时间，格式如下：[02:34.14]
    private String strTime;

    //该行歌词要开始播放的时间，由[02:34.14]格式转换为long型,单位为毫秒
    private long time;

    // 该行歌词的内容
    private String content;

    public LrcBean(String strTime, long time, String content) {
        this.strTime = strTime;
        this.time = time;
        this.content = content;
    }

    public String getStrTime() {
        return strTime;
    }

    public void setStrTime(String strTime) {
        this.strTime = strTime;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int compareTo(@NonNull LrcBean o) {
        return (int) (this.time - o.time);
    }

    @Override
    public String toString() {
        return "[" + strTime + " ]"  + content;
    }
}