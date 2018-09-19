package com.example.hongzebin.beanmusic.locality.model;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;

import com.example.hongzebin.beanmusic.locality.bean.MP3Info;
import com.example.hongzebin.beanmusic.util.PinyinComparator;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 获取本地歌曲
 * Created By Mr.Bean
 */
public class LocModel {

    /**
     * 获取本地音乐
     * @param contentResolver contentResolver
     * @return 本地音乐列表
     */
    public List<MP3Info> findSongs(ContentResolver contentResolver){
        Cursor cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        List<MP3Info> mp3InfoList = new ArrayList<>();
        if (cursor != null){
            cursor.moveToFirst();
            do {
                //音乐id
                long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
                //歌名
                String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                //专辑
                String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
                //歌手
                String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                //歌曲文件的路径
                String url = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                //判断是否为音乐
                int isMusic = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));//是否为音乐
                //音乐播放长度
                long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                //音乐文件大小
                long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
                //对音乐进行筛选（是音乐，播放长度大于一分钟，文件大小大于800k）
                if (isMusic != 0 && duration/(1000 * 60) >= 1 && size >1024*800){
                    mp3InfoList.add(new MP3Info(id, title, artist, album, url));
                }
            }while (cursor.moveToNext());
        }
        Collections.sort(mp3InfoList, new PinyinComparator());
        return mp3InfoList;
    }
}
