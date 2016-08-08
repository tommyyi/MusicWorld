package com.yueyinyue.cp.dialog;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.yueyinyue.Model.EventBusMessage.Reach2LimitedMessage;
import com.yueyinyue.Model.MusicItem;
import com.xk.m.R;
import com.yueyinyue.cp.dialog.tick.presenter.TickPresenter;
import com.yueyinyue.cp.dialog.tick.presenter.TickPresenterImpl;

import java.util.List;

import de.greenrobot.event.EventBus;

public class MusicSelectDialogAdapter extends BaseAdapter implements TickViewImpl
{
    private Activity activity;
    private List<MusicItem> musicItemList;
    private TickPresenterImpl mTickPresenterImpl;

    public MusicSelectDialogAdapter(Activity activity, List<MusicItem> musicItemList, int limited)
    {
        this.musicItemList = musicItemList;
        this.activity = activity;
        mTickPresenterImpl = new TickPresenter(this, musicItemList, limited);
    }

    @Override
    public int getCount()
    {
        return musicItemList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        Log.i("checkBox row", "row=" + position);
        if(convertView==null)
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_item_wrapper_in_cp_music_select_dialog, parent, false);
        new MusicItemViewHolderInSelectDialog(activity, convertView, musicItemList, position, mTickPresenterImpl);

        return convertView;
    }

    @Override
    public void showSelectedItemsExceedLimitedMsg(int limited)
    {
        EventBus.getDefault().post(new Reach2LimitedMessage(limited));
    }
}