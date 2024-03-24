package com.mobileapp.wowapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.model.City;

import java.util.ArrayList;
import java.util.List;

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.ViewHolder> {

    private List<City> dataList=new ArrayList<>();
    private OnItemClickListener onItemClickListener;
    int selected=0;
    public interface OnItemClickListener
    {
        void onItemClick(int position);
    }

    public List<City> getDataList() {
        return dataList;
    }

    public CityListAdapter(Context context, List<City> eventList) {
        this.dataList = eventList;
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_city, parent, false);
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
        final City item = dataList.get(position);
        holder.setDetails(item,position);
    }

    public int getSelected() {
        return selected;
    }

    public void selectPosition(int postion)
    {
        selected = postion;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView mTitle;
        ImageView checkedImage;

        public ViewHolder(View itemView)
        {
            super(itemView);
            mTitle = itemView.findViewById(R.id.tv_name);
            checkedImage=itemView.findViewById(R.id.iv_checked);
            itemView.setOnClickListener(this);
        }

         public void setDetails(City item,int pos)
        {
            if(pos==selected)
            {
                checkedImage.setVisibility(View.VISIBLE);
            }
            else
            {
                checkedImage.setVisibility(View.INVISIBLE);
            }

            mTitle.setText(item.getName());
        }



        @Override
        public void onClick(View v)
        {
            if (onItemClickListener != null)
                onItemClickListener.onItemClick(getAdapterPosition());
        }
    }


}


