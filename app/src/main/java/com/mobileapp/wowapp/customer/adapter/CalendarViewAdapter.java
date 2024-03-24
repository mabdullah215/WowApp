package com.mobileapp.wowapp.customer.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.card.MaterialCardView;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.customer.model.DayModel;

import java.util.List;

public class CalendarViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private List<DayModel> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    int selected=-1;

    public int getSelected() {
        return selected;
    }

    private  int TYPE_EMPTY = -1;
    // data is passed into the constructor
    public CalendarViewAdapter(Context context, List<DayModel> data)
    {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    public void selectedPosition(int postion)
    {
        selected = postion;
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view;
        view=mInflater.inflate(R.layout.control_calendar_day, parent, false);
        if(viewType==TYPE_EMPTY)
        {
            return new EmptyViewHolder(view);
        }
        else
        {
            return new CellViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        if (getItemViewType(position) == TYPE_EMPTY)
        {
            ((EmptyViewHolder) holder).setDetails();
        }

        else
        {
            ((CellViewHolder) holder).setDetails(position);
        }
    }

    @Override
    public int getItemViewType(int position)
    {
        return mData.get(position).getDate();
    }

    public class EmptyViewHolder extends RecyclerView.ViewHolder
    {

        TextView myTextView;
        MaterialCardView cellCard;

        EmptyViewHolder(@NonNull View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.circular_text);
            cellCard=itemView.findViewById(R.id.cell_card);
        }

        void setDetails()
        {
            myTextView.setText("");
            cellCard.setVisibility(View.GONE);
        }
    }


    public class CellViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        MaterialCardView cellCard;
        CellViewHolder(View itemView)
        {
            super(itemView);
            myTextView = itemView.findViewById(R.id.circular_text);
            cellCard=itemView.findViewById(R.id.cell_card);
            itemView.setOnClickListener(this);
        }

        public void setDetails(int position)
        {
            DayModel item= mData.get(position);
            myTextView.setText(""+item.getDate());

            if(item.isDisabled())
            {
               // cellCard.getBackground().setTint(Color.parseColor("#f8f8f8"));
                myTextView.setTextColor(Color.parseColor("#b9b9b9"));
            }
            else
            {
                if(position==selected)
                {
                    cellCard.getBackground().setTint(Color.parseColor("#3a3a3a"));
                    myTextView.setTextColor(Color.parseColor("#ffffff"));
                }
                else
                {
                    cellCard.getBackground().setTint(Color.parseColor("#ffffff"));
                    myTextView.setTextColor(Color.parseColor("#3a3a3a"));
                }
            }

        }

        @Override
        public void onClick(View view)
        {
            if (mClickListener != null)
            {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }



    public List<DayModel>getCurrentDataList()
    {
        return mData;
    }



    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public long getItemId(int position)
    {
       return position;
    }

    // stores and recycles views as they are scrolled off screen


    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}