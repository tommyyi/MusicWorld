package com.yueyinyue.Model.EventBusMessage;

/**
 * Created by Administrator on 2015/12/7.
 */
public class SearchMusicOverMessage
{
    public SearchMusicOverMessage(boolean isOver)
    {
        this.isSuccess = isOver;
    }

    public boolean isSuccess;
}
