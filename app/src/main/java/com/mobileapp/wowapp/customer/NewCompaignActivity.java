package com.mobileapp.wowapp.customer;
import androidx.annotation.Nullable;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mobileapp.wowapp.BaseActivity;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.interations.IResult;
import com.mobileapp.wowapp.interations.IResultData;
import com.mobileapp.wowapp.model.City;
import com.mobileapp.wowapp.model.Compaign;
import com.mobileapp.wowapp.network.APIList;
import com.mobileapp.wowapp.network.APIResult;
import com.mobileapp.wowapp.network.APIResultSingle;
import com.mobileapp.wowapp.network.NetworkManager;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.starry.file_utils.FileUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class NewCompaignActivity extends BaseActivity {

    ImageView advertiseImage;
    Calendar calendar;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        calendar=Calendar.getInstance();
        calendar.add(Calendar.DATE,10);
        setContentView(R.layout.activity_new_compaign);
        EditText etTitle=findViewById(R.id.et_title);
        Spinner durationSpinner=findViewById(R.id.spinner_duration);
        Spinner citySpinner=findViewById(R.id.spinner_city);
        String [] durationList={"1 Month","2 Months","3 Months","4 Months","5 Months"};
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,durationList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        durationSpinner.setAdapter(aa);
        NetworkManager manager=NetworkManager.getInstance(this);
        citySpinner.setAdapter(manager.getCityListAdapter());
        TextView tvStartDate=findViewById(R.id.tv_start_date);
        TextView tvEndDate=findViewById(R.id.tv_end_date);
        EditText etCarNumbers=findViewById(R.id.et_num_cars);
        ImageView imgClose=findViewById(R.id.img_close);
        TextView tvUpload=findViewById(R.id.tv_upload);
        advertiseImage=findViewById(R.id.img_source);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        tvStartDate.setText(format.format(calendar.getTime()));
        updateEndDate(tvEndDate,0);
        tvStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                showDatePicker(tvStartDate,tvEndDate,durationSpinner.getSelectedItemPosition());
            }
        });

        durationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                updateEndDate(tvEndDate,i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        MaterialButton buttonDone=findViewById(R.id.button_done);
        tvUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent();
                intent.setAction(MediaStore.ACTION_PICK_IMAGES);
                startActivityForResult(intent,100);
            }
        });

        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String title=etTitle.getText().toString().trim();
                String duration=durationSpinner.getSelectedItem().toString().trim();
                String startDate=tvStartDate.getText().toString().trim();
                String endDate=tvEndDate.getText().toString().trim();
                String numofCars=etCarNumbers.getText().toString().trim();
                int cityId=manager.getCityList().get(citySpinner.getSelectedItemPosition()).getId();
                if(title.isEmpty()||numofCars.equalsIgnoreCase("Select"))
                {
                    showToast("Information missing");
                }
                else
                {
                    HashMap<String,Object>map=new HashMap<>();
                    map.put("cityId",cityId);
                    map.put("name",title);
                    map.put("drivingStartTime","09:00:00");
                    map.put("noOfCars",numofCars);
                    map.put("drivingEndTime","21:00:00");
                    map.put("startTime",getformattedDate(startDate));
                    map.put("endTime",getformattedDate(endDate));
                    if(uri!=null)
                    {
                        map.put("design",uri);
                    }

                   showLoading();
                   manager.postRequest(APIList.CREATE_COMPAIGN, map, new IResultData() {
                       @Override
                       public void notifyResult(String result)
                       {
                           hideLoading();
                           Gson gson=new Gson();
                           APIResultSingle apiResultSingle=gson.fromJson(result,APIResultSingle.class);
                           if(apiResultSingle.getStatusCode().equalsIgnoreCase("200"))
                           {
                               Toast.makeText(NewCompaignActivity.this,apiResultSingle.getMessage(), Toast.LENGTH_SHORT).show();
                               setResult(RESULT_OK);
                               finish();
                           }
                           else
                           {
                               showToast(apiResultSingle.getMessage());
                           }
                       }
                   });
                }

            }
        });

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Animatoo.INSTANCE.animateSlideRight(NewCompaignActivity.this);
            }
        });
    }

    public String getformattedDate(String str)
    {
        String res="";
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat simpleFormat=new SimpleDateFormat("dd-MM-yyyy");
        try
        {
            Date date=simpleFormat.parse(str);
            date.setSeconds(0);
            date.setMinutes(0);
            date.setHours(0);
            res=format.format(date);
            return res;
        } catch (ParseException e)
        {
            throw new RuntimeException(e);
        }
    }


    public void showDatePicker(TextView tv,TextView tvEndDate,int months)
    {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        DatePickerDialog dialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(year, monthOfYear, dayOfMonth);
                tv.setText(format.format(calendar.getTime()));
                updateEndDate(tvEndDate,months);
            }

        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        Calendar minDate=Calendar.getInstance();
        minDate.add(Calendar.DATE,10);
        dialog.getDatePicker().setMinDate(minDate.getTimeInMillis());
        dialog.show();
    }

    public void updateEndDate(TextView textView,int which)
    {
        Calendar tempCal=Calendar.getInstance();
        tempCal.setTimeInMillis(calendar.getTimeInMillis());
        tempCal.add(Calendar.MONTH,(which+1));
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        textView.setText(format.format(tempCal.getTime()));
    }

    private void showList(TextView et, String[] list,TextView tvEndDate)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select from below");
        builder.setItems(list, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                et.setText(list[which]);
                updateEndDate(tvEndDate,which);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        hideLoading();
        if (resultCode == Activity.RESULT_OK)
        {
            showLoading();
            uri=data.getData();

            Picasso.get().load(uri).into(advertiseImage, new Callback()
            {
                @Override
                public void onSuccess()
                {
                    hideLoading();
                }

                @Override
                public void onError(Exception e) {
                    hideLoading();
                }
            });
        }
    }
}