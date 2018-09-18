package com.example.hongzebin.beanmusic.recommendation.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hongzebin.beanmusic.R;
import com.example.hongzebin.beanmusic.recommendation.bean.HotSongList;
import com.example.hongzebin.beanmusic.recommendation.bean.NewSong;
import com.example.hongzebin.beanmusic.recommendation.bean.RecSong;
import com.example.hongzebin.beanmusic.recommendation.bean.Shuffling;
import com.example.hongzebin.beanmusic.util.BeanMusicApplication;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int BANNER = 10001;
    private static final int PATTERN_HEAD = 10002;
    private static final int PATTERN_DES = 10003;
    private static final int PATTERN_NO_DES = 10004;

    private List<Shuffling> mShufflings;
    private List<HotSongList> mHotSongLists;
    private List<NewSong> mNewSongs;
    private List<RecSong> mRecSongs;
    private Context mContext;

    public RecyclerViewAdapter(Context context, List<Shuffling> shufflings, List<HotSongList> hotSongLists
            , List<NewSong> newSongs, List<RecSong> recSongs){
        mContext = context;
        mShufflings = shufflings;
        mHotSongLists = hotSongLists;
        mNewSongs = newSongs;
        mRecSongs = recSongs;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layout = LayoutInflater.from(mContext);
        switch (viewType){
            case BANNER:
                return new BannerViewHolder(layout.inflate(R.layout.recommendation_banner, parent, false));
            case PATTERN_HEAD:
                return new NameViewHolder(layout.inflate(R.layout.recommendation_pattern_head, parent ,false));
            case PATTERN_NO_DES:
                return new PatternNoDesViewHolder(layout.inflate(R.layout.recommendation_pattern_nodes, parent, false));
            default:
                return new PatternDesViewHolder(layout.inflate(R.layout.recommendation_pattern_des, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BannerViewHolder){
            setShuffling((BannerViewHolder) holder);
        }else if (holder instanceof NameViewHolder){
            setPatternHead((NameViewHolder) holder, position);
        }else if (holder instanceof PatternDesViewHolder){
            setDesPattern((PatternDesViewHolder) holder, position);
        }else if(holder instanceof  PatternNoDesViewHolder){
            setNoDesPattern((PatternNoDesViewHolder) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return 22;
    }

    @Override
    public int getItemViewType(int position) {
        switch(position){
            //轮播图
            case 0:
                return BANNER;
            //模块名字
            case 1:
            case 8:
            case 15:
                return PATTERN_HEAD;
            //图片和题目的模块成员
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                return PATTERN_NO_DES;
            //图片和题目和描述的模块成员
            default:
                return PATTERN_DES;
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        final RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager){
            final GridLayoutManager gridLayoutManager = ((GridLayoutManager) manager);
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    switch (type){
                        case BANNER:
                        case PATTERN_HEAD:
                            //占满一列
                            return gridLayoutManager.getSpanCount();
                        default:
                            //占一列的1/3
                            return gridLayoutManager.getSpanCount() / 3;
                    }
                }
            });
        }
    }

    /**
     * 设置轮播图
     * @param holder 轮播图viewHolder
     */
    private void setShuffling(BannerViewHolder holder){
        List<String> imageAddress = new ArrayList<>();
        for (Shuffling shuffling : mShufflings){
            imageAddress.add(shuffling.getImageAddress());
        }
        holder.mBanner.setImageLoader(new GlideImageLoader());
        holder.mBanner.setImages(imageAddress);
        holder.mBanner.start();
    }

    /**
     * 设置模块名字
     * @param holder 模块名字viewHolder
     * @param position 位置
     */
    private void setPatternHead(NameViewHolder holder, int position){
        if (position == 1){
            holder.mTvName.setText("热门歌单 >");
        }else if(position ==8){
            holder.mTvName.setText("新歌首发 >");
        }else {
            holder.mTvName.setText("推荐歌曲 >");
        }
    }

    /**
     * 设置没有描述的模块内容
     * @param holder  没有描述的模块内容viewHolder
     */
    private void setNoDesPattern(PatternNoDesViewHolder holder, int position){
        HotSongList hotSongList = mHotSongLists.get(position - 2);
        Glide.with(BeanMusicApplication.getContext()).load(hotSongList.getImage())
                .into(holder.mImageView);
        holder.mTvTitle.setText(hotSongList.getTitle());
    }

    /**
     * 设置有描述的模块内容
     * @param holder 有描述的模块内容viewHolder
     * @param position  位置
     */
    private void setDesPattern(PatternDesViewHolder holder, int position){
        //判断是新歌首发板块还是推荐歌曲板块
        if (position < 15){
            NewSong newSong = mNewSongs.get(position - 9);
            Glide.with(BeanMusicApplication.getContext()).load(newSong.getImageAddress())
                    .into(holder.mImageView);
            holder.mTvTitle.setText(newSong.getTitle());
            holder.mTvImaInfo.setText(newSong.getAuthor());
        }else {
            RecSong recSong = mRecSongs.get(position - 16);
            Glide.with(BeanMusicApplication.getContext()).load(recSong.getImage())
                    .into(holder.mImageView);
            holder.mTvTitle.setText(recSong.getSongName());
            holder.mTvImaInfo.setText(recSong.getSinger());
        }
    }

    /**
     * 设置通过Glide来加载网上图片
     */
    class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(path).into(imageView);
        }
    }

    /**
     * 轮播图的ViewHolder
     */
    class BannerViewHolder extends RecyclerView.ViewHolder{

        Banner mBanner;

        BannerViewHolder(View itemView) {
            super(itemView);
            mBanner = itemView.findViewById(R.id.recommendation_banner);
        }
    }

    /**
     * 模块名的ViewHolder
     */
    class NameViewHolder extends RecyclerView.ViewHolder{

        TextView mTvName;

        NameViewHolder(View itemView) {
            super(itemView);
            mTvName = itemView.findViewById(R.id.recommendation_name);
        }
    }

    /**
     * 图片和题目和描述的模块的ViewHolder
     */
    class PatternDesViewHolder extends RecyclerView.ViewHolder{

        ImageView mImageView;
        TextView mTvTitle, mTvImaInfo;

        PatternDesViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.recommendation_des_imageView);
            mTvTitle = itemView.findViewById(R.id.recommendation_des_name);
            mTvImaInfo = itemView.findViewById(R.id.recommendation_des_info);
        }
    }

    /**
     * 图片和题目的模块的ViewHolder
     */
    class PatternNoDesViewHolder extends RecyclerView.ViewHolder{

        ImageView mImageView;
        TextView mTvTitle;

        PatternNoDesViewHolder(View itemView) {
            super(itemView);
            mImageView  = itemView.findViewById(R.id.recommendation_no_des_imageView);
            mTvTitle = itemView.findViewById(R.id.recommendation_no_des_name);
        }
    }
}
