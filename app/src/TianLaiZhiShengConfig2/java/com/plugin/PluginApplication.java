package com.plugin;

import android.content.Context;

import com.xk.tianlaizhisheng2.YueApplication;

/**
 * Created by Administrator on 2016/9/21.
 */
public class PluginApplication
{
    public static void init(Context context)
    {
        YueApplication yueApplication=new YueApplication();
        //yueApplication.onCreate();
        yueApplication.initDB(context);
    }
}
