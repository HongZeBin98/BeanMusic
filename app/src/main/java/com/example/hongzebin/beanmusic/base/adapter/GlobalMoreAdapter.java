package com.example.hongzebin.beanmusic.base.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ProgressBar;

import com.example.hongzebin.beanmusic.R;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView通用Adapter，实现了ClickItem和加载更多功能
 * @param <T>
 * Created By Mr.Bean
 */
public abstract class GlobalMoreAdapter<T> extends GlobalClickAdapter<T> {

    public static final int TYPE_LOAD_MORE = 100001;
    public static final int TYPE_NORMAL = 100002;

    private List<T> mData;
    private OnLoadMoreCallBack mCallback;
    private boolean isLoading;  //是否正在加载更多

    private int visibleThreshold = 3;   //加载到倒数第几个开始加载更多
    private boolean canLoadMore = true; //是否能加载更多

    public interface OnLoadMoreCallBack{
        void onLoadMore();
    }

    public GlobalMoreAdapter(List mData, int mLayoutId, RecyclerView recyclerView) {
        super(mData, mLayoutId);
        this.mData = mData;
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int itemCount = linearLayoutManager.getItemCount();
                int lastPosition  = linearLayoutManager.findLastVisibleItemPosition();
                //如果当前不是正在加载更多，并且到了该加载更多的位置，加载更多。
                if(!isLoading && (lastPosition >= (itemCount - visibleThreshold))){
                    if (canLoadMore && mCallback != null){
                        isLoading = true;
                        mCallback.onLoadMore();
                    }
                }
            }
        });
    }

    @NonNull
    @Override
    public GlobalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == TYPE_LOAD_MORE){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_load_more, parent, false);
            ProgressBar progressBar = view.findViewById(R.id.loading);
            progressBar.setInterpolator(new AccelerateInterpolator(2));
            progressBar.setIndeterminate(true);
            return new GlobalViewHolder(view);
        }else {
            return super.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull GlobalViewHolder globalViewHolder, int position) {
        if(getItemViewType(position) == TYPE_LOAD_MORE){
            View itemView = globalViewHolder.itemView;
            //判定是不是可以加载更多或正在加载
            if (canLoadMore && isLoading) {
                if (itemView.getVisibility() != View.VISIBLE) {
                    itemView.setVisibility(View.VISIBLE);
                }
            }else if(itemView.getVisibility() == View.VISIBLE){
                itemView.setVisibility(View.GONE);
            }
        }else{
            super.onBindViewHolder(globalViewHolder, position);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size() + 1;
    }

    @Override
    public int getItemViewType(int position){
        if (position == getItemCount() - 1) {
            return TYPE_LOAD_MORE;
        }else {
            return TYPE_NORMAL;
        }
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public void addData(List<T> list, boolean create){
        if (create){
            mData = new ArrayList<>();
        }
        mData.addAll(list);
    }

    public void setOnLoadMoreCallBack(OnLoadMoreCallBack callBack){mCallback = callBack;}
}
