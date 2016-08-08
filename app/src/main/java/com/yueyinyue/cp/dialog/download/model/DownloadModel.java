package com.yueyinyue.cp.dialog.download.model;

import android.content.Context;

import com.yueyinyue.YueApplication;
import com.yueyinyue.Model.dao.MusicDl.MusicDlRecord;
import com.yueyinyue.Model.dao.MusicDl.MusicDlRecordDao;

import java.util.List;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.WhereCondition;

/**
 * Created by Administrator on 2016/1/12.
 */
public class DownloadModel implements DownloadModelImpl
{
    private final Context context;

    public DownloadModel(Context context)
    {
        this.context = context;
    }

    @Override
    public MusicDlRecord getMusicDlRecord(String musicId)
    {
        WhereCondition text = MusicDlRecordDao.Properties.MusicId.eq(musicId);
        QueryBuilder queryBuilder = YueApplication.musicDlRecordDaoSession.getMusicDlRecordDao().queryBuilder();
        Query query = queryBuilder.where(text).build();

        //查询结果以 List 返回
        List<MusicDlRecord> musicList = query.list();
        if (musicList.size() == 0)
        {
            return null;
        }
        else
        {
            return musicList.get(0);
        }
    }

    @Override
    public void updateMusicDlRecord_fileName(String musicId, String fileName)
    {
        MusicDlRecord musicDlRecord = getMusicDlRecord(musicId);
        musicDlRecord.setFullSongFileName(fileName);
        YueApplication.musicDlRecordDaoSession.getMusicDlRecordDao().update(musicDlRecord);
    }

    @Override
    public void updateMusicDlRecord_progress(String musicId, int progress)
    {
        MusicDlRecord musicDlRecord = getMusicDlRecord(musicId);
        musicDlRecord.setFullSongDlPercentage(progress);
        YueApplication.musicDlRecordDaoSession.getMusicDlRecordDao().update(musicDlRecord);
    }

    @Override
    public void addMusicDlRecord(String musicId, String musicUrl, String dirPath, String picUrl, String songName, String singer)
    {
        MusicDlRecord musicDlRecord = new MusicDlRecord();

        musicDlRecord.setMusicId(musicId);
        musicDlRecord.setRingListenUrl(musicUrl);
        musicDlRecord.setPicUrl(picUrl);
        musicDlRecord.setSongName(songName);
        musicDlRecord.setSinger(singer);
        musicDlRecord.setAbsoluteDir(dirPath);
        musicDlRecord.setFullSongFileName(" ");
        musicDlRecord.setFullSongDlPercentage(0);
        musicDlRecord.setVibrateRingFileName(" ");
        musicDlRecord.setVibrateRingDlPercentage(0);

        YueApplication.musicDlRecordDaoSession.getMusicDlRecordDao().insert(musicDlRecord);
    }
}
