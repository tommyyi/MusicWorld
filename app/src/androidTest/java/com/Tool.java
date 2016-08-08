package com;

/**
 * Created by Administrator on 2016/1/24.
 */
public class Tool
{
    public static void sleep(long duration)
    {
        try
        {
            Thread.sleep(duration);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
