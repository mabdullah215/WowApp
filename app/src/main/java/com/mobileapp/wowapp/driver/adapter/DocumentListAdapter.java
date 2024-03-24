package com.mobileapp.wowapp.driver.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.driver.model.Document;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DocumentListAdapter extends RecyclerView.Adapter<DocumentListAdapter.ViewHolder>
{
    Context mContext;
    private OnItemClickListener onItemClickListener;
    List<Document>documents=new ArrayList<>();
    public interface OnItemClickListener
    {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public DocumentListAdapter(Context context,List<Document>imgList)
    {
        mContext=context;
        this.documents=imgList;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void updateItem(String item, int position)
    {
        documents.get(position).setImgRes(item);
        notifyItemChanged(position);
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_update_documents, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public int getItemCount()
    {
        return 5;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position)
    {
        Document item=documents.get(position);
        holder.setDetails(item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView mTxt;
        private ImageView mImage;

        public ViewHolder(View itemView)
        {
            super(itemView);
            mTxt=itemView.findViewById(R.id.tv_title);
            mImage=itemView.findViewById(R.id.img_source);
            itemView.setOnClickListener(this);
        }

        public void setDetails(final Document item)
        {
            mTxt.setText(item.getTitle());

            if(!item.getImgRes().isEmpty())
            {
                Picasso.get().load(item.getImgRes()).fit().into(mImage);
            }
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null)
                onItemClickListener.onItemClick(getAdapterPosition());
        }
    }
}


