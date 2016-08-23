package com.yueyinyue.cp;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xk.m.R;
import com.xk.m.databinding.MusicItemHeaderBinding;
import com.yueyinyue.Model.Category;

public class CpActivityAdapterHeaderViewHolder extends RecyclerView.ViewHolder
{
    public final Activity mActivity;
    public final int mLimited;
    public final int mCpCategoryIndex;
    public final MusicItemHeaderBinding mMusicItemHeaderBinding;
    public CpActivityAdapterHeaderViewHolder(Activity activity, View view, int limited, int cpCategoryIndex)
    {
        super(view);
        mActivity=activity;
        mMusicItemHeaderBinding= DataBindingUtil.bind(view);
        mLimited=limited;
        mCpCategoryIndex=cpCategoryIndex;
    }

    public void setContent()
    {
        String serviceIntroStr=String.format(mActivity.getString(R.string.serviceIntro), Category.getCpCategoryName(mActivity.getApplicationContext(),mCpCategoryIndex),mLimited);
        int price = Category.getPrice(mActivity.getApplicationContext(), mCpCategoryIndex);
        String serviceFeeStr=String.format(mActivity.getString(R.string.serviceFee), price +"");
        mMusicItemHeaderBinding.serviceIntro.setText(serviceIntroStr);
        mMusicItemHeaderBinding.serviceFee.setText(serviceFeeStr);

        if(mCpCategoryIndex==0&&price==6)
            mMusicItemHeaderBinding.serviceFee.setText(String.format(mActivity.getString(R.string.serviceFee_month), price +""));
    }
}
