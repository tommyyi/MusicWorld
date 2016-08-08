package com.yueyinyue.home.Pagerfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.xk.m.R;
import com.yueyinyue.Model.Category;
import com.yueyinyue.Model.MusicItem;
import com.yueyinyue.cp.CpActivityAdapter;

import jp.wasabeef.recyclerview.animators.FlipInBottomXAnimator;
import jp.wasabeef.recyclerview.animators.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.adapters.ScaleInAnimationAdapter;

public class CpFragment extends Fragment
{

    public static CpFragment newInstance()
    {
        return new CpFragment();
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
        return inflater.inflate(R.layout.fragment_cp, container, false);
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

        RecyclerView.Adapter adapter = new RecyclerViewMaterialAdapter(new CpAdapter(getActivity()));
        //int categoryIndex=0;
        //RecyclerView.Adapter adapter = new RecyclerViewMaterialAdapter(new CpActivityAdapter(getActivity(),categoryIndex, MusicItem.getCPMusicList(getActivity()),Category.getLimited(getActivity().getApplicationContext(),categoryIndex)));

        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(adapter);
        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(alphaAdapter);
        recyclerView.setAdapter(scaleAdapter);

        adapter.notifyDataSetChanged();
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), recyclerView, null);
    }
}
