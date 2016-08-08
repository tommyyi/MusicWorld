package com.yueyinyue.search;

import android.app.Activity;
import android.content.Context;

import com.cmsc.cmmusic.common.MusicQueryInterface;
import com.cmsc.cmmusic.common.data.MusicInfo;
import com.cmsc.cmmusic.common.data.MusicListRsp;

import java.util.List;

/**
 * Created by Administrator on 2016/1/19.
 */
public class SearchMusicModel implements SearchMusicModelImp
{
    @Override
    public boolean search(Context context, List<MusicInfo> musicInfoList, String keyword)
    {
        int pagerNumber = 1;
        MusicListRsp musicListRsp = MusicQueryInterface.getMusicsByKey(context, keyword, "2", pagerNumber, 20);
        if (musicListRsp != null && musicListRsp.getMusics() != null && musicListRsp.getMusics().size() != 0)
        {
            musicInfoList.addAll(musicListRsp.getMusics());
            while (true)
            {
                pagerNumber++;
                musicListRsp = MusicQueryInterface.getMusicsByKey(context, keyword, "2", pagerNumber, 20);
                if (musicListRsp != null && musicListRsp.getMusics() != null && musicListRsp.getMusics().size() != 0)
                    musicInfoList.addAll(musicListRsp.getMusics());
                else
                    break;
            }
            return true;
        }
        else
            return false;
    }
}
