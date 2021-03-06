package com.example.hongzebin.beanmusic.locality.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.hongzebin.beanmusic.R;
import com.example.hongzebin.beanmusic.base.bean.Song;
import com.example.hongzebin.beanmusic.base.view.BaseMVPFragment;
import com.example.hongzebin.beanmusic.locality.adapter.LocRecyclerViewAdapter;
import com.example.hongzebin.beanmusic.locality.bean.MP3Info;
import com.example.hongzebin.beanmusic.locality.contract.LocMVPContract;
import com.example.hongzebin.beanmusic.locality.presenter.LocPresenter;
import com.example.hongzebin.beanmusic.main.view.activity.MainActivity;
import com.example.hongzebin.beanmusic.widget.IndexBar;

import java.util.List;
import java.util.Objects;

/**
 * 本地音乐界面
 * Created By Mr.Bean
 */
public class LocalityMVPFragment extends BaseMVPFragment<LocMVPContract.View, LocPresenter>
        implements LocMVPContract.View, IndexBar.OnAlphabetChangeListener
        , LocRecyclerViewAdapter.OnItemClickListener{

    private RecyclerView mRecyclerView;
    private IndexBar mIndexBar;
    private LocRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private TextView mTvIndex;
    private List<Song> mSongList;

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
        mTvIndex = getContentView().findViewById(R.id.locality_index);
        mRecyclerView = getContentView().findViewById(R.id.locality_list_view);
        mIndexBar = getContentView().findViewById(R.id.locality_index_bar);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
    }

    private void initData() {
        mPresenter.getData();
    }

    private void initEvent() {
        mIndexBar.setOnAlphabetChangeListener(this);
    }

    @Override
    protected LocPresenter createPresenter() {
        return new LocPresenter(this);
    }

    @Override
    public void showMusicList(List<MP3Info> mp3InfoList, List<Song> songList) {
        mSongList = songList;
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAdapter = new LocRecyclerViewAdapter(mp3InfoList);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void alphabetChangeListener(int position) {
        int i = mAdapter.getPositionForSection(position);
        //如果找不到对应的位置则不进行滑动
        if( i != -1){
            mLinearLayoutManager.scrollToPositionWithOffset(i, 0);
        }
    }

    @Override
    public void onTouch(String ch) {
        mTvIndex.setVisibility(View.VISIBLE);
        mTvIndex.setText(ch);
    }

    @Override
    public void onRelease() {
        mTvIndex.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(View view, int position) {
        ((MainActivity) Objects.requireNonNull(getActivity())).getLocalitySongList(mSongList, position);
    }
}
