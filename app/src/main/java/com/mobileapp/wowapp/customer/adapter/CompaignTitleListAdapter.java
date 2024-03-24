package com.mobileapp.wowapp.customer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.model.Compaign;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CompaignTitleListAdapter extends RecyclerView.Adapter<CompaignTitleListAdapter.ViewHolder>
{
    private List<Compaign> dataList=new ArrayList<>();
    Context mContext;
    int selected=0;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener
    {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public CompaignTitleListAdapter(Context context, List<Compaign> eventList)
    {
        this.dataList = eventList;
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
        View view = layoutInflater.inflate(R.layout.item_compaign_title, parent, false);
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
        private CircleImageView imgSource;
        private CheckBox checkBox;

        public ViewHolder(View itemView)
        {
            super(itemView);
            mTitle=itemView.findViewById(R.id.tv_title);
            imgSource=itemView.findViewById(R.id.compaign_img);
            checkBox=itemView.findViewById(R.id.checkbox);
            itemView.setOnClickListener(this);
        }

        public void setDetails(final Compaign item,int pos)
        {
            mTitle.setText(item.getName());
            Picasso.get().load(item.getDesign()).fit().into(imgSource);

            if(pos==selected)
            {
                checkBox.setChecked(true);
            }
            else
            {
                checkBox.setChecked(false);
            }

        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null)
                onItemClickListener.onItemClick(getAdapterPosition());
        }
    }
}


