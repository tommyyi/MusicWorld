package com;

/**
 * Created by Administrator on 2016/5/16.
 */
public class Consumer extends Thread
{
    Data mData;

    public Consumer(Data data)
    {
        this.mData = data;
    }

    @Override
    public void run()
    {
        while (true)
        {
            mData.sub();
        }
    }
}
