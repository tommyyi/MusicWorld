package com.yueyinyue.downloaded;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.xk.m.R;
import com.xk.m.databinding.ActivityMydownloadBinding;
import com.yueyinyue.DbSession;
import com.yueyinyue.Model.dao.MusicDl.MusicDlRecord;
import com.yueyinyue.Model.dao.MusicDl.MusicDlRecordDao;
import com.yueyinyue.BaseActivity;

import net.steamcrafted.loadtoast.LoadToast;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.WhereCondition;
import jp.wasabeef.recyclerview.animators.FlipInBottomXAnimator;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MyDownloadActivity extends BaseActivity
{
    private CompositeSubscription mCompositeSubscription=new CompositeSubscription();
    ActivityMydownloadBinding mActivityMydownloadBinding;
    private RecyclerView.Adapter mAdapter;

    public List<MusicDlRecord> musicDlRecordList = new ArrayList<MusicDlRecord>();

    public void onCloseActivityClick(View view)
    {
        MyDownloadActivity.this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mActivityMydownloadBinding= DataBindingUtil.setContentView(this,R.layout.activity_mydownload);
        mLoadToast = new LoadToast(this).setText("").setTranslationY(100);

        String text = "正在获取已下载音乐";
        mLoadToast.setText(text).show();
        mActivityMydownloadBinding.recyclerView.setItemAnimator(new FlipInBottomXAnimator());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mActivityMydownloadBinding.recyclerView.setLayoutManager(layoutManager);
        mActivityMydownloadBinding.recyclerView.setHasFixedSize(true);

        mAdapter = new RecyclerViewMaterialAdapter(new RecyclerViewAdapter(musicDlRecordList, MyDownloadActivity.this));
        //AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(mAdapter);
        //ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(alphaAdapter);
        mActivityMydownloadBinding.recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        mCompositeSubscription.add(Observable.create(new Observable.OnSubscribe<Boolean>()
        {
            @Override
            public void call(Subscriber<? super Boolean> subscriber)
            {
                WhereCondition fullSongCondition= MusicDlRecordDao.Properties.FullSongDlPercentage.eq(100);
                WhereCondition vibrateRingCondition= MusicDlRecordDao.Properties.VibrateRingDlPercentage.eq(100);
                QueryBuilder queryBuilder = DbSession.musicDlRecordDaoSession.getMusicDlRecordDao().queryBuilder();
                Query query = queryBuilder.where(queryBuilder.or(fullSongCondition, vibrateRingCondition)).build();
                List<MusicDlRecord> addedMusicDlRecordList=query.list();
                musicDlRecordList.addAll(addedMusicDlRecordList);
                subscriber.onNext(Boolean.TRUE);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Boolean>()
        {
            @Override
            public void call(Boolean aBoolean)
            {
                mLoadToast.success();
                mAdapter.notifyDataSetChanged();
            }
        }));
    }

    @Override
    protected void onDestroy()
    {
        setContentView(R.layout.empty);
        mLoadToast.destroy();
        mLoadToast=null;
        mActivityMydownloadBinding=null;
        mAdapter=null;
        musicDlRecordList=null;
        mCompositeSubscription.unsubscribe();
        mCompositeSubscription=null;
        mPlayCenterBinding =null;
        super.onDestroy();
    }
}
