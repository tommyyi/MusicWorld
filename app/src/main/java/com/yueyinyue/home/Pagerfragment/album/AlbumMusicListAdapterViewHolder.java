package com.yueyinyue.home.Pagerfragment.album;

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
import com.xk.m.databinding.MusicItemInAlbumBinding;
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

public class AlbumMusicListAdapterViewHolder extends RecyclerView.ViewHolder
{
    private final Activity mActivity;
    private final List<MusicItem> mMusicItemList;
    private final int mLimited;
    private final int mCpCategoryIndex;
    private final MusicItemInAlbumBinding mMusicItemInAlbumBinding;
    private MusicItem mMusicItem;

    public AlbumMusicListAdapterViewHolder(Activity activity, View view, List<MusicItem> musicItemList, int limited, int cpCategoryIndex)
    {
        super(view);
        mActivity = activity;
        mMusicItemList = musicItemList;
        mLimited = limited;
        mCpCategoryIndex = cpCategoryIndex;
        mMusicItemInAlbumBinding = DataBindingUtil.bind(view.findViewById(R.id.music_item_in_album));
    }

    public void setMusicContent(MusicItem musicItem)
    {
        mMusicItem = musicItem;
        mMusicItemInAlbumBinding.setClickhandler(new ClickHandler());
        mMusicItemInAlbumBinding.setMusicItem(mMusicItem);
        setUpperLayout();
        setLowerLayout();
        setBuyMusicButton();
    }

    private void setBuyMusicButton()
    {
        if (!mMusicItem.isDownloaded())
        {
            mMusicItemInAlbumBinding.buyMusics.setText(mActivity.getString(R.string.albumFee));
        }
        else
        {
            mMusicItemInAlbumBinding.buyMusics.setText("前往我的下载");
        }
    }

    private void setLowerLayout()
    {
        mMusicItemInAlbumBinding.lowerlinearlayout.setTag(mMusicItem.getMusicid());
        mMusicItemInAlbumBinding.lowerlinearlayout.setVisibility(View.GONE);
    }

    private void setUpperLayout()
    {
        mMusicItemInAlbumBinding.songname.setText(mMusicItem.getSong());
        mMusicItemInAlbumBinding.singername.setText(mMusicItem.getSinger());
        Picasso.with(mActivity).load(mMusicItem.getPicaddress()).placeholder(R.drawable.icon).error(R.drawable.icon).resizeDimen(R.dimen.songlogoimagesize, R.dimen.songlogoimagesize).centerInside().tag(mActivity).into(mMusicItemInAlbumBinding.songlogo);
    }

    public class ClickHandler
    {
        public void onShowMusicSelectDialog(View view)
        {
            new AlbumMusicSelectDialog(mActivity, mCpCategoryIndex, mMusicItemList, mLimited);
        }

        public void onGo2MyDownload(View view)
        {
            Intent intent = new Intent();
            intent.setClass(mActivity, MyDownloadActivity.class);
            mActivity.startActivity(intent);
        }

        public void onUpperLinearLayoutClick(View view)
        {
            if (mMusicItemInAlbumBinding.lowerlinearlayout.getVisibility() == View.GONE)
            {
                EventBus.getDefault().post(new ShowedViewTagMessage(mMusicItemInAlbumBinding.lowerlinearlayout, (String) mMusicItemInAlbumBinding.lowerlinearlayout.getTag()));
                mMusicItemInAlbumBinding.lowerlinearlayout.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.SlideInUp).duration(200).playOn(mMusicItemInAlbumBinding.lowerlinearlayout);
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
                        mMusicItemInAlbumBinding.lowerlinearlayout.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(com.nineoldandroids.animation.Animator animation)
                    {
                    }

                    @Override
                    public void onAnimationRepeat(com.nineoldandroids.animation.Animator animation)
                    {
                    }
                }).playOn(mMusicItemInAlbumBinding.lowerlinearlayout);
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
