package com.yueyinyue.search;

import android.app.Activity;
import android.content.Context;

import com.cmsc.cmmusic.common.data.MusicInfo;

import java.util.List;

/**
 * Created by Administrator on 2016/1/19.
 */
public interface SearchMusicModelImp
{
    boolean search(Context context, List<MusicInfo> musicInfoList, String keyword);
}
