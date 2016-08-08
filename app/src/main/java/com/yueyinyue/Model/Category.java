package com.yueyinyue.Model;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Created by Administrator on 2015/12/7.
 */
public class Category
{
    public static final String[] cmcc = {"全球单曲榜", "咪咕金曲榜", "咪咕新歌榜",/*"咪咕彩铃榜","咪咕音乐榜"*/};

    public static String[] getCpCategoryArray(Context context)
    {
        String str;
        try
        {
            str = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA).metaData.getString("categoryArray");
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }
        return str != null ? str.replace(" ", "").split(",") : null;
    }

    public static String getServiceId(Context context,int index)
    {
        String str;
        try
        {
            str = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA).metaData.getString("serviceIdArray");
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }

        String[] serviceIdArray=str.replace("serviceId:","").split(",");
        return serviceIdArray[index];
    }

    public static String getCpCategoryName(Context context,int index)
    {
        return getCpCategoryArray(context)[index];
    }

    public static int getLimited(Context context,int index)
    {
        String str;
        try
        {
            str = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA).metaData.getString("limitedArray");
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
            return 1;
        }

        String[] limitedArray=str.split(",");
        if(limitedArray.length==1)
            limitedArray[0]=limitedArray[0].replace("-","");
        return Integer.valueOf(limitedArray[index]);
    }

    public static int getPrice(Context context,int index)
    {
        String str;
        try
        {
            str = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA).metaData.getString("price");
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
            return 1;
        }

        String[] limitedArray=str.split(",");
        if(limitedArray.length==1)
            limitedArray[0]=limitedArray[0].replace("-","");
        return Integer.valueOf(limitedArray[index]);
    }
}
