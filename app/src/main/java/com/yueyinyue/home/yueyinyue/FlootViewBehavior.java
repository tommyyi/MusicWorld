package com.yueyinyue.home.yueyinyue;

import android.content.Context;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.xk.m.R;

public class FlootViewBehavior extends CoordinatorLayout.Behavior
{

    private FloatingActionButton mFloatingActionButton;

    public FlootViewBehavior()
    {
    }

    public FlootViewBehavior(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency)
    {
        return dependency.getId() == R.id.appbarlayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency)
    {
        float rate = (float) dependency.getBottom() / (float) dependency.getMeasuredHeight();
        if (mFloatingActionButton == null)
        {
            mFloatingActionButton = (FloatingActionButton) parent.findViewById(R.id.searchbutton);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {
            mFloatingActionButton.setAlpha(rate);
        }
        return true;
    }
}
