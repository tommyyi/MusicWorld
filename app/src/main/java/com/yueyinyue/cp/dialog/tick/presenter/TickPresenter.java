package com.yueyinyue.cp.dialog.tick.presenter;

import com.yueyinyue.Model.MusicItem;
import com.yueyinyue.cp.dialog.TickViewImpl;

import java.util.List;

public class TickPresenter implements TickPresenterImpl
{
    public TickViewImpl tickViewImpl;
    private List<MusicItem> mMusicItemList;
    private int mTotalChecked = 0;
    public int limited;

    public TickPresenter(TickViewImpl tickViewImpl, List<MusicItem> musicItemList, int limited)
    {
        this.tickViewImpl = tickViewImpl;
        mMusicItemList=musicItemList;
        this.limited=limited;
    }

    @Override
    public boolean checkTick(int index, boolean checkedStatus)
    {
        if(!isThisTickRight(index,checkedStatus))
        {
            tickViewImpl.showSelectedItemsExceedLimitedMsg(limited);
            return false;
        }
        else
            return true;
    }

    public boolean isThisTickRight(int index, boolean isChecked)
    {
        //Log.i("position="+index,"status="+isChecked);
        if (mTotalChecked >= limited && isChecked)
        {
            return false;
        }

        if (isChecked)
        {
            mTotalChecked++;
        }
        else
        {
            mTotalChecked--;
        }

        for (int localIndex = 0; localIndex < mMusicItemList.size(); localIndex++)
        {
            if (localIndex == index)
            {
                mMusicItemList.get(localIndex).setSelected(isChecked);
            }
        }

        return true;
    }
}
