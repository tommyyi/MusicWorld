package com.yueyinyue;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.cmsc.cmmusic.common.CPManagerInterface;
import com.cmsc.cmmusic.common.data.QueryResult;
import com.cmsc.cmmusic.init.InitCmmInterface;
import com.yueyinyue.search.SearchActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Hashtable;

@RunWith(AndroidJUnit4.class)
public class UserTest
{
    @Rule
    public ActivityTestRule<SearchActivity> cpActivityActivityTestRule= new ActivityTestRule<>(SearchActivity.class);
    public SearchActivity mSearchActivity;
    private boolean isInitiateOk;

    static {
        try
        {
            System.loadLibrary("mgpbase");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Before
    public void setup() throws Exception
    {
        mSearchActivity=cpActivityActivityTestRule.getActivity();
        InitCmmInterface.initSDK(mSearchActivity);
        Hashtable<String,String> hashTable=InitCmmInterface.initCmmEnv(mSearchActivity);
        String code=hashTable.get("code");
        if(code.equals("0"))
        {
            Log.i("USER INITIALIZATION","SUCCESS");
            isInitiateOk=true;
        }
        else
        {
            Log.i("USER INITIALIZATION","FAILED");
            isInitiateOk=false;
        }
    }

    @Test
    public void checkRelationship()
    {
        String serviceId = "600967020000006653";
        QueryResult result = CPManagerInterface.queryCPMonth(mSearchActivity, serviceId);
        return;
    }
}
