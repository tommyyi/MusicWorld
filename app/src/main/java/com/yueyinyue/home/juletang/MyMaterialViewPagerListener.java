package com.yueyinyue.home.juletang;

import android.app.Activity;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.xk.m.R;

/**
 * Created by Administrator on 2016/4/8.
 */
class MyMaterialViewPagerListener implements MaterialViewPager.Listener
{
    private final Activity mActivity;

    public MyMaterialViewPagerListener(Activity activity)
    {

        mActivity = activity;
    }

    /**通过包名判断当前APP是哪个APP
     * 1、不同的APP显示不同的顶部图片
     * 2、不同的APP在build.gradle中配置不同的flavor
     * @param fragmentPosition
     * @return
     */
    @Override
    public HeaderDesign getHeaderDesign(int fragmentPosition)
    {
        switch (fragmentPosition % 4)
        {
            default:
                return HeaderDesign.fromColorResAndDrawable(R.color.blue, mActivity.getResources().getDrawable(R.drawable.header_jlt));
        }
    }
}
