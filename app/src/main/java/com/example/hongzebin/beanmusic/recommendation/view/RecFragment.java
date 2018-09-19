package com.example.hongzebin.beanmusic.recommendation.view;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.hongzebin.beanmusic.R;
import com.example.hongzebin.beanmusic.base.BaseFragment;
import com.example.hongzebin.beanmusic.recommendation.bean.HotSongList;
import com.example.hongzebin.beanmusic.recommendation.bean.NewSong;
import com.example.hongzebin.beanmusic.recommendation.bean.RecSong;
import com.example.hongzebin.beanmusic.recommendation.contract.RecMVPContract;
import com.example.hongzebin.beanmusic.recommendation.adapter.RecRecyclerViewAdapter;
import com.example.hongzebin.beanmusic.recommendation.bean.Shuffling;
import com.example.hongzebin.beanmusic.recommendation.presenter.RecPresenter;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class RecFragment extends BaseFragment<RecMVPContract.View, RecPresenter> implements RecMVPContract.View {

    private RecyclerView mRecyclerView;
    private List<Shuffling> mShufflings;
    private List<HotSongList> mHotSongLists;
    private List<NewSong> mNewSongs;
    private List<RecSong> mRecSong;
    private CountDownLatch mCountDownLatch; //设置闭锁来保证从四个接口中获取到数据后再更新UI

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
        mCountDownLatch = new CountDownLatch(4);
        mRecyclerView = getContentView().findViewById(R.id.recommendation_recyclerView);
    }

    private void initData() {
        mPresenter.getData();
    }

    private void initEvent() {
        try {
            mCountDownLatch.await();
            //设置网格布局
            mRecyclerView.setLayoutManager(new GridLayoutManager(mRecyclerView.getContext(), 6,
                    GridLayoutManager.VERTICAL, false));
            mRecyclerView.setAdapter(new RecRecyclerViewAdapter(getContext(), mShufflings, mHotSongLists,
                    mNewSongs, mRecSong));
        } catch (InterruptedException e) {
            Log.e("RecFragment", Log.getStackTraceString(e));
        }
    }

    @Override
    protected RecPresenter createPresenter() {
        return new RecPresenter(this);
    }

    @Override
    public void showShuffling(List<Shuffling> shufflings) {
        mShufflings = shufflings;
        mCountDownLatch.countDown();
    }

    @Override
    public void showHotSongList(List<HotSongList> hotSongLists) {
        mHotSongLists = hotSongLists;
        mCountDownLatch.countDown();
    }

    @Override
    public void showNewSong(List<NewSong> newSongs) {
        mNewSongs = newSongs;
        mCountDownLatch.countDown();
    }

    @Override
    public void showRecSong(List<RecSong> recSongs) {
        mRecSong = recSongs;
        mCountDownLatch.countDown();
    }
}
