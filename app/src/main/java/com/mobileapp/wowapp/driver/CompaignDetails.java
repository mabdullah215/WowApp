package com.mobileapp.wowapp.driver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.interations.IResult;
import com.mobileapp.wowapp.interations.IResultData;
import com.mobileapp.wowapp.model.Compaign;
import com.mobileapp.wowapp.network.APIList;
import com.mobileapp.wowapp.network.APIResult;
import com.mobileapp.wowapp.network.NetworkManager;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class CompaignDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compaign_details);
        ImageView imgBack=findViewById(R.id.img_back);
        TextView tvTitle=findViewById(R.id.tv_title);
        Compaign compaign=(Compaign) getIntent().getSerializableExtra("item");
        tvTitle.setText(compaign.getName());
        TextView tvStartDate=findViewById(R.id.tv_starting_date);
        TextView tvEndingDate=findViewById(R.id.tv_ending_date);
        tvStartDate.setText(getDateFromString(compaign.getStartTime()));
        tvEndingDate.setText(getDateFromString(compaign.getEndTime()));
        TextView tvTodayImpressions=findViewById(R.id.tv_today_impressions);
        TextView tvTodayKms=findViewById(R.id.tv_today_kms);
        tvTodayImpressions.setText(String.valueOf(compaign.getTotalImpressions()));
        tvTodayKms.setText(String.valueOf(compaign.getTotalDistanceCovered()));
        ImageView imgRight=findViewById(R.id.img_right);
        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.map_card, mapFragment).commit();
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap)
            {
                LatLng currentLatLng = new LatLng(compaign.getCity().getLatitude(),compaign.getCity().getLongitude());
                CameraUpdate update = CameraUpdateFactory.newLatLngZoom(currentLatLng,12);
                googleMap.animateCamera(update);
            }
        });
        imgRight.setImageDrawable(getDrawable(R.drawable.ic_refresh));
        imgRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                getAssignedDrivers(compaign.getId());
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
                Animatoo.INSTANCE.animateSlideRight(CompaignDetails.this);
            }
        });

        getAssignedDrivers(compaign.getId());
    }

    public String getDateFromString(String str)
    {
        String res="";
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat simpleFormat=new SimpleDateFormat("dd MMM yyyy");
        try
        {
            Date date=format.parse(str);
            res=simpleFormat.format(date);
            return res;
        } catch (ParseException e)
        {
            throw new RuntimeException(e);
        }
    }


    public void getAssignedDrivers(int compaignid)
    {
        ConstraintLayout progressLayout=findViewById(R.id.progress_layout);
        progressLayout.setVisibility(View.VISIBLE);
        NetworkManager manager=NetworkManager.getInstance(this);
        HashMap<String,Object>map=new HashMap<>();
        map.put("campaignId",compaignid);
        manager.getRequest(APIList.ASSIGNED_DRIVER_LIST+"?campaignId="+compaignid, new IResultData() {
            @Override
            public void notifyResult(String result)
            {
                progressLayout.setVisibility(View.GONE);
            }
        });
    }

}