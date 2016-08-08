package com.yueyinyue.home.Pagerfragment;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cmsc.cmmusic.common.data.MusicInfo;
import com.squareup.picasso.Picasso;
import com.xk.m.R;
import com.xk.m.databinding.MusicItemBinding;
import com.yueyinyue.Model.dao.MusicDl.MusicDlRecord;
import com.yueyinyue.home.Pagerfragment.Model.MusicDlRecordModel;
import com.yueyinyue.home.Pagerfragment.Model.MusicDlRecordModelImp;
import com.yueyinyue.home.Pagerfragment.MusicItemHandler;

/**
 * Created by Administrator on 2016/3/24.
 */
public class MusicItemViewHolder extends RecyclerView.ViewHolder
{
    public Activity mActivity;
    public MusicItemBinding mCardContentBinding;

    public void init(Activity activity,MusicInfo musicInfo)
    {
        mActivity=activity;

        mCardContentBinding.lowerlinearlayout.setTag(musicInfo.getMusicId());
        mCardContentBinding.lowerlinearlayout.setVisibility(View.GONE);
        Picasso.with(activity.getApplicationContext()).load(musicInfo.getAlbumPicDir()).placeholder(R.drawable.icon).error(R.drawable.icon).resizeDimen(R.dimen.songlogoimagesize, R.dimen.songlogoimagesize).centerInside().tag(activity.getApplicationContext()).into(mCardContentBinding.songlogo);

        MusicDlRecordModelImp musicDlRecordModelImp = new MusicDlRecordModel(musicInfo.getMusicId());
        MusicDlRecord musicDlRecord = musicDlRecordModelImp.getMusicDlRecord(musicInfo.getMusicId());
        MusicItemHandler handler=new MusicItemHandler(mActivity,mCardContentBinding,musicDlRecordModelImp);

        mCardContentBinding.setHandler(handler);
        mCardContentBinding.setMusicDlRecord(musicDlRecord);
        mCardContentBinding.setMusicInfo(musicInfo);
    }

    public MusicItemViewHolder(View itemView)
    {
        super(itemView);
        mCardContentBinding=MusicItemBinding.bind(itemView.findViewById(R.id.card_content));
    }
}
