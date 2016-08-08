package com.yueyinyue.cp.dialog;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.example.bajian.materialcheckbox.MaterialCheckBox;
import com.squareup.picasso.Picasso;
import com.xk.m.R;
import com.xk.m.databinding.MusicItemInCpMusicSelectDialogBinding;
import com.yueyinyue.Model.MusicItem;
import com.yueyinyue.cp.dialog.tick.presenter.TickPresenterImpl;

import java.util.List;

public class MusicItemViewHolderInSelectDialog
{
    private final Activity mActivity;
    private final MusicItemInCpMusicSelectDialogBinding mMusicItem;
    private final TickPresenterImpl mTickPresenterImpl;
    private final int mPosition;
    private final List<MusicItem> mMusicItemList;
    public MusicItemViewHolderInSelectDialog(Activity activity, View view, List<MusicItem> musicItemList, int position, TickPresenterImpl tickPresenterImpl)
    {
        mActivity = activity;
        mPosition = position;
        mMusicItemList=musicItemList;
        mTickPresenterImpl = tickPresenterImpl;
        mMusicItem = DataBindingUtil.bind(view.findViewById(R.id.simpleMusicItem2select));
        setView();
    }

    private void setView()
    {
        mMusicItem.songname.setText(mMusicItemList.get(mPosition).getSong());
        mMusicItem.singername.setText(mMusicItemList.get(mPosition).getSinger());
        Picasso.with(mActivity).load(mMusicItemList.get(mPosition).getPicaddress()).placeholder(R.drawable.icon).error(R.drawable.icon).resizeDimen(R.dimen.songlogoimagesize, R.dimen.songlogoimagesize).centerInside().tag(mActivity).into(mMusicItem.songlogo);

        if (!mMusicItemList.get(mPosition).isDownloaded())
        {
            mMusicItem.musiccheck.setCheckedStatus(mMusicItemList.get(mPosition).isSelected(), false);
            mMusicItem.musiccheck.setOnCheckedChangeListener(new MaterialCheckBox.OnCheckedChangeListener()
            {
                @Override
                public void onChange(boolean checked)
                {
                    if (!mTickPresenterImpl.checkTick(mPosition, checked))
                    {
                        mMusicItem.musiccheck.setUnchecked();
                    }
                }
            });
        }
        else
        {
            mMusicItem.singername.setText(String.format("%s%s", mMusicItemList.get(mPosition).getSinger(), mActivity.getString(R.string.already_download)));
            mMusicItem.musiccheck.setCheckedStatus(mMusicItemList.get(mPosition).isSelected(), false);
            mMusicItem.musiccheck.setEnabled(false);
            Log.i("checkBox disable", "position=" + mPosition);
            mMusicItem.musiccheck.setOnCheckedChangeListener(new MaterialCheckBox.OnCheckedChangeListener()
            {
                @Override
                public void onChange(boolean checked)
                {
                    Snackbar.make(mMusicItem.musiccheck, mMusicItemList.get(mPosition).getSong() + "已下载过，您可以去我的下载中查看", Snackbar.LENGTH_SHORT).show();
                }
            });
        }
    }
}
