package com.yueyinyue.Model.EventBusMessage;

import com.cmsc.cmmusic.common.data.MusicListRsp;

/**
 * Created by Administrator on 2015/11/18.
 */
public class MusicListRspMessage
{
    public MusicListRspMessage(MusicListRsp musicListRsp,int pagerPosition)
    {
        this.musicListRsp = musicListRsp;
        this.pagerPosition = pagerPosition;
    }

    public MusicListRsp musicListRsp;
    public int pagerPosition;
}
