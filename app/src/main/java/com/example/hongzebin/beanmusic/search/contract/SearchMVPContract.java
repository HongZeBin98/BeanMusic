package com.example.hongzebin.beanmusic.search.contract;

import android.support.v4.app.FragmentActivity;

import com.example.hongzebin.beanmusic.search.bean.SearchAlbum;
import com.example.hongzebin.beanmusic.search.bean.SearchSinger;
import com.example.hongzebin.beanmusic.search.bean.SearchSong;
import com.example.hongzebin.beanmusic.base.bean.Song;

import java.util.List;

/**
 * 对搜索页面的view层和presenter层进行规范
 * Created By Mr.Bean
 */
public class SearchMVPContract {

    public interface View{
        void returnTag();
        void showResult(List<SearchAlbum> albumList, List<SearchSinger> singerList, List<SearchSong> songList, boolean loadMoreFlag);
        void returnSong(Song song);
    }

    public interface Presenter{
        void getRecSearchTag();
        void getSearchResult(FragmentActivity activity, String request, int pageNo);
        void getSong(SearchSong searchSong);
    }
}
