package com.example.hongzebin.beanmusic.music.bean;

/**
 * 播放顺序模式
 * Created By Mr.Bean
 */
public class PlayMode{

    private int modeId;
    private String modeText;

    public PlayMode(int modeId, String modeText) {
        this.modeId = modeId;
        this.modeText = modeText;
    }

    public int getModeId() {
        return modeId;
    }

    public void setModeId(int modeId) {
        this.modeId = modeId;
    }

    public String getModeText() {
        return modeText;
    }

    public void setModeText(String modeText) {
        this.modeText = modeText;
    }
}
