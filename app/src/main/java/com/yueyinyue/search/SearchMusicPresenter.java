package com.yueyinyue.search;

import android.app.Activity;
import android.content.Context;

import com.cmsc.cmmusic.common.data.MusicInfo;

import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/1/19.
 */
public class SearchMusicPresenter implements SearchMusicPresenterImp
{
    private SearchMusicViewImp searchMusicViewImp;
    private SearchMusicModelImp searchMusicModelImp;
    private Context context;

    public SearchMusicPresenter(Context context,SearchMusicViewImp searchMusicViewImp)
    {
        this.context=context;
        this.searchMusicViewImp = searchMusicViewImp;
        this.searchMusicModelImp=new SearchMusicModel();
    }

    @Override
    public void search(final List<MusicInfo> musicInfoList, final String keyword)
    {
        musicInfoList.clear();
        searchMusicViewImp.searchStart("正在搜索音乐");

        Observable.create(new Observable.OnSubscribe<Boolean>()
        {
            @Override
            public void call(Subscriber<? super Boolean> subscriber)
            {
                boolean result= searchMusicModelImp.search(context,musicInfoList,keyword);
                subscriber.onNext(result);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Boolean>()
        {
            @Override
            public void call(Boolean aBoolean)
            {
                if(aBoolean.booleanValue())
                    searchMusicViewImp.searchOK();
                else
                    searchMusicViewImp.searchError("search music with error");
            }
        });
    }
}
