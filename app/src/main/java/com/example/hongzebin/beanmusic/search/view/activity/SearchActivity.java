package com.example.hongzebin.beanmusic.search.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.example.hongzebin.beanmusic.R;
import com.example.hongzebin.beanmusic.search.view.fragment.SearchRecommendationFrag;
import com.example.hongzebin.beanmusic.search.view.fragment.SearchResultFrag;

import api.MusicApi;
/**
 * 搜索页面Activity
 * Created By Mr.Bean
 */
public class SearchActivity extends AppCompatActivity implements View.OnTouchListener, SearchView.OnQueryTextListener{

    private SearchView mSearchView;
    private ImageButton mIbBack;
    private FrameLayout mFlMiddle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Log.e("merge", ""+ MusicApi.Search.searchMerge("薛之谦", 0, 10));
        initView();
        initData();
        initEvent();
    }

    @SuppressLint("CommitTransaction")
    private void initView() {
        mSearchView = findViewById(R.id.search_search_view);
        mIbBack = findViewById(R.id.search_back);
        mFlMiddle = findViewById(R.id.search_frame_layout);
    }

    private void initData() {
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initEvent() {
        replaceFragment(R.id.search_frame_layout, new SearchRecommendationFrag());
        mSearchView.setOnQueryTextListener(this);
        mFlMiddle.setOnTouchListener(this);
        mIbBack.setOnTouchListener(this);
    }

    /**
     * 启动该Activity
     * @param context 上下文
     */
    public static void startActivity(Context context){
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }

    /**
     * 用Fragment替换activity中某个布局
     * @param ViewId 要被替换布局的id
     * @param fragment  将要替换上的Fragment
     */
    private void replaceFragment(int ViewId, Fragment fragment){
        FragmentManager fm = this.getSupportFragmentManager();
        FragmentTransaction ft= fm.beginTransaction();
        ft.replace(ViewId, fragment).commit();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()){
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
}
