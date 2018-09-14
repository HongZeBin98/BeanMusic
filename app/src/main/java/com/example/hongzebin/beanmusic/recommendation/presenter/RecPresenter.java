package com.example.hongzebin.beanmusic.recommendation.presenter;

import android.support.v4.app.FragmentActivity;

import com.example.hongzebin.beanmusic.base.BasePresenter;
import com.example.hongzebin.beanmusic.base.MVPContract;
import com.example.hongzebin.beanmusic.recommendation.bean.ShufflingBean;
import com.example.hongzebin.beanmusic.recommendation.model.RecModel;

import java.util.List;

public class RecPresenter extends BasePresenter<MVPContract.View> implements MVPContract.Presenter{

    private RecModel mRecModel;
    private MVPContract.View mView;
    private List<ShufflingBean> mShufflingBeanList;

    public RecPresenter(MVPContract.View view){
        mView = view;
        mRecModel = new RecModel();
    }

    @Override
    public void getData(final FragmentActivity fragmentActivity) {
        mRecModel.getShufflings(7, new RecModel.RecModelCallback() {
            @Override
            public void onFinish(final List<ShufflingBean> shufflingBeans) {
                mShufflingBeanList = shufflingBeans;
                //返回UI线程
                fragmentActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mView.showView(shufflingBeans);
                    }
                });
            }
        });
    }
}
