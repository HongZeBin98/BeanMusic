package com.example.hongzebin.beanmusic.recommendation.view;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.hongzebin.beanmusic.R;
import com.example.hongzebin.beanmusic.base.BaseFragment;
import com.example.hongzebin.beanmusic.base.MVPContract;
import com.example.hongzebin.beanmusic.recommendation.adapter.RecyclerViewAdapter;
import com.example.hongzebin.beanmusic.recommendation.bean.ShufflingBean;
import com.example.hongzebin.beanmusic.recommendation.presenter.RecPresenter;

import java.util.ArrayList;
import java.util.List;

public class RecFragment extends BaseFragment<MVPContract.View, RecPresenter> implements MVPContract.View {

    private RecyclerView mRecyclerView;
    private List<ShufflingBean> mShuBeans;

    @Override
    protected int setContentView() {
        return R.layout.fragment_recommendation;
    }

    @Override
    protected void lazyLoad() {
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        View view = getContentView();
        mRecyclerView = view.findViewById(R.id.recommendation_recyclerView);
    }

    private void initData() {
        mPresenter.getData();
    }

    private void initEvent() {

    }


    @Override
    public void showView(Object response) {
        mShuBeans = (List<ShufflingBean>)response;
        showShuffling();
    }

    @Override
    protected RecPresenter createPresenter() {
        return new RecPresenter(this);
    }

    /**
     * 显示轮播图
     */
    private void showShuffling(){
        final List<String> imageAddress = new ArrayList<>();
        for (ShufflingBean shufflingBean: mShuBeans){
            imageAddress.add(shufflingBean.getImageAddress());
        }
        boolean flag = imageAddress ==null;
        Log.e("flag", ""+flag);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //设置网格布局
                mRecyclerView.setLayoutManager(new GridLayoutManager(mRecyclerView.getContext(), 6, GridLayoutManager.VERTICAL, false));
                mRecyclerView.setAdapter(new RecyclerViewAdapter(getContext(), imageAddress));
            }
        });

    }
}
