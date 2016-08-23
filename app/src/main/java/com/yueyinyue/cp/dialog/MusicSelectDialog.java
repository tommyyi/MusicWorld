package com.yueyinyue.cp.dialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.ListViewCompat;
import android.view.LayoutInflater;
import android.view.View;

import com.dd.processbutton.iml.SubmitProcessButton;
import com.yueyinyue.Model.Category;
import com.yueyinyue.Model.EventBusMessage.Reach2LimitedMessage;
import com.yueyinyue.Model.EventBusMessage.UpdateItemMessage;
import com.yueyinyue.cp.dialog.download.presenter.DownloadPresenter;
import com.yueyinyue.cp.dialog.download.presenter.DownloadPresenterImpl;
import com.yueyinyue.playcenter.PlayCenterPresenter;
import com.yueyinyue.Model.MusicItem;
import com.yueyinyue.downloaded.MyDownloadActivity;
import com.xk.m.R;

import net.steamcrafted.loadtoast.LoadToast;

import java.util.List;

import de.greenrobot.event.EventBus;

public class MusicSelectDialog extends AppCompatDialog implements DownloadViewImpl
{
    public final LoadToast loadToast;
    public final SubmitProcessButton confirm;
    public final SubmitProcessButton cancel;
    public final MusicSelectDialogAdapter mAdapter;
    public DownloadPresenterImpl downloadPresenterImpl;
    public final Handler handler;
    public final Activity activity;
    public final int mCpCategoryIndex;

    public MusicSelectDialog(Activity activity, int cpCategoryIndex, final List<MusicItem> musicItemList, final int limited)
    {
        super(activity);
        setTitle("任意选择" + limited + "首歌曲");

        loadToast = new LoadToast(activity).setTranslationY(200);
        this.mCpCategoryIndex = cpCategoryIndex;
        resetSelectedTag(musicItemList);
        this.activity = activity;
        String serviceId= Category.getServiceId(activity.getApplicationContext(),mCpCategoryIndex);
        downloadPresenterImpl = new DownloadPresenter(activity, serviceId,this, musicItemList);
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_tickmusic, null, false);

        ListViewCompat listViewCompat = (ListViewCompat) view.findViewById(R.id.recyclerView);
        mAdapter = new MusicSelectDialogAdapter(activity, musicItemList, limited);
        listViewCompat.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        confirm = (SubmitProcessButton) view.findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(mCpCategoryIndex==0&&limited!=30&&limited!=3)
                    downloadPresenterImpl.handleCPMonthBuy();
                else if(mCpCategoryIndex==0&&limited==3)
                    downloadPresenterImpl.handleAlbumBuy();
                else
                    downloadPresenterImpl.handleCPPatchBuy();
            }
        });

        cancel = (SubmitProcessButton) view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dismiss();
            }
        });

        handler = new Handler();
        setContentView(view);
        show();
        EventBus.getDefault().register(this);
    }

    @Override
    public void dismiss()
    {
        super.dismiss();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void resetSelectedTag(List<MusicItem> musicItemList)
    {
        int size = musicItemList.size();
        for (int index = 0; index < size; index++)
        {
            musicItemList.get(index).setSelected(false);
        }
    }

    @Override
    public void setEnabled(final boolean enabled)
    {
        handler.post(new Runnable()
        {
            @Override
            public void run()
            {
                confirm.setEnabled(enabled);
                cancel.setEnabled(enabled);
            }
        });
    }

    @Override
    public void setProgress(final int progress)
    {
        handler.post(new Runnable()
        {
            @Override
            public void run()
            {
                confirm.setProgress(progress);
            }
        });
    }

    @Override
    public void setText(final String text)
    {
        handler.post(new Runnable()
        {
            @Override
            public void run()
            {
                confirm.setText(text);
            }
        });
    }

    @Override
    public void setOnClickListener()
    {
        confirm.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent();
                intent.setClass(activity, MyDownloadActivity.class);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public void showDlResult(final String dirPath)
    {
        handler.post(new Runnable()
        {
            @Override
            public void run()
            {
                PlayCenterPresenter.showDlResultDialog(activity, dirPath);
            }
        });
    }

    @Override
    public void notifyItemChanged(int itemPosition)
    {
        EventBus.getDefault().post(new UpdateItemMessage(mCpCategoryIndex, itemPosition));
    }

    @Override
    public void showToast(String message)
    {
        loadToast.setText(message).show();
    }

    @Override
    public void toastSuccess(String message)
    {
        loadToast.setText(message).success();
    }

    @Override
    public void toastError(String message)
    {
        loadToast.setText(message).error();
    }

    public void onEventMainThread(Reach2LimitedMessage reach2LimitedMessage)
    {
        String text = "您选择的歌曲数目已达上限" + reach2LimitedMessage.limited + "首";
        Snackbar.make(confirm, text, Snackbar.LENGTH_SHORT).show();
    }
}
