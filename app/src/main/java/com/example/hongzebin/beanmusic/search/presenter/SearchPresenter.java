package com.example.hongzebin.beanmusic.search.presenter;

import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.example.hongzebin.beanmusic.base.BasePresenter;
import com.example.hongzebin.beanmusic.search.bean.SearchAlbum;
import com.example.hongzebin.beanmusic.search.bean.SearchSinger;
import com.example.hongzebin.beanmusic.search.bean.SearchSong;
import com.example.hongzebin.beanmusic.search.contract.SearchMVPContract;
import com.example.hongzebin.beanmusic.search.model.SearchModel;
import com.example.hongzebin.beanmusic.util.BeanMusicApplication;

import java.util.List;

/**
 *通过从Model获取到搜索结果后回调给view层
 * Created By Mr.Bean
 */
public class SearchPresenter extends BasePresenter<SearchMVPContract.View> implements SearchMVPContract.Presenter {

    private SearchModel mModel;
    private SearchMVPContract.View mView;

    public SearchPresenter(SearchMVPContract.View view){
        mView = view;
        mModel = new SearchModel();

    }

    @Override
    public void getRecSearchTag() {
    }

    @Override
    public void getSearchResult(final FragmentActivity activity, String request, int pageNo) {
        final boolean loadMore;
        if (pageNo != 0){
            loadMore = true;
        }else {
            loadMore = false;
        }
        mModel.getSearchResult(request, pageNo, 10, new SearchModel.getSearchResultCallBack() {
            @Override
            public void onFinish(final List<SearchAlbum> albumList, final List<SearchSinger> singerList, final List<SearchSong> songList) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mView.showResult(albumList, singerList, songList, loadMore);
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {
                Log.e("SearchPresenter", Log.getStackTraceString(e));
            }
        });
    }
}
