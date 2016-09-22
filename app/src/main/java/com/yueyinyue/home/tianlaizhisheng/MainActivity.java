package com.yueyinyue.home.tianlaizhisheng;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.cmsc.cmmusic.common.MusicQueryInterface;
import com.cmsc.cmmusic.common.data.ChartListRsp;
import com.cmsc.cmmusic.init.InitCmmInterface;
import com.xk.m.R;
import com.xk.m.databinding.ActivityjltHomeBinding;
import com.yueyinyue.BaseActivity;
import com.yueyinyue.Model.EventBusMessage.ViewPagersLoadingSuccessMessage;
import com.yueyinyue.downloaded.MyDownloadActivity;
import com.yueyinyue.search.SearchActivity;

import net.steamcrafted.loadtoast.LoadToast;

import java.util.Hashtable;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 *
 */
public class MainActivity extends BaseActivity
{
    private CompositeSubscription mCompositeSubscription=new CompositeSubscription();
    private ActivityjltHomeBinding mActivityjltHomeBinding;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mDrawerToggle;

    private ChartListRsp mChartListRsp;

    public void onGo2dlActivity(View view)
    {
        startActivity(new Intent(this, MyDownloadActivity.class));
    }

    public void onSearchMusicClick(View view)
    {
        startActivity(new Intent(this, SearchActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setTheme(R.style.MyAppTheme);

        super.onCreate(savedInstanceState);
        mActivityjltHomeBinding = DataBindingUtil.setContentView(this,R.layout.activityjlt_home);
        mLoadToast = new LoadToast(this).setText("").setTranslationY(100);

        initiateUI();
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

    private void initiateUI()
    {
        mToolbar = mActivityjltHomeBinding.materialViewPager.getToolbar();
        if (mToolbar != null)
        {
            setSupportActionBar(mToolbar);

            final ActionBar actionBar = getSupportActionBar();
            if (actionBar != null)
            {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayShowTitleEnabled(true);
                actionBar.setDisplayUseLogoEnabled(false);
                actionBar.setHomeButtonEnabled(true);
            }
        }

        mDrawerToggle = new ActionBarDrawerToggle(this, mActivityjltHomeBinding.drawerLayout, 0, 0);
        mActivityjltHomeBinding.drawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onDestroy()
    {
        setContentView(R.layout.empty);
        InitCmmInterface.exitApp(getApplicationContext());
        mActivityjltHomeBinding.materialViewPager.removeAllViews();

        mCompositeSubscription.unsubscribe();
        mActivityjltHomeBinding =null;
        mChartListRsp=null;
        mPlayCenterBinding =null;
        mDrawerToggle=null;
        mToolbar=null;
        mLoadToast.destroy();
        mLoadToast=null;
        super.onDestroy();

        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /** @Description: communicate between last fragment and activity
     * @author : administor @time : 2016/4/11 10:59 */
    public void onEventMainThread(ViewPagersLoadingSuccessMessage viewPagersLoadingSuccessMessage)
    {
        mLoadToast.success();
    }

    public void showFragment(final ChartListRsp chartListRsp)
    {
        mLoadToast.setText("正在加载音乐").show();
        mActivityjltHomeBinding.materialViewPager.getViewPager().setAdapter(new MyFragmentStatePagerAdapter(getApplicationContext(),getSupportFragmentManager(), chartListRsp));
        mActivityjltHomeBinding.materialViewPager.setMaterialViewPagerListener(new MyMaterialViewPagerListener(this));
        mActivityjltHomeBinding.materialViewPager.getViewPager().setOffscreenPageLimit(mActivityjltHomeBinding.materialViewPager.getViewPager().getAdapter().getCount());
        mActivityjltHomeBinding.materialViewPager.getPagerTitleStrip().setViewPager(mActivityjltHomeBinding.materialViewPager.getViewPager());
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
}
