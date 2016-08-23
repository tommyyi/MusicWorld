package com.yueyinyue.cp;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yueyinyue.Model.MusicItem;
import com.xk.m.R;

import java.util.List;

public class CpMusicListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_CELL = 1;

    public final Activity activity;
    public final List<MusicItem> musicItemList;
    public final int limited;
    public final int cpCategoryIndex;

    public CpMusicListAdapter(Activity activity, int cpCategoryIndex, List<MusicItem> musicItemList, int limited)
    {
        this.activity = activity;
        this.musicItemList = musicItemList;
        this.limited = limited;
        this.cpCategoryIndex = cpCategoryIndex;
    }

    @Override
    public int getItemViewType(int position)
    {
        switch (position)
        {
            case 0:
                return TYPE_HEADER;
            default:
                return TYPE_CELL;
        }
    }

    @Override
    public int getItemCount()
    {
        return musicItemList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = null;
        switch (viewType)
        {
            case TYPE_HEADER:
            {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_item_header, parent, false);
                return new CpActivityAdapterHeaderViewHolder(activity,view,limited,cpCategoryIndex);
            }
            case TYPE_CELL:
            {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_item_wrapper_in_cp_activity, parent, false);
                return new CpActivityAdapterViewHolder(activity,view,musicItemList,limited,cpCategoryIndex);
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position)
    {
        switch (getItemViewType(position))
        {
            case TYPE_HEADER:
                ((CpActivityAdapterHeaderViewHolder)holder).setContent();
                break;

            case TYPE_CELL:
                ((CpActivityAdapterViewHolder)holder).setMusicContent(musicItemList.get(position-1));
                break;
        }
    }
}