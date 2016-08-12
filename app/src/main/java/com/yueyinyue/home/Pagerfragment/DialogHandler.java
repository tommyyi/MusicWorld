package com.yueyinyue.home.Pagerfragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import com.cmsc.cmmusic.common.CMMusicCallback;
import com.cmsc.cmmusic.common.RingbackManagerInterface;
import com.cmsc.cmmusic.common.data.CrbtListRsp;
import com.cmsc.cmmusic.common.data.OrderResult;
import com.cmsc.cmmusic.common.data.Result;

import java.util.Date;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by Administrator on 2016/3/23.
 */
public class DialogHandler
{
    private Activity activity;
    private String musicId;
    private MaterialDialog mMaterialDialog;
    public DialogHandler(Activity activity, MaterialDialog mMaterialDialog, String musicId)
    {
        this.activity = activity;
        this.musicId=musicId;
        this.mMaterialDialog=mMaterialDialog;
    }

    public void onOpenRingBack(View view)
    {
        RingbackManagerInterface.openRingback(activity, new CMMusicCallback<Result>()
        {
            @Override
            public void operationResult(Result result)
            {
                new AlertDialog.Builder(activity).setTitle("彩铃开通结果").setMessage(result==null?"失败":result.toString()).setPositiveButton("确认", null).show();
            }
        });
    }

    public void onOrderRing(View view)
    {
        RingbackManagerInterface.buyRingBack(activity, musicId, new CMMusicCallback<OrderResult>()
        {
            @Override
            public void operationResult(OrderResult ret)
            {
                if (null != ret)
                {
                    String time = String.valueOf(new Date(System.currentTimeMillis()));
                    Log.e("彩铃购买时间", time);
                    String msg = "订购时间:"+time+",resMsg:" + ret.getResMsg() + ",orderId:" + ret.getOrderId() + ",resultCode:" + ret.getResultCode();
                    Log.e("彩铃购买结果", msg);
                    activity.getSharedPreferences("log", Context.MODE_PRIVATE).edit().putString("log",msg).commit();
                    new android.app.AlertDialog.Builder(activity).setTitle("彩铃购买结果").setMessage(msg).setPositiveButton("确认", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            mMaterialDialog.dismiss();
                        }
                    }).show();

                    new Thread()
                    {
                        @Override
                        public void run()
                        {
                            CrbtListRsp crbtListRsp = RingbackManagerInterface.getCrbtBox(activity);
                            if(crbtListRsp==null||crbtListRsp.getToneInfos()==null)
                                return;

                            int length = crbtListRsp.getToneInfos().size();
                            for (int index = 0; index < length; index++)
                            {
                                if (crbtListRsp.getToneInfos().get(index).getToneID().equals(musicId))
                                {
                                    RingbackManagerInterface.setDefaultCrbt(activity, crbtListRsp.getToneInfos().get(index).getToneID());
                                }
                            }
                        }
                    }.start();
                }
            }
        });
    }

    public void onOwnRingMonth(View view)
    {
        RingbackManagerInterface.ownRingMonth(activity, new CMMusicCallback<Result>()
        {
            @Override
            public void operationResult(Result result)
            {
                if (null != result)
                {
                    new AlertDialog.Builder(activity).setTitle("OwnRingMonth").setMessage(result.toString()).setPositiveButton("确认", null).show();
                }
            }
        });
    }
}
