package com.example.hongzebin.beanmusic.music.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.hongzebin.beanmusic.service.IPlayerManager;

import java.io.IOException;

public class PlayerManagerService extends Service {

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
                mPlayer.release();
                mPlayer = new MediaPlayer();
                mPlayer.setDataSource(songAddress);
                mPlayer.prepare();
            } catch (IOException e) {
                Log.e("PlayerManagerService", Log.getStackTraceString(e));
            }
        }

        @Override
        public void setCurrDuration(long songCurrTime) throws RemoteException {
        }

    };

    @Override
    public void onCreate() {
        super.onCreate();
        mPlayer = new MediaPlayer();
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
        return super.onUnbind(intent);
    }
}
