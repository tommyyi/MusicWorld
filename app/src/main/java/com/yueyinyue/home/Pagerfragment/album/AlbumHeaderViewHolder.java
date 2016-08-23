package com.yueyinyue.home.Pagerfragment.album;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xk.m.R;
import com.xk.m.databinding.MusicItemHeaderBinding;
import com.yueyinyue.Model.Category;
import com.yueyinyue.cp.CpActivityAdapterHeaderViewHolder;

public class AlbumHeaderViewHolder extends CpActivityAdapterHeaderViewHolder
{
    public AlbumHeaderViewHolder(Activity activity, View view, int limited, int cpCategoryIndex)
    {
        super(activity, view, limited, cpCategoryIndex);
    }

    public void setContent()
    {
        String serviceIntroStr=mActivity.getString(R.string.albumIntro);
        mMusicItemHeaderBinding.serviceIntro.setText(serviceIntroStr);
        mMusicItemHeaderBinding.serviceFee.setText(mActivity.getString(R.string.albumFee));
    }
}
