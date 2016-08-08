package com;

import org.junit.Test;

/**
 * Created by Administrator on 2016/5/16.
 */
public class TestMyRunnable
{
    @Test
    public void doRunnable() throws Exception
    {
        MyRunnable myRunnable1=new MyRunnable();
        MyRunnable myRunnable2=new MyRunnable();

//        System.out.println(Thread.currentThread().getName()+","+Thread.currentThread().isAlive());
//        System.out.println(Thread.currentThread().getName()+","+Thread.currentThread().getPriority());

        Thread thread1=new Thread(myRunnable1);
        Thread thread2=new Thread(myRunnable1);
        Thread thread3=new Thread(myRunnable2);
        Thread thread4=new Thread(myRunnable2);
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        Thread.sleep(10100000);
//        thread.join();
//        for (int i = 0; i < 5; i++)
//        {
//            Thread.sleep(1000);
//            System.out.println("index="+i);
//        }
//        System.out.println(thread.getName()+","+thread.isAlive());
//        System.out.println(Thread.currentThread().getName()+","+Thread.currentThread().getPriority());
    }

    @Test
    public void communicate() throws Exception
    {
        Data data=new Data();
        Producer producer=new Producer(data);
        Consumer consumer=new Consumer(data);
        consumer.start();
        producer.start();
        Thread.sleep(10100000);
    }
}
