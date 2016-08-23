package com.yueyinyue.home.Pagerfragment.album;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xk.m.R;
import com.yueyinyue.Model.MusicItem;
import com.yueyinyue.cp.CpActivityAdapterHeaderViewHolder;
import com.yueyinyue.cp.CpActivityAdapterViewHolder;
import com.yueyinyue.cp.CpMusicListAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/8/23.
 */
public class AlbumMusicListAdapter extends CpMusicListAdapter
{
    public AlbumMusicListAdapter(Activity activity, int cpCategoryIndex, List<MusicItem> musicItemList, int limited)
    {
        super(activity, cpCategoryIndex, musicItemList, limited);
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
                return new AlbumHeaderViewHolder(activity,view,limited,cpCategoryIndex);
            }
            case TYPE_CELL:
            {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_item_wrapper_in_album, parent, false);
                return new AlbumMusicListAdapterViewHolder(activity,view,musicItemList,limited,cpCategoryIndex);
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
                ((AlbumHeaderViewHolder)holder).setContent();
                break;

            case TYPE_CELL:
                ((AlbumMusicListAdapterViewHolder)holder).setMusicContent(musicItemList.get(position-1));
                break;
        }
    }
}
