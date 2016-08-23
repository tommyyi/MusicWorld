package com.yueyinyue.cp;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.xk.m.R;
import com.xk.m.databinding.ActivityCpBinding;
import com.yueyinyue.Model.Category;
import com.yueyinyue.Model.EventBusMessage.UpdateItemMessage;
import com.yueyinyue.BaseActivity;
import com.yueyinyue.Model.MusicItem;

import net.steamcrafted.loadtoast.LoadToast;

import java.util.List;

/**
 * Created by Administrator on 2016/1/27.
 */
public class CpActivity extends BaseActivity
{
    public ActivityCpBinding mActivityCpBinding;
    private int mCpCategoryIndex =0;
    private RecyclerView.Adapter mAdapter;
    private List<MusicItem> musicItemList;

    public void onCloseCpActivityClick(View view)
    {
        this.finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mActivityCpBinding= DataBindingUtil.setContentView(this,R.layout.activity_cp);
        mLoadToast = new LoadToast(this).setText("").setTranslationY(100);

        mCpCategoryIndex =getIntent().getIntExtra("categoryNameIndex",0);
        musicItemList= MusicItem.getCPMusicListByCategoryName(getApplicationContext(), Category.getCpCategoryName(getApplicationContext(),mCpCategoryIndex));

        //mRecyclerView.setItemAnimator(new FlipInBottomXAnimator());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mActivityCpBinding.recyclerView.setLayoutManager(layoutManager);
        mActivityCpBinding.recyclerView.setHasFixedSize(true);

        mActivityCpBinding.swipeRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction)
            {
                mActivityCpBinding.swipeRefreshLayout.setRefreshing(false);
            }
        });
        mAdapter = new RecyclerViewMaterialAdapter(new CpMusicListAdapter(this, mCpCategoryIndex,musicItemList, Category.getLimited(getApplicationContext(),mCpCategoryIndex)));
        //AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(mAdapter);
        //ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(alphaAdapter);
        //mRecyclerView.setAdapter(scaleAdapter);
        mActivityCpBinding.recyclerView.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();
        MaterialViewPagerHelper.registerRecyclerView(this, mActivityCpBinding.recyclerView, null);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }

    public void onEventMainThread(UpdateItemMessage updateItemMessage)
    {
        if(updateItemMessage.mCpCategoryIndex == mCpCategoryIndex)
        {
            mAdapter.notifyDataSetChanged();
            Log.i("updateItem","itemPosition="+ updateItemMessage.itemPosition);
        }
    }

    @Override
    protected void onDestroy()
    {
        setContentView(R.layout.empty);
        mLoadToast.destroy();
        mLoadToast=null;
        mActivityCpBinding=null;
        mPlayCenterBinding =null;
        mAdapter=null;
        musicItemList=null;
        super.onDestroy();
    }
}
