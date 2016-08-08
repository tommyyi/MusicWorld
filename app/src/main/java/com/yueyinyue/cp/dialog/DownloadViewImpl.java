package com.yueyinyue.cp.dialog;

import com.yueyinyue.Model.MusicItem;

import java.util.List;

/**
 * Created by Administrator on 2016/1/12.
 */
public interface DownloadViewImpl
{
    void resetSelectedTag(List<MusicItem> musicItemList);
    void setEnabled(boolean enabled);
    void setProgress(int progress);
    void setText(String text);
    void setOnClickListener();
    void showDlResult(String dirPath);
    void notifyItemChanged(int itemPosition);
    void showToast(String message);
    void toastSuccess(String message);
    void toastError(String message);
}
