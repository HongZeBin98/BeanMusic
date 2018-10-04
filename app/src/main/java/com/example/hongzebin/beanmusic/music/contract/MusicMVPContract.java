package com.example.hongzebin.beanmusic.music.contract;

import android.support.v4.app.FragmentActivity;

import com.example.hongzebin.beanmusic.music.bean.LrcBean;

import java.util.List;

public class MusicMVPContract {

    public interface View{
        void showLrc(List<LrcBean> lrcBeans);
    }

    public interface Presenter{
        void getLrc(String lrcUrl, FragmentActivity activity);
    }
}
