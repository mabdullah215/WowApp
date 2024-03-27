package com.mobileapp.wowapp.customer;

import androidx.annotation.Nullable;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.common.moduleinstall.internal.ApiFeatureRequest;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.mobileapp.wowapp.BaseActivity;
import com.mobileapp.wowapp.CityList;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.interations.IResult;
import com.mobileapp.wowapp.interations.IResultData;
import com.mobileapp.wowapp.model.City;
import com.mobileapp.wowapp.network.APIList;
import com.mobileapp.wowapp.network.APIResult;
import com.mobileapp.wowapp.network.APIResultSingle;
import com.mobileapp.wowapp.network.NetworkManager;
import com.mobileapp.wowapp.serviceprovider.AppointmentDetails;
import com.squareup.picasso.Picasso;
import com.starry.file_utils.FileUtils;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class BusinessProfile extends BaseActivity
{
    ImageView imgProfile;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_profile);
        imgProfile=findViewById(R.id.img_profile);
        ImageView imgBack=findViewById(R.id.img_back);
        EditText etShopName=findViewById(R.id.et_shopname);
        EditText etbusinessDetails=findViewById(R.id.et_business_details);
        EditText etCommertialRegister=findViewById(R.id.et_commertialregister);
        EditText etFullName=findViewById(R.id.et_name);
        Spinner citySpinner=findViewById(R.id.spinner_city);
        MaterialButton buttonDone=findViewById(R.id.button_done);
        NetworkManager manager=NetworkManager.getInstance(getBaseContext());
        citySpinner.setAdapter(manager.getCityListAdapter());
        etShopName.setText(manager.getCustomer().getBusinessName());
        etFullName.setText(manager.getCustomer().getName());
        etbusinessDetails.setText(manager.getCustomer().getBusinessDetails());
        citySpinner.setSelection(Integer.parseInt(manager.getCustomer().getCity())-1);
        etCommertialRegister.setText(manager.getCustomer().getRegistrationNo());
        if(!manager.getCustomer().getProfilePic().isEmpty())
        {
            Picasso.get().load(manager.getCustomer().getProfilePic()).into(imgProfile);
        }

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                /*Intent intent=new Intent();
                intent.setAction(MediaStore.ACTION_PICK_IMAGES);
                startActivityForResult(intent,100);*/

                ImagePicker.Companion.with(BusinessProfile.this).galleryOnly().cropSquare().compress(200).start();
            }
        });
        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String shopName=etShopName.getText().toString().trim();
                String name=etFullName.getText().toString().trim();
                String businessDetails=etbusinessDetails.getText().toString().trim();
                String commertialRegister=etCommertialRegister.getText().toString().trim();
                String city=citySpinner.getSelectedItem().toString();
                if(shopName.isEmpty()||city.isEmpty()||commertialRegister.isEmpty())
                {
                    showToast("information missing");
                }

                else
                {
                    showLoading();
                    HashMap<String,Object>map=new HashMap<>();
                    map.put("name",name);
                    map.put("businessName",shopName);
                    map.put("businessDetails",businessDetails);
                    map.put("registrationNo",commertialRegister);
                    map.put("city",citySpinner.getSelectedItemPosition()+1);
                    if(uri!=null)
                    {
                        map.put("profilePic",uri);
                    }
                    manager.postRequest(APIList.UPDATE_PROFILE, map, new IResultData() {
                        @Override
                        public void notifyResult(String result)
                        {
                            hideLoading();
                            Gson gson=new Gson();
                            APIResultSingle apiResultSingle=gson.fromJson(result,APIResultSingle.class);
                            if(apiResultSingle.getStatusCode().equalsIgnoreCase("200"))
                            {
                                Toast.makeText(BusinessProfile.this, apiResultSingle.getMessage(), Toast.LENGTH_SHORT).show();
                                setResult(RESULT_OK);
                                finish();
                                Animatoo.INSTANCE.animateSlideRight(BusinessProfile.this);
                            }
                        }
                    });
                }
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                finish();
                Animatoo.INSTANCE.animateSlideRight(BusinessProfile.this);
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
        {
            uri=data.getData();
            Picasso.get().load(uri).into(imgProfile);
        }
    }

}