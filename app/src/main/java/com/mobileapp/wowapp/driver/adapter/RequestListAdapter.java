package com.mobileapp.wowapp.driver.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.customer.utils.Converter;
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
        this.dataList.clear();
        this.dataList.addAll(chatLists);
        notifyDataSetChanged();
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
        private TextView mTitle;
        private TextView mDuration;
        private TextView mDesc;
        TextView tvUpload;
        Chip chip_status;

        public ViewHolder(View itemView)
        {
            super(itemView);
            mTitle=itemView.findViewById(R.id.tv_title);
            mDuration=itemView.findViewById(R.id.tv_duration);
            mDesc=itemView.findViewById(R.id.tv_desc);
            chip_status=itemView.findViewById(R.id.chip_status);
            tvUpload=itemView.findViewById(R.id.tv_take_img);
            itemView.setOnClickListener(this);
        }

         public void setDetails(final SystemRequest item,int position)
         {
             mTitle.setText(item.getTitle());
             String duration=Converter.getDaysDifference(item.getExpiry_at());
             //mDuration.setText(duration);
             mDuration.setText(null);
             //mDesc.setText(item.getDescription());
             if(item.getStatus().equalsIgnoreCase("pending")||item.getStatus().equalsIgnoreCase("rejected"))
             {
                 tvUpload.setVisibility(View.VISIBLE);
                 chip_status.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(mContext,R.color.stop_driving)));
             }
             else
             {
                 tvUpload.setVisibility(View.GONE);
                 chip_status.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(mContext,R.color.purple_200)));
             }
             chip_status.setText(item.getStatus());
         }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null)
                onItemClickListener.onItemClick(getAdapterPosition());
        }
    }

}


