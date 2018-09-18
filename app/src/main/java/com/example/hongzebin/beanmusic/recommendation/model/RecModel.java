package com.example.hongzebin.beanmusic.recommendation.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.hongzebin.beanmusic.recommendation.bean.HotSongList;
import com.example.hongzebin.beanmusic.recommendation.bean.NewSong;
import com.example.hongzebin.beanmusic.recommendation.bean.RecSong;
import com.example.hongzebin.beanmusic.recommendation.bean.Shuffling;
import com.example.hongzebin.beanmusic.util.HttpUtil;
import com.example.hongzebin.beanmusic.util.ParseJson;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
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
 * 获取推荐页面的数据
 * Created By Mr.Bean
 */
public class RecModel {

    /**
     * 返回给Presenter层轮播图实体类的回调接口
     */
    public interface ReturnShufflingCallBack{
        void onFinish(List<Shuffling> shufflings);
        void onFailure(Exception e);
    }

    /**
     * 返回给Presenter层热门歌单实体类的回调接口
     */
    public interface ReturnHotSongListCallBack{
        void onFinish(List<HotSongList> hotSongLists);
        void onFailure(Exception e);
    }

    /**
     * 返回给Presenter层新歌首发实体类的回调接口
     */
    public interface ReturnNewSongCallBack{
        void onFinish(List<NewSong> newSongs);
        void onFailure(Exception e);
    }

    /**
     * 返回给Presenter层推荐歌曲实体类的回调接口
     */
    public interface ReturnRecSongCallBack{
        void onFinish(List<RecSong> recSongs);
        void onFailure(Exception e);
    }

    /**
     * 从网上获取到轮播图实体类
     * @param shufflingCount 需要获取的轮播图的数量
     * @param callback  回调接口
     */
    public void getShufflings(int shufflingCount, final ReturnShufflingCallBack callback){
        HttpUtil.sendHttpRequest(MusicApi.focusPic(shufflingCount), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                Log.e("IOException", Log.getStackTraceString(e));
                callback.onFailure(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                //获取Json字符串并且进行判空处理
                String responseData = Objects.requireNonNull(response.body()).string();
                //对原始的json数据进行截取获取其中需要的json数据
                responseData = ParseJson.obtainDesignationJson(responseData, "pic");
                //通过Gson解析json数据
                List<Shuffling> shuffling = new Gson().fromJson(responseData, new TypeToken<List<Shuffling>>(){}.getType());
                callback.onFinish(shuffling);
            }
        });
    }

    /**
     * 从网上获取到热门歌单实体类
     * @param songListCount 获取的热门歌单的数量
     * @param callback  回调接口
     */
    public void getHotSongList(int songListCount,  final ReturnHotSongListCallBack callback){
        HttpUtil.sendHttpRequest(MusicApi.GeDan.hotGeDan(songListCount), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                Log.e("IOException", Log.getStackTraceString(e));
                callback.onFailure(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                //获取Json字符串并且进行判空处理
                String responseData  = Objects.requireNonNull(response.body()).string();
                //对原始的json进行截取获取其中需要的json数据
                responseData = ParseJson.obtainDesignationJson(responseData, "content.list");
                //通过Gson解析json数据
                List<HotSongList> hotSongs = new Gson().fromJson(responseData, new TypeToken<List<HotSongList>>(){}.getType());
                callback.onFinish(hotSongs);
            }
        });
    }

    /**
     * 从网上获取到新歌首发实体类
     * @param offset 从第几个开始获取
     * @param newSongCount  获取数量
     * @param callback 回调接口
     */
    public void getNewSong(int offset, final int newSongCount, final ReturnNewSongCallBack callback){
        HttpUtil.sendHttpRequest(MusicApi.Album.recommendAlbum(offset, newSongCount), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                Log.e("IOException", Log.getStackTraceString(e));
                callback.onFailure(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                //获取Json字符串并且进行判空处理
                String responseData  = Objects.requireNonNull(response.body()).string();
                //对原始的json进行截取获取其中需要的json数据
                responseData = ParseJson.obtainDesignationJson(responseData, "plaze_album_list.RM.album_list.list");
                //通过Gson解析json数据
                List<NewSong> newSongs = new Gson().fromJson(responseData, new TypeToken<List<NewSong>>(){}.getType());
                callback.onFinish(newSongs);
            }
        });
    }

    /**
     * 从网上获取到推荐歌曲的实体类
     * @param recSongCount 获取数量
     * @param callback  回调接口
     */
    public void getRecSong(int recSongCount, final ReturnRecSongCallBack callback){
        HttpUtil.sendHttpRequest(MusicApi.Song.recommendSong(recSongCount), new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                Log.e("IOException", Log.getStackTraceString(e));
                callback.onFailure(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                //获取Json字符串并且进行判空处理
                String responseData  = Objects.requireNonNull(response.body()).string();
                //对原始的json进行截取获取其中需要的json数据
                responseData = parseJsonForRecSong(responseData);
                //通过Gson解析json数据
                List<RecSong> recSongs = new Gson().fromJson(responseData, new TypeToken<List<RecSong>>(){}.getType());
                callback.onFinish(recSongs);
            }
        });
    }

    /**
     * 对获取到的推荐歌曲的Json数据进行截取，获得需要的json数据
     * @param jsonData 原始的Json数据
     * @return 经过截取的json数据
     */
    private String parseJsonForRecSong(String jsonData)  {
        try {
            return new JSONObject(jsonData).getJSONArray("content").getJSONObject(0).getString("song_list");
        } catch (JSONException e) {
            Log.e("RecModel", Log.getStackTraceString(e));
        }
        return null;
    }

}
