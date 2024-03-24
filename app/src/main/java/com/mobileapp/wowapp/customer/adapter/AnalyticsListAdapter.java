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
import java.util.ArrayList;
import java.util.List;

public class AnalyticsListAdapter extends RecyclerView.Adapter<AnalyticsListAdapter.ViewHolder>
{
    private List<Analytics> dataList=new ArrayList<>();
    Context mContext;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener
    {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public AnalyticsListAdapter(Context context, List<Analytics> eventList)
    {
        this.dataList = eventList;
        mContext=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_analytics, parent, false);
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
        final Analytics item = dataList.get(position);
        holder.setDetails(item,position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        private TextView mTitle,mDesc;
        private Chip mCount;
        private ImageView imgSource;

        public ViewHolder(View itemView)
        {
            super(itemView);
            mTitle=itemView.findViewById(R.id.tv_title);
            mDesc=itemView.findViewById(R.id.tv_desc);
            mCount=itemView.findViewById(R.id.chip_count);
            imgSource=itemView.findViewById(R.id.src_img);
            itemView.setOnClickListener(this);
        }

        public void setDetails(final Analytics item,int pos)
        {
            mTitle.setText(item.getTitle());
            mDesc.setText(item.getDesc());
            mCount.setText(String.valueOf(item.getCount()));
            if(pos==0)
            {
                imgSource.setImageResource(R.drawable.ic_heat);
            }
            else
            {
                imgSource.setImageResource(R.drawable.ic_impression);
            }
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null)
                onItemClickListener.onItemClick(getAdapterPosition());
        }
    }
}


