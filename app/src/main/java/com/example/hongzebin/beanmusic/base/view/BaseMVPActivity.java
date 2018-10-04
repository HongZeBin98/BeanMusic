package com.example.hongzebin.beanmusic.base.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.hongzebin.beanmusic.base.presenter.BasePresenter;

public abstract class BaseMVPActivity<V, P extends BasePresenter<V>> extends AppCompatActivity {

    protected P mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //创建Presenter
        mPresenter = createPresenter();
        mPresenter.attachView((V)this);
        // 初始化布局
        initView();
        //初始化数据
        initData();
        // 初始化事件
        initEvents();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    protected abstract P createPresenter();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initEvents();
}
