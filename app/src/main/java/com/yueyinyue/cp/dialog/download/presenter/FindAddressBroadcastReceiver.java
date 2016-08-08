package com.yueyinyue.cp.dialog.download.presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cmsc.cmmusic.common.CPManagerInterface;
import com.xk.m.R;

public class FindAddressBroadcastReceiver extends BroadcastReceiver
{
    private final DownloadPresenter mDownloadPresenter;
    private int mTotal=0;
    private int mCount2find;

    public FindAddressBroadcastReceiver(DownloadPresenter downloadPresenter, int count2Selected)
    {
        mDownloadPresenter = downloadPresenter;
        mCount2find = count2Selected;
        mTotal=count2Selected;
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        int index = intent.getExtras().getInt("index");
        String musicId = mDownloadPresenter.mMusicItemList.get(index).getMusicid();
        CPManagerInterface.queryCPFullSongDownloadUrl(mDownloadPresenter.mActivity, mDownloadPresenter.mServiceId, musicId, "1", new FindAddressCallback(mDownloadPresenter, index));

        toast();

        mCount2find--;
        if (mCount2find == 0)
        {
            mDownloadPresenter.mActivity.unregisterReceiver(this);
        }
    }

    public void toast()
    {
        Toast toast = new Toast(mDownloadPresenter.mActivity);

        String text=String.format(mDownloadPresenter.mActivity.getString(R.string.pleasePressButton2getAddress),mTotal- mCount2find +1);
        setToast(toast,text);

        toast.show();
    }

    private void setToast(Toast toast, String text)
    {
        View view;
        view = ((LayoutInflater) mDownloadPresenter.mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.mytoast, null);
        TextView textView=(TextView)view.findViewById(R.id.message);
        textView.setText(text);
        toast.setView(view);

        toast.setGravity(Gravity.CENTER,0,0);
        toast.setDuration(Toast.LENGTH_LONG);
    }
}
