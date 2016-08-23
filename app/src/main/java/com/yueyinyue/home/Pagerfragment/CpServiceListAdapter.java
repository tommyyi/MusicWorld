package com.yueyinyue.home.Pagerfragment;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xk.m.R;
import com.yueyinyue.Model.Category;
import com.yueyinyue.cp.CpActivity;

public class CpServiceListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_CELL = 1;

    private Activity activity;
    public CpServiceListAdapter(Activity activity)
    {
        this.activity = activity;
    }

    @Override
    public int getItemViewType(int position)
    {
        switch (position)
        {
            case 0:
                //return TYPE_HEADER;
            default:
                return TYPE_CELL;
        }
    }

    @Override
    public int getItemCount()
    {
        String[] cpCategoryArray = Category.getCpCategoryArray(activity.getApplicationContext());
        assert cpCategoryArray != null;
        return cpCategoryArray.length;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view;
        switch (viewType)
        {
            case TYPE_HEADER:
            {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_item_header, parent, false);
                return new RecyclerView.ViewHolder(view)
                {
                };
            }
            case TYPE_CELL:
            {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_wrapper_incpfragment, parent, false);
                return new RecyclerView.ViewHolder(view)
                {
                };
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position)
    {
        switch (getItemViewType(position))
        {
            case TYPE_HEADER:
                break;
            case TYPE_CELL:
                TextView title=(TextView)holder.itemView.findViewById(R.id.titleName);
                String[] cpCategoryArray = Category.getCpCategoryArray(activity);
                title.setText(cpCategoryArray != null ? cpCategoryArray[position] : null);
                CardView card_view=(CardView)holder.itemView.findViewById(R.id.card_view);
                card_view.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        startActivity(activity,position);
                    }
                });
                break;
        }
    }

    private void startActivity(Activity activity, int categoryNameIndex)
    {
        Intent intent=new Intent();
        intent.putExtra("categoryNameIndex",categoryNameIndex);
        intent.setClass(activity, CpActivity.class);

        activity.startActivity(intent);
    }
}