package com.example.hongzebin.beanmusic.main.view.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.hongzebin.beanmusic.R;
import com.example.hongzebin.beanmusic.base.bean.Song;
import com.example.hongzebin.beanmusic.base.view.BaseActivity;
import com.example.hongzebin.beanmusic.base.bean.PlayConditionStickEvent;
import com.example.hongzebin.beanmusic.bottom_player.BottomPlayerFragment;
import com.example.hongzebin.beanmusic.main.adapter.ViewPagerAdapter;
import com.example.hongzebin.beanmusic.locality.view.LocalityFragment;
import com.example.hongzebin.beanmusic.main.view.fragment.MusicFragment;
import com.example.hongzebin.beanmusic.search.view.activity.SearchActivity;
import com.example.hongzebin.beanmusic.util.Permission;

import java.util.ArrayList;
import java.util.List;

import api.MusicApi;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private List<Fragment> mFragments;
    private ViewPager mViewPager;
    private RadioGroup mRadioGroup;
    private RadioButton mRbMusic;
    private RadioButton mRbLocality;
    private ImageButton mImageButton;
    private BottomPlayerFragment mFgBottomPlayer;


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0){
            mRbMusic.setChecked(true);
        }else if(position == 1) {
            mRbLocality.setChecked(true);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if(checkedId == mRbMusic.getId()){
            mViewPager.setCurrentItem(0);
        }else if(checkedId == mRbLocality.getId()){
            mViewPager.setCurrentItem(1);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_top_search:
                SearchActivity.startActivity(this);
                break;
            default:
                break;
        }
    }

    @Override
    protected void setConditionStickEvent(PlayConditionStickEvent event) {
        mFgBottomPlayer.setCondition(event);
    }

    @Override
    protected PlayConditionStickEvent getConditionStickEvent() {
        return mFgBottomPlayer.getPlayerCondition();
    }

    @Override
    protected void initView() {
        //动态获取权限
        Permission.requestAllPower(this);
        setContentView(R.layout.activity_main);
        mFragments = new ArrayList<>();
        mFgBottomPlayer = new BottomPlayerFragment();
        mViewPager = findViewById(R.id.main_viewpager);
        mRadioGroup = findViewById(R.id.main_top_RG);
        mRbMusic = findViewById(R.id.main_top_music);
        mRbLocality = findViewById(R.id.main_top_locality);
        mImageButton = findViewById(R.id.main_top_search);
    }

    @Override
    protected void initData() {
        mFragments.add(new MusicFragment());
        mFragments.add(new LocalityFragment());
    }

    @Override
    protected void initEvents() {
        //动态添加底部播放栏Fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.main_bottom_player, mFgBottomPlayer).commit();
        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), mFragments));
        mViewPager.addOnPageChangeListener(this);
        mRadioGroup.setOnCheckedChangeListener(this);
        mImageButton.setOnClickListener(this);
    }

    /**
     * 把本地歌曲歌单传入底部播放栏
     * @param songList 本地歌曲歌单
     */
    public void getLocalitySongList(List<Song> songList, int position){
        mFgBottomPlayer.setSongList(songList, position);
    }
}
