package com.example.hongzebin.beanmusic.locality.bean;

/**
 * 音乐实体类
 * Created By Mr.Bean
 */
public class MP3Info {

    private long id;
    private String songName;
    private String singer;
    private String album;
    private String URL;
    private String firstAlphabet;

    public MP3Info(long id, String songName, String singer, String album, String URL
            , String firstAlphabet) {
        this.id = id;
        this.songName = songName;
        this.singer = singer;
        this.album = album;
        this.URL = URL;
        this.firstAlphabet = firstAlphabet;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getFirstAlphabet() {
        return firstAlphabet;
    }

    public void setFirstAlphabet(String firstAlphabet) {
        this.firstAlphabet = firstAlphabet;
    }
}
