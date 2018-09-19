package com.example.hongzebin.beanmusic.locality.presenter;

import com.example.hongzebin.beanmusic.base.BasePresenter;
import com.example.hongzebin.beanmusic.locality.bean.MP3Info;
import com.example.hongzebin.beanmusic.locality.contract.LocMVPContract;
import com.example.hongzebin.beanmusic.locality.model.LocModel;
import com.example.hongzebin.beanmusic.util.BeanMusicApplication;

import java.util.List;

public class LocPresenter extends BasePresenter<LocMVPContract.View> implements LocMVPContract.Presenter {

    private LocMVPContract.View mView;
    private LocModel mLocModel;

    public LocPresenter(LocMVPContract.View view){
        mView = view;
        mLocModel = new LocModel();
    }

    @Override
    public void getData() {
        List<MP3Info> mp3InfoList = mLocModel.findSongs(BeanMusicApplication.getContext().getContentResolver());
        mView.showMusicList(mp3InfoList);
    }
}
