package com.mobileapp.wowapp.driver.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.customer.utils.Converter;
import com.mobileapp.wowapp.driver.model.ServiceProviderCompaign;
import com.mobileapp.wowapp.serviceprovider.model.ServiceProvider;

import java.util.ArrayList;
import java.util.List;


public class ServiceProvierListAdapter extends RecyclerView.Adapter<ServiceProvierListAdapter.ViewHolder>
{
    private List<ServiceProvider>dataList=new ArrayList<>();
    Context mContext;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener
    {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public List<ServiceProvider> getDataList() {
        return dataList;
    }

    public ServiceProvierListAdapter(Context context, List<ServiceProvider> eventList)
    {
        this.dataList.addAll(eventList);
        mContext=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_compaign_service_list, parent, false);
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
        final ServiceProvider item = dataList.get(position);
        holder.setDetails(item,position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView mName;
        private TextView mBookingTime;
        private TextView mDuration;
        private ImageView mLocationImage;
        private MaterialButton applyButton;

        public ViewHolder(View itemView)
        {
            super(itemView);
            mName=itemView.findViewById(R.id.tv_name);
            mBookingTime=itemView.findViewById(R.id.tv_booking_time);
            mDuration=itemView.findViewById(R.id.tv_duration);
            applyButton=itemView.findViewById(R.id.button_apply);
            mLocationImage=itemView.findViewById(R.id.img_location);
            applyButton.setOnClickListener(this);
        }

         public void setDetails(final ServiceProvider item,int position)
         {
             mName.setText(item.getBusinessName());
             String startDate=Converter.TimeConverter(item.getBusinessStartTime());
             String endDate=Converter.TimeConverter(item.getBusinessEndTime());
             mBookingTime.setText("Booking Time: "+ startDate+" - "+endDate);
             mDuration.setText("Appointment Duration: "+String.valueOf(item.getDuration())+" Minutes");
             mLocationImage.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view)
                 {
                     /*Uri navigationIntentUri = Uri.parse("google.navigation:q=" +31.46503013540308 +"," + 74.3366755080716);
                     Intent mapIntent = new Intent(Intent.ACTION_VIEW, navigationIntentUri);
                     mContext.startActivity(mapIntent);*/
                 }
             });

         }

        @Override
        public void onClick(View v)
        {
            if (onItemClickListener != null)
                onItemClickListener.onItemClick(getAdapterPosition());
        }
    }

}


