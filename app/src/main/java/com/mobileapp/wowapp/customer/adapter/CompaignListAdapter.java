package com.mobileapp.wowapp.customer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.customer.model.Analytics;
import com.mobileapp.wowapp.model.Compaign;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CompaignListAdapter extends RecyclerView.Adapter<CompaignListAdapter.ViewHolder>
{
    private List<Compaign> dataList=new ArrayList<>();
    Context mContext;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener
    {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public CompaignListAdapter(Context context, List<Compaign> eventList)
    {
        this.dataList.clear();
        this.dataList.addAll(eventList);
        mContext=context;
    }

    public List<Compaign> getDataList() {
        return dataList;
    }

    public void setDataList(List<Compaign> dataList) {
        this.dataList = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_active_compaigns_list, parent, false);
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
        final Compaign item = dataList.get(position);
        holder.setDetails(item,position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        private TextView mTitle;
        private TextView mDistance;
        private TextView mImpressions;
        private ImageView imgSource;

        public ViewHolder(View itemView)
        {
            super(itemView);
            mTitle=itemView.findViewById(R.id.tv_title);
            imgSource=itemView.findViewById(R.id.img_source);
            mDistance=itemView.findViewById(R.id.tv_distance);
            mImpressions=itemView.findViewById(R.id.tv_impressions);
            itemView.setOnClickListener(this);
        }

        public void setDetails(final Compaign item,int pos)
        {
            mTitle.setText(item.getName());
            if(item.getDesign()!=null&&!item.getDesign().isEmpty())
            {
                Picasso.get().load(item.getDesign()).fit().into(imgSource);
            }

            mDistance.setText(item.getTotalDistanceCovered()+" Km");
            mImpressions.setText("0");
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null)
                onItemClickListener.onItemClick(getAdapterPosition());
        }
    }
}


