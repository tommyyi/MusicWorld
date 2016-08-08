package com.yueyinyue.Model.EventBusMessage;

/**
 * Created by Administrator on 2016/1/14.
 */
public class UpdateItemMessage
{
    public UpdateItemMessage(int mCpCategoryIndex, int itemPosition)
    {
        this.mCpCategoryIndex = mCpCategoryIndex;
        this.itemPosition = itemPosition;
    }

    public int itemPosition;
    public int mCpCategoryIndex;
}
