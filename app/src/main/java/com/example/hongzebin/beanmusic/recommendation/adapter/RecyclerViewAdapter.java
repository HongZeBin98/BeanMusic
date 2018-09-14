package com.example.hongzebin.beanmusic.recommendation.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hongzebin.beanmusic.R;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int Banner_view = 10001;
    private static final int Pattern_head_view = 10002;
    private static final int Pattern_view = 10003;

    private List<String> mImageList;
    private Context mContext;

    public RecyclerViewAdapter(Context context, List<String> imageList){
        mContext = context;
        mImageList = imageList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layout = LayoutInflater.from(mContext);
        switch (viewType){
            case Banner_view:
                return new BannerViewHolder(layout.inflate(R.layout.recommendation_banner, parent, false));
            case Pattern_head_view:
                return new NameViewHolder(layout.inflate(R.layout.recommendation_pattern_name, parent ,false));
            default:
                return new PatternViewHolder(layout.inflate(R.layout.recommendation_pattern, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BannerViewHolder){
            //设置轮播图
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            bannerViewHolder.mBanner.setImageLoader(new GlideImageLoader());
            bannerViewHolder.mBanner.setImages(mImageList);
            bannerViewHolder.mBanner.start();
        }else if (holder instanceof NameViewHolder){

        }else if (holder instanceof PatternViewHolder){

        }
    }

    @Override
    public int getItemCount() {
        return 22;
    }

    @Override
    public int getItemViewType(int position) {
        switch(position){
            case 0:
                return Banner_view;
            case 1:
            case 8:
            case 15:
                return Pattern_head_view;
            default:
                return Pattern_view;
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
                        case Banner_view:
                        case Pattern_head_view:
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
     * 图片和它的描述的模块的ViewHolder
     */
    class PatternViewHolder extends RecyclerView.ViewHolder{

        ImageView mImageView;
        TextView mTvImaName, mTvImaInfo;

        PatternViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.recommendation_imageView);
            mTvImaName = itemView.findViewById(R.id.recommendation_imageView_name);
            mTvImaInfo = itemView.findViewById(R.id.recommendation_imageView_info);
        }
    }
}
