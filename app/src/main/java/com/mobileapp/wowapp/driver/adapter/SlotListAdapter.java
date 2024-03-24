package com.mobileapp.wowapp.driver.adapter;

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
import com.mobileapp.wowapp.driver.model.Slot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SlotListAdapter extends RecyclerView.Adapter<SlotListAdapter.ViewHolder>
{
    Context mContext;
    private OnItemClickListener onItemClickListener;
    List<Slot>documents=new ArrayList<>();
    int selected=-1;
    public interface OnItemClickListener
    {
        void onItemClick(int position);
    }

    public void setSelected(int selected) {
        this.selected = selected;
        notifyDataSetChanged();
    }

    public int getSelected() {
        return selected;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public SlotListAdapter(Context context, List<Slot>imgList)
    {
        mContext=context;
        this.documents=imgList;
    }


    public List<Slot> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Slot> documents)
    {
        this.documents.clear();
        this.documents.addAll(documents);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_slot, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public int getItemCount()
    {
        return documents.size();
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position)
    {
        Slot item=documents.get(position);
        holder.setDetails(item,position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView mStartTime;
        private TextView mEndTime;
        private TextView mLabel;
        private MaterialCardView cardView;

        public ViewHolder(View itemView)
        {
            super(itemView);
            mStartTime=itemView.findViewById(R.id.tv_start_time);
            mEndTime=itemView.findViewById(R.id.tv_end_time);
            mLabel=itemView.findViewById(R.id.tv_label);
            cardView=itemView.findViewById(R.id.card_view);
            itemView.setOnClickListener(this);
        }

        public void setDetails(final Slot item,int position)
        {
            mStartTime.setText(item.getStartTime());
            mEndTime.setText(item.getEndTime());

            if(item.isBooked())
            {
                cardView.getBackground().setTint(Color.parseColor("#fafafa"));
                mStartTime.setTextColor(Color.parseColor("#b9b9b9"));
                mEndTime.setTextColor(Color.parseColor("#b9b9b9"));
                mLabel.setTextColor(Color.parseColor("#b9b9b9"));
            }
            else
            {
                if(position==selected)
                {
                    cardView.getBackground().setTint(Color.parseColor("#3a3a3a"));
                    mStartTime.setTextColor(Color.parseColor("#ffffff"));
                    mEndTime.setTextColor(Color.parseColor("#ffffff"));
                    mLabel.setTextColor(Color.parseColor("#ffffff"));
                }
                else
                {
                    cardView.getBackground().setTint(Color.parseColor("#ffffff"));
                    mStartTime.setTextColor(Color.parseColor("#3a3a3a"));
                    mEndTime.setTextColor(Color.parseColor("#3a3a3a"));
                    mLabel.setTextColor(Color.parseColor("#3a3a3a"));
                }
            }
        }

        @Override
        public void onClick(View v)
        {
            Slot item=documents.get(getAdapterPosition());
            if (onItemClickListener != null&&!item.isBooked())
                onItemClickListener.onItemClick(getAdapterPosition());
        }
    }
}


