package com.yueyinyue.cp.dialog.download.model;

import com.yueyinyue.Model.dao.MusicDl.MusicDlRecord;

/**
 * Created by Administrator on 2016/1/12.
 */
public interface DownloadModelImpl
{
    void addMusicDlRecord(String musicId, String musicUrl, String dirPath, String picUrl, String songName, String singer);
    MusicDlRecord getMusicDlRecord(String musicId);
    void updateMusicDlRecord_fileName(String musicId, String fileName);
    void updateMusicDlRecord_progress(String musicId, int progress);
}
