package com.mobileapp.wowapp.customer;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.button.MaterialButton;
import com.mobileapp.wowapp.BaseActivity;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.customer.utils.CustomTimePickerDialog;
import com.mobileapp.wowapp.model.Compaign;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class OfferActivity extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_offer);
        ImageView imgBack=findViewById(R.id.img_close);
        Spinner compaignSpinner=findViewById(R.id.spinner_compaign);
        EditText etOffer=findViewById(R.id.et_offer);
        ArrayList<Compaign> campaigns = (ArrayList<Compaign>) getIntent().getSerializableExtra("list");
        List<String>campaignTitle=new ArrayList<>();
        for(int i=0;i<campaigns.size();i++)
        {
            campaignTitle.add(campaigns.get(i).getName());
        }
        ArrayAdapter spineradapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,campaignTitle);
        spineradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        compaignSpinner.setAdapter(spineradapter);
        EditText etMapLocation=findViewById(R.id.et_location);
        TextView startTime=findViewById(R.id.tv_start_time);
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        startTime.setText(getformattedTime(hour,minute));
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                CustomTimePickerDialog dialog=new CustomTimePickerDialog(OfferActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int mins)
                    {
                        startTime.setText(getformattedTime(hour,mins));
                        mcurrentTime.set(Calendar.HOUR_OF_DAY,hour);
                        mcurrentTime.set(Calendar.MINUTE,mins);
                    }
                },hour,minute,false);
                dialog.show();

            }
        });

        TextView tvDate=findViewById(R.id.tv_date);
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, d MMM yyyy");
        tvDate.setText(sdf.format(new Date()));
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker(tvDate);
            }
        });

        TextView endTime=findViewById(R.id.tv_end_time);
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                CustomTimePickerDialog dialog=new CustomTimePickerDialog(OfferActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int mins)
                    {
                        endTime.setText(getformattedTime(hour,mins));
                        mcurrentTime.set(Calendar.HOUR_OF_DAY,hour);
                        mcurrentTime.set(Calendar.MINUTE,mins);
                    }
                },hour,minute,false);

                dialog.show();
            }
        });

        EditText etCarNumber=findViewById(R.id.et_num_cars);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Animatoo.INSTANCE.animateSlideRight(OfferActivity.this);
            }
        });

        MaterialButton buttonDone=findViewById(R.id.button_done);
        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String title=compaignSpinner.getSelectedItem().toString();
                String maplocation=etMapLocation.getText().toString().trim();
                String timeStart=startTime.getText().toString().trim();
                String timeEnd=endTime.getText().toString().trim();
                String offer=etOffer.getText().toString().trim();
                String numofCars=etCarNumber.getText().toString().trim();
                String date=tvDate.getText().toString().trim();

            }
        });
    }

    public void showDatePicker(TextView tv)
    {
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, d MMM yyyy");
        DatePickerDialog dialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(year, monthOfYear, dayOfMonth);
                tv.setText(sdf.format(calendar.getTime()));
            }

        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dialog.getDatePicker().setMinDate(System.currentTimeMillis());
        dialog.show();
    }

    public String getformattedTime(int hours, int mins)
    {
        if(mins>0)
            mins=30;
        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12) {
            timeSet = "PM";
        } else {
            timeSet = "AM";
        }

        // Append in a StringBuilder
        String aTime = new StringBuilder().append(pad(hours)).append(':')
                .append(pad(mins)).append(" ").append(timeSet).toString();

        return aTime;
    }
    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }




}