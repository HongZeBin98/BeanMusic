package com.example.hongzebin.beanmusic.music.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.example.hongzebin.beanmusic.R;
import com.example.hongzebin.beanmusic.base.bean.Song;

import java.util.List;

public class SongListPopupWindow extends PopupWindow implements View.OnTouchListener{

    private int mWidth;
    private int mHeight;
    private View mConvertView;
    private List<Song> mSongList;
    private OnDirSelectedListener mListener;

    public interface OnDirSelectedListener{
        void onSelected(Song song);
    }

    public void setOnDirSelectedListener(OnDirSelectedListener mListener){
        this.mListener = mListener;
    }

    public SongListPopupWindow(Context context, List<Song> songList){
        setWidthAndHeight(context);
        mSongList = songList;
        setContentView(LayoutInflater.from(context).inflate(R.layout.popup_window_main, null));
        setWidth(mWidth);
        setHeight(mHeight);
        setFocusable(true);
        setTouchable(true);
        setOutsideTouchable(true);
//        setTouchInterceptor(this)
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

}
