package com.yueyinyue.home.yueyinyue;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cmsc.cmmusic.common.data.ChartListRsp;
import com.yueyinyue.Model.Category;
import com.yueyinyue.home.Pagerfragment.CMCCFragment;
import com.yueyinyue.home.Pagerfragment.CpFragment;

/**
 * Created by Administrator on 2016/4/8.
 */
class MyFragmentStatePagerAdapter extends FragmentStatePagerAdapter
{
    private final ChartListRsp mChartListRsp;

    public MyFragmentStatePagerAdapter(FragmentManager fragmentManager, ChartListRsp chartListRsp)
    {
        super(fragmentManager);
        mChartListRsp = chartListRsp;
    }

    @Override
    public Fragment getItem(int fragmentPosition)
    {
        if (fragmentPosition < Category.cmcc.length)
        {
            return CMCCFragment.newInstance(fragmentPosition, mChartListRsp);
        }
        else
        {
            return CpFragment.newInstance();
        }
    }

    @Override
    public int getCount()
    {
        return Category.cmcc.length + 1;
    }

    @Override
    public CharSequence getPageTitle(int fragmentPosition)
    {
        if (fragmentPosition <= Category.cmcc.length-1)
        {
            return Category.cmcc[fragmentPosition];
        }
        else
        {
            return "悦音乐专属";
        }
    }
}
