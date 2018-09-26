package com.example.hongzebin.beanmusic.search.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.SearchView;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.example.hongzebin.beanmusic.R;
import com.example.hongzebin.beanmusic.base.view.BaseActivity;
import com.example.hongzebin.beanmusic.base.bean.PlayConditionStickEvent;
import com.example.hongzebin.beanmusic.bottom_player.BottomPlayerFragment;
import com.example.hongzebin.beanmusic.search.view.fragment.SearchRecommendationFrag;
import com.example.hongzebin.beanmusic.search.view.fragment.SearchResultFrag;
import com.example.hongzebin.beanmusic.base.bean.Song;

/**
 * 搜索页面Activity
 * Created By Mr.Bean
 */
public class SearchActivity extends BaseActivity implements View.OnTouchListener, SearchView.OnQueryTextListener {

    private SearchView mSearchView;
    private ImageButton mIbBack;
    private FrameLayout mFlMiddle;
    private BottomPlayerFragment mBottomFragment;


    /**
     * 启动该Activity
     *
     * @param context 上下文
     */
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }

    /**
     * 用Fragment替换activity中某个布局
     *
     * @param ViewId   要被替换布局的id
     * @param fragment 将要替换上的Fragment
     */
    private void replaceFragment(int ViewId, Fragment fragment) {
        FragmentManager fm = this.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(ViewId, fragment).commit();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.search_back:
                finish();
                return false;
            default:
                //取消searchView光标
                mSearchView.clearFocus();
                //隐藏键盘
                mFlMiddle.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(mSearchView.getWindowToken(), 0);
                }
                return false;
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        //如果什么都没有输入无法搜索
        if (query != null) {
            SearchResultFrag resultFragment = new SearchResultFrag();
            replaceFragment(R.id.search_frame_layout, resultFragment);
            resultFragment.getKeyWord(query);
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }


    /**
     * 获取到要播放的歌
     *
     * @param song 要播放的歌
     */
    public void getSong(Song song) {

    }

    @Override
    protected void setConditionStickEvent(PlayConditionStickEvent event) {
        mBottomFragment = BottomPlayerFragment.newInstance(event);
        replaceFragment(R.id.search_frame_bottom_player, mBottomFragment);
    }

    @Override
    protected PlayConditionStickEvent getConditionStickEvent() {
        return mBottomFragment.getPlayerCondition();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_search);
        mSearchView = findViewById(R.id.search_search_view);
        mIbBack = findViewById(R.id.search_back);
        mFlMiddle = findViewById(R.id.search_frame_layout);
    }

    @Override
    protected void initData() {
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initEvents() {
        replaceFragment(R.id.search_frame_layout, new SearchRecommendationFrag());
        mSearchView.setOnQueryTextListener(this);
        mFlMiddle.setOnTouchListener(this);
        mIbBack.setOnTouchListener(this);
    }

}
