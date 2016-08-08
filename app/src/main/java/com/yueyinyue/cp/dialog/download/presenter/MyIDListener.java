package com.yueyinyue.cp.dialog.download.presenter;

import android.content.Intent;
import android.util.Log;

import com.xk.m.R;

import java.io.File;

import cn.aigestudio.downloader.interfaces.IDListener;

class MyIDListener implements IDListener
{
    private DownloadPresenter mDownloadPresenter;
    private final int mIndex;
    private final String mMusicId;
    private final String mDirPath;

    public MyIDListener(DownloadPresenter downloadPresenter, int index, String musicId, String dirPath)
    {
        mDownloadPresenter = downloadPresenter;
        mIndex = index;
        mMusicId = musicId;
        mDirPath = dirPath;
    }

    @Override
    public void onPrepare()
    {
        mDownloadPresenter.downloadViewImpl.setProgress(0);
        mDownloadPresenter.downloadViewImpl.setEnabled(false);
        mDownloadPresenter.downloadViewImpl.setText(/*"正在下载"+*/mDownloadPresenter.mMusicItemList.get(mIndex).getSong());
    }

    @Override
    public void onStart(String fileName, String realUrl, int fileLength)
    {
        mDownloadPresenter.downloadModelImpl.updateMusicDlRecord_fileName(mMusicId, fileName);
    }

    @Override
    public void onProgress(final int progress)
    {
        mDownloadPresenter.downloadViewImpl.setProgress(progress);
        mDownloadPresenter.downloadModelImpl.updateMusicDlRecord_progress(mMusicId, progress);
        mDownloadPresenter.downloadViewImpl.setText(/*"正在下载"+*/mDownloadPresenter.mMusicItemList.get(mIndex).getSong());
    }

    @Override
    public void onStop(int progress)
    {
    }

    @Override
    public void onFinish(final File file)
    {
        mDownloadPresenter.mCount2Addressed--;
        mDownloadPresenter.downloadModelImpl.updateMusicDlRecord_progress(mMusicId, 100);
        mDownloadPresenter.mMusicItemList.get(mIndex).setDownloaded(true);
        mDownloadPresenter.downloadViewImpl.notifyItemChanged(mIndex + 1);
        if (mDownloadPresenter.mCount2Addressed == 0)
        {
            mDownloadPresenter.downloadViewImpl.setProgress(0);
            mDownloadPresenter.downloadViewImpl.setEnabled(true);
            mDownloadPresenter.downloadViewImpl.setText(mDownloadPresenter.mActivity.getResources().getString(R.string.go2myDownload));
            mDownloadPresenter.downloadViewImpl.setOnClickListener();
            mDownloadPresenter.downloadViewImpl.showDlResult(mDirPath);
        }
        else
        {
            int i = mDownloadPresenter.locateNextAddressedIndex(mIndex + 1);
            Intent intent = new Intent(DownloadPresenter.ACTION);
            intent.putExtra("index", i);
            mDownloadPresenter.mActivity.sendBroadcast(intent);
        }
    }

    @Override
    public void onError(int status, String error)
    {
        mDownloadPresenter.mCount2Addressed--;
        if (mDownloadPresenter.mCount2Addressed == 0)
        {
            mDownloadPresenter.downloadViewImpl.setProgress(0);
            mDownloadPresenter.downloadViewImpl.setEnabled(true);
            mDownloadPresenter.downloadViewImpl.setText(mDownloadPresenter.mActivity.getResources().getString(R.string.go2myDownload));
            mDownloadPresenter.downloadViewImpl.setOnClickListener();
            mDownloadPresenter.downloadViewImpl.showDlResult(mDirPath);
        }
        else
        {
            int i = mDownloadPresenter.locateNextAddressedIndex(mIndex + 1);
            Intent intent = new Intent(DownloadPresenter.ACTION);
            intent.putExtra("index", i);
            mDownloadPresenter.mActivity.sendBroadcast(intent);
        }
        Log.i("download error,status", status + error);
    }
}
