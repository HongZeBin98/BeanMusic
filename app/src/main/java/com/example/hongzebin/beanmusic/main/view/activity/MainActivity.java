package com.example.hongzebin.beanmusic.main.view.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.hongzebin.beanmusic.R;
import com.example.hongzebin.beanmusic.base.bean.Song;
import com.example.hongzebin.beanmusic.base.view.BaseEventBusActivity;
import com.example.hongzebin.beanmusic.base.bean.PlayerCondition;
import com.example.hongzebin.beanmusic.music.MusicManager;
import com.example.hongzebin.beanmusic.music.view.BottomPlayerFragment;
import com.example.hongzebin.beanmusic.main.adapter.ViewPagerAdapter;
import com.example.hongzebin.beanmusic.locality.view.LocalityMVPFragment;
import com.example.hongzebin.beanmusic.main.view.fragment.MusicFragment;
import com.example.hongzebin.beanmusic.search.view.activity.SearchActivity;
import com.example.hongzebin.beanmusic.util.DimensionUtil;
import com.example.hongzebin.beanmusic.util.Permission;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseEventBusActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private List<Fragment> mFragments;
    private ViewPager mViewPager;
    private RadioGroup mRadioGroup;
    private RadioButton mRbMusic;
    private RadioButton mRbLocality;
    private ImageButton mImageButton;
    private BottomPlayerFragment mFgBottomPlayer;
    private FrameLayout mFrameLayout;

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
        mFrameLayout = findViewById(R.id.main_bottom_player);
        isBottomPlayerShow();
    }

    @Override
    protected void initData() {
        mFragments.add(new MusicFragment());
        mFragments.add(new LocalityMVPFragment());
    }

    @Override
    protected void initEvents() {
        //动态添加底部播放栏Fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.main_bottom_player, mFgBottomPlayer).commit();
        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), mFragments));
        mViewPager.addOnPageChangeListener(this);
        mRadioGroup.setOnCheckedChangeListener(this);
        mImageButton.setOnClickListener(this);
        mFgBottomPlayer.setBottomPlayerHideCallback(new BottomPlayerFragment.BottomPlayerHideCallback() {
            @Override
            public void onFinish() {
                isBottomPlayerShow();
            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            mRbMusic.setChecked(true);
        } else if (position == 1) {
            mRbLocality.setChecked(true);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == mRbMusic.getId()) {
            mViewPager.setCurrentItem(0);
        } else if (checkedId == mRbLocality.getId()) {
            mViewPager.setCurrentItem(1);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_top_search:
                SearchActivity.startActivity(this);
                break;
            default:
                break;
        }
    }

    @Override
    protected void setConditionStickEvent(PlayerCondition event) {
        mFgBottomPlayer.setCondition(event);
        isBottomPlayerShow();
    }

    @Override
    protected PlayerCondition getConditionStickEvent() {
        return mFgBottomPlayer.getPlayerCondition();
    }

    /**
     * 把本地歌曲歌单传入底部播放栏
     *
     * @param songList 本地歌曲歌单
     */
    public void getLocalitySongList(List<Song> songList, int position) {
        mFgBottomPlayer.setSongList(songList, position);
        isBottomPlayerShow();
    }

    /**
     * 底部播放栏显示和隐藏
     */
    private void isBottomPlayerShow() {
        ViewGroup.LayoutParams lp = mFrameLayout.getLayoutParams();
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        if (mFgBottomPlayer.getSongListSize() != 0) {
            lp.height = DimensionUtil.dip2px(50);
        } else {
            lp.height = 0;
        }
        mFrameLayout.setLayoutParams(lp);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        MusicManager.getInstance().unBindService();
    }
}
