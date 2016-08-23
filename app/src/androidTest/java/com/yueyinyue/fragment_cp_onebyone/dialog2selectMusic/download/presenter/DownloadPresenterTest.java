package com.yueyinyue.fragment_cp_onebyone.dialog2selectMusic.download.presenter;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.yueyinyue.Model.Category;
import com.yueyinyue.Model.MusicItem;
import com.yueyinyue.cp.dialog.DownloadViewImpl;
import com.yueyinyue.cp.dialog.MusicSelectDialog;
import com.yueyinyue.cp.dialog.download.presenter.DownloadPresenter;
import com.yueyinyue.cp.dialog.download.presenter.FindAddressBroadcastReceiver;
import com.yueyinyue.search.SearchActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/26.
 */
@RunWith(AndroidJUnit4.class)
public class DownloadPresenterTest
{
    @Rule
    public ActivityTestRule<SearchActivity> mainActivityActivityTestRule = new ActivityTestRule<>(SearchActivity.class);
    private SearchActivity mMainActivity;

    @Before
    public void setup() throws Exception
    {
        mMainActivity = mainActivityActivityTestRule.getActivity();
    }

    @Test
    public void testHandleMusicDownload() throws Exception
    {
        mMainActivity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                final MusicSelectDialog musicSelectDialog = new MusicSelectDialog(mMainActivity, 6, MusicItem.getCPMusicListByCategoryName(mMainActivity, Category.getCpCategoryName(mMainActivity.getApplicationContext(),0)), 6);
                musicSelectDialog.show();
            }
        });
        Thread.sleep(1000000);
    }

    @Test
    public void testToast() throws Exception
    {
        mMainActivity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                List<MusicItem> musicItemList= new ArrayList<>();
                DownloadPresenter dlpresenter=new DownloadPresenter(mMainActivity, "",new DownloadViewImpl()
                {
                    @Override
                    public void resetSelectedTag(List<MusicItem> musicItemList)
                    {

                    }

                    @Override
                    public void setEnabled(boolean enabled)
                    {

                    }

                    @Override
                    public void setProgress(int progress)
                    {

                    }

                    @Override
                    public void setText(String text)
                    {

                    }

                    @Override
                    public void setOnClickListener()
                    {

                    }

                    @Override
                    public void showDlResult(String dirPath)
                    {

                    }

                    @Override
                    public void notifyItemChanged(int itemPosition)
                    {

                    }

                    @Override
                    public void showToast(String message)
                    {

                    }

                    @Override
                    public void toastSuccess(String message)
                    {

                    }

                    @Override
                    public void toastError(String message)
                    {

                    }
                },musicItemList);
                FindAddressBroadcastReceiver receiver = new FindAddressBroadcastReceiver(dlpresenter, 3);
                receiver.toast();
            }
        });
        Thread.sleep(10000000);
    }
}