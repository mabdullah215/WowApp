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

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.customer.utils.Converter;
import com.mobileapp.wowapp.model.Compaign;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class CompaignListAdapter extends RecyclerView.Adapter<CompaignListAdapter.ViewHolder>
{
    private List<Compaign>dataList=new ArrayList<>();
    Context mContext;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener
    {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public List<Compaign> getDataList() {
        return dataList;
    }

    public CompaignListAdapter(Context context, List<Compaign> eventList)
    {
        this.dataList.addAll(eventList);
        mContext=context;
    }

    public void updateList(List<Compaign> chatLists)
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
        View view = layoutInflater.inflate(R.layout.item_upcoming_compaigns, parent, false);
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
        private ImageView mAdvertisedImage;
        private TextView mTitle;
        private TextView mStartDate;
        private TextView mDuration;
        private TextView mSpots;
        private TextView mCity;
        private TextView mEndDate;
        private TextView mDistance;
        private MaterialButton applyButton;

        public ViewHolder(View itemView)
        {
            super(itemView);
            mAdvertisedImage=itemView.findViewById(R.id.img_advertised);
            mTitle=itemView.findViewById(R.id.tv_title);
            mDuration=itemView.findViewById(R.id.tv_duration);
            mCity=itemView.findViewById(R.id.tv_city);
            mStartDate=itemView.findViewById(R.id.tv_start_date);
            mEndDate=itemView.findViewById(R.id.tv_end_date);
            mSpots=itemView.findViewById(R.id.tv_spots);
            mDistance=itemView.findViewById(R.id.tv_kms);
            itemView.setOnClickListener(this);
        }

         public void setDetails(final Compaign item,int position)
         {
             mTitle.setText(item.getName());
             String startDate=Converter.datePreview(item.getStartTime());
             String endDate=Converter.datePreview(item.getEndTime());
             mStartDate.setText(startDate);
             mEndDate.setText(endDate);
             mDuration.setText(Converter.getDifferenceInDaysAndMonths(startDate,endDate));
             mDistance.setText(item.getKms()+" Km/Day");
             mCity.setText(item.getCity().getName());
             mSpots.setText(""+(item.getNoOfCars()-item.getTotalAssighnedDrivers()));
             if(!item.getDesign().isEmpty())
             Picasso.get().load(item.getDesign()).fit().into(mAdvertisedImage);

         }

        @Override
        public void onClick(View v)
        {
            if (onItemClickListener != null)
                onItemClickListener.onItemClick(getAdapterPosition());
        }
    }

    public static class DiffCallback extends DiffUtil.Callback
    {
        private List<Compaign> mOldList;
        private List<Compaign> mNewList;

        public DiffCallback(List<Compaign> oldList, List<Compaign> newList) {
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
            return mOldList.get(oldItemPosition).getId()==(mNewList.get(newItemPosition).getId());
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition)
        {
            Compaign oldContact = mOldList.get(oldItemPosition);
            Compaign newContact = mNewList.get(newItemPosition);
            return oldContact.equals(newContact);
        }
    }



}


