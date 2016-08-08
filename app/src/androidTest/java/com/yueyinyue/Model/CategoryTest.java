package com.yueyinyue.Model;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Administrator on 2016/4/27.
 */
@RunWith(AndroidJUnit4.class)
public class CategoryTest
{
    private Context mContext;

    @Before
    public void setup() throws Exception
    {
        mContext = InstrumentationRegistry.getTargetContext();
    }
    @Test
    public void testGetCpCategoryArray() throws Exception
    {
        String[] cpArray=Category.getCpCategoryArray(mContext.getApplicationContext());
        cpArray=null;
    }

    @Test
    public void testGetCpCategoryName() throws Exception
    {
        String category=Category.getServiceId(mContext,0);
        category=null;
    }

    @Test
    public void testGetLimited() throws Exception
    {
        int limited=Category.getLimited(mContext,0);
        limited=0;
    }

    @Test
    public void testGetPrice() throws Exception
    {
        int price=Category.getPrice(mContext,0);
        price=0;
    }
}