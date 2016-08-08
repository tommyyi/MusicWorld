package com.yueyinyue.home.Pagerfragment;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.Animator;
import com.yueyinyue.Model.EventBusMessage.ShowedViewTagMessage;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2015/11/23.
 */
public class LowerLinearlayout extends LinearLayout
{
    public LowerLinearlayout(Context context)
    {
        super(context);
    }

    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();
        EventBus.getDefault().register(this);
    }

    public LowerLinearlayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public void onEventMainThread(final ShowedViewTagMessage showedViewTagMessage)
    {
        if(!showedViewTagMessage.tag.equals((String)getTag()))
        {
            YoYo.with(Techniques.SlideOutUp).duration(200).withListener(new Animator.AnimatorListener()
            {
                @Override
                public void onAnimationStart(Animator animation)
                {

                }

                @Override
                public void onAnimationEnd(Animator animation)
                {
                    LowerLinearlayout.this.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animation)
                {

                }

                @Override
                public void onAnimationRepeat(Animator animation)
                {

                }
            }).playOn(this);
        }
    }

    @Override
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        EventBus.getDefault().unregister(this);
    }
}
