package com;

import org.junit.Test;

/**
 * Created by Administrator on 2016/8/12.
 */
public class MyDate
{
    @Test
    public void testDate() throws Exception
    {
        String s = String.valueOf(new java.util.Date(System.currentTimeMillis()));
    }
}
