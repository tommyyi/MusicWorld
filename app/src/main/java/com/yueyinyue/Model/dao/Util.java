package com.yueyinyue.Model.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.cmsc.cmmusic.common.data.ChartInfo;
import com.cmsc.cmmusic.common.data.ChartListRsp;
import com.cmsc.cmmusic.common.data.MusicInfo;
import com.cmsc.cmmusic.common.data.MusicListRsp;
import com.yueyinyue.Model.dao.cache.Chart;
import com.yueyinyue.Model.dao.cache.ChartDao;
import com.yueyinyue.Model.dao.cache.DaoMaster;
import com.yueyinyue.Model.dao.cache.DaoSession;
import com.yueyinyue.Model.dao.cache.music1;
import com.yueyinyue.Model.dao.cache.music2;
import com.yueyinyue.Model.dao.cache.music3;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/4.
 */

public class Util
{

    private static final String DB_NAME = "musicCache";

    public static ChartListRsp getChartListCache(Context context)
    {
        try
        {
            checkCache(context);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

        DaoMaster.DevOpenHelper devOpenHelper=new DaoMaster.DevOpenHelper(context, DB_NAME,null);
        SQLiteDatabase database=devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster=new DaoMaster(database);
        DaoSession daoSession=daoMaster.newSession();

        List<Chart> chartList = daoSession.getChartDao().loadAll();
        ChartListRsp chartListRsp=new ChartListRsp();
        chartListRsp.setResCounter(chartList.size()+"");
        chartListRsp.setChartInfos(new ArrayList<ChartInfo>());
        for(Chart chart:chartList)
        {
            ChartInfo chartInfo =new ChartInfo();
            chartInfo.setChartName(chart.getChartName());
            chartInfo.setChartCode(chart.getChartCode());
            chartListRsp.getChartInfos().add(chartInfo);
        }
        chartListRsp.setResCode("000000");
        chartListRsp.setResMsg("获取榜单信息成功");
        return chartListRsp;
    }

    public static MusicListRsp getMusic1Cache(Context context)
    {
        DaoMaster.DevOpenHelper devOpenHelper=new DaoMaster.DevOpenHelper(context, DB_NAME,null);
        SQLiteDatabase database=devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster=new DaoMaster(database);
        DaoSession daoSession=daoMaster.newSession();

        List<music1> music1List = daoSession.getMusic1Dao().loadAll();
        MusicListRsp musicListRsp=new MusicListRsp();
        musicListRsp.setResCounter(music1List.size()+"");
        musicListRsp.setMusics(new ArrayList<MusicInfo>());
        for(music1 music:music1List)
        {
            MusicInfo musicInfo =new MusicInfo();
            musicInfo.setMusicId(music.getMusicId());
            musicInfo.setCount(music.getCount());
            musicInfo.setCrbtValidity(music.getCrbtValidity());
            musicInfo.setPrice(music.getPrice());
            musicInfo.setSongName(music.getSongName());
            musicInfo.setSingerId(music.getSingerId());
            musicInfo.setSingerName(music.getSingerName());
            musicInfo.setRingValidity(music.getRingValidity());
            musicInfo.setSongValidity(music.getSongValidity());
            musicInfo.setAlbumPicDir(music.getAlbumPicDir());
            musicInfo.setSingerPicDir(music.getSingerPicDir());
            musicInfo.setCrbtListenDir(music.getCrbtListenDir());
            musicInfo.setRingListenDir(music.getRingListenDir());
            musicInfo.setSongListenDir(music.getSongListenDir());
            musicInfo.setLrcDir(music.getLrcDir());
            musicInfo.setHasDolby(music.getHasDolby());
            musicListRsp.getMusics().add(musicInfo);
        }
        musicListRsp.setResCode("000000");
        musicListRsp.setResMsg("获取音乐列表信息成功");
        return musicListRsp;
    }

    public static MusicListRsp getMusic2Cache(Context context)
    {
        DaoMaster.DevOpenHelper devOpenHelper=new DaoMaster.DevOpenHelper(context, DB_NAME,null);
        SQLiteDatabase database=devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster=new DaoMaster(database);
        DaoSession daoSession=daoMaster.newSession();

        List<music2> music2List = daoSession.getMusic2Dao().loadAll();
        MusicListRsp musicListRsp=new MusicListRsp();
        musicListRsp.setResCounter(music2List.size()+"");
        musicListRsp.setMusics(new ArrayList<MusicInfo>());
        for(music2 music:music2List)
        {
            MusicInfo musicInfo =new MusicInfo();
            musicInfo.setMusicId(music.getMusicId());
            musicInfo.setCount(music.getCount());
            musicInfo.setCrbtValidity(music.getCrbtValidity());
            musicInfo.setPrice(music.getPrice());
            musicInfo.setSongName(music.getSongName());
            musicInfo.setSingerId(music.getSingerId());
            musicInfo.setSingerName(music.getSingerName());
            musicInfo.setRingValidity(music.getRingValidity());
            musicInfo.setSongValidity(music.getSongValidity());
            musicInfo.setAlbumPicDir(music.getAlbumPicDir());
            musicInfo.setSingerPicDir(music.getSingerPicDir());
            musicInfo.setCrbtListenDir(music.getCrbtListenDir());
            musicInfo.setRingListenDir(music.getRingListenDir());
            musicInfo.setSongListenDir(music.getSongListenDir());
            musicInfo.setLrcDir(music.getLrcDir());
            musicInfo.setHasDolby(music.getHasDolby());
            musicListRsp.getMusics().add(musicInfo);
        }
        musicListRsp.setResCode("000000");
        musicListRsp.setResMsg("获取音乐列表信息成功");
        return musicListRsp;
    }

    public static MusicListRsp getMusic3Cache(Context context)
    {
        DaoMaster.DevOpenHelper devOpenHelper=new DaoMaster.DevOpenHelper(context, DB_NAME,null);
        SQLiteDatabase database=devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster=new DaoMaster(database);
        DaoSession daoSession=daoMaster.newSession();

        List<music3> music3List = daoSession.getMusic3Dao().loadAll();
        MusicListRsp musicListRsp=new MusicListRsp();
        musicListRsp.setResCounter(music3List.size()+"");
        musicListRsp.setMusics(new ArrayList<MusicInfo>());
        for(music3 music:music3List)
        {
            MusicInfo musicInfo =new MusicInfo();
            musicInfo.setMusicId(music.getMusicId());
            musicInfo.setCount(music.getCount());
            musicInfo.setCrbtValidity(music.getCrbtValidity());
            musicInfo.setPrice(music.getPrice());
            musicInfo.setSongName(music.getSongName());
            musicInfo.setSingerId(music.getSingerId());
            musicInfo.setSingerName(music.getSingerName());
            musicInfo.setRingValidity(music.getRingValidity());
            musicInfo.setSongValidity(music.getSongValidity());
            musicInfo.setAlbumPicDir(music.getAlbumPicDir());
            musicInfo.setSingerPicDir(music.getSingerPicDir());
            musicInfo.setCrbtListenDir(music.getCrbtListenDir());
            musicInfo.setRingListenDir(music.getRingListenDir());
            musicInfo.setSongListenDir(music.getSongListenDir());
            musicInfo.setLrcDir(music.getLrcDir());
            musicInfo.setHasDolby(music.getHasDolby());
            musicListRsp.getMusics().add(musicInfo);
        }
        musicListRsp.setResCode("000000");
        musicListRsp.setResMsg("获取音乐列表信息成功");
        return musicListRsp;
    }

    private static void checkCache(Context context) throws Exception
    {
        String filePath=context.getFilesDir().getAbsolutePath().replace("files","databases");
        File file=new File(filePath,DB_NAME);

        if(file.exists())
            return;
        else
        {
            copy(context,file);
        }
    }

    private static void copy(Context context, File file) throws Exception
    {
        InputStream inputStream = context.getAssets().open(DB_NAME);
        FileOutputStream fileOutputStream=new FileOutputStream(file);

        byte[] buffer=new byte[inputStream.available()];
        inputStream.read(buffer);
        fileOutputStream.write(buffer);

        inputStream.close();
        fileOutputStream.close();
    }

    public static void cacheChartList(Context context,ChartListRsp chartListRsp)
    {
        DaoMaster.DevOpenHelper devOpenHelper=new DaoMaster.DevOpenHelper(context, DB_NAME,null);
        SQLiteDatabase database=devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster=new DaoMaster(database);
        DaoSession daoSession=daoMaster.newSession();

        List<ChartInfo> chartInfos = chartListRsp.getChartInfos();
        for(ChartInfo chartInfo:chartInfos)
        {
            Chart chart = new Chart();
            setChart(chartInfo, chart);
            daoSession.getChartDao().insert(chart);
        }
    }

    private static void setChart(ChartInfo chartInfo, Chart chart)
    {
        chart.setChartCode(chartInfo.getChartCode());
        chart.setChartName(chartInfo.getChartName());
    }

    public static void cacheMusic1(Context context, List<MusicInfo> musicInfoArrayList)
    {
        DaoMaster.DevOpenHelper devOpenHelper=new DaoMaster.DevOpenHelper(context, DB_NAME,null);
        SQLiteDatabase database=devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster=new DaoMaster(database);
        DaoSession daoSession=daoMaster.newSession();

        for(MusicInfo musicInfo:musicInfoArrayList)
        {
            music1 music = new music1();
            copy(musicInfo,music);
            daoSession.getMusic1Dao().insert(music);
        }
    }

    public static void cacheMusic2(Context context, List<MusicInfo> musicInfoArrayList)
    {
        DaoMaster.DevOpenHelper devOpenHelper=new DaoMaster.DevOpenHelper(context, DB_NAME,null);
        SQLiteDatabase database=devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster=new DaoMaster(database);
        DaoSession daoSession=daoMaster.newSession();

        for(MusicInfo musicInfo:musicInfoArrayList)
        {
            music2 music = new music2();
            copy(musicInfo,music);
            daoSession.getMusic2Dao().insert(music);
        }
    }

    public static void cacheMusic3(Context context, List<MusicInfo> musicInfoArrayList)
    {
        DaoMaster.DevOpenHelper devOpenHelper=new DaoMaster.DevOpenHelper(context, DB_NAME,null);
        SQLiteDatabase database=devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster=new DaoMaster(database);
        DaoSession daoSession=daoMaster.newSession();

        for(MusicInfo musicInfo:musicInfoArrayList)
        {
            music3 music = new music3();
            copy(musicInfo,music);
            daoSession.getMusic3Dao().insert(music);
        }
    }

    private static void copy(MusicInfo musicInfo, music1 music)
    {
        music.setMusicId(musicInfo.getMusicId());
        music.setCount(musicInfo.getCount());
        music.setCrbtValidity(musicInfo.getCrbtValidity());
        music.setPrice(musicInfo.getPrice());
        music.setSongName(musicInfo.getSongName());
        music.setSingerId(musicInfo.getSingerId());
        music.setSingerName(musicInfo.getSingerName());
        music.setRingValidity(musicInfo.getRingValidity());
        music.setSongValidity(musicInfo.getSongValidity());
        music.setAlbumPicDir(musicInfo.getAlbumPicDir());
        music.setSingerPicDir(musicInfo.getSingerPicDir());
        music.setCrbtListenDir(musicInfo.getCrbtListenDir());
        music.setRingListenDir(musicInfo.getRingListenDir());
        music.setSongListenDir(musicInfo.getSongListenDir());
        music.setLrcDir(musicInfo.getLrcDir());
        music.setHasDolby(musicInfo.getHasDolby());
    }

    private static void copy(MusicInfo musicInfo, music2 music)
    {
        music.setMusicId(musicInfo.getMusicId());
        music.setCount(musicInfo.getCount());
        music.setCrbtValidity(musicInfo.getCrbtValidity());
        music.setPrice(musicInfo.getPrice());
        music.setSongName(musicInfo.getSongName());
        music.setSingerId(musicInfo.getSingerId());
        music.setSingerName(musicInfo.getSingerName());
        music.setRingValidity(musicInfo.getRingValidity());
        music.setSongValidity(musicInfo.getSongValidity());
        music.setAlbumPicDir(musicInfo.getAlbumPicDir());
        music.setSingerPicDir(musicInfo.getSingerPicDir());
        music.setCrbtListenDir(musicInfo.getCrbtListenDir());
        music.setRingListenDir(musicInfo.getRingListenDir());
        music.setSongListenDir(musicInfo.getSongListenDir());
        music.setLrcDir(musicInfo.getLrcDir());
        music.setHasDolby(musicInfo.getHasDolby());
    }

    private static void copy(MusicInfo musicInfo, music3 music)
    {
        music.setMusicId(musicInfo.getMusicId());
        music.setCount(musicInfo.getCount());
        music.setCrbtValidity(musicInfo.getCrbtValidity());
        music.setPrice(musicInfo.getPrice());
        music.setSongName(musicInfo.getSongName());
        music.setSingerId(musicInfo.getSingerId());
        music.setSingerName(musicInfo.getSingerName());
        music.setRingValidity(musicInfo.getRingValidity());
        music.setSongValidity(musicInfo.getSongValidity());
        music.setAlbumPicDir(musicInfo.getAlbumPicDir());
        music.setSingerPicDir(musicInfo.getSingerPicDir());
        music.setCrbtListenDir(musicInfo.getCrbtListenDir());
        music.setRingListenDir(musicInfo.getRingListenDir());
        music.setSongListenDir(musicInfo.getSongListenDir());
        music.setLrcDir(musicInfo.getLrcDir());
        music.setHasDolby(musicInfo.getHasDolby());
    }
}
