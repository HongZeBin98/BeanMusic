package com.example.hongzebin.beanmusic.music;


import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.hongzebin.beanmusic.music.service.PlayerManagerService;
import com.example.hongzebin.beanmusic.service.IPlayerManager;
import com.example.hongzebin.beanmusic.util.BeanMusicApplication;

/**
 * 对后台播放服务进行管理
 * Created By Mr.Bean
 */
public class MusicManager {

    private IPlayerManager mService;
    private Context mContext;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = IPlayerManager.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }
    };

    private MusicManager(){
        mContext = BeanMusicApplication.getContext();
        Intent intent = new Intent(mContext, PlayerManagerService.class);
        mContext.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    public static MusicManager getInstance(){
        return MusicManagerHolder.INSTANCE;
    }

    private static class MusicManagerHolder{
        @SuppressLint("StaticFieldLeak")
        private static final MusicManager INSTANCE = new MusicManager();
    }

    /**
     * 解绑服务
     */
    public void unBindService(){
        mContext.unbindService(mConnection);
    }

    /**
     * 控制后台播放音乐
     */
    public void playMusic(){
        try {
            mService.play();
        } catch (RemoteException e) {
            Log.e("MusicManager", Log.getStackTraceString(e));
        }
    }

    /**
     * 控制后台暂停音乐
     */
    public void pauseMusic(){
        try {
            mService.pause();
        } catch (RemoteException e) {
            Log.e("MusicManager", Log.getStackTraceString(e));
        }
    }

    /**
     * 设置歌曲并且开始播放
     * @param songAddress 歌曲地址
     */
    public void setSongPlay(String songAddress){
        try {
            mService.setSong(songAddress);
        } catch (RemoteException e) {
            Log.e("MusicManager", Log.getStackTraceString(e));
        }
    }

    /**
     * 设置跳转到歌曲的某个时间点
     * @param currDuration 跳转到的时间点
     */
    public void setCurrDuration(long currDuration){
        try {
            mService.setCurrDuration(currDuration);
        } catch (RemoteException e) {
            Log.e("MusicManager", Log.getStackTraceString(e));
        }
    }
}
