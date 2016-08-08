package com.yueyinyue.cp.dialog.download.presenter;

import android.content.Intent;
import android.util.Log;

import com.cmsc.cmmusic.common.CMMusicCallback;
import com.cmsc.cmmusic.common.data.DownloadResult;
import com.cmsc.cmmusic.common.data.OrderResult;

public class FindAddressCallback implements CMMusicCallback<OrderResult>
{
    private final DownloadPresenter mDownloadPresenter;
    private final int mIndex;

    public FindAddressCallback(DownloadPresenter downloadPresenter, int index)
    {
        mDownloadPresenter = downloadPresenter;
        mIndex = index;
    }

    @Override
    public void operationResult(OrderResult downloadResult)
    {
        Log.i("查询下载地址", "" + mIndex);
        String downUrl = downloadResult.getDownUrl();
        if (downUrl != null)
        {
            mDownloadPresenter.mMusicItemList.get(mIndex).setMusicaddress(downUrl);
            mDownloadPresenter.mCount2Addressed++;
        }
        else
        {
            mDownloadPresenter.mCountAddressNotExist++;
        }

        if (mDownloadPresenter.mCount2Addressed + mDownloadPresenter.mCountAddressNotExist == mDownloadPresenter.mCount2Selected)
        {
            mDownloadPresenter.handleMusicDownload();
        }
        else
        {
            int index = mDownloadPresenter.locateNextSelected(mIndex + 1);
            Intent intent = new Intent(DownloadPresenter.ACTION);
            intent.putExtra("index", index);
            mDownloadPresenter.mActivity.sendBroadcast(intent);
        }
    }
}
