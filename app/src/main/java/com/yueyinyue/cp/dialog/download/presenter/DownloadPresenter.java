package com.yueyinyue.cp.dialog.download.presenter;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;

import com.cmsc.cmmusic.common.CMMusicCallback;
import com.cmsc.cmmusic.common.CPManagerInterface;
import com.cmsc.cmmusic.common.DigitalAlbumManagerInterface;
import com.cmsc.cmmusic.common.ExclusiveManagerInterface;
import com.cmsc.cmmusic.common.data.OrderResult;
import com.cmsc.cmmusic.init.InitCmmInterface;
import com.yueyinyue.Model.Category;
import com.yueyinyue.cp.dialog.download.model.DownloadModel;
import com.yueyinyue.cp.dialog.DownloadViewImpl;
import com.yueyinyue.playcenter.PlayCenterPresenter;
import com.yueyinyue.cp.dialog.download.model.DownloadModelImpl;
import com.yueyinyue.Model.MusicItem;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Hashtable;
import java.util.List;

import cn.aigestudio.downloader.bizs.DLManager;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class DownloadPresenter implements DownloadPresenterImpl
{
    protected static final String ACTION = "checkisover";
    protected static final int OUTOFINDEX = -1;
    private static final String PAY_BY_PATCH = "payByPatch";
    protected final Activity mActivity;
    protected final DownloadViewImpl downloadViewImpl;
    protected final DownloadModelImpl downloadModelImpl;
    protected final List<MusicItem> mMusicItemList;
    protected String mServiceId = "";
    protected int mCount2Selected = 0;
    protected int mCount2Addressed = 0;
    protected int mCountAddressNotExist = 0;

    public DownloadPresenter(Activity activity, String serviceId, DownloadViewImpl downloadViewImpl, List<MusicItem> musicItemList)
    {
        this.mActivity = activity;
        this.mServiceId= serviceId;
        this.downloadViewImpl = downloadViewImpl;
        this.downloadModelImpl = new DownloadModel(activity);
        this.mMusicItemList = musicItemList;
    }

    protected void handleMusicDownload()
    {
        BroadcastReceiver broadcastReceiver = new DlCmdBroadcastReceiver(this, mCount2Addressed);
        mActivity.registerReceiver(broadcastReceiver, new IntentFilter(ACTION));

        int index = locateNextAddressedIndex(0);
        Intent intent = new Intent(ACTION);
        intent.putExtra("index", index);
        mActivity.sendBroadcast(intent);
    }

    protected int locateNextAddressedIndex(int beginIndex)
    {
        int size = mMusicItemList.size();
        for (int index = beginIndex; index < size; index++)
        {
            if (mMusicItemList.get(index).isSelected())
            {
                String musicAddress = mMusicItemList.get((index)).getMusicaddress();
                if (musicAddress != null && !musicAddress.equals("") && !musicAddress.equals("null"))
                {
                    return index;
                }
            }
        }
        return OUTOFINDEX;
    }

    @Override
    public void handleAlbumBuy()
    {
        mCount2Selected = getTotalSelect(mMusicItemList);
        mCount2Addressed = 0;
        mCountAddressNotExist = 0;

        if (mCount2Selected == 0 || mServiceId.equals(""))
        {
            return;
        }

        Observable.create(new Observable.OnSubscribe<Boolean>()
        {
            @Override
            public void call(Subscriber<? super Boolean> subscriber)
            {
                subscriber.onNext(InitCmmInterface.initCheck(mActivity));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Boolean>()
        {
            @Override
            public void call(Boolean isInitiateOK)
            {
                if (isInitiateOK)
                {
                    Log.i("USER STATUS", "OK");
                    payByAlbum(mServiceId);
                }
                else
                {
                    Log.i("USER STATUS", "NOT OK");
                    initiateUserThenPayByAlbum(mServiceId);
                }
            }
        });
    }

    @Override
    public void handleCPMonthBuy()
    {
        mCount2Selected = getTotalSelect(mMusicItemList);
        mCount2Addressed = 0;
        mCountAddressNotExist = 0;

        if (mCount2Selected == 0 || mServiceId.equals(""))
        {
            return;
        }

        Observable.create(new Observable.OnSubscribe<Boolean>()
        {
            @Override
            public void call(Subscriber<? super Boolean> subscriber)
            {
                subscriber.onNext(InitCmmInterface.initCheck(mActivity));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Boolean>()
        {
            @Override
            public void call(Boolean isInitiateOK)
            {
                if (isInitiateOK)
                {
                    Log.i("USER STATUS", "OK");
                    payByMonth(mServiceId);
                }
                else
                {
                    Log.i("USER STATUS", "NOT OK");
                    initiateUserThenPayByMonth(mServiceId);
                }
            }
        });
    }

    @Override
    public void handleCPPatchBuy()
    {
        mCount2Selected = getTotalSelect(mMusicItemList);
        mCount2Addressed = 0;
        mCountAddressNotExist = 0;

        if (mCount2Selected == 0 || mServiceId.equals(""))
        {
            return;
        }

        Observable.create(new Observable.OnSubscribe<Boolean>()
        {
            @Override
            public void call(Subscriber<? super Boolean> subscriber)
            {
                subscriber.onNext(InitCmmInterface.initCheck(mActivity));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Boolean>()
        {
            @Override
            public void call(Boolean isInitiateOK)
            {
                if (isInitiateOK)
                {
                    Log.i("USER STATUS", "OK");
                    payByPatch(mServiceId);
                }
                else
                {
                    Log.i("USER STATUS", "NOT OK");
                    initiateUserThenPayByPatch(mServiceId);
                }
            }
        });
    }

    private void setAddress()
    {
        mCount2Addressed = mCount2Selected;

        int size = mMusicItemList.size();
        for (int index = 0; index < size; index++)
        {
            if (mMusicItemList.get(index).isSelected())
            {
                String musicAddress = null;
                try
                {
                    musicAddress = "http://musicd.tianyigames.com/" + URLEncoder.encode(mMusicItemList.get(index).getSong(), "utf-8")/* + ".mp4"*/;
                }
                catch (UnsupportedEncodingException e)
                {
                    e.printStackTrace();
                }
                mMusicItemList.get(index).setMusicaddress(musicAddress);
            }
        }
    }

    private void initiateUserThenPayByMonth(final String serviceId)
    {
        Observable.create(new Observable.OnSubscribe<String>()
        {
            @Override
            public void call(Subscriber<? super String> subscriber)
            {
                Hashtable<String, String> hashTable = InitCmmInterface.initCmmEnv(mActivity);
                String code = hashTable.get("code");
                if (code.equals("0"))
                {
                    subscriber.onNext(serviceId);
                    Log.i("USER INITIALIZATION", "SUCCESS");
                }
                else
                {
                    Log.i("USER INITIALIZATION", "FAILED");
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<String>()
        {
            @Override
            public void call(String serviceId)
            {
                payByMonth(serviceId);
            }
        });
    }

    private void initiateUserThenPayByAlbum(final String serviceId)
    {
        Observable.create(new Observable.OnSubscribe<String>()
        {
            @Override
            public void call(Subscriber<? super String> subscriber)
            {
                Hashtable<String, String> hashTable = InitCmmInterface.initCmmEnv(mActivity);
                String code = hashTable.get("code");
                if (code.equals("0"))
                {
                    subscriber.onNext(serviceId);
                    Log.i("USER INITIALIZATION", "SUCCESS");
                }
                else
                {
                    Log.i("USER INITIALIZATION", "FAILED");
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<String>()
        {
            @Override
            public void call(String serviceId)
            {
                payByAlbum(serviceId);
            }
        });
    }

    private void initiateUserThenPayByPatch(final String serviceId)
    {
        Observable.create(new Observable.OnSubscribe<String>()
        {
            @Override
            public void call(Subscriber<? super String> subscriber)
            {
                Hashtable<String, String> hashTable = InitCmmInterface.initCmmEnv(mActivity);
                String code = hashTable.get("code");
                if (code.equals("0"))
                {
                    subscriber.onNext(serviceId);
                    Log.i("USER INITIALIZATION", "SUCCESS");
                }
                else
                {
                    Log.i("USER INITIALIZATION", "FAILED");
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<String>()
        {
            @Override
            public void call(String serviceId)
            {
                payByPatch(serviceId);
            }
        });
    }

    private void payByPatch(final String serviceId)
    {
        ExclusiveManagerInterface.exclusiveOnce(mActivity, serviceId, "999",new CMMusicCallback<OrderResult>()
        {
            @Override
            public void operationResult(OrderResult result)
            {
                if (result == null)
                {
                    Log.e(PAY_BY_PATCH,"result="+result);
                    Toast.makeText(mActivity.getApplicationContext(),"无结果",Toast.LENGTH_LONG).show();
                    return;
                }
                Log.e(PAY_BY_PATCH,"result code="+result.getResultCode());
                Log.e(PAY_BY_PATCH,"result msg="+result.getResMsg());

                int resultCode = result.getResultCode();
                if (resultCode == 1)
                {
                    setAddress();
                    handleMusicDownload();
                    //fillInDlAddress();
                }
                else
                {
                    Toast.makeText(mActivity.getApplicationContext(),result.getResMsg(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void payByAlbum(final String serviceId)
    {
        DigitalAlbumManagerInterface.orderDigitalAlbum(mActivity, serviceId, new CMMusicCallback<OrderResult>()
        {
            @Override
            public void operationResult(OrderResult result)
            {
                if (result == null)
                {
                    Toast.makeText(mActivity,"无结果返回",Toast.LENGTH_LONG).show();
                    return;
                }

                int resultCode = result.getResultCode();
                if (resultCode == 1)
                {
                    fillInDlAddress();
                }
                else
                {
                    Toast.makeText(mActivity,result.getResMsg(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void payByMonth(final String serviceId)
    {
        CPManagerInterface.openCPMonth(mActivity, serviceId, null, new CMMusicCallback<OrderResult>()
        {
            @Override
            public void operationResult(OrderResult result)
            {
                if (result == null)
                {
                    Toast.makeText(mActivity,"无结果返回",Toast.LENGTH_LONG).show();
                    return;
                }

                int resultCode = result.getResultCode();
                if (resultCode == 1)
                {
                    fillInDlAddress();
                }
                else
                {
                    Toast.makeText(mActivity,result.getResMsg(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void fillInDlAddress()
    {
        BroadcastReceiver broadcastReceiver = new FindAddressBroadcastReceiver(this, mCount2Selected);
        mActivity.registerReceiver(broadcastReceiver, new IntentFilter(ACTION));

        int index = locateNextSelected(0);
        Intent intent = new Intent(ACTION);
        intent.putExtra("index", index);
        mActivity.sendBroadcast(intent);
    }

    protected int locateNextSelected(int beginIndex)
    {
        for (int index = beginIndex; index < mMusicItemList.size(); index++)
        {
            if (mMusicItemList.get(index).isSelected())
            {
                return index;
            }
        }
        return OUTOFINDEX;
    }

    protected void downThisOne(final int index)
    {
        final String dirPath = PlayCenterPresenter.getDirPath();
        final String musicId = mMusicItemList.get(index).getMusicid();
        String dlUrl = mMusicItemList.get(index).getMusicaddress();
        String picUrl = mMusicItemList.get(index).getPicaddress();
        String songName = mMusicItemList.get(index).getSong();
        String singer = mMusicItemList.get(index).getSinger();
        downloadModelImpl.addMusicDlRecord(musicId, mMusicItemList.get(index).getMusicaddress(), dirPath, picUrl, songName, singer);

        DLManager.getInstance(mActivity).dlStart(dlUrl, dirPath, new MyIDListener(this, index, musicId, dirPath));
    }

    private int getTotalSelect(List<MusicItem> musicItemList)
    {
        int total = 0;
        for (int index = 0; index < musicItemList.size(); index++)
        {
            if (musicItemList.get(index).isSelected())
            {
                total++;
            }
        }
        return total;
    }

}
