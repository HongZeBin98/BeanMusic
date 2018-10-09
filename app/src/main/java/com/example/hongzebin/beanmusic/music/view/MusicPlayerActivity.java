package com.example.hongzebin.beanmusic.music.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hongzebin.beanmusic.R;
import com.example.hongzebin.beanmusic.base.bean.PlayerCondition;
import com.example.hongzebin.beanmusic.base.bean.Song;
import com.example.hongzebin.beanmusic.base.view.BaseMVPActivity;
import com.example.hongzebin.beanmusic.music.MusicManager;
import com.example.hongzebin.beanmusic.music.PlayerManager;
import com.example.hongzebin.beanmusic.music.bean.LrcBean;
import com.example.hongzebin.beanmusic.music.bean.PlayMode;
import com.example.hongzebin.beanmusic.music.contract.MusicMVPContract;
import com.example.hongzebin.beanmusic.music.presenter.MusicPresenter;
import com.example.hongzebin.beanmusic.util.BeanMusicApplication;
import com.example.hongzebin.beanmusic.widget.LrcView;

import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class MusicPlayerActivity extends BaseMVPActivity<MusicMVPContract.View, MusicPresenter>
        implements MusicMVPContract.View, View.OnClickListener, CompoundButton.OnCheckedChangeListener
        , SeekBar.OnSeekBarChangeListener, LrcView.ILrcViewListener, PlayerManager.NextSongListener
        , SongListPopupWindow.OnDismissListener, SongListPopupWindow.OnDirSelectedListener {

    private SeekBar mSeekBar;
    private ImageButton mIbBack;
    private TextView mTvSongName;
    private TextView mTvSinger;
    private CheckBox mCbPlay;
    private ImageButton mIbNextSong;
    private ImageButton mIbPreviousSong;
    private ImageButton mIbPlayMode;
    private ImageButton mIbSongList;
    private LrcView mLrcView;
    private List<Song> mSongList;
    private int mPosition;
    private MusicManager mMusicManager;
    private Timer mTimerSeekBar;
    private Timer mTimerLrcTrundle;
    private boolean mSeekBarTouching;
    private PlayerManager mPlayerManager;
    private Context mContext;
    private IPlayModeChangeListener mPlayModeListener;
    private SongListPopupWindow mPopupWindow;

    @Override
    protected MusicPresenter createPresenter() {
        return new MusicPresenter(this);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_music_player);
        mIbPlayMode = findViewById(R.id.music_player_play_mode);
        mSeekBar = findViewById(R.id.music_player_seek_bar);
        mIbBack = findViewById(R.id.music_player_back);
        mIbSongList = findViewById(R.id.music_player_song_list);
        mTvSongName = findViewById(R.id.music_player_song_name);
        mTvSinger = findViewById(R.id.music_player_singer);
        mCbPlay = findViewById(R.id.music_player_play);
        mIbNextSong = findViewById(R.id.music_player_next_song);
        mIbPreviousSong = findViewById(R.id.music_player_previous_song);
        mLrcView = findViewById(R.id.music_player_lrc_view);
        mPopupWindow = SongListPopupWindow.getInstance();
        mMusicManager = MusicManager.getInstance();
        mTimerSeekBar = new Timer();
        mTimerLrcTrundle = new Timer();
        mSeekBarTouching = false;
        mPlayerManager = PlayerManager.getInstance();
        mContext = BeanMusicApplication.getContext();
    }

    @Override
    protected void initData() {
        PlayerCondition condition = getIntent().getParcelableExtra("Condition");
        mSongList = condition.getSongList();
        mPosition = condition.getPosition();
        mCbPlay.setChecked(condition.isPlay());
        setCondition(mSongList.get(mPosition));
    }

    @Override
    protected void initEvents() {
        mPlayerManager.setPlayActivity(this);
        mIbNextSong.setOnClickListener(this);
        mIbSongList.setOnClickListener(this);
        mIbPreviousSong.setOnClickListener(this);
        mIbBack.setOnClickListener(this);
        mIbPlayMode.setOnClickListener(this);
        mCbPlay.setOnCheckedChangeListener(this);
        mSeekBar.setOnSeekBarChangeListener(this);
        mLrcView.setListener(this);
        mPlayerManager.addNextSongListener(this);
        mPopupWindow.setOnDismissListener(this);
        mPopupWindow.setOnDirSelectedListener(this);
        mPopupWindow.getIBTrashCan().setOnClickListener(this);
        mTimerSeekBar.schedule(new TimerTask() {
            @Override
            public void run() {
                mSeekBar.setProgress((int) (mMusicManager.getCurrProgress() * 100));
            }
        }, 0, 500);
        mTimerLrcTrundle.schedule(new TimerTask() {
            @Override
            public void run() {
                final long songTime = (long) (mSongList.get(mPosition).getSongTime() * 1000 * mMusicManager.getCurrProgress());
                MusicPlayerActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mLrcView.seekLrcToTime(songTime);
                    }
                });
            }
        }, 0, 500);
    }

    public static void startActivity(Activity context, PlayerCondition condition) {
        Intent intent = new Intent(context, MusicPlayerActivity.class);
        intent.putExtra("Condition", condition);
        context.startActivityForResult(intent, 1);
    }

    @Override
    public void showLrc(List<LrcBean> lrcBeans) {
        mLrcView.setLrc(lrcBeans);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.music_player_back:
                finishActivity();
                break;
            case R.id.music_player_next_song:
                playNextSong();
                break;
            case R.id.music_player_previous_song:
                playPreviousSong();
                break;
            case R.id.music_player_play_mode:
                mPlayModeListener.modeChange();
                PlayMode playMode = mPlayerManager.getPlayModeView();
                mIbPlayMode.setBackground(mContext.getResources()
                        .getDrawable(playMode.getModeId()));
                Toast.makeText(mContext, playMode.getModeText(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.music_player_song_list:
                mPopupWindow.showAtLocation(mCbPlay, Gravity.BOTTOM, 0, 0);
                mPopupWindow.lightOff(this);
                break;
            case R.id.popup_window_trash_can:
                mTimerSeekBar.cancel();
                mTimerLrcTrundle.cancel();
                mSongList.clear();
                mPopupWindow.getPopupListAdapter().notifyDataSetChanged();
                MusicManager.getInstance().pauseMusic();
                mPopupWindow.dismiss();
                finishActivity();
                break;
            default:
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTimerSeekBar.cancel();
        mTimerLrcTrundle.cancel();
        mTimerSeekBar = null;
        mTimerLrcTrundle = null;
    }

    private void setCondition(Song song) {
        mPresenter.getLrc(song.getLyric(), this);
        mTvSongName.setText(song.getSongName());
        mTvSinger.setText(song.getSinger());
        mIbPlayMode.setBackground(mContext.getResources()
                .getDrawable(mPlayerManager.getPlayModeView().getModeId()));
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
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (mSeekBarTouching) {
            float songProgress = (float) (progress * 1.00 / 100);
            long songTime;
            Song song = mSongList.get(mPosition);
            if (song.isLocality()) {
                songTime = (long) (songProgress * song.getSongTime());
            } else {
                songTime = (long) (songProgress * song.getSongTime() * 1000);
            }
            mMusicManager.setCurrDuration(songTime);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mSeekBarTouching = true;
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mSeekBarTouching = false;
    }

    @Override
    public void onLrcSeek(int newPosition, LrcBean lrcBean) {
        mMusicManager.setCurrDuration(lrcBean.getTime());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                finishActivity();
                break;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void finishActivity() {
        Intent intent = new Intent();
        PlayerCondition condition = new PlayerCondition(mSongList, mPosition, mCbPlay.isChecked());
        intent.putExtra("Condition", condition);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void nextSong(final int position) {
        Objects.requireNonNull(this).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (position == -1) {
                    mPosition++;
                } else {
                    mPosition = position;
                }
                Song song = mSongList.get(mPosition);
                mMusicManager.setSongPlay(song.getSongAddress());
                setCondition(song);
            }
        });
    }

    /**
     * 播放上一首歌，会根据不同的播放方式有不同的逻辑
     */
    private void playNextSong() {
        if (mPlayerManager.getPlayMode() == 1) {
            mPosition = (int) (Math.random() * mSongList.size());
        } else {
            if (mPosition + 1 == mSongList.size()) {
                mPosition = 0;
            } else {
                mPosition++;
            }
        }
        Song song = mSongList.get(mPosition);
        mMusicManager.setSongPlay(song.getSongAddress());
        setCondition(song);
        if (!mCbPlay.isChecked()) {
            mCbPlay.setChecked(true);
        }
    }

    /**
     * 播放下一首歌，会根据不同的播放方式有不同的逻辑
     */
    private void playPreviousSong() {
        if (mPlayerManager.getPlayMode() == 1) {
            mPosition = (int) (Math.random() * mSongList.size());
        } else {
            if (mPosition == 0) {
                mPosition = mSongList.size() - 1;
            } else {
                mPosition--;
            }
        }
        Song song = mSongList.get(mPosition);
        mMusicManager.setSongPlay(song.getSongAddress());
        setCondition(song);
        if (!mCbPlay.isChecked()) {
            mCbPlay.setChecked(true);
        }
    }

    public void setPlayModeChangeListener(IPlayModeChangeListener listener) {
        mPlayModeListener = listener;
    }

    @Override
    public void onDismiss() {
        mIbPlayMode.setBackground(mContext.getResources()
                .getDrawable(mPlayerManager.getPlayModeView().getModeId()));
        mPopupWindow.lightOn(this);
    }

    @Override
    public void onSelected(Song song) {
        //popupWindow中选择的歌曲
        mMusicManager.setSongPlay(song.getSongAddress());
        setCondition(song);
        if (!mCbPlay.isChecked()) {
            mCbPlay.setChecked(true);
        }
    }
}
