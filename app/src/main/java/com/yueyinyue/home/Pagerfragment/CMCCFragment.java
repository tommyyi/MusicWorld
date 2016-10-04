package com.yueyinyue.home.Pagerfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cmsc.cmmusic.common.MusicQueryInterface;
import com.cmsc.cmmusic.common.data.ChartListRsp;
import com.cmsc.cmmusic.common.data.MusicInfo;
import com.cmsc.cmmusic.common.data.MusicListRsp;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.yueyinyue.Model.Category;
import com.yueyinyue.Model.EventBusMessage.MusicListRspMessage;
import com.yueyinyue.Model.EventBusMessage.ViewPagersLoadingSuccessMessage;
import com.xk.m.R;
import com.yueyinyue.Model.dao.Util;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import jp.wasabeef.recyclerview.animators.FlipInBottomXAnimator;
import jp.wasabeef.recyclerview.animators.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.adapters.ScaleInAnimationAdapter;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class CMCCFragment extends Fragment
{
    private static final int NUMBER_PER_PAGE = 10;
    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();
    private int fragmentPosition;
    private ChartListRsp chartListRsp;
    private RecyclerView.Adapter mAdapter;
    private SwipyRefreshLayout swipyRefreshLayout;
    private int chartIndex = 0;
    private int pageIndexInChart = 2;
    public List<MusicInfo> musicInfoList = new ArrayList<MusicInfo>();

    public static CMCCFragment newInstance(int fragmentPosition, @NonNull ChartListRsp chartListRsp)
    {
        CMCCFragment cmccFragment = new CMCCFragment();
        cmccFragment.chartListRsp = chartListRsp;
        cmccFragment.fragmentPosition = fragmentPosition;
        setFirstSubChartIndex(cmccFragment, chartListRsp, Category.cmcc[fragmentPosition]);
        return cmccFragment;
    }

    private static void setFirstSubChartIndex(CMCCFragment cmccFragment, ChartListRsp chartListRsp, String pagerName)
    {
        int size = chartListRsp.getChartInfos().size();
        for (int index = 0; index < size; index++)
        {
            if (chartListRsp.getChartInfos().get(index).getChartName().contains(pagerName))
            {
                cmccFragment.chartIndex = index;
                return;
            }
        }
    }

    @Override
    public void onDestroy()
    {
        mCompositeSubscription.unsubscribe();
        super.onDestroy();
    }

    @Override
    public void onStart()
    {
        super.onStart();
    }

    @Override
    public void onStop()
    {
        super.onStop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_cmcc, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        swipyRefreshLayout = (SwipyRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipyRefreshLayout.setOnRefreshListener(new OnUserRefreshListener());
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setItemAnimator(new FlipInBottomXAnimator());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        mAdapter = new RecyclerViewMaterialAdapter(new CMCCFragmentAdapter(musicInfoList, getActivity()));
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(mAdapter);
        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(alphaAdapter);
        recyclerView.setAdapter(scaleAdapter);

        mAdapter.notifyDataSetChanged();
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), recyclerView, null);

        mCompositeSubscription.add(Observable.create(new Observable.OnSubscribe<MusicListRspMessage>()
        {
            @Override
            public void call(Subscriber<? super MusicListRspMessage> subscriber)
            {
                String chartCode = chartListRsp.getChartInfos().get(chartIndex).getChartCode();
                //MusicListRsp musicListRsp = MusicQueryInterface.getMusicsByChartId(getActivity().getApplicationContext(), chartCode, pageIndexInChart, NUMBER_PER_PAGE);
                MusicListRsp musicListRsp=null;
                if (fragmentPosition == 0)
                {
                    musicListRsp=Util.getMusic1Cache(getContext());
                }
                else if (fragmentPosition == 1)
                {
                    musicListRsp=Util.getMusic2Cache(getContext());
                    musicListRsp.getMusics().remove(0);
                }
                else if (fragmentPosition == 2)
                {
                    musicListRsp=Util.getMusic3Cache(getContext());
                }
                else
                {
                    System.exit(0);
                }
                MusicListRspMessage musicListRspMessage = new MusicListRspMessage(musicListRsp, fragmentPosition);
                subscriber.onNext(musicListRspMessage);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<MusicListRspMessage>()
        {
            @Override
            public void onCompleted()
            {

            }

            @Override
            public void onError(Throwable e)
            {
                e.printStackTrace();
            }

            @Override
            public void onNext(MusicListRspMessage musicListRspMessage)
            {
                handleMusicListRsp(musicListRspMessage);
                if (fragmentPosition == Category.cmcc.length - 1)
                {
                    EventBus.getDefault().post(new ViewPagersLoadingSuccessMessage("cmcc fragment loading is over"));
                }
            }
        }));
    }

    private boolean hasTravelAllSubChart()
    {
        return chartIndex == -1;
    }

    private void handleMusicListRsp(MusicListRspMessage musicListRspMessage)
    {
        if (musicListRspMessage.pagerPosition != fragmentPosition)
        {
            return;
        }

        swipyRefreshLayout.setRefreshing(false);
        if (musicListRspMessage.musicListRsp == null)
        {
            return;
        }

        if (musicListRspMessage.musicListRsp.getMusics() != null && musicListRspMessage.musicListRsp.getMusics().size() != 0)
        {
            musicInfoList.addAll(musicListRspMessage.musicListRsp.getMusics());
            mAdapter.notifyDataSetChanged();
            if (musicListRspMessage.musicListRsp.getMusics().size() == 20)
            {
                pageIndexInChart++;
            }
            else
            {
                locateNextSubChart();
            }
        }
        else
        {
            locateNextSubChart();
        }
    }

    /**
     * 因为一个榜单下面有很多子榜单，在浏览的时候需要遍历子榜单
     * 本函数功能是找到下一个子榜单，复位pageIndexInChart
     */
    private void locateNextSubChart()
    {
        int size = chartListRsp.getChartInfos().size();
        for (int index = chartIndex + 1; index < size; index++)
        {
            if (chartListRsp.getChartInfos().get(index).getChartName().contains(Category.cmcc[fragmentPosition]))
            {
                chartIndex = index;
                pageIndexInChart = 1;
                return;
            }
        }

        chartIndex = -1;
    }

    private class OnUserRefreshListener implements SwipyRefreshLayout.OnRefreshListener
    {
        @Override
        public void onRefresh(SwipyRefreshLayoutDirection direction)
        {
            if (hasTravelAllSubChart())
            {
                swipyRefreshLayout.setRefreshing(false);
                return;
            }

            mCompositeSubscription.add(Observable.create(new Observable.OnSubscribe<MusicListRspMessage>()
            {
                @Override
                public void call(Subscriber<? super MusicListRspMessage> subscriber)
                {
                    String chartCode = chartListRsp.getChartInfos().get(chartIndex).getChartCode();
                    MusicListRsp musicListRsp = MusicQueryInterface.getMusicsByChartId(getActivity().getApplicationContext(), chartCode, pageIndexInChart, NUMBER_PER_PAGE);
                    MusicListRspMessage musicListRspMessage = new MusicListRspMessage(musicListRsp, fragmentPosition);
                    subscriber.onNext(musicListRspMessage);
                }
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<MusicListRspMessage>()
            {
                @Override
                public void call(MusicListRspMessage musicListRspMessage)
                {
                    handleMusicListRsp(musicListRspMessage);
                }
            }));
        }
    }
}
