package com.yueyinyue.home.Pagerfragment;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.cmsc.cmmusic.common.CMMusicCallback;
import com.cmsc.cmmusic.common.FullSongManagerInterface;
import com.cmsc.cmmusic.common.VibrateRingManagerInterface;
import com.cmsc.cmmusic.common.data.DownloadResult;
import com.cmsc.cmmusic.common.data.OrderResult;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.Animator;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerButton;
import com.xk.m.R;
import com.xk.m.databinding.DialogRingbuyBinding;
import com.xk.m.databinding.MusicItemBinding;
import com.yueyinyue.Model.EventBusMessage.PlayMusicMessage;
import com.yueyinyue.Model.EventBusMessage.SetRingtoneMessage;
import com.yueyinyue.Model.EventBusMessage.ShowedViewTagMessage;
import com.yueyinyue.Model.dao.MusicDl.MusicDlRecord;
import com.yueyinyue.home.Pagerfragment.Model.DownloadFullSongModel;
import com.yueyinyue.home.Pagerfragment.Model.DownloadVibrateRingModel;
import com.yueyinyue.home.Pagerfragment.Model.MusicDlRecordModelImp;
import com.yueyinyue.playcenter.PlayCenterPresenter;

import java.io.File;

import de.greenrobot.event.EventBus;
import me.drakeet.materialdialog.MaterialDialog;

public class MusicItemHandler implements ViewCardContentUpdateImp, ViewButtonImp, ViewDlResultImp
{
    Activity mActivity;
    MusicItemBinding mCardContentBinding;
    MusicDlRecordModelImp mMusicDlRecordModelImp;

    public MusicItemHandler(Activity activity, MusicItemBinding cardContentBinding, MusicDlRecordModelImp musicDlRecordModelImp)
    {
        mActivity = activity;
        this.mCardContentBinding = cardContentBinding;
        this.mMusicDlRecordModelImp = musicDlRecordModelImp;
    }

    public void onUpperLinearLayout(View view)
    {
        if (mCardContentBinding.lowerlinearlayout.getVisibility() == View.GONE)
        {
            EventBus.getDefault().post(new ShowedViewTagMessage(mCardContentBinding.lowerlinearlayout, (String) mCardContentBinding.lowerlinearlayout.getTag()));
            mCardContentBinding.lowerlinearlayout.setVisibility(View.VISIBLE);
            YoYo.with(Techniques.SlideInUp).duration(200).playOn(mCardContentBinding.lowerlinearlayout);
        }
        else
        {
            YoYo.with(Techniques.FadeOut).duration(200).withListener(new Animator.AnimatorListener()
            {
                @Override
                public void onAnimationStart(Animator animation)
                {
                }

                @Override
                public void onAnimationEnd(Animator animation)
                {
                    mCardContentBinding.lowerlinearlayout.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animation)
                {
                }

                @Override
                public void onAnimationRepeat(Animator animation)
                {
                }
            }).playOn(mCardContentBinding.lowerlinearlayout);
        }
    }

    public void onTryListenButton(View view)
    {
        EventBus.getDefault().post(new PlayMusicMessage(mCardContentBinding.getMusicInfo().getRingListenDir(),mCardContentBinding.getMusicInfo().getSongName()));
    }

