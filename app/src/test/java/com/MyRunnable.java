package com;

/**
 * Created by Administrator on 2016/5/16.
 */
public class MyRunnable implements Runnable
{
    private static int tickets = 20;

    //    private static Object mObject=new Object();
    @Override
    public void run()
    {
//        System.out.println(Thread.currentThread().getName()+","+Thread.currentThread().isAlive());
//        System.out.println(Thread.currentThread().getName()+","+Thread.currentThread().getPriority());
        for (int i = 0; i < 50; i++)
        {
            sell();
//            sell2();
//            synchronized (mObject)
//            {
//                if(tickets>0)
//                {
//                    System.out.println(Thread.currentThread().getName()+",tickets="+tickets);
//                    try
//                    {
//                        Thread.sleep(1000);
//                    }
//                    catch (InterruptedException e)
//                    {
//                        e.printStackTrace();
//                    }
//                    tickets--;
//                }
//            }
        }
    }

    private synchronized void sell()
    {
        if (tickets > 0)
        {
            System.out.println(Thread.currentThread().getName() + ",tickets=" + tickets);
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            tickets--;
//            sell2();
        }
    }

    private synchronized void sell2()
    {
        if (tickets > 0)
        {
            System.out.println(Thread.currentThread().getName() + ",sell2=" + tickets);
        }
    }
}
