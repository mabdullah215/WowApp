package com.mobileapp.wowapp.driver;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.mobileapp.wowapp.BaseActivity;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.driver.adapter.DocumentListAdapter;
import com.mobileapp.wowapp.driver.model.Document;
import com.mobileapp.wowapp.driver.model.Driver;
import com.mobileapp.wowapp.interations.IResult;
import com.mobileapp.wowapp.interations.IResultData;
import com.mobileapp.wowapp.network.APIList;
import com.mobileapp.wowapp.network.APIResult;
import com.mobileapp.wowapp.network.APIResultSingle;
import com.mobileapp.wowapp.network.NetworkManager;
import com.mobileapp.wowapp.serviceprovider.AppointmentDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DocumentsInformation extends BaseActivity
{
    int adapterPosition=0;
    Uri cnicFront,cnicBack,licence,estmara,violation;
    DocumentListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documents_information);
        ImageView imgBack=findViewById(R.id.img_back);
        MaterialButton buttonDone=findViewById(R.id.button_done);
        RecyclerView recyclerView=findViewById(R.id.data_list);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        List<Document>documents=new ArrayList<>();
        documents.add(new Document("drivingLicense"));
        documents.add(new Document("estimera"));
        documents.add(new Document("cnicFront"));
        documents.add(new Document("cnicBack"));
        documents.add(new Document("trafficVoilations"));
        adapter=new DocumentListAdapter(this,documents);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new DocumentListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position)
            {
                adapterPosition=position;
                ImagePicker.Companion.with(DocumentsInformation.this).galleryOnly().cropSquare().compress(200).start();
            }
        });
        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(licence==null||estmara==null||cnicFront==null||cnicBack==null||violation==null)
                {
                    showToast("Please upload all the images");
                }
                else
                {
                    uploadImages();
                }
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Animatoo.INSTANCE.animateSlideRight(DocumentsInformation.this);
            }
        });
    }

    public void uploadImages()
    {
        showLoading();
        HashMap<String,String>map=new HashMap<>();
        map.put("estimera",estmara.toString());
        map.put("drivingLicense",licence.toString());
        map.put("trafficVoilations",violation.toString());
        map.put("cnicFront",cnicFront.toString());
        map.put("cnicBack",cnicBack.toString());

        NetworkManager manager=NetworkManager.getInstance(this);
        manager.uploadImages(APIList.UPLOAD_DOCUMENTS, map, new IResultData() {
            @Override
            public void notifyResult(String result)
            {
                Gson gson=new Gson();
                APIResultSingle apiResultSingle=gson.fromJson(result,APIResultSingle.class);
                Toast.makeText(DocumentsInformation.this, apiResultSingle.getMessage(), Toast.LENGTH_SHORT).show();
                if(apiResultSingle.getStatusCode().equalsIgnoreCase("200"))
                {
                    updateProfile();
                }
                else
                {
                    hideLoading();
                }

            }
        });
    }

    public void updateProfile()
    {
        NetworkManager manager=NetworkManager.getInstance(this);
        HashMap<String,Object>map=new HashMap<>();
        Driver driver=(Driver)getIntent().getSerializableExtra("item");
        map.put("accountName",driver.getAccountName());
        map.put("carMake",driver.getCarMake());
        map.put("registrationNo",driver.getRegistrationNo());
        map.put("bankId",driver.getBankName());
        map.put("workedBeofre",driver.getWorkedBefore());
        map.put("city",driver.getCity());
        map.put("address",driver.getAddress());
        map.put("iban",driver.getIban());
        map.put("businessAddress",driver.getBusinessAddress());
        map.put("name",driver.getName());
        map.put("birthday",driver.getBirthday());
        map.put("carModel",driver.getCarModel());
        map.put("carColor",driver.getCarColor());
        map.put("carYear",driver.getCarYear());
        map.put("nationality",driver.getNationality());
        map.put("profilePic",driver.getProfilePic());
        map.put("carNo",driver.getCarNo());

        manager.postRequest(APIList.UPDATE_PROFILE, map, new IResultData() {
            @Override
            public void notifyResult(String result)
            {
                hideLoading();
                Gson gson=new Gson();
                APIResultSingle apiResult=gson.fromJson(result,APIResultSingle.class);
                Toast.makeText(DocumentsInformation.this, apiResult.getMessage(), Toast.LENGTH_SHORT).show();
                if(apiResult.getStatusCode().equalsIgnoreCase("200"))
                {
                    Intent i = new Intent(DocumentsInformation.this, DriverHome.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    Animatoo.INSTANCE.animateSlideRight(DocumentsInformation.this);
                    startActivity(i);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK)
        {
            Uri uri=data.getData();
            adapter.updateItem(uri.toString(),adapterPosition);

            if(adapterPosition==0)
            {
                licence=uri;
            }
            else if(adapterPosition==1)
            {
                estmara=uri;
            }
            else if(adapterPosition==2)
            {
                cnicFront=uri;
            }
            else if(adapterPosition==3)
            {
                cnicBack=uri;
            }
            else if(adapterPosition==4)
            {
                violation=uri;
            }
        }

    }
}