package com.yueyinyue.search;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.cmsc.cmmusic.common.data.MusicInfo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2016/1/19.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class SearchMusicPresenterTest
{
    private SearchActivity searchActivity;
    private Context context;
    @Rule
    public ActivityTestRule<SearchActivity> mActivityRule = new ActivityTestRule<>(SearchActivity.class);

    @Before
    public void setUp() throws Exception
    {
        searchActivity = mActivityRule.getActivity();
        context= InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void testSearch() throws Exception
    {
        final SearchMusicPresenter searchMusicPresenter = new SearchMusicPresenter(context,searchActivity);
        searchMusicPresenter.search(searchActivity.musicInfoList,"来吧兄弟");
        Thread.sleep(1000000);
    }
}