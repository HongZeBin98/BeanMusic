package com.example.hongzebin.beanmusic.music.view;

import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.hongzebin.beanmusic.R;
import com.example.hongzebin.beanmusic.base.bean.PlayerCondition;
import com.example.hongzebin.beanmusic.base.bean.Song;
import com.example.hongzebin.beanmusic.base.view.BaseMVPActivity;
import com.example.hongzebin.beanmusic.music.MusicManager;
import com.example.hongzebin.beanmusic.music.bean.LrcBean;
import com.example.hongzebin.beanmusic.music.contract.MusicMVPContract;
import com.example.hongzebin.beanmusic.music.presenter.MusicPresenter;
import com.example.hongzebin.beanmusic.widget.LrcView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MusicPlayerActivity extends BaseMVPActivity<MusicMVPContract.View, MusicPresenter>
        implements MusicMVPContract.View, View.OnClickListener, CompoundButton.OnCheckedChangeListener
        , SeekBar.OnSeekBarChangeListener, LrcView.ILrcViewListener {

    private SeekBar mSeekBar;
    private ImageButton mIbBack;
    private TextView mTvSongName;
    private TextView mTvSinger;
    private CheckBox mCbPlay;
    private ImageButton mIbNextSong;
    private ImageButton mIbPrevious;
    private LrcView mLrcView;
    private List<Song> mSongList;
    private int mPosition;
    private MusicManager mMusicManager;
    private Timer mTimerSeekBar;
    private Timer mTimerLrcTrundle;
    private boolean mSeekBarTouching;

    @Override
    protected MusicPresenter createPresenter() {
        return new MusicPresenter(this);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_music_player);
        mSeekBar = findViewById(R.id.music_player_seek_bar);
        mIbBack = findViewById(R.id.music_player_back);
        mTvSongName = findViewById(R.id.music_player_song_name);
        mTvSinger = findViewById(R.id.music_player_singer);
        mCbPlay = findViewById(R.id.music_player_play);
        mIbNextSong = findViewById(R.id.music_player_next_song);
        mIbPrevious = findViewById(R.id.music_player_previous_song);
        mLrcView = findViewById(R.id.music_player_lrc_view);
        mMusicManager = MusicManager.getInstance();
        mTimerSeekBar = new Timer();
        mTimerLrcTrundle = new Timer();
        mSeekBarTouching = false;
    }

    @Override
    protected void initData() {
        PlayerCondition condition = getIntent().getParcelableExtra("Condition");
        setCondition(condition);
        mPresenter.getLrc(mSongList.get(mPosition).getLyric(), this);
    }

    @Override
    protected void initEvents() {
        mIbBack.setOnClickListener(this);
        mCbPlay.setOnCheckedChangeListener(this);
        mSeekBar.setOnSeekBarChangeListener(this);
        mLrcView.setListener(this);
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

    public static void startActivity(Activity context, PlayerCondition condition){
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
        switch (v.getId()){
            case R.id.music_player_back:
               finishActivity();
                break;
            default:
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTimerSeekBar.cancel();
        mTimerSeekBar = null;
        mTimerLrcTrundle.cancel();
        mTimerLrcTrundle = null;
    }

    private void setCondition(PlayerCondition condition){
        mSongList = condition.getSongList();
        mPosition = condition.getPosition();
        Song song = mSongList.get(mPosition);
        mCbPlay.setChecked(condition.isPlay());
        mTvSongName.setText(song.getSongName());
        mTvSinger.setText(song.getSinger());
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
            if (song.isLocality()){
                songTime = (long) (songProgress * song.getSongTime());
            }else {
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

    private void finishActivity(){
        Intent intent = new Intent();
        PlayerCondition condition = new PlayerCondition(mSongList, mPosition, mCbPlay.isChecked());
        intent.putExtra("Condition", condition);
        setResult(RESULT_OK, intent);
        finish();
    }
}
