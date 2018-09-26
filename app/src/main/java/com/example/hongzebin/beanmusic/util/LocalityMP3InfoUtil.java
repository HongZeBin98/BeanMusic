package com.example.hongzebin.beanmusic.util;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import com.example.hongzebin.beanmusic.R;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

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


//    /**
//     * 获取专辑封面位图对象
//     * @param context
//     * @param song_id
//     * @param album_id
//     * @param allowdefalut
//     * @return
//     */
//    public static Bitmap getArtwork(Context context, long song_id, long album_id, boolean allowdefalut, boolean small){
//        if(album_id < 0) {
//            if(song_id < 0) {
//                Bitmap bm = getArtworkFromFile(context, song_id, -1);
//                if(bm != null) {
//                    return bm;
//                }
//            }
//            if(allowdefalut) {
//                return getDefaultArtwork(context, small);
//            }
//            return null;
//        }
//        ContentResolver res = context.getContentResolver();
//        Uri uri = ContentUris.withAppendedId(albumArtUri, album_id);
//        if(uri != null) {
//            InputStream in = null;
//            try {
//                in = res.openInputStream(uri);
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                //先制定原始大小
//                options.inSampleSize = 1;
//                //只进行大小判断
//                options.inJustDecodeBounds = true;
//                //调用此方法得到options得到图片的大小
//                BitmapFactory.decodeStream(in, null, options);
//                /** 我们的目标是在你N pixel的画面上显示。 所以需要调用computeSampleSize得到图片缩放的比例 **/
//                /** 这里的target为800是根据默认专辑图片大小决定的，800只是测试数字但是试验后发现完美的结合 **/
//                if(small){
//                    options.inSampleSize = computeSampleSize(options, 40);
//                } else{
//                    options.inSampleSize = computeSampleSize(options, 600);
//                }
//                // 我们得到了缩放比例，现在开始正式读入Bitmap数据
//                options.inJustDecodeBounds = false;
//                options.inDither = false;
//                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//                in = res.openInputStream(uri);
//                return BitmapFactory.decodeStream(in, null, options);
//            } catch (FileNotFoundException e) {
//                Bitmap bm = getArtworkFromFile(context, song_id, album_id);
//                if(bm != null) {
//                    if (bm.getConfig() == null) {
//                        bm = bm.copy(Bitmap.Config.RGB_565, false);
//                        if (bm == null && allowdefalut) {
//                            return getDefaultArtwork(context, small);
//                        }
//                    } else if (allowdefalut) {
//                        bm = getDefaultArtwork(context, small);
//                    }
//                    return bm;
//                }
//            } finally {
//                try {
//                    if(in != null) {
//                        in.close();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return null;
//    }
//
//    /**
//     * 获取默认专辑图片
//     * @param context
//     * @return
//     */
//    public static Bitmap getDefaultArtwork(Context context,boolean small) {
//        BitmapFactory.Options opts = new BitmapFactory.Options();
//        opts.inPreferredConfig = Bitmap.Config.RGB_565;
//        if(small){
//            //返回小图片
//            return BitmapFactory.decodeStream(context.getResources().openRawResource(R.drawable.music5), null, opts);
//        }
//        return BitmapFactory.decodeStream(context.getResources().openRawResource(R.drawable.defaultalbum), null, opts);
//    }
}
