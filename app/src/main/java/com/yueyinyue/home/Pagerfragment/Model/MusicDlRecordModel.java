package com.yueyinyue.home.Pagerfragment.Model;

import com.cmsc.cmmusic.common.data.MusicInfo;
import com.yueyinyue.YueApplication;
import com.yueyinyue.Model.dao.MusicDl.MusicDlRecord;
import com.yueyinyue.Model.dao.MusicDl.MusicDlRecordDao;

import java.io.File;
import java.util.List;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.WhereCondition;

/**
 * Created by Administrator on 2016/3/23.
 */
public class MusicDlRecordModel implements MusicDlRecordModelImp
{
    public MusicDlRecordModel(String musicId)
    {
        reset(getMusicDlRecord(musicId));
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
    public void addMusicDlRecord(MusicInfo musicInfo, String dirPath)
    {
        MusicDlRecord musicDlRecord = new MusicDlRecord();

        musicDlRecord.setMusicId(musicInfo.getMusicId());
        musicDlRecord.setRingListenUrl(musicInfo.getRingListenDir());
        musicDlRecord.setPicUrl(musicInfo.getAlbumPicDir());
        musicDlRecord.setSongName(musicInfo.getSongName());
        musicDlRecord.setSinger(musicInfo.getSingerName());
        musicDlRecord.setAbsoluteDir(dirPath);
        musicDlRecord.setFullSongFileName(" ");
        musicDlRecord.setFullSongDlPercentage(0);
        musicDlRecord.setVibrateRingFileName(" ");
        musicDlRecord.setVibrateRingDlPercentage(0);

        YueApplication.musicDlRecordDaoSession.getMusicDlRecordDao().insert(musicDlRecord);
    }

    @Override
    public boolean reset(MusicDlRecord musicDlRecord)
    {
        boolean isRight=true;
        if (musicDlRecord != null&&musicDlRecord.getFullSongDlPercentage() != 100)
        {
            File file = new File(musicDlRecord.getAbsoluteDir(), musicDlRecord.getFullSongFileName());
            if (file.exists())
                file.delete();
            musicDlRecord.setFullSongDlPercentage(0);
            YueApplication.musicDlRecordDaoSession.update(musicDlRecord);
            isRight=false;
        }

        if (musicDlRecord != null&&musicDlRecord.getVibrateRingDlPercentage() != 100)
        {
            File file = new File(musicDlRecord.getAbsoluteDir(), musicDlRecord.getVibrateRingFileName());
            if (file.exists())
            {
                file.delete();
            }
            musicDlRecord.setVibrateRingDlPercentage(0);
            YueApplication.musicDlRecordDaoSession.update(musicDlRecord);
            isRight=false;
        }

        return isRight;
    }

    @Override
    public void updateFullSongFileName(String musicId, String fileName)
    {
        MusicDlRecord musicDlRecord = getMusicDlRecord(musicId);
        musicDlRecord.setFullSongFileName(fileName);
        YueApplication.musicDlRecordDaoSession.getMusicDlRecordDao().update(musicDlRecord);
    }

    @Override
    public void updateVibrateFileName(String musicId, String fileName)
    {
        MusicDlRecord musicDlRecord = getMusicDlRecord(musicId);
        musicDlRecord.setVibrateRingFileName(fileName);
        YueApplication.musicDlRecordDaoSession.getMusicDlRecordDao().update(musicDlRecord);
    }

    @Override
    public void updateFullSongPercentage(String musicId, int progress)
    {
        MusicDlRecord musicDlRecord = getMusicDlRecord(musicId);
        musicDlRecord.setFullSongDlPercentage(progress);
        YueApplication.musicDlRecordDaoSession.getMusicDlRecordDao().update(musicDlRecord);
    }

    @Override
    public void updateVibratePercentage(String musicId, int progress)
    {
        MusicDlRecord musicDlRecord = getMusicDlRecord(musicId);
        musicDlRecord.setVibrateRingDlPercentage(progress);
        YueApplication.musicDlRecordDaoSession.getMusicDlRecordDao().update(musicDlRecord);
    }
}
