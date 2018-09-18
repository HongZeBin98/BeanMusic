package com.example.hongzebin.beanmusic.recommendation.contract;

import android.support.v4.app.FragmentActivity;

import com.example.hongzebin.beanmusic.recommendation.bean.HotSongList;
import com.example.hongzebin.beanmusic.recommendation.bean.NewSong;
import com.example.hongzebin.beanmusic.recommendation.bean.RecSong;
import com.example.hongzebin.beanmusic.recommendation.bean.Shuffling;

import java.util.List;

/**
 * 对推荐页面的view层和presenter层进行规范
 * Created By Mr.Bean
 */
public class RecMVPContract {

    public interface View {
        void showShuffling(List<Shuffling> shufflings);
        void showHotSongList(List<HotSongList> hotSongLists);
        void showNewSong(List<NewSong> newSongs);
        void showRecSong(List<RecSong> recSongs);
    }

    public interface Presenter {
        void getData();
    }
}