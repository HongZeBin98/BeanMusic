package com.example.hongzebin.beanmusic.search.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hongzebin.beanmusic.R;

/**
 * 对热门搜索和搜索结果进行展示
 * Created By Mr.Bean
 */
public class SearchRecommendationFrag extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_recommendation, container, false);
        return view;
    }
}
