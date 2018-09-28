package com.example.hongzebin.beanmusic.music.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.example.hongzebin.beanmusic.R;
import com.example.hongzebin.beanmusic.base.adapter.GlobalClickAdapter;
import com.example.hongzebin.beanmusic.base.bean.Song;
import com.example.hongzebin.beanmusic.music.adapter.PopupListAdapter;

import java.util.List;

/**
 * 对popupWindow设置基本属性
 * Created By Mr.Bean
 */
public class SongListPopupWindow extends PopupWindow implements View.OnTouchListener, GlobalClickAdapter.OnClickItemCallBack{

    private int mWidth;
    private int mHeight;
    private View mConvertView;
    private List<Song> mSongList;
    private OnDirSelectedListener mListener;
    private PopupListAdapter mPopupListAdapter;

    public interface OnDirSelectedListener{
        void onSelected(Song song);
    }

    public void setOnDirSelectedListener(OnDirSelectedListener mListener){
        this.mListener = mListener;
    }

    public SongListPopupWindow(Context context, List<Song> songList){
        setWidthAndHeight(context);
        mSongList = songList;
        mConvertView = LayoutInflater.from(context).inflate(R.layout.popup_window_main, null);
        setContentView(mConvertView);
        setWidth(mWidth);
        setHeight(mHeight);
        setFocusable(true);
        setTouchable(true);
        setOutsideTouchable(true);
        setTouchInterceptor(this);
        initViews(context);
    }

    private void initViews(Context context) {
        RecyclerView recyclerView = mConvertView.findViewById(R.id.popup_window_recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(manager);
        mPopupListAdapter = new PopupListAdapter(mSongList, R.layout.popup_window_item_default);
        mPopupListAdapter.setOnClickItemCallBack(this);
        recyclerView.setAdapter(mPopupListAdapter);
    }

    /**
     * 设置popupWindow的宽和高
     */
    private void setWidthAndHeight(Context context) {
        //获取屏幕的长和宽
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        if(wm == null){
            return;
        }
        wm.getDefaultDisplay().getMetrics(outMetrics);
        //设置popupWindow宽是屏幕宽度
        mWidth = outMetrics.widthPixels;
        //设置popupWindow高度是屏幕高度的70%
        mHeight = (int) (outMetrics.heightPixels * 0.6);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_OUTSIDE){
            dismiss();
            return true;
        }
        return false;
    }

    @Override
    public void onClickItem(int position) {
        mListener.onSelected(mSongList.get(position));
    }

    public PopupListAdapter getPopupListAdapter(){
        return mPopupListAdapter;
    }

}
