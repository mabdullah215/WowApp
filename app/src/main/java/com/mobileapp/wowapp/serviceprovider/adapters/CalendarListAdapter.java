package com.mobileapp.wowapp.serviceprovider.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.driver.model.Document;
import com.mobileapp.wowapp.serviceprovider.model.DateModel;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class CalendarListAdapter extends RecyclerView.Adapter<CalendarListAdapter.ViewHolder>
{
    Context mContext;
    private OnItemClickListener onItemClickListener;
    List<DateModel>list=new ArrayList<>();
    int selected=0;
    public interface OnItemClickListener
    {
        void onItemClick(int position);
    }

    public void setSelected(int selected) {
        this.selected = selected;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public CalendarListAdapter(Context context, List<DateModel>list)
    {
        mContext=context;
        this.list=list;
    }

    public void updateEventList(HashMap<String,DateModel>updateMap)
    {
        for (Map.Entry<String, DateModel> entry : updateMap.entrySet())
        {
            DateModel updatedModel = entry.getValue();
            for (int i = 0; i < list.size(); i++)
            {
                DateModel originalModel = list.get(i);
                if (originalModel.getDate().equalsIgnoreCase(updatedModel.getDate()))
                {
                    originalModel.setEvents(updatedModel.getEvents());
                    notifyItemChanged(i);
                    break; // Exit loop once the update is done
                }
            }
        }

    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_timeline, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public int getItemCount()
    {
        return list.size();
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position)
    {
        DateModel item=list.get(position);
        holder.setDetails(item,position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView mtvMonth,mTvDate,mTvEvents;
        MaterialCardView mCard;
        public ViewHolder(View itemView)
        {
            super(itemView);
            mTvEvents=itemView.findViewById(R.id.tv_events);
            mtvMonth=itemView.findViewById(R.id.monthView);
            mTvDate=itemView.findViewById(R.id.dateView);
            mCard=itemView.findViewById(R.id.material_card);
            itemView.setOnClickListener(this);
        }

        public void setDetails(final DateModel item,int position)
        {
            mtvMonth.setText(item.getMonth());
            mTvDate.setText(item.getDay());
            if(item.getEvents()>0)
            {
                mTvEvents.setText(""+item.getEvents());
            }
            else
            {
                mTvEvents.setText("");
            }

            if(position==selected)
            {
                mCard.setCardBackgroundColor(Color.parseColor("#006cab"));
                mtvMonth.setTextColor(Color.WHITE);
                mTvDate.setTextColor(Color.WHITE);
                mTvEvents.setTextColor(Color.WHITE);
            }
            else
            {
                mCard.setCardBackgroundColor(Color.WHITE);
                mtvMonth.setTextColor(Color.BLACK);
                mTvDate.setTextColor(Color.BLACK);
                mTvEvents.setTextColor(Color.parseColor("#006cab"));
            }

        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null)
                onItemClickListener.onItemClick(getAdapterPosition());
        }
    }
}


