package com.dexreload;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity
{
    //private static final String LAUNCHER = "com.dex.DynamicalActivity";
    private static final String LAUNCHER = "com.yueyinyue.home.tianlaizhisheng.MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent();
        intent.setClassName(getApplicationContext(), LAUNCHER);
        startActivity(intent);
    }
}
