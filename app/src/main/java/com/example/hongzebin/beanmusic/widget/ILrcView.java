package com.example.hongzebin.beanmusic.widget;

import com.example.hongzebin.beanmusic.music.bean.LrcBean;

import java.util.List;

/**
 * 展示歌词的接口
 */
public interface ILrcView {
    //设置要展示的歌词行集合
    void setLrc(List<LrcBean> lrcBeans);

    //音乐播放的时候调用该方法滚动歌词，高亮正在播放的那句歌词
    void seekLrcToTime(long time);

    //设置歌词拖动时候的监听类
    void setListener(LrcView.ILrcViewListener listener);
}

