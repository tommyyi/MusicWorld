package com.yueyinyue.home.Pagerfragment.album;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.xk.m.R;
import com.yueyinyue.Model.Category;
import com.yueyinyue.Model.MusicItem;
import com.yueyinyue.cp.CpMusicListAdapter;
import com.yueyinyue.home.Pagerfragment.CpFragment;

import jp.wasabeef.recyclerview.animators.FlipInBottomXAnimator;
import jp.wasabeef.recyclerview.animators.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.adapters.ScaleInAnimationAdapter;

public class AlbumFragment extends CpFragment
{
    public static AlbumFragment newInstance()
    {
        return new AlbumFragment();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setItemAnimator(new FlipInBottomXAnimator());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        //RecyclerView.Adapter adapter = new RecyclerViewMaterialAdapter(new CpServiceListAdapter(getActivity()));
        int categoryIndex=0;
        RecyclerView.Adapter adapter = new RecyclerViewMaterialAdapter(new AlbumMusicListAdapter(getActivity(),categoryIndex, MusicItem.getCPMusicList(getActivity()),3));

        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(adapter);
        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(alphaAdapter);
        recyclerView.setAdapter(scaleAdapter);

        adapter.notifyDataSetChanged();
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), recyclerView, null);
    }
}
