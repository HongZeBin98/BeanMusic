package com.example.hongzebin.beanmusic.recommendation.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hongzebin.beanmusic.R;
import com.example.hongzebin.beanmusic.base.LazyFragment;
import com.example.hongzebin.beanmusic.recommendation.adapter.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class RecommendationFragment extends LazyFragment {

    private RecyclerView mRecyclerView;
    private List<String> mImageList;

//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        initView();
//        initData();
//        initEvent();
//        return mView;
//    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_recommendation;
    }

    @Override
    protected void lazyLoad() {
        Log.e("recommendation lazy", "????????????" );
        initView();
        initData();
        initEvent();
    }

    private void initView() {
//        mView = inflater.inflate(R.layout.fragment_recommendation, container,false);
        View view = getContentView();
        mRecyclerView = view.findViewById(R.id.recommendation_recyclerView);
        mImageList = new ArrayList<>();
    }

    private void initData() {
        mImageList.add("http://business.cdn.qianqian.com/qianqian/pic/bos_client_15366530960acb386095a4f9b182881b69330983d3.jpg");
        mImageList.add("http://business.cdn.qianqian.com/qianqian/pic/bos_client_153630713397df57f1f6063fc8f72fff077e6818f8.jpg");
        mImageList.add("http://business.cdn.qianqian.com/qianqian/pic/bos_client_1536293939ad553dee899c20eb704e0af9f3942b80.jpg");
        mImageList.add("http://business.cdn.qianqian.com/qianqian/pic/bos_client_1536306440823f8ace9e3cb1b8690297a5496f5913.jpg");
        mImageList.add("http://business.cdn.qianqian.com/qianqian/pic/bos_client_15366379262c93ddfa11416f080feb6ed5c623691d.jpg");
    }

    private void initEvent() {
        //设置网格布局
        mRecyclerView.setLayoutManager(new GridLayoutManager(mRecyclerView.getContext(), 6, GridLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(new RecyclerViewAdapter(getContext(), mImageList));
    }
}
