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
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hongzebin.beanmusic.R;
import com.example.hongzebin.beanmusic.base.adapter.GlobalClickAdapter;
import com.example.hongzebin.beanmusic.base.bean.Song;
import com.example.hongzebin.beanmusic.music.MusicManager;
import com.example.hongzebin.beanmusic.music.adapter.PopupListAdapter;
import com.example.hongzebin.beanmusic.util.BeanMusicApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * 对popupWindow设置基本属性
 * Created By Mr.Bean
 */
public class SongListPopupWindow extends PopupWindow implements View.OnTouchListener
        , GlobalClickAdapter.OnClickItemCallBack, View.OnClickListener{

    private int mWidth;
    private int mHeight;
    private View mConvertView;
    private List<Song> mSongList;
    private OnDirSelectedListener mListener;
    private PopupListAdapter mPopupListAdapter;
    private RelativeLayout mRelativeLayout;
    private List<Integer> mPlayMode;
    private List<String> mPlayModeText;
    private int mPlayModeSelected;
    private ImageButton mIbPlayMode;
    private TextView mTvPlayMode;
    private ImageButton mIBTrashCan;

    public interface OnDirSelectedListener{
        void onSelected(Song song);
    }

    public void setOnDirSelectedListener(OnDirSelectedListener mListener){
        this.mListener = mListener;
    }

    SongListPopupWindow(Context context, List<Song> songList){
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
        initData();
        initEvent();
    }

    private void initViews(Context context) {
        mPlayModeSelected = 0;
        mPlayMode = new ArrayList<>();
        mPlayModeText= new ArrayList<>();
        mIBTrashCan = mConvertView.findViewById(R.id.popup_window_trash_can);
        mRelativeLayout = mConvertView.findViewById(R.id.popup_window_play_mode);
        mIbPlayMode = mConvertView.findViewById(R.id.popup_window_play_mode_button);
        mTvPlayMode = mConvertView.findViewById(R.id.popup_window_play_mode_text_view);
        RecyclerView recyclerView = mConvertView.findViewById(R.id.popup_window_recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(manager);
        mPopupListAdapter = new PopupListAdapter(mSongList, R.layout.popup_window_item_default);
        recyclerView.setAdapter(mPopupListAdapter);
    }


    private void initData() {
        mPlayMode.add(R.drawable.random_play);
        mPlayMode.add(R.drawable.single_play);
        mPlayMode.add(R.drawable.loop_play);
        mPlayModeText.add("随机播放");
        mPlayModeText.add("单曲循环");
        mPlayModeText.add("循环播放");
        //默认播放方式为列表循环
        mIbPlayMode.setBackground(BeanMusicApplication.getContext().getResources().getDrawable(R.drawable.loop_play));
        mTvPlayMode.setText("循环播放");
    }

    private void initEvent() {
        mPopupListAdapter.setOnClickItemCallBack(this);
        mRelativeLayout.setOnClickListener(this);
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
        //设置popupWindow高度是屏幕高度的60%
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.popup_window_play_mode:
                if (mPlayModeSelected >= 3){
                    mPlayModeSelected = 0;
                }
                mIbPlayMode.setBackground(BeanMusicApplication.getContext()
                        .getResources().getDrawable(mPlayMode.get(mPlayModeSelected)));
                mTvPlayMode.setText(mPlayModeText.get(mPlayModeSelected));
                mPlayModeSelected++;
                break;
            default:
                break;
        }
    }

    @Override
    public void onClickItem(int position) {
        mListener.onSelected(mSongList.get(position));
    }

    public PopupListAdapter getPopupListAdapter(){
        return mPopupListAdapter;
    }

    public ImageButton getmIBTrashCan(){
        return mIBTrashCan;
    }

}
