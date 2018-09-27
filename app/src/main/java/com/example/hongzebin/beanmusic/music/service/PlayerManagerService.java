package com.example.hongzebin.beanmusic.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;

public class PlayerManagerService extends Service {

    public static final String TAG = "PlayerManagerService";

    private MediaPlayer mPlayer;

    private Binder mBinder = new IPlayerManager.Stub() {
        @Override
        public void play() throws RemoteException {
            try {
                if (mPlayer.isPlaying()) {
                }else {
                    mPlayer.prepare();
                    mPlayer.start();
                }
            } catch (IOException e) {
                Log.e("PlayerManagerService", Log.getStackTraceString(e));
            }
        }

        @Override
        public void stop() throws RemoteException {
            try {
                if (mPlayer.isPlaying()) {
                    mPlayer.stop();
                }
            } catch (IllegalStateException e) {
                Log.e("PlayerManagerService", Log.getStackTraceString(e) );
            }
        }

        @Override
        public void setSong(String songAddress) throws RemoteException {
            try {
                mPlayer.setDataSource(songAddress);
            } catch (IOException e) {
                Log.e("PlayerManagerService", Log.getStackTraceString(e) );
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
        if (mPlayer != null){
            mPlayer.release();
        }
        return super.onUnbind(intent);
    }
}
