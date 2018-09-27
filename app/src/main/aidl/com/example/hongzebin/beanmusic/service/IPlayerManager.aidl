// IPlayerManager.aidl
package com.example.hongzebin.beanmusic.service;

// Declare any non-default types here with import statements

interface IPlayerManager {
    void play();
    void pause();
    void setSong(String songAddress);
    void setCurrDuration(long songCurrTime);
}
