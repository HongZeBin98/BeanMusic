package com.example.hongzebin.beanmusic.search.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.hongzebin.beanmusic.search.bean.SearchAlbum;
import com.example.hongzebin.beanmusic.search.bean.SearchSinger;
import com.example.hongzebin.beanmusic.search.bean.SearchSong;
import com.example.hongzebin.beanmusic.base.bean.Song;
import com.example.hongzebin.beanmusic.util.HttpUtil;
import com.example.hongzebin.beanmusic.util.ParseJson;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

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

    public interface getSongCallBack{
        void onFinish(Song song);
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

    /**
     * 从网上获取到歌曲结果
     * @param songId 歌曲Id
     * @param callBack 回调接口
     */
    public void getSong(final String songId, final getSongCallBack callBack){
            HttpUtil.sendHttpRequest(MusicApi.Song.songInfo(songId), new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    callBack.onFailure(e);
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    callBack.onFinish(parseSongJson(Objects.requireNonNull(response.body()).string()));
                }
            });
    }

    /**
     * 解析出歌曲信息，因为是在两个JSONArray中获取数据，故无法使用Gson
     * @param Json 需要解析的原始Json数据
     * @return  解析出来的歌曲
     */
    private Song parseSongJson(String Json){
        try {
            JSONObject object = new JSONObject(Json).getJSONObject("songinfo");
            JSONObject jsonObject = new JSONObject(Json).getJSONObject("songurl").getJSONArray("url").getJSONObject(0);
            String songAddress = (String) jsonObject.get("file_link");
            int songTime = (int) jsonObject.get("file_duration");
            String smallImageAddress = (String) object.get("pic_big");
            String largeImageAddress = (String) object.get("pic_radio");
            String songName = (String) object.get("title");
            String singer = (String) object.get("artist");
            String album = (String) object.get("album_title");
            String songId = (String) object.get("song_id");
            String singerId = (String) object.get("artist_id");
            String albumId = (String) object.get("album_id");
            String lyric = (String) object.get("lrclink");
            return new Song(songAddress, songTime, smallImageAddress, largeImageAddress, songName, singer, album, songId
                    , singerId, albumId, lyric, false);
        } catch (JSONException e) {
            Log.e("SearchModel", Log.getStackTraceString(e) );
        }
        return null;
    }
}
