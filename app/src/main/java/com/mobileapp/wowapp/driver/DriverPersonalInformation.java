package com.mobileapp.wowapp.driver;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.mobileapp.wowapp.BaseActivity;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.driver.model.Driver;
import com.mobileapp.wowapp.interations.IResult;
import com.mobileapp.wowapp.network.APIList;
import com.mobileapp.wowapp.network.APIResult;
import com.mobileapp.wowapp.network.NetworkManager;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import de.hdodenhof.circleimageview.CircleImageView;

public class DriverPersonalInformation extends BaseActivity
{
    CircleImageView profileImage;
    Uri profileUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_personal_information);
        Driver driver=(Driver) getIntent().getSerializableExtra("driver");
        Log.i("driverdetails",new Gson().toJson(driver));
        EditText etName=findViewById(R.id.et_fullname);
        EditText etNationalID=findViewById(R.id.et_national_id);
        EditText etNationalAddress=findViewById(R.id.et_national_address);
        TextView tvDateofBirth=findViewById(R.id.tv_birth_date);
        Spinner citySpinner=findViewById(R.id.spinner_city);
        NetworkManager manager=NetworkManager.getInstance(this);
        citySpinner.setAdapter(manager.getCityListAdapter());
        EditText etPrimaryAddress=findViewById(R.id.et_primary_address);
        SwitchCompat switchWorking=findViewById(R.id.switch_working);
        MaterialButton buttonNext=findViewById(R.id.button_done);
        etName.setText(driver.getName());
        profileImage=findViewById(R.id.user_img);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent();
                intent.setAction(MediaStore.ACTION_PICK_IMAGES);
                intent.setType("image/*");
                startActivityForResult(intent,140);
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
                String primaryAddress=etPrimaryAddress.getText().toString().trim();

                if(name.isEmpty()||nationalID.isEmpty()||address.isEmpty()||dateofBirth.equalsIgnoreCase("Select Date")||primaryAddress.isEmpty())
                {
                    showToast("Information missing");
                }
                else if(profileUri==null)
                {
                    showToast("Please add profile image");
                }

                else
                {
                    int working=0;
                    if(switchWorking.isChecked())
                    {
                        working=1;
                    }
                    int cityId=manager.getCityList().get(citySpinner.getSelectedItemPosition()).getId();
                    driver.setName(name);
                    driver.setRegistrationNo(nationalID);
                    driver.setBusinessAddress(address);
                    driver.setCity(""+cityId);
                    driver.setBirthday(getformattedDate(dateofBirth));
                    driver.setWorkedBefore(working);
                    driver.setProfilePic(profileUri.toString());
                    startActivity(new Intent(getBaseContext(), BankInformation.class).putExtra("item",driver));
                    Animatoo.INSTANCE.animateSlideLeft(DriverPersonalInformation.this);
                }

            }
        });
        ImageView imgBack=findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Animatoo.INSTANCE.animateSlideRight(DriverPersonalInformation.this);
            }
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
        }
        else
        {
            enableLocationSettings();
        }

    }


    private void showCityList(TextView et,String [] list)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select city from below");
        builder.setItems(list, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                et.setText(list[which]);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public String getformattedDate(String str)
    {
        String res="";
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat simpleFormat=new SimpleDateFormat("dd-MMM-yyyy");
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==140&&resultCode == Activity.RESULT_OK)
        {
            profileUri=data.getData();
            Picasso.get().load(profileUri).fit().into(profileImage);
        }
    }

    public void showDatePicker(TextView tv)
    {
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(year, monthOfYear, dayOfMonth);
                tv.setText(format.format(calendar.getTime()));
            }

        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}