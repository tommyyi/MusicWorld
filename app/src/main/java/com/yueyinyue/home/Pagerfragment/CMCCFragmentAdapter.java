package com.yueyinyue.home.Pagerfragment;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cmsc.cmmusic.common.data.MusicInfo;
import com.xk.m.R;

import java.util.ArrayList;
import java.util.List;

public class CMCCFragmentAdapter extends RecyclerView.Adapter<MusicItemViewHolder>
{
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_CELL = 1;

    private Activity activity;
    private List<MusicInfo> musicInfoList = new ArrayList<>();

    public CMCCFragmentAdapter(List<MusicInfo> musicInfoList, Activity activity)
    {
        this.musicInfoList = musicInfoList;
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
        return musicInfoList.size();
    }

    @Override
    public MusicItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view;
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
                holder.init(activity,musicInfoList.get(position));
                break;
        }
    }
}