package com.yueyinyue.downloaded;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cmsc.cmmusic.common.data.MusicInfo;
import com.yueyinyue.home.Pagerfragment.MusicItemViewHolder;
import com.xk.m.R;
import com.yueyinyue.Model.dao.MusicDl.MusicDlRecord;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<MusicItemViewHolder>
{
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_CELL = 1;

    private Activity activity;
    private List<MusicDlRecord> musicDlRecordList = new ArrayList<MusicDlRecord>();

    public RecyclerViewAdapter(List<MusicDlRecord> musicDlRecordList, Activity activity)
    {
        this.musicDlRecordList = musicDlRecordList;
        this.activity = activity;
    }

    @Override
    public int getItemViewType(int position)
    {
        switch (position)
        {
            /*case 0:
                return TYPE_HEADER;*/
            default:
                return TYPE_CELL;
        }
    }

    @Override
    public int getItemCount()
    {
        return musicDlRecordList.size();
    }

    @Override
    public MusicItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = null;
        switch (viewType)
        {
            case TYPE_HEADER:
            {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_item_header, parent, false);
                return new MusicItemViewHolder(view)
                {
                };
            }
            case TYPE_CELL:
            {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_item_wrapper, parent, false);
                return new MusicItemViewHolder(view)
                {
                };
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(MusicItemViewHolder holder, final int position)
    {
        switch (getItemViewType(position))
        {
            case TYPE_HEADER:
                break;
            case TYPE_CELL:
                MusicInfo musicInfo=new MusicInfo();
                musicInfo.setMusicId(musicDlRecordList.get(position).getMusicId());
                musicInfo.setAlbumPicDir(musicDlRecordList.get(position).getPicUrl());
                musicInfo.setSingerName(musicDlRecordList.get(position).getSinger());
                musicInfo.setSongName(musicDlRecordList.get(position).getSongName());
                musicInfo.setRingListenDir(musicDlRecordList.get(position).getRingListenUrl());
                musicInfo.setSongListenDir(musicDlRecordList.get(position).getFullSongFileName());
                holder.init(activity,musicInfo);
                break;
        }
    }
}