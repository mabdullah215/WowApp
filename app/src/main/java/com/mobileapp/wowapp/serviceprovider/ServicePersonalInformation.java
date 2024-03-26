package com.mobileapp.wowapp.serviceprovider;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.button.MaterialButton;
import com.mobileapp.wowapp.BaseActivity;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.network.NetworkManager;
import com.mobileapp.wowapp.serviceprovider.model.ServiceProvider;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ServicePersonalInformation extends BaseActivity {

    Uri uri;
    ImageView imgProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_personal_information);
        EditText etName=findViewById(R.id.et_fullname);
        EditText etNationalID=findViewById(R.id.et_national_id);
        EditText etNationalAddress=findViewById(R.id.et_national_address);
        TextView tvDateofBirth=findViewById(R.id.tv_birth_date);
        EditText etBusinessAddress=findViewById(R.id.et_business_address);
        EditText etShopName=findViewById(R.id.et_shop_name);
        Spinner citySpinner=findViewById(R.id.spinner_city);
        NetworkManager manager=NetworkManager.getInstance(getBaseContext());
        citySpinner.setAdapter(manager.getCityListAdapter());
        MaterialButton buttonNext=findViewById(R.id.button_done);
        imgProfile=findViewById(R.id.img_profile);
        Picasso.get().load(manager.getServiceProvider().getProfilePic()).fit().into(imgProfile);
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent();
                intent.setAction(MediaStore.ACTION_PICK_IMAGES);
                startActivityForResult(intent,100);
            }
        });
        tvDateofBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                showDatePicker(tvDateofBirth);
            }
        });
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String name=etName.getText().toString().trim();
                String nationalID=etNationalID.getText().toString().trim();
                String address=etNationalAddress.getText().toString().trim();
                String dateofBirth=tvDateofBirth.getText().toString().trim();
                String businessAddress=etBusinessAddress.getText().toString().trim();
                String shopName=etShopName.getText().toString().trim();

                if(name.isEmpty()||nationalID.isEmpty()||shopName.isEmpty()||address.isEmpty()||dateofBirth.equalsIgnoreCase("Select Date")||businessAddress.isEmpty())
                {
                    showToast("Information missing");
                }
                else
                {
                    ServiceProvider serviceProvider=new ServiceProvider();
                    startActivity(new Intent(getBaseContext(), ServiceBankInformation.class).putExtra("item",serviceProvider));
                    Animatoo.INSTANCE.animateSlideLeft(ServicePersonalInformation.this);
                }

            }
        });
        ImageView imgBack=findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Animatoo.INSTANCE.animateSlideRight(ServicePersonalInformation.this);
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

    public void showDatePicker(TextView tv)
    {
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(year, monthOfYear, dayOfMonth);
                tv.setText(format.format(calendar.getTime()));
            }

        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}