package com.example.hongzebin.beanmusic.main.view.fragment;

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

public class LocalityFragment extends LazyFragment {
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_locality, container, false);
//    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_locality;
    }

    @Override
    protected void lazyLoad() {
        Log.e("locality lazy", "????????????" );
    }
}
