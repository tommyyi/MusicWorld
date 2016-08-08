package com.yueyinyue.home.Pagerfragment;

/**
 * Created by Administrator on 2016/3/24.
 */
public interface ViewButtonImp
{
    void setFullSongDownloadButtonBeforeDl();
    void setFullSongDownloadButtonDuringDl(int progress);
    void setFullSongDownloadButtonAfterDl();

    void setVibrateRingDownloadButtonBeforeDl();
    void setVibrateRingDownloadButtonDuringDl(int progress);
    void setVibrateRingDownloadButtonAfterDl();
}
