package com.mobileapp.wowapp.serviceprovider.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.customer.utils.Converter;
import com.mobileapp.wowapp.model.Appointment;
import com.mobileapp.wowapp.serviceprovider.model.ShopAppointment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class ShopAppointmentListAdapter extends RecyclerView.Adapter<ShopAppointmentListAdapter.ViewHolder>
{
    private List<ShopAppointment>dataList=new ArrayList<>();
    Context mContext;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener
    {
        void onItemClick(int position);
        void onDelete(int position);
        void onReshcedule(int position);
        void mapClick(int positon);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public List<ShopAppointment> getDataList() {
        return dataList;
    }

    public ShopAppointmentListAdapter(Context context, List<ShopAppointment> eventList)
    {
        this.dataList.addAll(eventList);
        mContext=context;
    }

    public void updateList(List<ShopAppointment> chatLists)
    {
        this.dataList.clear();
        this.dataList.addAll(chatLists);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_shop_appointment, parent, false);
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
        final ShopAppointment item = dataList.get(position);
        holder.setDetails(item,position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView mName;
        private TextView mCarMake;
        private TextView mCarNo;
        private TextView mType;
        private ImageView imgProfile;
        private TextView tvAppointmentTime;
        private Chip status;


        public ViewHolder(View itemView)
        {
            super(itemView);
            mType=itemView.findViewById(R.id.tv_appointmentType);
            status=itemView.findViewById(R.id.chip_status);
            mName=itemView.findViewById(R.id.tv_driver_name);
            mCarMake=itemView.findViewById(R.id.tv_car_make);
            mCarNo=itemView.findViewById(R.id.tv_car_number);
            imgProfile=itemView.findViewById(R.id.user_profile);
            tvAppointmentTime=itemView.findViewById(R.id.tv_appointmentTime);
            itemView.setOnClickListener(this);
        }

         public void setDetails(final ShopAppointment item,int position)
         {
             mName.setText(item.getDriver().getName());
             if(item.getDriver().getId()==62)
             {
                 mCarMake.setText("Ford fusion");
                 mCarNo.setText("2341-ABC");
             }
             else
             {
                 mCarMake.setText(item.getDriver().getCar_make()+" "+item.getDriver().getCar_model());
                 mCarNo.setText(item.getDriver().getCar_type());
             }
             Picasso.get().load(item.getDriver().getProfile_pic()).into(imgProfile);
             tvAppointmentTime.setText(Converter.shortCustomFormat(item.getAppointment_time()));
             if(item.getSticker_request()==1)
             {
                 mType.setText("Sticker Apply");
             }
             else
             {
                 mType.setText("Sticker removal");
             }

             if(item.getStatus().equalsIgnoreCase("Pending"))
             {
                 status.setChipBackgroundColor(ColorStateList.valueOf(Color.parseColor("#b45f06")));
             }
             else if(item.getStatus().equalsIgnoreCase("Completed"))
             {
                 status.setChipBackgroundColor(ColorStateList.valueOf(Color.parseColor("#198c19")));
             }
             else if(item.getStatus().equalsIgnoreCase("Failed"))
             {
                 status.setChipBackgroundColor(ColorStateList.valueOf(Color.RED));
             }
             status.setText(item.getStatus());
         }

        @Override
        public void onClick(View v)
        {
            if (onItemClickListener != null)
                onItemClickListener.onItemClick(getAdapterPosition());
        }
    }




}


