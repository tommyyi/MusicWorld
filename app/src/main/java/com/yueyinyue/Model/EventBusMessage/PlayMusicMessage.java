package com.yueyinyue.Model.EventBusMessage;

import java.io.File;

/**
 * Created by Administrator on 2015/11/18.
 */
public class PlayMusicMessage
{
    public String mSongname;
    public boolean isFullSongListen=false;
    public String url;
    public File musicFile;

    public PlayMusicMessage(File musicFile,boolean isFullSongListen,String songname)
    {
        this.musicFile = musicFile;
        this.isFullSongListen=isFullSongListen;
        mSongname=songname;
    }

    public PlayMusicMessage(String url,String songname)
    {
        this.url = url;
        mSongname=songname;
    }
}
