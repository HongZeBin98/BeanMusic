package com.example.hongzebin.beanmusic.music.view;

import android.app.Activity;
import android.content.Intent;

import com.example.hongzebin.beanmusic.R;
import com.example.hongzebin.beanmusic.base.bean.PlayerCondition;
import com.example.hongzebin.beanmusic.base.view.BaseMVPActivity;
import com.example.hongzebin.beanmusic.music.bean.LrcBean;
import com.example.hongzebin.beanmusic.music.contract.MusicMVPContract;
import com.example.hongzebin.beanmusic.music.presenter.MusicPresenter;

import java.util.List;

public class MusicPlayerActivity extends BaseMVPActivity<MusicMVPContract.View, MusicPresenter> implements MusicMVPContract.View {

    private PlayerCondition mCondition;

    @Override
    protected MusicPresenter createPresenter() {
        return new MusicPresenter(this);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_music_player);
    }

    @Override
    protected void initData() {
        mCondition = getIntent().getParcelableExtra("Condition");
        mPresenter.getLrc(mCondition.getSongList().get(mCondition.getPosition()).getLyric(), this);
    }

    @Override
    protected void initEvents() {

    }

    public static void startActivity(Activity context, PlayerCondition condition){
        Intent intent = new Intent(context, MusicPlayerActivity.class);
        intent.putExtra("Condition", condition);
        context.startActivityForResult(intent, 1);
    }

    @Override
    public void showLrc(List<LrcBean> lrcBeans) {

    }
}
