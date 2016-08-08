package com;

/**
 * Created by Administrator on 2016/5/16.
 */
public class Data
{
    private int quantity=0;
    private long last=0;
    public void add()
    {
        synchronized (this)
        {
            quantity = quantity + 5;
            long current= System.currentTimeMillis();
            long diff=current-last;
            last=current;
            System.out.println(diff+", "+Thread.currentThread().getName() + ",product to " + quantity);

            try
            {
                Thread.sleep(2000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            this.notify();
        }
    }

    public  void sub()
    {
        synchronized(this)
        {
            try
            {
                this.wait();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            quantity = quantity - 5;
            long current= System.currentTimeMillis();
            long diff=current-last;
            last=current;
            System.out.println(diff+", "+Thread.currentThread().getName() + ",consume to " + quantity);
        }
    }
}
