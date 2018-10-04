package com.example.hongzebin.beanmusic.base.view;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.hongzebin.beanmusic.base.presenter.BasePresenter;

/**
 * 使得Fragment的生命周期和Presenter进行关联，防止出现内存泄漏，进行懒加载
 * @param <V> View层接口
 * @param <P> Presenter
 * Created By Mr.Bean
 */
public abstract class BaseMVPFragment<V, P extends BasePresenter<V>> extends LazyFragment {

    protected P mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //创建Presenter
        mPresenter = createPresenter();
        mPresenter.attachView((V)this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    protected abstract P createPresenter();
}
