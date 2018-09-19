package com.example.hongzebin.beanmusic.locality.contract;

import com.example.hongzebin.beanmusic.locality.bean.MP3Info;

import java.util.List;

/**
 * 对本地音乐页面的Presenter层和view层进行规范
 * Created By Mr.Bean
 */
public class LocMVPContract {

    public interface View{
        void showMusicList(List<MP3Info> mp3InfoList);
    }

    public interface Presenter{
        void getData();
    }
}
