package com.example.hongzebin.beanmusic.music.presenter;

import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.example.hongzebin.beanmusic.base.presenter.BasePresenter;
import com.example.hongzebin.beanmusic.music.bean.LrcBean;
import com.example.hongzebin.beanmusic.music.contract.MusicMVPContract;
import com.example.hongzebin.beanmusic.music.model.MusicModel;

import java.util.List;

public class MusicPresenter extends BasePresenter<MusicMVPContract.View> implements MusicMVPContract.Presenter{

    private MusicMVPContract.View mView;
    private MusicModel mModel;

    public MusicPresenter(MusicMVPContract.View view){
        mView = view;
        mModel = new MusicModel();
    }

    @Override
    public void getLrc(String lrcUrl, final FragmentActivity activity) {
        if (lrcUrl == null){
            mView.showLrc(null);
        }else {
            mModel.downLoadLrc(lrcUrl, new MusicModel.DownLoadCallBack() {
                @Override
                public void onFinish(final List<LrcBean> lrcBeans) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mView.showLrc(lrcBeans);
                        }
                    });
                }

                @Override
                public void onFailure(Exception e) {
                    Log.e("MusicPresenter", Log.getStackTraceString(e));
                }
            });
        }
    }
}
