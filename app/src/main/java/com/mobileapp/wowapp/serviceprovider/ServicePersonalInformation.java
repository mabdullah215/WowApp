package com.mobileapp.wowapp.serviceprovider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.button.MaterialButton;
import com.mobileapp.wowapp.BaseActivity;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.serviceprovider.model.ServiceProvider;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ServicePersonalInformation extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_personal_information);
        EditText etName=findViewById(R.id.et_fullname);
        EditText etNationalID=findViewById(R.id.et_national_id);
        EditText etNationalAddress=findViewById(R.id.et_national_address);
        TextView tvDateofBirth=findViewById(R.id.tv_birth_date);
        EditText etPrimaryAddress=findViewById(R.id.et_primary_address);
        MaterialButton buttonNext=findViewById(R.id.button_done);
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
                String primaryAddress=etPrimaryAddress.getText().toString().trim();

                if(name.isEmpty()||nationalID.isEmpty()||address.isEmpty()||dateofBirth.equalsIgnoreCase("Select Date")||primaryAddress.isEmpty())
                {
                    showToast("Information missing");
                }
                else
                {
                    ServiceProvider serviceProvider=new ServiceProvider();
                    /*serviceProvider.setFullName(name);
                    serviceProvider.setNationalId(nationalID);
                    serviceProvider.setAddress(address);
                    serviceProvider.setDateofBirth(dateofBirth);
                    serviceProvider.setPrimaryWorkingAddress(primaryAddress);*/
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