    public void onsetColorRingButton(View view)
    {
        final MaterialDialog mMaterialDialog = new MaterialDialog(mActivity);
        final Shimmer shimmer = new Shimmer();
        mMaterialDialog.setTitle("音乐商店");
        mMaterialDialog.setPositiveButton("关闭", new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                shimmer.cancel();
                mMaterialDialog.dismiss();
            }
        });

        View dialogView = LayoutInflater.from(mActivity).inflate(R.layout.dialog_ringbuy, null, false);
        DialogRingbuyBinding dialogRingbuyBinding = DialogRingbuyBinding.bind(dialogView);
        DialogHandler handler = new DialogHandler(mActivity, mMaterialDialog, mCardContentBinding.getMusicInfo().getMusicId());
        dialogRingbuyBinding.setHandlers(handler);
        mMaterialDialog.setContentView(dialogView);

        ShimmerButton openringback = (ShimmerButton) dialogView.findViewById(R.id.openringback);
        ShimmerButton orderRing = (ShimmerButton) dialogView.findViewById(R.id.orderring);
        ShimmerButton ownRingMonth = (ShimmerButton) dialogView.findViewById(R.id.own_ring_month);

        shimmer.setRepeatCount(1).setDuration(1000).setStartDelay(300).setDirection(Shimmer.ANIMATION_DIRECTION_RTL);
        shimmer.start(openringback);
        shimmer.start(orderRing);
        shimmer.start(ownRingMonth);

        mMaterialDialog.show();
    }

    public void onPlayFullSong(View view)
    {
        EventBus.getDefault().post(new PlayMusicMessage(new File(mCardContentBinding.getMusicDlRecord().getAbsoluteDir(), mCardContentBinding.getMusicDlRecord().getFullSongFileName()), true,mCardContentBinding.getMusicInfo().getSongName()));
    }

    public void onFullDownload(View view)
    {
        FullSongManagerInterface.getFullSongDownloadUrl(mActivity, mCardContentBinding.getMusicInfo().getMusicId(), new CMMusicCallback<OrderResult>()
        {
            @Override
            public void operationResult(final OrderResult downloadResult)
            {
                if (null != downloadResult)
                {
                    final String musicUrl = downloadResult.getDownUrl();
                    if (musicUrl != null && !musicUrl.equals(""))
                    {
                        String dirPath = PlayCenterPresenter.getDirPath();

                        MusicItemHandler handler = MusicItemHandler.this;
                        new DownloadFullSongModel(mActivity.getApplicationContext(), mCardContentBinding.getMusicInfo(), musicUrl, dirPath, mMusicDlRecordModelImp, handler, handler, handler).download();
                    }
                    else
                    {
                        urlIsNotReady();
                    }
                }
                else
                {
                    urlIsNotReady();
                }
            }

            private void urlIsNotReady()
            {
                new AlertDialog.Builder(mActivity).setTitle("全曲下载结果").setMessage("没有获得下载信息").setPositiveButton("确认", null).show();
                mCardContentBinding.fulldownloadButton.setEnabled(true);
                mCardContentBinding.fulldownloadButton.setText(mActivity.getText(R.string.fulldownload));
            }
        });
    }

    public void onSetVibrateRing(View view)
    {
        File file = new File(mCardContentBinding.getMusicDlRecord().getAbsoluteDir(), mCardContentBinding.getMusicDlRecord().getVibrateRingFileName());
        String songName = mCardContentBinding.getMusicDlRecord().getSongName();
        String singerName = mCardContentBinding.getMusicDlRecord().getSinger();
        EventBus.getDefault().post(new SetRingtoneMessage(file, songName, singerName));
    }

    public void onDownloadVibrateRing(View view)
    {
        VibrateRingManagerInterface.queryVibrateRingDownloadUrl(mActivity, mCardContentBinding.getMusicInfo().getMusicId(), new CMMusicCallback<OrderResult>()
        {
            @Override
            public void operationResult(OrderResult downloadResult)
            {
                if (null != downloadResult)
                {
                    final String musicUrl = downloadResult.getDownUrl();
                    if (musicUrl != null && !musicUrl.equals(""))
                    {
                        String dirPath = PlayCenterPresenter.getDirPath();

                        MusicItemHandler handler = MusicItemHandler.this;
                        new DownloadVibrateRingModel(mActivity, mCardContentBinding.getMusicInfo(), musicUrl, dirPath, mMusicDlRecordModelImp, handler, handler, handler).download();
                    }
                    else
                    {
                        urlIsNotReady();
                    }
                }
                else
                {
                    urlIsNotReady();
                }
            }

            private void urlIsNotReady()
            {
                new AlertDialog.Builder(mActivity).setTitle("振铃下载结果").setMessage("没有获得下载信息").setPositiveButton("确认", null).show();
                mCardContentBinding.setvibrateringButton.setText(mActivity.getText(R.string.vibrateRingDownload));
                mCardContentBinding.setvibrateringButton.setEnabled(true);
            }
        });
    }

    @Override
    public void updateCard()
    {
        final MusicDlRecord musicDlRecord = mMusicDlRecordModelImp.getMusicDlRecord(mCardContentBinding.getMusicInfo().getMusicId());
        mActivity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                mCardContentBinding.setMusicDlRecord(musicDlRecord);
            }
        });
    }

    @Override
    public void setFullSongDownloadButtonBeforeDl()
    {
        mActivity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                mCardContentBinding.fulldownloadButton.setProgress(0);
                mCardContentBinding.fulldownloadButton.setEnabled(false);
            }
        });
    }

    @Override
    public void setFullSongDownloadButtonDuringDl(final int progress)
    {
        mActivity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                mCardContentBinding.fulldownloadButton.setProgress(progress);
            }
        });
    }

    @Override
    public void setFullSongDownloadButtonAfterDl()
    {
        mActivity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                mCardContentBinding.fulldownloadButton.setProgress(0);
                mCardContentBinding.fulldownloadButton.setEnabled(true);
            }
        });
    }

    @Override
    public void setVibrateRingDownloadButtonBeforeDl()
    {
        mActivity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                mCardContentBinding.setvibrateringButton.setProgress(0);
                mCardContentBinding.setvibrateringButton.setEnabled(false);
            }
        });
    }

    @Override
    public void setVibrateRingDownloadButtonDuringDl(final int progress)
    {
        mActivity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                mCardContentBinding.setvibrateringButton.setProgress(progress);
            }
        });
    }

    @Override
    public void setVibrateRingDownloadButtonAfterDl()
    {
        mActivity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                mCardContentBinding.setvibrateringButton.setProgress(0);
                mCardContentBinding.setvibrateringButton.setEnabled(true);
            }
        });
    }

    @Override
    public void showResult(final File file)
    {
        mActivity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                PlayCenterPresenter.showDlResultDialog(mActivity, file.getAbsolutePath());
            }
        });
    }
}
