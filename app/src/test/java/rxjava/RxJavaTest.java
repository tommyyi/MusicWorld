package rxjava;

import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Administrator on 2016/1/27.
 */
public class RxJavaTest
{
    @Before
    public void setUp() throws Exception
    {
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
            }
        });
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
}