package com.example.hongzebin.beanmusic.search.model;

import android.support.annotation.NonNull;

import com.example.hongzebin.beanmusic.search.bean.SearchAlbum;
import com.example.hongzebin.beanmusic.search.bean.SearchSinger;
import com.example.hongzebin.beanmusic.search.bean.SearchSong;
import com.example.hongzebin.beanmusic.util.HttpUtil;
import com.example.hongzebin.beanmusic.util.ParseJson;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import api.MusicApi;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 获取搜索页面需要的数据
 * Created By Mr.Bean
 */
public class SearchModel {

    public interface getSearchResultCallBack{
        void onFinish(List<SearchAlbum> albumList, List<SearchSinger> singerList, List<SearchSong> songList);
        void onFailure(Exception e);
    }

    /**
     * 从网上获取到搜索结果
     * @param search 搜索的关键字
     * @param pageNo    第几页
     * @param pageSize  获取最多数量
     * @param callBack  回调接口
     */
    public void getSearchResult(String search, int pageNo, int pageSize, final getSearchResultCallBack callBack){
        HttpUtil.sendHttpRequest(MusicApi.Search.searchMerge(search, pageNo, pageSize), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callBack.onFailure(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                //获取Json字符串并且进行判空处理
                String responseData = Objects.requireNonNull(response.body()).string();
                String albumJson = ParseJson.obtainDesignationJson(responseData, "result.album_info.album_list");
                String songJson = ParseJson.obtainDesignationJson(responseData, "result.song_info.song_list");
                String singerJson = ParseJson.obtainDesignationJson(responseData, "result.artist_info.artist_list");
                List<SearchAlbum> albumList = new Gson().fromJson(albumJson, new TypeToken<List<SearchAlbum>>(){}.getType());
                List<SearchSinger> singerList = new Gson().fromJson(singerJson, new TypeToken<List<SearchSinger>>(){}.getType());
                List<SearchSong> songList = new Gson().fromJson(songJson, new TypeToken<List<SearchSong>>(){}.getType());
                callBack.onFinish(albumList, singerList, songList);
            }
        });
    }
}
