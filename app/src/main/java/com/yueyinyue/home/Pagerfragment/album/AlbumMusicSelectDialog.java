package com.yueyinyue.home.Pagerfragment.album;

import android.app.Activity;

import com.yueyinyue.Model.MusicItem;
import com.yueyinyue.cp.dialog.MusicSelectDialog;
import com.yueyinyue.cp.dialog.download.presenter.DownloadPresenter;

import java.util.List;

/**
 * Created by Administrator on 2016/8/23.
 */
public class AlbumMusicSelectDialog extends MusicSelectDialog
{
    /**
     *A dialog that lists some musics which user can selects to download
     * @param activity
     * @param cpCategoryIndex 其实没什么用
     * @param musicItemList
     * @param limited
     */
    public AlbumMusicSelectDialog(Activity activity, int cpCategoryIndex, List<MusicItem> musicItemList, int limited)
    {
        super(activity, cpCategoryIndex, musicItemList, limited);
        String serviceId="60050402697";/*数字专辑的ID*/
        downloadPresenterImpl = new DownloadPresenter(activity, serviceId,this, musicItemList);
    }
}
