package com.xk.m2;

import android.database.sqlite.SQLiteDatabase;

import com.cmsc.cmmusic.common.demo.MiguApplication;
import com.yueyinyue.DbSession;
import com.yueyinyue.Model.dao.MusicDl.MusicDlRecordDaoMaster;

/**
 * Created by Administrator on 2016/1/13.
 */
public class YueApplication extends MiguApplication
{
    //    private RefWatcher refWatcher;

//    public static RefWatcher getRefWatcher(Context context)
//    {
//        YueApplication application = (YueApplication) context.getApplicationContext();
//        return application.refWatcher;
//    }

    @Override
    public void onCreate()
    {
        super.onCreate();

//        if (refWatcher == null)
//        {
//            LeakCanary.enableDisplayLeakActivity(getApplicationContext());
//            refWatcher = LeakCanary.install(this);
//        }

        if (DbSession.musicDlRecordDaoSession == null)
        {
            MusicDlRecordDaoMaster.DevOpenHelper helper = new MusicDlRecordDaoMaster.DevOpenHelper(getApplicationContext(), "musicDlRecord", null);
            SQLiteDatabase db = helper.getWritableDatabase();
            // 注意：该数据库连接属于 MusicDlRecordDaoMaster，所以多个 Session 指的是相同的数据库连接。
            MusicDlRecordDaoMaster musicDlRecordDaoMaster = new MusicDlRecordDaoMaster(db);
            DbSession.musicDlRecordDaoSession = musicDlRecordDaoMaster.newSession();
        }
    }
}
