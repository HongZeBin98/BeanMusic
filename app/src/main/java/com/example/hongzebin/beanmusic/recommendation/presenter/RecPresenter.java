package com.example.hongzebin.beanmusic.recommendation.presenter;

import android.util.Log;

import com.example.hongzebin.beanmusic.base.BasePresenter;
import com.example.hongzebin.beanmusic.recommendation.contract.RecMVPContract;
import com.example.hongzebin.beanmusic.recommendation.bean.HotSongList;
import com.example.hongzebin.beanmusic.recommendation.bean.NewSong;
import com.example.hongzebin.beanmusic.recommendation.bean.RecSong;
import com.example.hongzebin.beanmusic.recommendation.bean.Shuffling;
import com.example.hongzebin.beanmusic.recommendation.model.RecModel;

import java.util.List;

public class RecPresenter extends BasePresenter<RecMVPContract.View> implements RecMVPContract.Presenter{

    private RecModel mRecModel;
    private RecMVPContract.View mView;

    public RecPresenter(RecMVPContract.View view){
        mView = view;
        mRecModel = new RecModel();
    }

    @Override
    public void getData() {
        getShuffling();
        getHotSongList();
        getNewSong();
        getRecSong();
    }

    /**
     * 通过Model层获取到轮播图实体类，再回调给view层
     */
    private void getShuffling(){
        mRecModel.getShufflings(7, new RecModel.ReturnShufflingCallBack() {
            @Override
            public void onFinish(final List<Shuffling> shufflings) {
                mView.showShuffling(shufflings);
            }

            @Override
            public void onFailure(Exception e) {
                Log.e("RecPresenter", Log.getStackTraceString(e));
            }

        });
    }

    /**
     * 通过Model层获取到热门歌单实体类，再回调给view层
     */
    private void getHotSongList(){
        mRecModel.getHotSongList(6, new RecModel.ReturnHotSongListCallBack() {
            @Override
            public void onFinish(final List<HotSongList> hotSongLists) {
                mView.showHotSongList(hotSongLists);
            }

            @Override
            public void onFailure(Exception e) {
                Log.e("RecPresenter", Log.getStackTraceString(e));
            }
        });
    }

    /**
     * 通过Model层获取到新歌首发实体类，再回调给view层
     */
    private void getNewSong(){
        mRecModel.getNewSong(0, 6, new RecModel.ReturnNewSongCallBack() {
            @Override
            public void onFinish(final List<NewSong> newSongs) {
                mView.showNewSong(newSongs);
            }

            @Override
            public void onFailure(Exception e) {
                Log.e("RecPresenter", Log.getStackTraceString(e));
            }
        });
    }

    /**
     * 通过Model层获取到推荐歌单实体类，再回调给view层
     */
    private void getRecSong(){
        mRecModel.getRecSong(6, new RecModel.ReturnRecSongCallBack() {
            @Override
            public void onFinish(final List<RecSong> recSongs) {
                mView.showRecSong(recSongs);
            }

            @Override
            public void onFailure(Exception e) {
                Log.e("RecPresenter", Log.getStackTraceString(e));
            }
        });
    }
}
