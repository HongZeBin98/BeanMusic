package com.example.hongzebin.beanmusic.base.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * RecyclerView通用Adapter，实现了ClickItem功能
 * @param <T>
 * Created By Mr.Bean
 */
public abstract class GlobalClickAdapter<T> extends RecyclerView.Adapter<GlobalViewHolder> {

    private List<T> mData;
    private int mLayoutId;
    private OnClickItemCallBack mCallback;

    public abstract void convert(GlobalViewHolder viewHolder, T item);

    public interface OnClickItemCallBack{
        void onClickItem(int position);
    }

    public GlobalClickAdapter(List<T> mData, int mLayoutId){
        this.mData = mData;
        this.mLayoutId = mLayoutId;
    }

    @NonNull
    @Override
    public GlobalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final GlobalViewHolder globalViewHolder = GlobalViewHolder.createGlobalViewHolder(parent, mLayoutId);
        globalViewHolder.getmItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCallback != null) {
                    mCallback.onClickItem(globalViewHolder.getAdapterPosition());
                }
            }
        });
        return globalViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GlobalViewHolder globalViewHolder, int position) {
        convert(globalViewHolder, mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setOnClickItemCallBack(OnClickItemCallBack callback){
        mCallback = callback;
    }
}
