package com.mobileapp.wowapp.serviceprovider;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.mobileapp.wowapp.BaseActivity;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.customer.utils.Converter;
import com.mobileapp.wowapp.driver.DocumentsInformation;
import com.mobileapp.wowapp.driver.adapter.DocumentListAdapter;
import com.mobileapp.wowapp.driver.model.Document;
import com.mobileapp.wowapp.interations.IResultData;
import com.mobileapp.wowapp.network.APIList;
import com.mobileapp.wowapp.network.APIResult;
import com.mobileapp.wowapp.network.APIResultSingle;
import com.mobileapp.wowapp.network.NetworkManager;
import com.mobileapp.wowapp.serviceprovider.model.ShopAppointment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AppointmentDetails extends BaseActivity {

    int adapterPosition=0;
    DocumentListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_details);
        ShopAppointment appointment=(ShopAppointment) getIntent().getSerializableExtra("appointment");
        TextView tvTitle=findViewById(R.id.tv_title);
        tvTitle.setText("Appointment Details");
        ImageView imgBack=findViewById(R.id.img_back);
        TextView tvDriverName=findViewById(R.id.tv_driver_name);
        tvDriverName.setText(appointment.getDriver().getName());
        TextView tvTimings=findViewById(R.id.tv_time);
        tvTimings.setText(Converter.shortCustomFormat(appointment.getAppointment_time()));
        TextView tvAppointmentType=findViewById(R.id.tv_appointment_tyoe);
        TextView tvCampaignName=findViewById(R.id.tv_campaign);
        tvCampaignName.setText(appointment.getCampaign().getName());
        ImageView imgDriver=findViewById(R.id.img_driver_name);
        ImageView imgCampaign=findViewById(R.id.img_campaign);
        LinearLayout installLayout=findViewById(R.id.installation_layout);
        NetworkManager manager=NetworkManager.getInstance(this);
        if(appointment.getStatus().equalsIgnoreCase("pending"))
        {
            installLayout.setVisibility(View.VISIBLE);
            RecyclerView recyclerView=findViewById(R.id.photos_list);
            recyclerView.setLayoutManager(new GridLayoutManager(this,2));
            List<Document>documents=new ArrayList<>();
            documents.add(new Document("Front Side"));
            documents.add(new Document("Back Side"));
            documents.add(new Document("Left Side"));
            documents.add(new Document("Right Side"));
            adapter=new DocumentListAdapter(this,documents);
            recyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener(new DocumentListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position)
                {
                    adapterPosition=position;
                    ImagePicker.Companion.with(AppointmentDetails.this).galleryOnly().cropSquare().compress(200).start();
                }
            });

            MaterialButton completeButton=findViewById(R.id.button_complete);
            MaterialButton cancelButton=findViewById(R.id.button_cancel);
            completeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                      boolean upload=true;
                      for(int i=0;i<adapter.getDocuments().size();i++)
                      {
                          if(adapter.getDocuments().get(i).getImgRes().isEmpty())
                          {
                              upload=false;
                          }
                      }

                      if(!upload)
                      {
                          Toast.makeText(AppointmentDetails.this, "Please select all images first", Toast.LENGTH_SHORT).show();
                      }
                      else
                      {
                          showLoading();
                          HashMap<String,Object> map=new HashMap<>();
                          map.put("carFront",adapter.getDocuments().get(0).getImgRes());
                          map.put("carBack",adapter.getDocuments().get(1).getImgRes());
                          map.put("carLeft",adapter.getDocuments().get(2).getImgRes());
                          map.put("carRight",adapter.getDocuments().get(3).getImgRes());
                          map.put("appointmentId",appointment.getId());
                          manager.postSpecialRequest(APIList.UPLOAD_DOCUMENTS_APPOINTMENT, map, new IResultData() {
                              @Override
                              public void notifyResult(String result)
                              {
                                  Gson gson=new Gson();
                                  APIResultSingle apiResultSingle=gson.fromJson(result,APIResultSingle.class);
                                  if(apiResultSingle.getStatusCode().equalsIgnoreCase("200"))
                                  {
                                      manager.getRequest(APIList.COMPLETE_APPOINTMENT + "?appointmentId=" + appointment.getId(), new IResultData() {
                                          @Override
                                          public void notifyResult(String result)
                                          {
                                              hideLoading();
                                              Gson gson=new Gson();
                                              APIResult apiResult=gson.fromJson(result,APIResult.class);
                                              Toast.makeText(AppointmentDetails.this,apiResult.getMessage(), Toast.LENGTH_SHORT).show();
                                              setResult(RESULT_OK);
                                              finish();
                                              Animatoo.INSTANCE.animateSlideRight(AppointmentDetails.this);
                                          }
                                      });
                                  }
                                  else
                                  {
                                      hideLoading();
                                  }
                              }
                          });
                      }
                }
            });

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    new AlertDialog.Builder(AppointmentDetails.this)
                            .setTitle("Cancel Appointment")
                            .setMessage("Are you sure you want to cancel your appointment?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    showLoading();
                                    HashMap<String,Object>map=new HashMap<>();
                                    map.put("appointmentId",appointment.getId());
                                    manager.postRequest(APIList.CANCEL_APPOINTMENT,map,new IResultData() {
                                        @Override
                                        public void notifyResult(String result)
                                        {
                                            hideLoading();
                                            Gson gson=new Gson();
                                            APIResult apiResult=gson.fromJson(result,APIResult.class);
                                            Toast.makeText(AppointmentDetails.this,apiResult.getMessage(), Toast.LENGTH_SHORT).show();
                                            setResult(RESULT_OK);
                                            finish();
                                            Animatoo.INSTANCE.animateSlideRight(AppointmentDetails.this);
                                        }
                                    });
                                }
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(R.drawable.ic_logo)
                            .show();

                }
            });

        }
        else
        {
            installLayout.setVisibility(View.GONE);
        }
        Picasso picasso=Picasso.get();
        if(appointment.getSticker_request()==1)
        {
            tvAppointmentType.setText("Sticker Apply");
        }
        else
        {
            tvAppointmentType.setText("Sticker Remove");
        }
        picasso.load(appointment.getDriver().getProfile_pic()).into(imgDriver);
        picasso.load(appointment.getCampaign().getDesign()).into(imgCampaign);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
                Animatoo.INSTANCE.animateSlideRight(AppointmentDetails.this);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
        {
            Uri uri=data.getData();
            adapter.updateItem(uri.toString(),adapterPosition);
        }
    }

}