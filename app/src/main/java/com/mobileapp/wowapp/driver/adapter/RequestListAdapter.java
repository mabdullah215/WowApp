package com.mobileapp.wowapp.driver.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.model.Compaign;
import com.mobileapp.wowapp.model.SystemRequest;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class RequestListAdapter extends RecyclerView.Adapter<RequestListAdapter.ViewHolder>
{
    private List<SystemRequest> dataList=new ArrayList<>();
    Context mContext;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener
    {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public List<SystemRequest> getDataList() {
        return dataList;
    }

    public RequestListAdapter(Context context, List<SystemRequest> eventList)
    {
        this.dataList.addAll(eventList);
        mContext=context;
    }

    public void updateList(List<SystemRequest> chatLists)
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
        View view = layoutInflater.inflate(R.layout.item_request, parent, false);
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
        final SystemRequest item = dataList.get(position);
        holder.setDetails(item,position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private Chip mTitle;
        private TextView mDuration;
        private TextView mDesc;

        public ViewHolder(View itemView)
        {
            super(itemView);
            mTitle=itemView.findViewById(R.id.tv_title);
            mDuration=itemView.findViewById(R.id.tv_duration);
            mDesc=itemView.findViewById(R.id.tv_desc);
            itemView.setOnClickListener(this);
        }

         public void setDetails(final SystemRequest item,int position)
         {
             mTitle.setText(item.getTitle());
             mDuration.setText(item.getEndTime());
             mDesc.setText(item.getDescription());
         }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null)
                onItemClickListener.onItemClick(getAdapterPosition());
        }
    }

    public static class DiffCallback extends DiffUtil.Callback
    {
        private List<SystemRequest> mOldList;
        private List<SystemRequest> mNewList;

        public DiffCallback(List<SystemRequest> oldList, List<SystemRequest> newList) {
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
            SystemRequest oldContact = mOldList.get(oldItemPosition);
            SystemRequest newContact = mNewList.get(newItemPosition);
            return oldContact.equals(newContact);
        }
    }



}


