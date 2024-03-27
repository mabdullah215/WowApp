package com.mobileapp.wowapp.driver.adapter;

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

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.customer.utils.Converter;
import com.mobileapp.wowapp.model.Appointment;
import com.mobileapp.wowapp.model.Compaign;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class AppointmentListAdapter extends RecyclerView.Adapter<AppointmentListAdapter.ViewHolder>
{
    private List<Appointment>dataList=new ArrayList<>();
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

    public List<Appointment> getDataList() {
        return dataList;
    }

    public AppointmentListAdapter(Context context, List<Appointment> eventList)
    {
        this.dataList.addAll(eventList);
        mContext=context;
    }

    public void updateList(List<Appointment> chatLists)
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
        View view = layoutInflater.inflate(R.layout.item_appointment, parent, false);
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
        final Appointment item = dataList.get(position);
        holder.setDetails(item,position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView mTitle;
        private TextView mShop;
        private TextView mCity;
        private TextView mDate;
        private TextView mType;
        private Chip status;
        private ImageView mImageDelete;
        private ImageView mImageReschedule;
        private ImageView mImageMaps;
        private LinearLayout editLayout;

        public ViewHolder(View itemView)
        {
            super(itemView);
            mTitle=itemView.findViewById(R.id.tv_appointmentTitle);
            mShop=itemView.findViewById(R.id.tv_shop);
            mCity=itemView.findViewById(R.id.tv_city);
            mDate=itemView.findViewById(R.id.tv_appointmentTime);
            mType=itemView.findViewById(R.id.tv_appointmentType);
            mImageDelete=itemView.findViewById(R.id.img_delete);
            mImageReschedule=itemView.findViewById(R.id.img_reschedule);
            mImageMaps=itemView.findViewById(R.id.img_maps);
            status=itemView.findViewById(R.id.chip_status);
            editLayout=itemView.findViewById(R.id.edit_layout);
            itemView.setOnClickListener(this);
        }

         public void setDetails(final Appointment item,int position)
         {
             mTitle.setText(item.getShop().getName());
             mShop.setText("Shop: "+item.getShop().getBusinessName());
             mCity.setText("City: "+item.getShop().getCity());
             mDate.setText("Time: "+ Converter.convertToCustomFormat(item.getAppointment_time()));
             if(item.getSticker_request()==1)
             {
                 mType.setText("Type: Sticker Apply");
             }
             else
             {
                 mType.setText("Type: Sticker removal");
             }

             if(item.getStatus().equalsIgnoreCase("Pending"))
             {
                 editLayout.setVisibility(View.VISIBLE);
                 status.setChipBackgroundColor(ColorStateList.valueOf(Color.parseColor("#b45f06")));
             }
             else if(item.getStatus().equalsIgnoreCase("Completed"))
             {
                 editLayout.setVisibility(View.GONE);
                 status.setChipBackgroundColor(ColorStateList.valueOf(Color.parseColor("#198c19")));
             }
             else if(item.getStatus().equalsIgnoreCase("Failed"))
             {
                 editLayout.setVisibility(View.GONE);
                 status.setChipBackgroundColor(ColorStateList.valueOf(Color.RED));
             }
             status.setText(item.getStatus());
             mImageDelete.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view)
                 {
                     if (onItemClickListener != null)
                         onItemClickListener.onDelete(getAdapterPosition());
                 }
             });
             mImageReschedule.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     if (onItemClickListener != null)
                         onItemClickListener.onReshcedule(getAdapterPosition());
                 }
             });
             mImageMaps.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     if (onItemClickListener != null)
                         onItemClickListener.mapClick(getAdapterPosition());
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

    public static class DiffCallback extends DiffUtil.Callback
    {
        private List<Appointment> mOldList;
        private List<Appointment> mNewList;

        public DiffCallback(List<Appointment> oldList, List<Appointment> newList) {
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
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition)
        {
            return mOldList.get(oldItemPosition)==(mNewList.get(newItemPosition));
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition)
        {
            Appointment oldContact = mOldList.get(oldItemPosition);
            Appointment newContact = mNewList.get(newItemPosition);
            return oldContact.equals(newContact);
        }
    }



}


