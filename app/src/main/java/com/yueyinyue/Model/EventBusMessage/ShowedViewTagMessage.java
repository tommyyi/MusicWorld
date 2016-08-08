package com.yueyinyue.Model.EventBusMessage;

import com.yueyinyue.home.Pagerfragment.LowerLinearlayout;

/**
 * Created by Administrator on 2015/11/24.
 */
public class ShowedViewTagMessage
{
    public ShowedViewTagMessage(LowerLinearlayout lowerLinearlayout, String tag)
    {
        this.lowerLinearlayout = lowerLinearlayout;
        this.tag = tag;
    }

    public String tag;
    public LowerLinearlayout lowerLinearlayout;
}
