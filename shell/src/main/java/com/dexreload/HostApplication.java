package com.dexreload;

import android.app.Application;
import android.content.Context;

/**
 * 如果插件中或宿主中，有so库直接解压安装文件获取asset资源
 * 如果asset资源放置在插件中，则宿主无法正常加载插件
 *
 * 正常的方式访问assets资源的情况下，插件中的assets资源是可以被访问的
 *
 * 因为咪咕音乐SDK是通过so库直接解压宿主安装包获取assets资源
 * 如果将咪咕SDK的assets在插件中，则无法正常加载
 *
 * APP能正常加载插件assets中的cp.json、咪咕SDK能加载插件assets中的各种资源
 * 就说明目前这种方式是可以的
 */
public class HostApplication extends Application
{
    Plugin plugin;

    @Override
    protected void attachBaseContext(Context base)
    {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate()
    {
        super.onCreate();

        plugin = Plugin.getPlugin(getApplicationContext());

        try
        {
            plugin.loadPlugin();
            /*下面这句是关键，没有这一句，无法正常加载插件
            * 可能原因是虽然我们修改了activitythread中的resource
            * 但是系统已经在我们修改activitythread之前
            * 就给application的resource对象赋值了*
            * application的mBase在整个APP的运行中非常重要*/
            plugin.modifyBaseInHostApplication();
            plugin.initPlugin();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        System.loadLibrary("mg20pbase");
    }
}
