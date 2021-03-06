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
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.example.hongzebin.beanmusic.R;
import com.example.hongzebin.beanmusic.base.view.BaseEventBusActivity;
import com.example.hongzebin.beanmusic.base.bean.PlayerCondition;
import com.example.hongzebin.beanmusic.music.view.BottomPlayerFragment;
import com.example.hongzebin.beanmusic.search.view.fragment.SearchRecommendationFrag;
import com.example.hongzebin.beanmusic.search.view.fragment.SearchResultFrag;
import com.example.hongzebin.beanmusic.base.bean.Song;
import com.example.hongzebin.beanmusic.util.DimensionUtil;

/**
 * 搜索页面Activity
 * Created By Mr.Bean
 */
public class SearchActivity extends BaseEventBusActivity implements View.OnTouchListener
        , SearchView.OnQueryTextListener{

    private SearchView mSearchView;
    private ImageButton mIbBack;
    private FrameLayout mFlMiddle;
    private FrameLayout mFlBottom;
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

    @Override
    protected void initView() {
        setContentView(R.layout.activity_search);
        mSearchView = findViewById(R.id.search_search_view);
        mIbBack = findViewById(R.id.search_back);
        mFlMiddle = findViewById(R.id.search_frame_layout);
        mFlBottom = findViewById(R.id.search_frame_bottom_player);
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
        mBottomFragment.setBottomPlayerHideCallback(new BottomPlayerFragment.BottomPlayerHideCallback() {
            @Override
            public void onFinish() {
                isBottomPlayerShow();
            }
        });
        isBottomPlayerShow();
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
        ft.replace(ViewId, fragment).commitAllowingStateLoss();
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        switch (keyCode) {
//            case KeyEvent.KEYCODE_BACK:
//                finish();
//                break;
//            default:
//                break;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.search_back:
                finish();
                return true;
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
     * @param song 要播放的歌
     */
    public void getSong(Song song) {
        mBottomFragment.insertSongToSongList(song);
    }

    @Override
    protected void setConditionStickEvent(PlayerCondition event) {
        if (mBottomFragment == null) {
            mBottomFragment = BottomPlayerFragment.newInstance(event);
            replaceFragment(R.id.search_frame_bottom_player, mBottomFragment);
        }else {
            mBottomFragment.setCondition(event);
            isBottomPlayerShow();
        }
    }

    @Override
    protected PlayerCondition getConditionStickEvent() {
        return mBottomFragment.getPlayerCondition();
    }

    /**
     * 底部播放栏显示和隐藏
     */
    private void isBottomPlayerShow(){
        if (mFlBottom.getLayoutParams() != null){
            ViewGroup.LayoutParams lp = mFlBottom.getLayoutParams();
            lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            if(mBottomFragment.getSongListSize() != 0 ){
                lp.height = DimensionUtil.dip2px(50);
            }else {
                lp.height = 0;
            }
            mFlBottom.setLayoutParams(lp);
        }
    }
}
