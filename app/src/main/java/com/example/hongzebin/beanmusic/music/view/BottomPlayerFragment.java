package com.example.hongzebin.beanmusic.music.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hongzebin.beanmusic.R;
import com.example.hongzebin.beanmusic.base.bean.PlayerCondition;
import com.example.hongzebin.beanmusic.base.bean.Song;
import com.example.hongzebin.beanmusic.music.MusicManager;
import com.example.hongzebin.beanmusic.music.PlayerManager;
import com.example.hongzebin.beanmusic.music.adapter.PopupListAdapter;
import com.example.hongzebin.beanmusic.util.BeanMusicApplication;
import com.example.hongzebin.beanmusic.util.LocalityMP3InfoUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 底部音乐播放栏
 * Created By Mr.Bean
 */
public class BottomPlayerFragment extends Fragment implements View.OnClickListener
        , CompoundButton.OnCheckedChangeListener, SongListPopupWindow.OnDirSelectedListener
        , PopupWindow.OnDismissListener, PlayerManager.NextSongListener{

    private View mView;
    private TextView mTvSongName;
    private TextView mTvSinger;
    private TextView mTvAlbum;
    private CheckBox mCbPlay;
    private CircleImageView mCivImage;
    private ImageButton mIbSongList;
    private List<Song> mSongList;
    private int mPosition;
    private MusicManager mMusicManager;
    private RelativeLayout mRelativeLayout;
    private SongListPopupWindow mPopupWindow;
    private Activity mActivity;
    private PopupListAdapter mPopupListAdapter;
    private BottomPlayerHideCallback mCallback;
    private boolean mFirst;
    private PlayerManager mPlayerManager;

    public interface BottomPlayerHideCallback {
        void onFinish();
    }

    /**
     * 新建一个底部播放栏，并且传入该播放栏的状态
     *
     * @param playerCondition 播放栏状态
     * @return 底部播放栏
     */
    public static BottomPlayerFragment newInstance(PlayerCondition playerCondition) {
        BottomPlayerFragment fragment = new BottomPlayerFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("Condition", playerCondition);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initView(inflater, container);
        initData();
        initEvent();
        return mView;
    }

    private void initView(LayoutInflater inflater, ViewGroup container) {
        mView = inflater.inflate(R.layout.player_bottom, container, false);
        mPosition = 0;
        mFirst = true;
        mRelativeLayout = mView.findViewById(R.id.player_bottom_relative_layout);
        mTvSongName = mView.findViewById(R.id.player_bottom_song_name);
        mTvSinger = mView.findViewById(R.id.player_bottom_singer);
        mTvAlbum = mView.findViewById(R.id.player_bottom_album);
        mCbPlay = mView.findViewById(R.id.player_bottom_play_pause);
        mCivImage = mView.findViewById(R.id.player_bottom_circle_image_view);
        mIbSongList = mView.findViewById(R.id.player_bottom_song_list);
        mMusicManager = MusicManager.getInstance();
        mPopupWindow = SongListPopupWindow.getInstance();
        mSongList = mPopupWindow.getSongList();
        mPopupListAdapter = mPopupWindow.getPopupListAdapter();
        mActivity = getActivity();
        mPlayerManager = PlayerManager.getInstance();
    }

    private void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            setCondition((PlayerCondition) bundle.getParcelable("Condition"));
        }
    }

    private void initEvent() {
        mRelativeLayout.setOnClickListener(this);
        mIbSongList.setOnClickListener(this);
        mCbPlay.setOnCheckedChangeListener(this);
        mPopupWindow.setOnDirSelectedListener(this);
        mPopupWindow.setOnDismissListener(this);
        mIbSongList.setOnClickListener(this);
        mPopupWindow.getIBTrashCan().setOnClickListener(this);
        mPlayerManager.addNextSongListener(this);
    }

    /**
     * 返回当前底部播放栏的状态
     *
     * @return 底部播放栏状态
     */
    public PlayerCondition getPlayerCondition() {
        List<Song> songList = new ArrayList<>(mSongList);
        return new PlayerCondition(songList, mPosition, mCbPlay.isChecked());
    }

    /**
     * 获取播放列表，并播放指定位置的歌
     *
     * @param songList 播放列表
     * @param position 播放歌曲的在播放列表中的位置
     */
    public void setSongList(List<Song> songList, int position) {
        Song song = songList.get(position);
        registerPlayFinish();
        mMusicManager.setSongPlay(song.getSongAddress());
        mSongList.clear();
        mSongList.addAll(songList);
        mPosition = position;
        showView(song, true);
    }

    /**
     * 插入歌曲到播放列表中 ,并播放
     *
     * @param song 要插入的歌
     */
    public void insertSongToSongList(Song song) {
        if (mSongList == null || mSongList.size() == 0) {
            mSongList.clear();
            mSongList.add(song);
            mPosition = 0;
        } else {
            mPosition++;
            mSongList.add(mPosition, song);
        }
        mMusicManager.setSongPlay(song.getSongAddress());
        showView(song, true);
    }

    /**
     * 设置底部播放栏的状态
     *
     * @param condition 底部播放栏的状态
     */
    public void setCondition(PlayerCondition condition) {
        if (condition != null) {
            List<Song> songList = condition.getSongList();
            mCbPlay.setChecked(condition.isPlay());
            mSongList.clear();
            mSongList.addAll(songList);
            if (mSongList.size() != 0) {
                mPosition = condition.getPosition();
                Song song = mSongList.get(mPosition);
                showView(song, false);
            }
        }
    }

    /**
     * 把播放的歌曲展示在底部播放栏上
     * @param song 播放歌曲
     */
    private void showView(Song song, boolean setPlay) {
        mPopupListAdapter.notifyDataSetChanged();
        if (mCallback != null) {
            mCallback.onFinish();
        }
        mTvSongName.setText(song.getSongName());
        mTvSinger.setText(song.getSinger());
        mTvAlbum.setText(song.getAlbum());
        if (setPlay) {
            if (!mCbPlay.isChecked()) {
                mCbPlay.setChecked(true);
            }
        }
        if (song.isLocality()) {
            Bitmap bitmap = LocalityMP3InfoUtil.getLocalityMusicBitmap(song.getSongId(), song.getSmallImageAddress(), 150);
            mCivImage.setImageBitmap(bitmap);
        } else {
            if (song.getSmallImageAddress().equals("")) {
                //无法获取到专辑图片
                mCivImage.setImageResource(R.drawable.music_false);
            } else {
                Glide.with(BeanMusicApplication.getContext()).load(song.getSmallImageAddress()).into(mCivImage);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.player_bottom_song_list:
                mPopupListAdapter.notifyDataSetChanged();
                //打开播放列表
                mPopupWindow.showAtLocation(mRelativeLayout, Gravity.BOTTOM, 0, 0);
                lightOff();
                break;
            case R.id.player_bottom_relative_layout:
                //打开音乐播放界面
                MusicPlayerActivity.startActivity(getActivity(), getPlayerCondition());
                break;
            case R.id.popup_window_trash_can:
                mSongList.clear();
                mPopupListAdapter.notifyDataSetChanged();
                MusicManager.getInstance().pauseMusic();
                if (mCallback != null) {
                    mCallback.onFinish();
                }
                mPopupWindow.dismiss();
                break;
            default:
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            mMusicManager.playMusic();
        } else {
            mMusicManager.pauseMusic();
        }
    }

    @Override
    public void onDismiss() {
        lightOn();
    }

    @Override
    public void onSelected(Song song) {
        //popupWindow中选择的歌曲
        mMusicManager.setSongPlay(song.getSongAddress());
        showView(song, true);
        mPopupWindow.dismiss();
    }

    /**
     * 内容区变亮
     */
    private void lightOn() {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = 1.0f;
        mActivity.getWindow().setAttributes(lp);
    }

    /**
     * 内容区域变暗
     */
    private void lightOff() {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = 0.3f;
        mActivity.getWindow().setAttributes(lp);
    }

    public int getSongListSize() {
        if (mSongList == null) {
            return 0;
        }
        return mSongList.size();
    }

    public void setBottomPlayerHideCallback(BottomPlayerHideCallback callback) {
        mCallback = callback;
    }

    private void registerPlayFinish(){
        if (mFirst){
            mMusicManager.registerPlayFinishListener();
            mFirst = false;
        }
    }

    @Override
    public void nextSong(final int position) {
        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (position == -1){
                    mPosition ++;
                }else {
                    mPosition = position;
                }
                Song song  = mSongList.get(mPosition);
                mMusicManager.setSongPlay(song.getSongAddress());
                showView(song, true);
            }
        });
    }
}
