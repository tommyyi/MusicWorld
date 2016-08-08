package com.yueyinyue.home.Pagerfragment.Model;

import android.content.Context;

import com.cmsc.cmmusic.common.data.MusicInfo;
import com.yueyinyue.home.Pagerfragment.ViewButtonImp;
import com.yueyinyue.home.Pagerfragment.ViewCardContentUpdateImp;
import com.yueyinyue.home.Pagerfragment.ViewDlResultImp;

import java.io.File;

import cn.aigestudio.downloader.bizs.DLManager;
import cn.aigestudio.downloader.interfaces.IDListener;

/**
 * Created by Administrator on 2016/3/24.
 */
public class DownloadFullSongModel implements DownloadFullSongModelImp
{
    public DownloadFullSongModel(Context context, MusicInfo musicInfo, String musicUrl,String dirPath, MusicDlRecordModelImp musicDlRecordModelImp,  ViewCardContentUpdateImp viewCardContentUpdateImp, ViewButtonImp viewButtonImp, ViewDlResultImp viewDlResultImp)
    {
        mContext = context;
        mMusicInfo = musicInfo;
        this.musicUrl = musicUrl;
        this.dirPath = dirPath;
        mMusicDlRecordModelImp = musicDlRecordModelImp;
        mViewCardContentUpdateImp=viewCardContentUpdateImp;
        mViewButtonImp=viewButtonImp;
        mViewDlResultImp=viewDlResultImp;
    }

    private Context mContext;
    private MusicInfo mMusicInfo;
    private String musicUrl;
    private java.lang.String dirPath;
    private ViewButtonImp mViewButtonImp;
    private ViewCardContentUpdateImp mViewCardContentUpdateImp;
    private  ViewDlResultImp mViewDlResultImp;
    private MusicDlRecordModelImp mMusicDlRecordModelImp;

    @Override
    public void download()
    {
        DLManager.getInstance(mContext).dlStart(musicUrl, dirPath, new IDListener()
        {
            @Override
            public void onPrepare()
            {
                if(mMusicDlRecordModelImp.getMusicDlRecord(mMusicInfo.getMusicId())==null)
                    mMusicDlRecordModelImp.addMusicDlRecord(mMusicInfo,dirPath);
                mViewButtonImp.setFullSongDownloadButtonBeforeDl();
            }

            @Override
            public void onStart(String fileName, String realUrl, int fileLength)
            {
                mMusicDlRecordModelImp.updateFullSongFileName(mMusicInfo.getMusicId(),fileName);
            }

            @Override
            public void onProgress(final int progress)
            {
                mViewButtonImp.setFullSongDownloadButtonDuringDl(progress);
                mMusicDlRecordModelImp.updateFullSongPercentage(mMusicInfo.getMusicId(),progress);
            }

            @Override
            public void onStop(int progress)
            {
            }

            @Override
            public void onFinish(final File file)
            {
                mViewCardContentUpdateImp.updateCard();
                mViewButtonImp.setFullSongDownloadButtonAfterDl();
                mViewDlResultImp.showResult(file);
            }

            @Override
            public void onError(int status, String error)
            {
            }
        });
    }
}
