package com.yueyinyue;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.xk.m.databinding.PlaycenterBinding;
import com.yueyinyue.cp.CpActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Administrator on 2016/1/27.
 */
@RunWith(AndroidJUnit4.class)
public class CpActivityTest
{
    @Rule
    public ActivityTestRule<CpActivity> cpActivityActivityTestRule= new ActivityTestRule<>(CpActivity.class);
    public CpActivity cpActivity;

    @Before
    public void setUp() throws Exception
    {
        cpActivity=cpActivityActivityTestRule.getActivity();
    }

    @Test
    public void doTest() throws Exception
    {
        cpActivity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                ((PlaycenterBinding)cpActivity.mPlayCenterBinding).pause.setVisibility(View.VISIBLE);
            }
        });
        Thread.sleep(1000000);
    }
}