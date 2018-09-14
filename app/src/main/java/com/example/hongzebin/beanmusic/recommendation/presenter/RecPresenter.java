package com.example.hongzebin.beanmusic.recommendation.presenter;

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
    public void getData() {
        mRecModel.getShufflings(7, new RecModel.RecModelCallback() {
            @Override
            public void onFinish(List<ShufflingBean> shufflingBeans) {
                mView.showView(shufflingBeans);
            }
        });
    }
}
