package com.mobileapp.wowapp.driver;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.mobileapp.wowapp.BaseActivity;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.customer.utils.Converter;
import com.mobileapp.wowapp.driver.adapter.DocumentListAdapter;
import com.mobileapp.wowapp.driver.model.Document;
import com.mobileapp.wowapp.interations.IResultData;
import com.mobileapp.wowapp.model.SystemRequest;
import com.mobileapp.wowapp.network.APIList;
import com.mobileapp.wowapp.network.APIResult;
import com.mobileapp.wowapp.network.NetworkManager;
import com.mobileapp.wowapp.serviceprovider.AppointmentDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RequestDetails extends BaseActivity
{
    int adapterPosition=0;
    DocumentListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_details);
        TextView tvTitle=findViewById(R.id.tv_title);
        TextView tvDesc=findViewById(R.id.tv_desc);
        TextView tvDuration=findViewById(R.id.tv_duration);
        tvTitle.setText("Reqeust Details");
        SystemRequest request=(SystemRequest)getIntent().getSerializableExtra("request");
        TextView tvRequestTitle=findViewById(R.id.tv_request_title);
        TextView tvNote=findViewById(R.id.tv_note);
        tvNote.setText("Note: "+request.getRejection_note());
        tvRequestTitle.setText(request.getTitle());
        tvDesc.setText(request.getDescription());
        EditText etComments=findViewById(R.id.et_comments);
        etComments.setText(request.getComments());
        tvDuration.setText(Converter.getDaysDifference(request.getExpiry_at()));
        MaterialButton submitButton=findViewById(R.id.button_submit);
        NetworkManager manager=NetworkManager.getInstance(getBaseContext());
        RecyclerView recyclerView=findViewById(R.id.data_list);
        recyclerView.setLayoutManager(new GridLayoutManager(getBaseContext(),2));
        if(request.getStatus().equalsIgnoreCase("pending"))
        {
            List<Document> documents=new ArrayList<>();
            for(int i=0;i<request.getNo_of_photos();i++)
            {
                documents.add(new Document(""));
            }
            adapter=new DocumentListAdapter(getBaseContext(),documents);
        }
        else
        {
            List<Document> documents=new ArrayList<>();
            for(int i=0;i<request.getPhotos().size();i++)
            {
                Document document=new Document("");
                document.setImgRes(request.getPhotos().get(i).getLink());
                documents.add(document);
            }
            adapter=new DocumentListAdapter(getBaseContext(),documents);
        }

        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new DocumentListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position)
            {
                adapterPosition=position;
                ImagePicker.Companion.with(RequestDetails.this).galleryOnly().cropSquare().compress(200).start();
            }
        });

        if(request.getStatus().equalsIgnoreCase("pending")||request.getStatus().equalsIgnoreCase("rejected"))
        {
            submitButton.setVisibility(View.VISIBLE);
        }
        else
        {
            submitButton.setVisibility(View.GONE);
        }

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                boolean upload=true;
                HashMap<String,Object>map=new HashMap<>();
                for(int i=0;i<adapter.getDocuments().size();i++)
                {
                    if(adapter.getDocuments().get(i).getImgRes().isEmpty())
                    {
                        upload=false;
                    }
                    else if(!adapter.getDocuments().get(i).getImgRes().startsWith("https:\\/\\/wow-bucket.s3.us-east-2.amazonaws.com"))
                    {
                        map.put("image"+i,adapter.getDocuments().get(i).getImgRes());
                    }
                }

                if(!upload)
                {
                    Toast.makeText(RequestDetails.this, "Please upload all requested images", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    showLoading();
                    String comments=etComments.getText().toString().trim();

                    if(!comments.isEmpty()||map.isEmpty())
                    {
                        map.put("requestId",request.getId());
                        map.put("comments",comments);
                    }

                    manager.postRequest(APIList.SUBMIT_REQUEST,map, new IResultData() {
                        @Override
                        public void notifyResult(String result)
                        {
                            hideLoading();
                            APIResult apiResult=new Gson().fromJson(result,APIResult.class);
                            Toast.makeText(RequestDetails.this,apiResult.getMessage(), Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }
            }
        });

        ImageView imgBack=findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Animatoo.INSTANCE.animateSlideRight(RequestDetails.this);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK)
        {
            Uri uri=data.getData();
            adapter.updateItem(uri.toString(),adapterPosition);
        }

    }
}