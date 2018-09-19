package com.example.hongzebin.beanmusic.locality.contract;

import com.example.hongzebin.beanmusic.locality.bean.MP3Info;

import java.util.List;

public class LocMVPContract {
    public interface View{
        void showMusicList(List<MP3Info> mp3InfoList);
        void showNavigationBar();
    }

    public interface Presenter{
        void getData();
    }
}
