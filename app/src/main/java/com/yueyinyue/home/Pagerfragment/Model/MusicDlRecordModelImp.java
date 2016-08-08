package com.yueyinyue.home.Pagerfragment.Model;

import com.cmsc.cmmusic.common.data.MusicInfo;
import com.yueyinyue.Model.dao.MusicDl.MusicDlRecord;

/**
 * Created by Administrator on 2016/3/23.
 */
public interface MusicDlRecordModelImp
{
    MusicDlRecord getMusicDlRecord(String musicId);
    void addMusicDlRecord(MusicInfo musicInfo, String dirPath);
    boolean reset(MusicDlRecord musicDlRecord);

    void updateFullSongFileName(String musicId, String fileName);
    void updateVibrateFileName(String musicId, String fileName);

    void updateFullSongPercentage(String musicId, int progress);

    void updateVibratePercentage(String musicId, int progress);
}
