package com.example.hongzebin.beanmusic.search.view.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.hongzebin.beanmusic.R;
import com.example.hongzebin.beanmusic.base.adapter.GlobalClickAdapter;
import com.example.hongzebin.beanmusic.base.adapter.GlobalMoreAdapter;
import com.example.hongzebin.beanmusic.base.view.LazyFragment;
import com.example.hongzebin.beanmusic.search.adapter.LoadMoreRvAdapter;
import com.example.hongzebin.beanmusic.search.bean.SearchSong;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索到的歌曲页面
 * Created By Mr.Bean
 */
public class SongFragment extends LazyFragment implements GlobalClickAdapter.OnClickItemCallBack, GlobalMoreAdapter.OnLoadMoreCallBack{

    private List<SearchSong> mSongList;
    private RecyclerView mRecyclerView;
    private LoadMoreRvAdapter mAdapter;
    private SearchResultFrag mFragment;

    public void showSongList(SearchResultFrag fragment, List<SearchSong> songList, boolean loadMoreFlag){
        mFragment = fragment;
        if (loadMoreFlag && songList != null) {
            mSongList.addAll(songList);
            mAdapter.addData(songList, false);
            mAdapter.notifyDataSetChanged();
            mAdapter.setLoading(false);
        }else {
            mSongList = new ArrayList<>();
            if(songList != null){
                mSongList.addAll(songList);
                showRecyclerView();
            }
        }
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_search_result;
    }

    @Override
    protected void lazyLoad() {
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        mRecyclerView = getContentView().findViewById(R.id.search_result_recycler_view);
    }

    private void initData() {
    }

    private void initEvent() {
        showRecyclerView();
    }

    /**
     * 把歌曲的搜索结果展示出来
     */
    private void showRecyclerView(){
        if (mSongList != null){
            LinearLayoutManager manager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(manager);
            mAdapter = new LoadMoreRvAdapter(mSongList, R.layout.song_list_item, mRecyclerView);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.setOnLoadMoreCallBack(this);
            mAdapter.setOnClickItemCallBack(this);
        }
    }

    @Override
    public void onClickItem(int position) {
        mFragment.getClickSearchSong(mSongList.get(position));
    }

    @Override
    public void onLoadMore() {
        mFragment.getMoreData();
    }
}
