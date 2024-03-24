package com.mobileapp.wowapp.driver.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.model.Offers;
import com.mobileapp.wowapp.model.SystemRequest;

import java.util.ArrayList;
import java.util.List;


public class OfferListAdapter extends RecyclerView.Adapter<OfferListAdapter.ViewHolder>
{
    private List<Offers> dataList=new ArrayList<>();
    Context mContext;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener
    {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public List<Offers> getDataList() {
        return dataList;
    }

    public OfferListAdapter(Context context, List<Offers> eventList)
    {
        this.dataList.addAll(eventList);
        mContext=context;
    }

    public void updateList(List<Offers> chatLists)
    {
        final DiffCallback diffCallback = new DiffCallback(this.dataList, chatLists);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        this.dataList.clear();
        this.dataList.addAll(chatLists);
        diffResult.dispatchUpdatesTo(this);
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_offers, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public int getItemCount()
    {
        return dataList == null? 0: dataList.size();
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position)
    {
        final Offers item = dataList.get(position);
        holder.setDetails(item,position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView mTitle;
        private TextView mDate;
        private TextView mStartTime;
        private TextView mEndTime;
        private TextView tvPrice;
        private Chip mSlots;

        public ViewHolder(View itemView)
        {
            super(itemView);
            mTitle=itemView.findViewById(R.id.tv_title);
            mDate=itemView.findViewById(R.id.tv_date);
            mStartTime=itemView.findViewById(R.id.tv_ending_time);
            mEndTime=itemView.findViewById(R.id.tv_ending_time);
            mSlots=itemView.findViewById(R.id.tv_num_cars);
            tvPrice=itemView.findViewById(R.id.tv_price);
            itemView.setOnClickListener(this);
        }

         public void setDetails(final Offers item, int position)
         {
             mTitle.setText(item.getTitle());
             mStartTime.setText(item.getStartTime());
             mEndTime.setText(item.getStartTime());
             tvPrice.setText(item.getCity());
             mSlots.setText(String.valueOf(item.getNumofcars())+" slots available");
         }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null)
                onItemClickListener.onItemClick(getAdapterPosition());
        }
    }

    public static class DiffCallback extends DiffUtil.Callback
    {
        private List<Offers> mOldList;
        private List<Offers> mNewList;

        public DiffCallback(List<Offers> oldList, List<Offers> newList) {
            this.mOldList = oldList;
            this.mNewList = newList;
        }
        @Override
        public int getOldListSize() {
            return mOldList.size();
        }

        @Override
        public int getNewListSize() {
            return mNewList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            // add a unique ID property on Contact and expose a getId() method
            return mOldList.get(oldItemPosition).getId() .equalsIgnoreCase(mNewList.get(newItemPosition).getId());
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition)
        {
            Offers oldContact = mOldList.get(oldItemPosition);
            Offers newContact = mNewList.get(newItemPosition);
            return oldContact.equals(newContact);
        }
    }



}


