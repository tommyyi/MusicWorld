package com.yueyinyue;

import android.database.sqlite.SQLiteDatabase;

import com.cmsc.cmmusic.common.demo.MiguApplication;
import com.yueyinyue.Model.dao.MusicDl.MusicDlRecordDaoMaster;
import com.yueyinyue.Model.dao.MusicDl.MusicDlRecordDaoSession;

/**
 * Created by Administrator on 2016/1/13.
 */
public class YueApplication extends MiguApplication
{
    public static MusicDlRecordDaoSession musicDlRecordDaoSession;
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

        if (musicDlRecordDaoSession == null)
        {
            MusicDlRecordDaoMaster.DevOpenHelper helper = new MusicDlRecordDaoMaster.DevOpenHelper(getApplicationContext(), "musicDlRecord", null);
            SQLiteDatabase db = helper.getWritableDatabase();
            // 注意：该数据库连接属于 MusicDlRecordDaoMaster，所以多个 Session 指的是相同的数据库连接。
            MusicDlRecordDaoMaster musicDlRecordDaoMaster = new MusicDlRecordDaoMaster(db);
            musicDlRecordDaoSession = musicDlRecordDaoMaster.newSession();
        }
    }
}
