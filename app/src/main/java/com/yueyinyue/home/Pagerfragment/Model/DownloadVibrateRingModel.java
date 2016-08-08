package com.yueyinyue.home.Pagerfragment.Model;

import android.app.Activity;

import com.cmsc.cmmusic.common.data.MusicInfo;
import com.yueyinyue.home.Pagerfragment.ViewButtonImp;
import com.yueyinyue.home.Pagerfragment.ViewCardContentUpdateImp;
import com.yueyinyue.home.Pagerfragment.ViewDlResultImp;

import java.io.File;

import cn.aigestudio.downloader.bizs.DLManager;
import cn.aigestudio.downloader.interfaces.IDListener;

/**
 * Created by Administrator on 2016/3/23.
 */
public class DownloadVibrateRingModel implements DownloadVibrateRingModelImp
{
    public DownloadVibrateRingModel(Activity activity, MusicInfo musicInfo, String musicUrl, String dirPath, MusicDlRecordModelImp musicDlRecordModelImp, ViewCardContentUpdateImp viewCardContentUpdateImp, ViewButtonImp viewButtonImp,ViewDlResultImp viewDlResultImp)
    {
        mActivity = activity;
        mMusicInfo = musicInfo;
        this.musicUrl = musicUrl;
        this.dirPath = dirPath;
        mMusicDlRecordModelImp = musicDlRecordModelImp;
        mViewCardContentUpdateImp=viewCardContentUpdateImp;
        mViewButtonImp=viewButtonImp;
        mViewDlResultImp=viewDlResultImp;
    }

    private Activity mActivity;
    private MusicInfo mMusicInfo;
    private String musicUrl;
    private String dirPath;
    private MusicDlRecordModelImp mMusicDlRecordModelImp;
    private ViewCardContentUpdateImp mViewCardContentUpdateImp;
    private ViewButtonImp mViewButtonImp;
    private ViewDlResultImp mViewDlResultImp;

    @Override
    public void download()
    {
        DLManager.getInstance(mActivity).dlStart(musicUrl, dirPath, new IDListener()
        {
            @Override
            public void onPrepare()
            {
                if(mMusicDlRecordModelImp.getMusicDlRecord(mMusicInfo.getMusicId())==null)
                    mMusicDlRecordModelImp.addMusicDlRecord(mMusicInfo,dirPath);
                mViewButtonImp.setVibrateRingDownloadButtonBeforeDl();
            }

            @Override
            public void onStart(String fileName, String realUrl, int fileLength)
            {
                mMusicDlRecordModelImp.updateVibrateFileName(mMusicInfo.getMusicId(),fileName);
            }

            @Override
            public void onProgress(final int progress)
            {
                mViewButtonImp.setVibrateRingDownloadButtonDuringDl(progress);
                mMusicDlRecordModelImp.updateVibratePercentage(mMusicInfo.getMusicId(),progress);
            }

            @Override
            public void onStop(int progress)
            {
            }

            @Override
            public void onFinish(final File file)
            {
                mViewButtonImp.setVibrateRingDownloadButtonAfterDl();
                mViewCardContentUpdateImp.updateCard();
                mViewDlResultImp.showResult(file);
            }

            @Override
            public void onError(int status, String error)
            {
            }
        });
    }
}
