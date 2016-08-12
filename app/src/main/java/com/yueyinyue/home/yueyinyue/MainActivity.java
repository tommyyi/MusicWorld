package com.yueyinyue.home.yueyinyue;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.cmsc.cmmusic.common.MusicQueryInterface;
import com.cmsc.cmmusic.common.data.ChartListRsp;
import com.cmsc.cmmusic.init.InitCmmInterface;
import com.xk.m.R;
import com.xk.m.databinding.ActivityyyyHomeBinding;
import com.yueyinyue.Model.EventBusMessage.PlayMusicMessage;
import com.yueyinyue.Model.EventBusMessage.ViewPagersLoadingSuccessMessage;
import com.yueyinyue.BaseActivity;
import com.yueyinyue.downloaded.MyDownloadActivity;
import com.yueyinyue.search.SearchActivity;

import net.steamcrafted.loadtoast.LoadToast;

import java.util.Hashtable;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends BaseActivity
{
    private CompositeSubscription mCompositeSubscription=new CompositeSubscription();
    private ActivityyyyHomeBinding mActivityyyyHomeBinding;
    private ActionBarDrawerToggle mToggle;
    private ChartListRsp mChartListRsp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mActivityyyyHomeBinding = DataBindingUtil.setContentView(this, R.layout.activityyyy_home);
        mLoadToast = new LoadToast(this).setText("").setTranslationY(100);

        mActivityyyyHomeBinding.toolbar.setTitle("");
        setSupportActionBar(mActivityyyyHomeBinding.toolbar);

        mToggle = new ActionBarDrawerToggle(this, mActivityyyyHomeBinding.drawerLayout, mActivityyyyHomeBinding.toolbar, R.string.openDrawer,R.string.closeDrawer);
        mActivityyyyHomeBinding.drawerLayout.setDrawerListener(mToggle);
        mToggle.syncState();

        InitCmmInterface.initSDK(this);
        mLoadToast.setText("咪咕用户初始化中...").show();
        mCompositeSubscription.add(Observable.create(new Observable.OnSubscribe<String>()
        {
            @Override
            public void call(Subscriber<? super String> subscriber)
            {
                Hashtable<String,String> hashTable=InitCmmInterface.initCmmEnv(getApplicationContext());
                String code=hashTable.get("code");
                if(code.equals("0"))
                {
                    subscriber.onNext(code);
                    Log.i("USER INITIALIZATION","SUCCESS");
                }
                else
                {
                    subscriber.onError(null);
                    Log.i("USER INITIALIZATION","FAILED");
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>()
        {
            @Override
            public void onCompleted()
            {

            }

            @Override
            public void onError(Throwable e)
            {
                mLoadToast.error();
            }

            @Override
            public void onNext(String s)
            {
                mLoadToast.success();

                mLoadToast.setText("正在获取榜单").show();
                loadCategoryInfo();
            }
        }));
    }

    private void loadCategoryInfo()
    {
        mCompositeSubscription.add(Observable.create(new Observable.OnSubscribe<ChartListRsp>()
        {
            @Override
            public void call(Subscriber<? super ChartListRsp> subscriber)
            {
                mChartListRsp = MusicQueryInterface.getChartInfo(getApplicationContext(), 1, 20);
                if (mChartListRsp ==null|| mChartListRsp.getResCode()==null|| !mChartListRsp.getResCode().equals("000000"))
                    subscriber.onError(null);
                else
                    subscriber.onNext(mChartListRsp);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<ChartListRsp>()
        {
            @Override
            public void onCompleted()
            {

            }

            @Override
            public void onError(Throwable e)
            {
                mLoadToast.error();
            }

            @Override
            public void onNext(ChartListRsp chartListRsp)
            {
                mLoadToast.success();
                showFragment(chartListRsp);
            }
        }));
    }

    @Override
    protected void onDestroy()
    {
        setContentView(R.layout.empty);
        InitCmmInterface.exitApp(getApplicationContext());
        mActivityyyyHomeBinding =null;
        mCompositeSubscription.unsubscribe();
        mChartListRsp=null;
        mPlayCenterBinding =null;
        mToggle=null;
        mLoadToast.destroy();
        mLoadToast=null;
        super.onDestroy();

        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public void onEventMainThread(ViewPagersLoadingSuccessMessage viewPagersLoadingSuccessMessage)
    {
        mLoadToast.success();
    }

    public void showFragment(final ChartListRsp chartListRsp)
    {
        mLoadToast.setText("正在加载音乐").show();
        mActivityyyyHomeBinding.viewpager.setAdapter((new MyFragmentStatePagerAdapter(getApplicationContext(),getSupportFragmentManager(), chartListRsp)));
        mActivityyyyHomeBinding.tablayout.setupWithViewPager(mActivityyyyHomeBinding.viewpager);
        mActivityyyyHomeBinding.viewpager.setOffscreenPageLimit(mActivityyyyHomeBinding.viewpager.getAdapter().getCount());
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState)
    {
        mToggle.syncState();
        super.onPostCreate(savedInstanceState, persistentState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return mToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    public void onGo2dlActivity(MenuItem item)
    {
        startActivity(new Intent(this, MyDownloadActivity.class));
    }

    public void onSearchMusicClick(View view)
    {
        startActivity(new Intent(this, SearchActivity.class));
    }
}
