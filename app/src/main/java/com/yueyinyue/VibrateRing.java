package com.yueyinyue;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.content.ContentResolverCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.xk.m.R;
import com.yueyinyue.Model.EventBusMessage.SetRingtoneMessage;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by Administrator on 2016/4/11.
 */
public class VibrateRing
{
    Activity mActivity;
    SetRingtoneMessage mSetRingtoneMessage;
    private Cursor mCursor;

    public VibrateRing(Activity activity, Intent intent, SetRingtoneMessage ringtoneMessage)
    {
        mActivity = activity;
        mSetRingtoneMessage = ringtoneMessage;
        Uri contactData = intent.getData();
//        mCursor = mContentResolver.query(contactData, null, null, null, null);
//        mCursor = mActivity.managedQuery(contactData, null, null, null, null);

//        final CursorLoader loader = new CursorLoader(context);
//        loader.setUri(contactData);
//        mCursor=loader.loadInBackground();

        mCursor = ContentResolverCompat.query(mActivity.getContentResolver(), contactData, null, null, null, null, null);
        if (mCursor != null)
        {
            mCursor.moveToFirst();
        }
    }

    public void showVibrateRingResultDialog(String result)
    {
        final MaterialDialog mMaterialDialog = new MaterialDialog(mActivity);
        mMaterialDialog.setTitle("振铃设置结果");
        mMaterialDialog.setPositiveButton("关闭", new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mMaterialDialog.dismiss();
            }
        });
        View view = LayoutInflater.from(mActivity).inflate(R.layout.dialog_download_result, null, false);
        mMaterialDialog.setContentView(view);
        TextView textView = (TextView) view.findViewById(R.id.downloadResult);
        textView.setText(result);
        mMaterialDialog.show();
    }

    public boolean set()
    {
        if (mCursor == null)
        {
            return false;
        }

        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.DATA, mSetRingtoneMessage.musicFile.getAbsolutePath());
        values.put(MediaStore.MediaColumns.TITLE, mSetRingtoneMessage.title);
        values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/*");
        values.put(MediaStore.Audio.Media.IS_ALARM, false);
        values.put(MediaStore.Audio.Media.IS_RINGTONE, false);
        values.put(MediaStore.Audio.Media.IS_MUSIC, false);
        values.put(MediaStore.Audio.Media.IS_NOTIFICATION, false);
        // Insert it into the database,uri="content://media/external/audio/media"
        //"content://media/external/audio/media/1006"
        ///storage/emulated/0/yueyinyue/20160324162542.mp3
        //Love Me Like You Do(五十度灰插曲)
        Uri uri = MediaStore.Audio.Media.getContentUriForPath(mSetRingtoneMessage.musicFile.getAbsolutePath());
        mActivity.getContentResolver().delete(uri, null, null);
        Uri newUri = mActivity.getContentResolver().insert(uri, values);
        ContentValues values1 = new ContentValues();
        try
        {
            if (newUri != null)
            {
                values1.put(ContactsContract.Contacts.CUSTOM_RINGTONE, newUri.toString());
                //这里的mContactId是当前联系人的Id
                mActivity.getContentResolver().update(ContactsContract.Contacts.CONTENT_URI, values1, ContactsContract.Contacts._ID + " = " + getContactId(), null);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public int getContactId()
    {
        // 获得联系人的ID号
        int idColumn = mCursor.getColumnIndex(ContactsContract.Contacts._ID);
        idColumn = mCursor.getInt(idColumn);//根据列名取得该联系人的id；
        return idColumn;
    }

    public String getContactName()
    {
        // 获得联系人的ID号
        int idColumn = mCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
        return mCursor.getString(idColumn);//根据列名取得该联系人的名字；
    }

    public Cursor getCursor()
    {
        return mCursor;
    }
}
