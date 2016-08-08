package com;

/**
 * Created by Administrator on 2016/5/16.
 */
public class Producer extends Thread
{
    private final Data data;

    public Producer(Data data)
    {
        this.data = data;
    }

    @Override
    public void run()
    {
        while (true)
        {
            data.add();
        }
    }
}
