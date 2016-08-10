package com.yueyinyue.Model;

import android.content.Context;

import com.alibaba.fastjson.JSONArray;
import com.yueyinyue.DbSession;
import com.yueyinyue.Model.dao.MusicDl.MusicDlRecord;
import com.yueyinyue.Model.dao.MusicDl.MusicDlRecordDao;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.WhereCondition;

/**
 * Created by Administrator on 2015/12/8.
 */
public class MusicItem
{
    public static List<MusicItem> getCPMusicList(Context context)
    {
        String musicItemListStr="";

        try
        {
            InputStream in = context.getAssets().open("cp.json");
            int size = in.available();
            byte buf[] = new byte[size];
            in.read(buf);
            musicItemListStr= new String(buf, "utf-8");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        List<MusicItem> totalCpMusicList= JSONArray.parseArray(musicItemListStr,MusicItem.class);
        List<MusicItem> currentCpCategoryMusicList=new ArrayList<>();

        WhereCondition fullSongCondition= MusicDlRecordDao.Properties.FullSongDlPercentage.eq(100);
        WhereCondition vibrateRingCondition= MusicDlRecordDao.Properties.VibrateRingDlPercentage.eq(100);
        QueryBuilder queryBuilder = DbSession.musicDlRecordDaoSession.getMusicDlRecordDao().queryBuilder();
        Query query = queryBuilder.where(queryBuilder.or(fullSongCondition, vibrateRingCondition)).build();
        List<MusicDlRecord> musicDlRecordList=query.list();

        int sizeTotalList=totalCpMusicList.size();
        int sizeRecordedList=musicDlRecordList.size();
        for(int index=0;index<sizeTotalList;index++)
        {
            for(int secondIndex=0;secondIndex<sizeRecordedList;secondIndex++)
                if(musicDlRecordList.get(secondIndex).getMusicId().equals(totalCpMusicList.get(index).getMusicid()))
                    totalCpMusicList.get(index).setDownloaded(true);
            currentCpCategoryMusicList.add(totalCpMusicList.get(index));
        }

        return currentCpCategoryMusicList;
    }

    public static List<MusicItem> getCPMusicListByCategoryName(Context context, String category)
    {
        String musicItemListStr="";

        try
        {
            InputStream in = context.getAssets().open("cp.json");
            int size = in.available();
            byte buf[] = new byte[size];
            in.read(buf);
            musicItemListStr= new String(buf, "utf-8");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        List<MusicItem> totalCpMusicList= JSONArray.parseArray(musicItemListStr,MusicItem.class);
        List<MusicItem> currentCpCategoryMusicList=new ArrayList<>();

        WhereCondition fullSongCondition= MusicDlRecordDao.Properties.FullSongDlPercentage.eq(100);
        WhereCondition vibrateRingCondition= MusicDlRecordDao.Properties.VibrateRingDlPercentage.eq(100);
        QueryBuilder queryBuilder = DbSession.musicDlRecordDaoSession.getMusicDlRecordDao().queryBuilder();
        Query query = queryBuilder.where(queryBuilder.or(fullSongCondition, vibrateRingCondition)).build();
        List<MusicDlRecord> musicDlRecordList=query.list();

        int sizeTotalList=totalCpMusicList.size();
        int sizeRecordedList=musicDlRecordList.size();
        for(int index=0;index<sizeTotalList;index++)
        {
            if(totalCpMusicList.get(index).getCategory().equals(category))
            {
                for(int secondIndex=0;secondIndex<sizeRecordedList;secondIndex++)
                    if(musicDlRecordList.get(secondIndex).getMusicId().equals(totalCpMusicList.get(index).getMusicid()))
                        totalCpMusicList.get(index).setDownloaded(true);
                currentCpCategoryMusicList.add(totalCpMusicList.get(index));
            }
        }

        return currentCpCategoryMusicList;
    }

    private String musicid;

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    private String category;

    public String getFullsongid()
    {
        return fullsongid;
    }

    public void setFullsongid(String fullsongid)
    {
        this.fullsongid = fullsongid;
    }

    private String fullsongid;
    private String ringid;
    private String song;
    private String singer;
    private String price;
    private String picaddress;
    private String musicaddress;
    private boolean isDownloaded;
    private boolean isSelected;

    public boolean isSelected()
    {
        return isSelected;
    }

    public void setSelected(boolean selected)
    {
        isSelected = selected;
    }


    public boolean isDownloaded()
    {
        return isDownloaded;
    }

    public void setDownloaded(boolean downloaded)
    {
        isDownloaded = downloaded;
    }


    public String getMusicaddress()
    {
        return musicaddress;
    }

    public void setMusicaddress(String musicaddress)
    {
        this.musicaddress = musicaddress;
    }

    public String getMusicid()
    {
        return musicid;
    }

    public void setMusicid(String musicid)
    {
        this.musicid = musicid;
    }

    public String getPicaddress()
    {
        return picaddress;
    }

    public void setPicaddress(String picaddress)
    {
        this.picaddress = picaddress;
    }

    public String getPrice()
    {
        return price;
    }

    public void setPrice(String price)
    {
        this.price = price;
    }

    public String getRingid()
    {
        return ringid;
    }

    public void setRingid(String ringid)
    {
        this.ringid = ringid;
    }

    public String getSinger()
    {
        return singer;
    }

    public void setSinger(String singer)
    {
        this.singer = singer;
    }

    public String getSong()
    {
        return song;
    }

    public void setSong(String song)
    {
        this.song = song;
    }

}
