package com.example.hongzebin.beanmusic.music.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class PlayerManagerService extends Service implements MediaPlayer.OnPreparedListener
        , MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener{

    private IPlayFinishListener mListener;
    private MediaPlayer mPlayer;
    private Binder mBinder = new IPlayerManager.Stub() {
        @Override
        public void play() throws RemoteException {
            try {
                if (!mPlayer.isPlaying()) {
                    mPlayer.start();
                }
            } catch (Exception e) {
                Log.e("PlayerManagerService", Log.getStackTraceString(e));
            }
        }

        @Override
        public void pause() throws RemoteException {
            try {
                if (mPlayer.isPlaying()) {
                    mPlayer.pause();
                }
            } catch (IllegalStateException e) {
                Log.e("PlayerManagerService", Log.getStackTraceString(e));
            }
        }

        @Override
        public void setSong(String songAddress) throws RemoteException {
            try {
                mPlayer.reset();
                mPlayer.setDataSource(songAddress);
                mPlayer.prepareAsync();
            } catch (Exception e) {
                Log.e("PlayerManagerService", Log.getStackTraceString(e));
            }
        }

        @Override
        public void setCurrDuration(long songCurrTime) throws RemoteException {
            try {
                mPlayer.seekTo((int)songCurrTime);
            } catch (Exception e) {
                Log.e("PlayerManagerService", Log.getStackTraceString(e));
            }
        }

        @Override
        public float getProgress() throws RemoteException {
            float progress = 0;
            try {
                progress = (float) (mPlayer.getCurrentPosition() * 1.00 / mPlayer.getDuration());
            } catch (Exception e) {
                Log.e("PlayerManagerService", Log.getStackTraceString(e));
            }
            return progress;
        }

        @Override
        public void registerListener(IPlayFinishListener listener) throws RemoteException {
            mListener = listener;
        }

        @Override
        public void setLooping(boolean looping) throws RemoteException {
            mPlayer.setLooping(looping);
            if(looping){
                mPlayer.start();
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mPlayer = new MediaPlayer();
        mPlayer.setOnPreparedListener(this);
        mPlayer.setOnErrorListener(this);
        mPlayer.setOnCompletionListener(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        if (mPlayer != null) {
            mPlayer.release();
        }
        mListener = null;
        return super.onUnbind(intent);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mPlayer.start();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (mListener != null) {
            try {
                mListener.onPlayFinish();
            } catch (RemoteException e) {
                Log.e("PlayerManagerService", Log.getStackTraceString(e));
            }
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return true;
    }
}
