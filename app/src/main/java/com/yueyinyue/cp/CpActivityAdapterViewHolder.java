package com.yueyinyue.cp;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cmsc.cmmusic.common.RingbackManagerInterface;
import com.cmsc.cmmusic.common.data.CrbtPrelistenRsp;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;
import com.xk.m.R;
import com.xk.m.databinding.MusicItemInCpActivityBinding;
import com.yueyinyue.Model.Category;
import com.yueyinyue.Model.EventBusMessage.PlayMusicMessage;
import com.yueyinyue.Model.EventBusMessage.ShowedViewTagMessage;
import com.yueyinyue.Model.MusicItem;
import com.yueyinyue.cp.dialog.MusicSelectDialog;
import com.yueyinyue.downloaded.MyDownloadActivity;

import java.util.List;
import java.util.Locale;

import de.greenrobot.event.EventBus;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class CpActivityAdapterViewHolder extends RecyclerView.ViewHolder
{
    private final Activity mActivity;
    private final List<MusicItem> mMusicItemList;
    private final int mLimited;
    private final int mCpCategoryIndex;
    private final MusicItemInCpActivityBinding mMusicItemInCpActivityBinding;
    private MusicItem mMusicItem;

    public CpActivityAdapterViewHolder(Activity activity, View view, List<MusicItem> musicItemList, int limited, int cpCategoryIndex)
    {
        super(view);
        mActivity = activity;
        mMusicItemList = musicItemList;
        mLimited = limited;
        mCpCategoryIndex = cpCategoryIndex;
        mMusicItemInCpActivityBinding = DataBindingUtil.bind(view.findViewById(R.id.music_item_in_cp_activity));
    }

    public void setMusicContent(MusicItem musicItem)
    {
        mMusicItem = musicItem;
        mMusicItemInCpActivityBinding.setClickhandler(new ClickHandler());
        mMusicItemInCpActivityBinding.setMusicItem(mMusicItem);
        setUpperLayout();
        setLowerLayout();
        setBuyMusicButton();
    }

    private void setBuyMusicButton()
    {
        if (!mMusicItem.isDownloaded())
        {
            mMusicItemInCpActivityBinding.buyMusics.setText(String.format(Locale.CHINA, "%d元任意%d首", Category.getPrice(mActivity.getApplicationContext(),mCpCategoryIndex), mLimited));
        }
        else
        {
            mMusicItemInCpActivityBinding.buyMusics.setText("前往我的下载");
        }
    }

    private void setLowerLayout()
    {
        mMusicItemInCpActivityBinding.lowerlinearlayout.setTag(mMusicItem.getMusicid());
        mMusicItemInCpActivityBinding.lowerlinearlayout.setVisibility(View.GONE);
    }

    private void setUpperLayout()
    {
        mMusicItemInCpActivityBinding.songname.setText(mMusicItem.getSong());
        mMusicItemInCpActivityBinding.singername.setText(mMusicItem.getSinger());
        Picasso.with(mActivity).load(mMusicItem.getPicaddress()).placeholder(R.drawable.icon).error(R.drawable.icon).resizeDimen(R.dimen.songlogoimagesize, R.dimen.songlogoimagesize).centerInside().tag(mActivity).into(mMusicItemInCpActivityBinding.songlogo);
    }

    public class ClickHandler
    {
        public void onShowMusicSelectDialog(View view)
        {
            new MusicSelectDialog(mActivity, mCpCategoryIndex, mMusicItemList, mLimited);
        }

        public void onGo2MyDownload(View view)
        {
            Intent intent = new Intent();
            intent.setClass(mActivity, MyDownloadActivity.class);
            mActivity.startActivity(intent);
        }

        public void onUpperLinearLayoutClick(View view)
        {
            if (mMusicItemInCpActivityBinding.lowerlinearlayout.getVisibility() == View.GONE)
            {
                EventBus.getDefault().post(new ShowedViewTagMessage(mMusicItemInCpActivityBinding.lowerlinearlayout, (String) mMusicItemInCpActivityBinding.lowerlinearlayout.getTag()));
                mMusicItemInCpActivityBinding.lowerlinearlayout.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.SlideInUp).duration(200).playOn(mMusicItemInCpActivityBinding.lowerlinearlayout);
            }
            else
            {
                YoYo.with(Techniques.FadeOut).duration(200).withListener(new com.nineoldandroids.animation.Animator.AnimatorListener()
                {
                    @Override
                    public void onAnimationStart(com.nineoldandroids.animation.Animator animation)
                    {
                    }

                    @Override
                    public void onAnimationEnd(com.nineoldandroids.animation.Animator animation)
                    {
                        mMusicItemInCpActivityBinding.lowerlinearlayout.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(com.nineoldandroids.animation.Animator animation)
                    {
                    }

                    @Override
                    public void onAnimationRepeat(com.nineoldandroids.animation.Animator animation)
                    {
                    }
                }).playOn(mMusicItemInCpActivityBinding.lowerlinearlayout);
            }
        }

        public void onTryListenClick(View view)
        {
            String musicAddress = mMusicItem.getMusicaddress();
            if (musicAddress != null && !musicAddress.equals("") && !musicAddress.equals("null"))
            {
                EventBus.getDefault().post(new PlayMusicMessage(mMusicItem.getMusicaddress(), mMusicItem.getSong()));
            }
            else
            {
                Observable.create(new Observable.OnSubscribe<String>()
                {
                    @Override
                    public void call(Subscriber<? super String> subscriber)
                    {
                        CrbtPrelistenRsp crbtPrelistenRsp = RingbackManagerInterface.getCrbtPrelisten(mActivity, mMusicItem.getMusicid());
                        subscriber.onNext(crbtPrelistenRsp.getStreamUrl());
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<String>()
                {
                    @Override
                    public void call(String musicAddress)
                    {
                        EventBus.getDefault().post(new PlayMusicMessage(musicAddress, mMusicItem.getSong()));
                    }
                });
            }
        }
    }
}
