package com.example.hongzebin.beanmusic.locality.presenter;

import com.example.hongzebin.beanmusic.base.bean.Song;
import com.example.hongzebin.beanmusic.base.presenter.BasePresenter;
import com.example.hongzebin.beanmusic.locality.bean.MP3Info;
import com.example.hongzebin.beanmusic.locality.contract.LocMVPContract;
import com.example.hongzebin.beanmusic.locality.model.LocModel;
import com.example.hongzebin.beanmusic.util.BeanMusicApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * 通过从Model获取到本地音乐列表后回调给view层
 * Created By Mr.Bean
 */
public class LocPresenter extends BasePresenter<LocMVPContract.View> implements LocMVPContract.Presenter {

    private LocMVPContract.View mView;
    private LocModel mLocModel;

    public LocPresenter(LocMVPContract.View view){
        mView = view;
        mLocModel = new LocModel();
    }

    @Override
    public void getData() {
        List<MP3Info> mp3InfoList = mLocModel.findSongs(BeanMusicApplication.getContext().getContentResolver());
        List<Song> songList = turnMP3ListToSongList(mp3InfoList);
        mView.showMusicList(mp3InfoList, songList);
    }

    /**
     * 把MP3InfoList转换成播放列表
     * @param mp3InfoList MP3InfoList
     * @return 播放列表
     */
    private List<Song> turnMP3ListToSongList(List<MP3Info> mp3InfoList){
        List<Song> songList = new ArrayList<>();
        for(MP3Info mp3Info: mp3InfoList){
            String id = Long.toString(mp3Info.getId());
            String songName = mp3Info.getSongName();
            String singer = mp3Info.getSinger();
            String album = mp3Info.getAlbum();
            long songTime = mp3Info.getDuration();
            String smallImageAddress = Long.toString(mp3Info.getAlbumId());
            String songAddress = mp3Info.getURL();
            Song song = new Song(songAddress, songTime, smallImageAddress, null, songName, singer
                    , album, id, null, null, null, true);
            songList.add(song);
        }
        return songList;
    }
}
