package com.mobileapp.wowapp.serviceprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.button.MaterialButton;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.customer.OfferActivity;
import com.mobileapp.wowapp.customer.utils.Converter;
import com.mobileapp.wowapp.customer.utils.CustomTimePickerDialog;
import com.mobileapp.wowapp.network.NetworkManager;
import com.mobileapp.wowapp.serviceprovider.model.ServiceProvider;

import java.util.Calendar;
import java.util.List;

public class ServiceProviderWorkTimings extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_work_timings);
        TextView tvOpeningTime=findViewById(R.id.tv_opening_time);
        TextView tvClosingTime=findViewById(R.id.tv_closing_time);
        TextView tvBreakTimeStart=findViewById(R.id.tv_break_start);
        TextView tvBreakTimeEnd=findViewById(R.id.tv_break_end);
        Spinner spinnerDuration=findViewById(R.id.spinner_duration);
        String [] durations={"30","60"};
        ArrayAdapter durationAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,durations);
        durationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDuration.setAdapter(durationAdapter);
        MaterialButton buttonDone=findViewById(R.id.button_done);
        NetworkManager manager=NetworkManager.getInstance(this);
        if(manager.getServiceProvider().isVerified())
        {
            tvOpeningTime.setText(Converter.TimeConverter(manager.getServiceProvider().getBusinessStartTime()));
            tvClosingTime.setText(Converter.TimeConverter(manager.getServiceProvider().getBusinessEndTime()));
            tvBreakTimeStart.setText(Converter.TimeConverter(manager.getServiceProvider().getBreakStartTime()));
            tvBreakTimeEnd.setText(Converter.TimeConverter(manager.getServiceProvider().getBreakEndTime()));
            if(String.valueOf(manager.getServiceProvider().getDuration()).equalsIgnoreCase("30"))
            {
                spinnerDuration.setSelection(0);
            }
            else
            {
                spinnerDuration.setSelection(1);
            }
        }
        tvOpeningTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                openTimePicker(tvOpeningTime);
            }
        });

        tvClosingTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTimePicker(tvClosingTime);
            }
        });

        tvBreakTimeStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTimePicker(tvBreakTimeStart);
            }
        });

        tvBreakTimeEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTimePicker(tvBreakTimeEnd);
            }
        });

        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ServiceProvider serviceProvider=(ServiceProvider) getIntent().getSerializableExtra("item");
                serviceProvider.setBusinessStartTime(Converter.reverseTimeConverter(tvOpeningTime.getText().toString()));
                serviceProvider.setBusinessEndTime(Converter.reverseTimeConverter(tvClosingTime.getText().toString()));
                serviceProvider.setBreakStartTime(Converter.reverseTimeConverter(tvBreakTimeStart.getText().toString()));
                serviceProvider.setBreakEndTime(Converter.reverseTimeConverter(tvBreakTimeEnd.getText().toString()));
                serviceProvider.setDuration(Integer.parseInt(spinnerDuration.getSelectedItem().toString()));
                startActivity(new Intent(getBaseContext(), ServiceBankInformation.class).putExtra("item",serviceProvider));
                Animatoo.INSTANCE.animateSlideLeft(ServiceProviderWorkTimings.this);
            }
        });
        ImageView imgBack=findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
                Animatoo.INSTANCE.animateSlideRight(ServiceProviderWorkTimings.this);
            }
        });

    }

    public void openTimePicker(TextView tv)
    {
        Calendar mcurrentTime=Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        CustomTimePickerDialog dialog=new CustomTimePickerDialog(ServiceProviderWorkTimings.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int mins)
            {
                tv.setText(Converter.getformattedTime(hour,mins));
                mcurrentTime.set(Calendar.HOUR_OF_DAY,hour);
                mcurrentTime.set(Calendar.MINUTE,mins);
            }
        },hour,minute,false);
        dialog.show();
    }

}
