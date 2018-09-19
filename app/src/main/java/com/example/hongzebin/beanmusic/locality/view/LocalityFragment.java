package com.example.hongzebin.beanmusic.locality.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hongzebin.beanmusic.R;
import com.example.hongzebin.beanmusic.base.BaseFragment;
import com.example.hongzebin.beanmusic.base.LazyFragment;
import com.example.hongzebin.beanmusic.locality.adapter.LocRecyclerViewAdapter;
import com.example.hongzebin.beanmusic.locality.bean.MP3Info;
import com.example.hongzebin.beanmusic.locality.contract.LocMVPContract;
import com.example.hongzebin.beanmusic.locality.presenter.LocPresenter;
import com.example.hongzebin.beanmusic.recommendation.contract.RecMVPContract;

import java.util.List;

public class LocalityFragment extends BaseFragment<LocMVPContract.View, LocPresenter> implements LocMVPContract.View{

    private RecyclerView mRecyclerView;

    @Override
    protected int setContentView() {
        return R.layout.fragment_locality;
    }

    @Override
    protected void lazyLoad() {
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        mRecyclerView = getContentView().findViewById(R.id.locality_list_view);
    }

    private void initData() {
        mPresenter.getData();
    }

    private void initEvent() {

    }

    @Override
    protected LocPresenter createPresenter() {
        return new LocPresenter(this);
    }

    @Override
    public void showMusicList(List<MP3Info> mp3InfoList) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        LocRecyclerViewAdapter adapter = new LocRecyclerViewAdapter(mp3InfoList);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void showNavigationBar() {

    }
}
