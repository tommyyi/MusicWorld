package com.yueyinyue.cp.dialog.download.presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class DlCmdBroadcastReceiver extends BroadcastReceiver
{
    private final DownloadPresenter mDownloadPresenter;
    private int mCount2dl;

    public DlCmdBroadcastReceiver(DownloadPresenter downloadPresenter, int count2Addressed)
    {
        mDownloadPresenter = downloadPresenter;
        mCount2dl = count2Addressed;
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        int index = intent.getExtras().getInt("index");
        if (index != DownloadPresenter.OUTOFINDEX)
        {
            mDownloadPresenter.downThisOne(index);

            mCount2dl--;
            if (mCount2dl == 0)
            {
                mDownloadPresenter.mActivity.unregisterReceiver(this);
            }
        }
        else
        {
            mDownloadPresenter.mActivity.unregisterReceiver(this);
        }
    }
}
