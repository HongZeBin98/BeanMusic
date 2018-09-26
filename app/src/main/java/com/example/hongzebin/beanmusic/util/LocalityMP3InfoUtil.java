package com.example.hongzebin.beanmusic.util;

import android.content.ContentUris;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import com.example.hongzebin.beanmusic.R;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;

public class LocalityMP3InfoUtil {

    //获取专辑封面的Uri
    private static final Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");

    /**
     * 格式化时间，将毫秒转换为分:秒格式
     *
     * @param time 秒格式时间
     * @return 分秒格式的事件
     */
    public static String formatTime(long time) {
        String min = time / (1000 * 60) + "";
        String sec = time % (1000 * 60) + "";
        if (min.length() < 2) {
            min = "0" + time / (1000 * 60) + "";
        } else {
            min = time / (1000 * 60) + "";
        }
        if (sec.length() == 4) {
            sec = "0" + (time % (1000 * 60)) + "";
        } else if (sec.length() == 3) {
            sec = "00" + (time % (1000 * 60)) + "";
        } else if (sec.length() == 2) {
            sec = "000" + (time % (1000 * 60)) + "";
        } else if (sec.length() == 1) {
            sec = "0000" + (time % (1000 * 60)) + "";
        }
        return min + ":" + sec.trim().substring(0, 2);
    }

    /**
     * 获取到本地歌曲的专辑封面
     * @param sSongId 本地歌曲id
     * @param sAlbumId   本地歌曲专辑id
     * @param length    获取图片大小
     * @return 本地歌曲专辑Bitmap封面
     */
    public static Bitmap getLocalityMusicBitmap(String sSongId, String sAlbumId, int length) {
        Long songId = Long.valueOf(sSongId);
        Long albumId = Long.valueOf(sAlbumId);
        Context context = BeanMusicApplication.getContext();
        Bitmap bm = null;
        // 专辑id和歌曲id小于0说明没有专辑、歌曲，并抛出异常
        if (albumId < 0 && songId < 0) {
            throw new IllegalArgumentException("Must specify an album or a song id");
        }
        try {
            if (albumId < 0) {
                Uri uri = Uri.parse("content://media/external/audio/media/" + songId + "/albumart");
                ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(uri, "r");
                if (pfd != null) {
                    FileDescriptor fd = pfd.getFileDescriptor();
                    bm = BitmapFactory.decodeFileDescriptor(fd);
                }
            } else {
                Uri uri = ContentUris.withAppendedId(sArtworkUri, albumId);
                ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(uri, "r");
                if (pfd != null) {
                    FileDescriptor fd = pfd.getFileDescriptor();
                    bm = BitmapFactory.decodeFileDescriptor(fd);
                } else {
                    return null;
                }
            }
        } catch (FileNotFoundException ignored) {
        }
        //如果获取的bitmap为空，则返回一个默认的bitmap
        if (bm == null) {
            Resources resources = context.getResources();
            Drawable drawable = resources.getDrawable(R.drawable.music_false);
            //Drawable 转 Bitmap
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            bm = bitmapDrawable.getBitmap();
        }
        return Bitmap.createScaledBitmap(bm, length, length, true);
    }
}
