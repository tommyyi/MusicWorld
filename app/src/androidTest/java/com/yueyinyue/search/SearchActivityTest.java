package com.yueyinyue.search;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;

import com.Tool;
import com.cmsc.cmmusic.init.InitCmmInterface;
import com.xk.m.R;
import com.yueyinyue.Model.Category;
import com.yueyinyue.Model.MusicItem;
import com.yueyinyue.cp.dialog.download.presenter.DownloadPresenter;
import com.yueyinyue.cp.dialog.DownloadViewImpl;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Hashtable;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by on 15/6/4.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SearchActivityTest
{
    private SearchActivity searchActivity;
    @Rule
    public ActivityTestRule<SearchActivity> mActivityRule = new ActivityTestRule<>(SearchActivity.class);

    @Before
    public void setUp()
    {
        searchActivity = mActivityRule.getActivity();
    }

    @Test
    public void showSearchOverToast()
    {
        Tool.sleep(1000000);
    }

    @Test
    public void typeSearchString()
    {
        onView(withId(com.arlib.floatingsearchview.R.id.search_bar_text)).perform(typeText("tianxia"), closeSoftKeyboard());
        onView(withId(R.id.floating_search_view)).perform(click());
        Tool.sleep(100000);
    }

    @Test
    public void payByMonth()
    {
        List<MusicItem> musicItemList = MusicItem.getCPMusicListByCategoryName(searchActivity, Category.getCpCategoryName(searchActivity.getApplicationContext(),0));
        musicItemList.get(0).setSelected(true);
        musicItemList.get(0).setMusicid("63354400060");
        final DownloadPresenter downloadPresenter = new DownloadPresenter(searchActivity, "",new DownloadViewImpl()
        {
            @Override
            public void resetSelectedTag(List<MusicItem> musicItemList)
            {

            }

            @Override
            public void setEnabled(boolean enabled)
            {

            }

            @Override
            public void setProgress(int progress)
            {

            }

            @Override
            public void setText(String text)
            {

            }

            @Override
            public void setOnClickListener()
            {

            }

            @Override
            public void showDlResult(String dirPath)
            {

            }

            @Override
            public void notifyItemChanged(int itemPosition)
            {

            }

            @Override
            public void showToast(String message)
            {

            }

            @Override
            public void toastSuccess(String message)
            {

            }

            @Override
            public void toastError(String message)
            {

            }
        }, musicItemList);
        searchActivity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                InitCmmInterface.initSDK(searchActivity);
                Observable.create(new Observable.OnSubscribe<String>()
                {
                    @Override
                    public void call(Subscriber<? super String> subscriber)
                    {
                        Hashtable<String,String> hashTable=InitCmmInterface.initCmmEnv(searchActivity);
                        String code=hashTable.get("code");
                        if(code.equals("0"))
                        {
                            subscriber.onNext(code);
                            Log.i("USER INITIALIZATION","SUCCESS");
                        }
                        else
                        {
                            Log.i("USER INITIALIZATION","FAILED");
                        }
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<String>()
                {
                    @Override
                    public void call(String s)
                    {
                        downloadPresenter.handleCPMonthBuy();
                    }
                });
            }
        });
        Tool.sleep(1000000);
    }
}