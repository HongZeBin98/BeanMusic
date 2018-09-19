package com.example.hongzebin.beanmusic.main.view.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.hongzebin.beanmusic.R;
import com.example.hongzebin.beanmusic.main.adapter.ViewPagerAdapter;
import com.example.hongzebin.beanmusic.locality.view.LocalityFragment;
import com.example.hongzebin.beanmusic.main.view.fragment.MusicFragment;
import com.example.hongzebin.beanmusic.util.Permission;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {

    private List<Fragment> mFragments;
    private ViewPager mViewPager;
    private RadioGroup mRadioGroup;
    private RadioButton mRbMusic;
    private RadioButton mRbLocality;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Permission.requestAllPower(this);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        mFragments = new ArrayList<>();
        mViewPager = findViewById(R.id.main_viewpager);
        mRadioGroup = findViewById(R.id.main_top_RG);
        mRbMusic = findViewById(R.id.main_top_music);
        mRbLocality = findViewById(R.id.main_top_locality);
    }

    private void initData() {
        mFragments.add(new MusicFragment());
        mFragments.add(new LocalityFragment());
    }

    private void initEvent() {
        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), mFragments));
        mViewPager.addOnPageChangeListener(this);
        mRadioGroup.setOnCheckedChangeListener(this);
        mViewPager.setCurrentItem(0);
    }

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
}
