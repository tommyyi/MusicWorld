package com.rxjava;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.yueyinyue.Model.Category;
import com.yueyinyue.Model.MusicItem;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/1/27.
 */
@RunWith(AndroidJUnit4.class)
public class RxJavaTest
{
    Context context;

    @Before
    public void setUp() throws Exception
    {
        context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void observableJustOne() throws Exception
    {
        Observable<String> observable = Observable.just("hello world");
        observable.subscribe(new Action1<String>()
        {
            @Override
            public void call(String s)
            {
                Log.i("observable.just", s);
            }
        });
        Thread.sleep(1000000);
    }

    @Test
    public void observableJustMore() throws Exception
    {
        Observable<String> observable = Observable.just("hello world", "yijianfeng", "qiumin");
        observable.subscribe(new Action1<String>()
        {
            @Override
            public void call(String s)
            {
                Log.i("observable.just", s);
            }
        });
        Thread.sleep(1000000);
    }

    @Test
    public void observableFrom() throws Exception
    {
        String[] array = {"hello world", "yijianfeng"};
        Observable<String> observable = Observable.from(array);
        observable.subscribe(new Action1<String>()
        {
            @Override
            public void call(String s)
            {
                Log.i("observable.from", s);
            }
        });
        Thread.sleep(1000000);
    }

    @Test
    public void observableMap() throws Exception
    {
        String[] array = {"hello world", "yijianfeng"};
        Observable<String> observable = Observable.from(array);
        observable = observable.map(new Func1<String, String>()
        {
            @Override
            public String call(String s)
            {
                return s.substring(0, 3);
            }
        });
        observable.subscribe(new Action1<String>()
        {
            @Override
            public void call(String s)
            {
                Log.i("observable.map", s);
            }
        });
        Thread.sleep(1000000);
    }

    @Test
    public void observableFlatMap() throws Exception
    {
        Observable<List<MusicItem>> observable = Observable.create(new Observable.OnSubscribe<List<MusicItem>>()
        {
            @Override
            public void call(Subscriber<? super List<MusicItem>> subscriber)
            {
                List<MusicItem> list = MusicItem.getCPMusicListByCategoryName(context, Category.getCpCategoryName(context,0));
                Log.i("onSubscribe print:", "from original observable");
                subscriber.onNext(list);
            }
        });
        observable.flatMap(new Func1<List<MusicItem>, Observable<String>>()
        {
            @Override
            public Observable<String> call(List<MusicItem> musicItemList)
            {
                int length=musicItemList.size();
                String[] strArray=new String[length];
                for(int index=0;index<length;index++)
                    strArray[index]=musicItemList.get(index).getSong();

                return Observable.from(strArray);
            }
        }).subscribeOn(Schedulers.io()).subscribe(new Action1<String>()
        {
            @Override
            public void call(String s)
            {
                Log.i("observer print:", s);
            }
        });
        Thread.sleep(1000000);
    }
}