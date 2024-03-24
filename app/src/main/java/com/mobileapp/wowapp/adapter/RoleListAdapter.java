package com.mobileapp.wowapp.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.mobileapp.wowapp.R;

public class RoleListAdapter extends RecyclerView.Adapter<RoleListAdapter.ViewHolder>
{
    Context mContext;
    private OnItemClickListener onItemClickListener;
    int selected=0;
    int [] imgList={R.drawable.business_owner,R.drawable.driver,R.drawable.shop};
    String [] titleList={"Customer","Driver","Service Provider"};

    public interface OnItemClickListener
    {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public RoleListAdapter(Context context)
    {
        mContext=context;
    }

    public void setSelected(int selected) {
        this.selected = selected;
        notifyDataSetChanged();
    }

    public int getSelected() {
        return selected;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_role, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public int getItemCount()
    {
        return 3;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position)
    {
        String item=titleList[position];
        holder.setDetails(item,position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private Chip mTxt;
        private ImageView imgSource;

        public ViewHolder(View itemView)
        {
            super(itemView);
            mTxt=itemView.findViewById(R.id.chip_txt);
            imgSource=itemView.findViewById(R.id.img_source);
            itemView.setOnClickListener(this);
        }

        public void setDetails(final String item,int pos)
        {
            mTxt.setText(item);
            imgSource.setImageResource(imgList[pos]);
            if(pos==selected)
            {
                mTxt.setChipBackgroundColor(ColorStateList.valueOf(Color.parseColor("#333333")));
                mTxt.setTextColor(Color.WHITE);
            }
            else
            {
                mTxt.setChipBackgroundColor(ColorStateList.valueOf(Color.parseColor("#ffffff")));
                mTxt.setTextColor(Color.BLACK);
            }
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null)
                onItemClickListener.onItemClick(getAdapterPosition());
        }
    }
}


