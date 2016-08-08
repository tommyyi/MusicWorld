package com.yueyinyue.search;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.cmsc.cmmusic.common.data.MusicInfo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2016/1/19.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class SearchMusicModelTest
{
    private SearchActivity searchActivity;
    @Rule
    public ActivityTestRule<SearchActivity> mActivityRule = new ActivityTestRule<>(SearchActivity.class);
    @Before
    public void setUp() throws Exception
    {
        searchActivity = mActivityRule.getActivity();
    }

    @Test
    public void testSearch() throws Exception
    {
        SearchMusicModelImp searchMusicModelImp=new SearchMusicModel();
        searchMusicModelImp.search(searchActivity,new ArrayList<MusicInfo>(),"你好");
        Thread.sleep(1000000);
    }
}