package com.example.hongzebin.beanmusic.rankingList.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hongzebin.beanmusic.R;
import com.example.hongzebin.beanmusic.base.LazyFragment;

public class RankingListFragment extends LazyFragment {

    @Override
    protected int setContentView() {
        return R.layout.fragment_rankinglist;
    }

    @Override
    protected void lazyLoad() {
    }
}
