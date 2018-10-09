// IPlayerManager.aidl
package com.example.hongzebin.beanmusic.music.service;

// Declare any non-default types here with import statements
import com.example.hongzebin.beanmusic.music.service.IPlayFinishListener;

interface IPlayerManager {
    void play();
    void pause();
    void setSong(String songAddress);
    void setCurrDuration(long songCurrTime);
    float getProgress();
    void registerListener(IPlayFinishListener listener);
    void setLooping(boolean looping);
}
