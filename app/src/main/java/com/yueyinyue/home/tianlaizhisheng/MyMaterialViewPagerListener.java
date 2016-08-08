package com.yueyinyue.home.tianlaizhisheng;

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

    @Override
    public HeaderDesign getHeaderDesign(int fragmentPosition)
    {
        switch (fragmentPosition % 4)
        {
            default:
                return HeaderDesign.fromColorResAndDrawable(R.color.blue, mActivity.getResources().getDrawable(R.drawable.header_tlzs));
            /*case 0:
                return HeaderDesign.fromColorResAndUrl(R.color.blue, "http://www.sucaitianxia.com/Photo/pic/201003/fenggs05.jpg");
            case 1:
                return HeaderDesign.fromColorResAndUrl(R.color.blue, "http://www.sucaitianxia.com/Photo/pic/201003/fenggs05.jpg");
            case 2:
                return HeaderDesign.fromColorResAndUrl(R.color.blue, "http://www.sucaitianxia.com/Photo/pic/201003/fenggs05.jpg");
            case 3:
                return HeaderDesign.fromColorResAndUrl(R.color.blue, "http://www.sucaitianxia.com/Photo/pic/201003/fenggs05.jpg");*/
        }
//        return null;
    }
}
