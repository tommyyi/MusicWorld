package com.yueyinyue.playcenter;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xk.m.databinding.PlaycenterBinding;
import com.yueyinyue.Model.EventBusMessage.PlayMusicMessage;
import com.xk.m.R;

import java.io.File;

import me.drakeet.materialdialog.MaterialDialog;
import nl.changer.audiowife.AudioWife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/1/17.
 */
public class PlayCenterPresenter implements PlayCenterPresenterImp
{
    Activity mActivity;
    private PlaycenterBinding mPlaycenterBinding;

    private AudioWife mAudioWife;
    boolean mIsPlaying=false;
    PlayCenterViewImp mPlayCenterViewImp;

    public PlayCenterPresenter(Activity context,PlayCenterViewImp playCenterViewImp,Object object)
    {
        mActivity=context;
        mPlayCenterViewImp = playCenterViewImp;
        mPlaycenterBinding=(PlaycenterBinding)object;
    }

    @Override
    public void playMusic(PlayMusicMessage playMusicMessage)
    {
        if(playMusicMessage.url==null&&playMusicMessage.musicFile==null)
        {
            Toast.makeText(mActivity,"音乐不存在",Toast.LENGTH_LONG).show();
            return;
        }
        mPlaycenterBinding.songname.setText(playMusicMessage.mSongname);
        if(mAudioWife ==null)
        {
            mAudioWife = new AudioWife();
            mAudioWife.init(mActivity);
        }
        else
        {
            mAudioWife.pause();
        }

        Uri uri;
        if (playMusicMessage.isFullSongListen)
            uri = Uri.fromFile(playMusicMessage.musicFile);
        else
            uri = Uri.parse(playMusicMessage.url);

        mAudioWife.setUri(uri);
        mPlayCenterViewImp.showMessage("正在加载音乐");
        Observable.create(new Observable.OnSubscribe<String>()
        {
            @Override
            public void call(Subscriber<? super String> subscriber)
            {
                mAudioWife.initPlayer(mActivity);
                subscriber.onNext("initiate view");
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<String>()
        {
            @Override
            public void call(String s)
            {
                mAudioWife.setPlayView(mPlaycenterBinding.play).setPauseView(mPlaycenterBinding.pause).setSeekBar(mPlaycenterBinding.mediaSeekbar).setRuntimeView(mPlaycenterBinding.runTime).setTotalTimeView(mPlaycenterBinding.totalTime);
                mAudioWife.addOnCompletionListener(new MediaPlayer.OnCompletionListener()
                {
                    @Override
                    public void onCompletion(MediaPlayer mp)
                    {
                    }
                });
                mAudioWife.addOnPlayClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                    }
                });
                mAudioWife.addOnPauseClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                    }
                });
                mAudioWife.play();
                mPlayCenterViewImp.showSuccess();
            }
        });
    }

    public static String getDirPath()
    {
        String dirPath = android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + "/yueyinyue";
        File dir = new File(dirPath);
        if (!dir.exists())
            dir.mkdirs();
        return dirPath;
    }

    public static void showDlResultDialog(Activity activity, String result)
    {
        final MaterialDialog mMaterialDialog = new MaterialDialog(activity);
        mMaterialDialog.setTitle("下载结果");
        mMaterialDialog.setPositiveButton("关闭", new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mMaterialDialog.dismiss();
            }
        });
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_download_result, null, false);
        mMaterialDialog.setContentView(view);
        TextView textView = (TextView)view.findViewById(R.id.downloadResult);
        textView.setText("您下载的音乐存放位置为："+result);
        mMaterialDialog.show();
    }

    @Override
    public void onResume()
    {
        if(mAudioWife !=null&&mIsPlaying)
        {
            mAudioWife.play();
            mIsPlaying=true;
        }
    }

    @Override
    public void onPause()
    {
        if(mAudioWife !=null)
        {
            mIsPlaying= mAudioWife.getMediaPlayer().isPlaying();
            mAudioWife.pause();
        }
    }

    @Override
    public void onDestroy()
    {
        if(mAudioWife !=null)
            mAudioWife.release();
    }
}
