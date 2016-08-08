package com.yueyinyue.home.tianlaizhisheng;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.bartoszlipinski.viewpropertyobjectanimator.BuildConfig;
import com.cmsc.cmmusic.common.data.ChartListRsp;
import com.yueyinyue.Model.Category;
import com.yueyinyue.home.Pagerfragment.CMCCFragment;
import com.yueyinyue.home.Pagerfragment.CpFragment;

/**
 * Created by Administrator on 2016/4/8.
 */
class MyFragmentStatePagerAdapter extends FragmentStatePagerAdapter
{
    private Context mContext;
    private final ChartListRsp mChartListRsp;

    public MyFragmentStatePagerAdapter(Context context, FragmentManager fragmentManager, ChartListRsp chartListRsp)
    {
        super(fragmentManager);
        mContext = context;
        mChartListRsp = chartListRsp;
    }

    @Override
    public Fragment getItem(int fragmentPosition)
    {
        if (fragmentPosition <= Category.cmcc.length - 1)
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
        if (com.xk.m.BuildConfig.FLAVOR.equals("TianLaiZhiShengConfig2"))
        {
            boolean includecp=true;
            try
            {
                ApplicationInfo info = mContext.getPackageManager().getApplicationInfo(mContext.getPackageName(), PackageManager.GET_META_DATA);
                includecp = info.metaData.getBoolean("INCLUDECP");
            }
            catch (PackageManager.NameNotFoundException e)
            {
                e.printStackTrace();
            }

            if (includecp)
            {
                return Category.cmcc.length + 1;
            }
            else
            {
                return Category.cmcc.length;
            }
        }
        else
        {
            return Category.cmcc.length + 1;
        }
    }

    @Override
    public CharSequence getPageTitle(int fragmentPosition)
    {
        if (fragmentPosition <= Category.cmcc.length - 1)
        {
            return Category.cmcc[fragmentPosition];
        }
        else
        {
            return "天籁之声专属";
        }
    }
}
