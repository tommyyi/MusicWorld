package com.yueyinyue.Model.EventBusMessage;

import java.io.File;

/**
 * Created by Administrator on 2015/12/1.
 */
public class SetRingtoneMessage
{
    public File musicFile;
    public String title;
    public String singer;

    public SetRingtoneMessage(File musicFile, String title, String singer)
    {
        this.musicFile = musicFile;
        this.singer = singer;
        this.title = title;
    }
}
