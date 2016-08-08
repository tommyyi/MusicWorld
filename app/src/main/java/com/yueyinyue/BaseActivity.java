package com.yueyinyue;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;

import com.xk.m.R;
import com.yueyinyue.Model.EventBusMessage.PlayMusicMessage;
import com.yueyinyue.Model.EventBusMessage.SetRingtoneMessage;
import com.yueyinyue.playcenter.PlayCenterPresenter;
import com.yueyinyue.playcenter.PlayCenterPresenterImp;
import com.yueyinyue.playcenter.PlayCenterViewImp;

import net.steamcrafted.loadtoast.LoadToast;

import de.greenrobot.event.EventBus;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public abstract class BaseActivity extends AppCompatActivity implements PlayCenterViewImp
{
    public Object mPlayCenterBinding;

    public LoadToast mLoadToast;
    private PlayCenterPresenterImp mPlayCenterPresenterImp;
    private SetRingtoneMessage mSetRingtoneMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 1&&resultCode == RESULT_OK)
        {
            final VibrateRing vibrateRing = new VibrateRing(this, intent, mSetRingtoneMessage);
            assert vibrateRing.getCursor()!=null;
            Observable.create(new Observable.OnSubscribe<String>()
            {
                @Override
                public void call(Subscriber<? super String> subscriber)
                {
                    if (vibrateRing.set())
                    {
                        String result = "已将" + vibrateRing.getContactName() + "的振铃设置为" + mSetRingtoneMessage.title;
                        subscriber.onNext(result);
                    }
                    else
                    {
                        subscriber.onNext("设置振铃失败");
                    }
                }
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<String>()
            {
                @Override
                public void call(String result)
                {
                    vibrateRing.showVibrateRingResultDialog(result);
                }
            });
        }
    }

    public void onEventMainThread(SetRingtoneMessage setRingtoneMessage)
    {
        this.mSetRingtoneMessage = setRingtoneMessage;
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    public void onEventMainThread(PlayMusicMessage playMusicMessage)
    {
        mPlayCenterPresenterImp.playMusic(playMusicMessage);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if(mPlayCenterBinding==null)
            mPlayCenterBinding = DataBindingUtil.bind(findViewById(R.id.playcenter));
        if(mPlayCenterPresenterImp==null)
            mPlayCenterPresenterImp = new PlayCenterPresenter(this, this, mPlayCenterBinding);

        mPlayCenterPresenterImp.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        mPlayCenterPresenterImp.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mPlayCenterPresenterImp.onDestroy();
    }

    @Override
    public void showMessage(String text)
    {
        mLoadToast.setText(text).setTranslationY(100).show();
    }

    @Override
    public void showSuccess()
    {
        mLoadToast.success();
    }
}
