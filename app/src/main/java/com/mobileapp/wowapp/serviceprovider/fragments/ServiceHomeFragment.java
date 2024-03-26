package com.mobileapp.wowapp.serviceprovider.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.gson.Gson;
import com.mobileapp.wowapp.Helper;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.customer.model.Customer;
import com.mobileapp.wowapp.customer.utils.Converter;
import com.mobileapp.wowapp.driver.adapter.AppointmentListAdapter;
import com.mobileapp.wowapp.interations.IResultData;
import com.mobileapp.wowapp.model.Appointment;
import com.mobileapp.wowapp.network.APIList;
import com.mobileapp.wowapp.network.APIResult;
import com.mobileapp.wowapp.network.APIResultSingle;
import com.mobileapp.wowapp.network.NetworkManager;
import com.mobileapp.wowapp.serviceprovider.AppointmentDetails;
import com.mobileapp.wowapp.serviceprovider.adapters.ShopAppointmentListAdapter;
import com.mobileapp.wowapp.serviceprovider.model.ServiceProvider;
import com.mobileapp.wowapp.serviceprovider.model.ShopAppointment;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;
import com.vivekkaushik.datepicker.DatePickerTimeline;
import com.vivekkaushik.datepicker.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class ServiceHomeFragment extends Fragment
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.fragment_service_provider_home, container, false);
        TextView tvUsername=view.findViewById(R.id.tv_username);
        CircleImageView imgProfile=view.findViewById(R.id.user_img);
        NetworkManager manager=NetworkManager.getInstance(getContext());
        TextView tvDate=view.findViewById(R.id.tv_today_date);
        RecyclerView recyclerView=view.findViewById(R.id.data_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<ShopAppointment>appointments=new ArrayList<>();
        ShopAppointmentListAdapter adapter=new ShopAppointmentListAdapter(getContext(),appointments);
        recyclerView.setAdapter(adapter);
        ConstraintLayout progressLayout=view.findViewById(R.id.progress_layout);
        TextView tvNumAppointments=view.findViewById(R.id.tv_num_appoitments);
        SimpleDateFormat format = new SimpleDateFormat("EEEE, dd MMMM yyyy");
        tvDate.setText(format.format(new Date()));
        if(manager.getCityList().isEmpty())
        {
            manager.setCityList();
            manager.setBankList();
        }

        progressLayout.setVisibility(View.VISIBLE);
        manager.getRequest(APIList.GET_PROFILE, new IResultData() {
            @Override
            public void notifyResult(String result)
            {
                Gson gson=new Gson();
                progressLayout.setVisibility(View.GONE);
                APIResultSingle apiResultSingle=gson.fromJson(result,APIResultSingle.class);
                if(apiResultSingle.getStatusCode().equalsIgnoreCase("200"))
                {
                    String data=gson.toJson(apiResultSingle.getData());
                    ServiceProvider serviceProvider=gson.fromJson(data,ServiceProvider.class);
                    manager.setServiceProvider(serviceProvider);
                    tvUsername.setText(serviceProvider.getName());
                    Picasso.get().load(serviceProvider.getProfilePic()).into(imgProfile);
                }
            }
        });
        DatePickerTimeline datePickerTimeline = view.findViewById(R.id.datePickerTimeline);
        Calendar calendar=Calendar.getInstance();
        datePickerTimeline.setActiveDate(calendar);
        datePickerTimeline.setInitialDate(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        datePickerTimeline.setOnDateSelectedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(int year, int month, int day, int dayOfWeek)
            {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,day);
                String date=Converter.getShortDate(calendar.getTimeInMillis());
                tvDate.setText(format.format(calendar.getTime()));
                progressLayout.setVisibility(View.VISIBLE);
                manager.getRequest(APIList.GET_APPOINTMENTS_SHOP+"?date="+date, new IResultData() {
                    @Override
                    public void notifyResult(String result)
                    {
                        progressLayout.setVisibility(View.GONE);
                        APIResult apiResult=new Gson().fromJson(result,APIResult.class);
                        if(apiResult.getStatusCode().equalsIgnoreCase("200"))
                        {
                            List<ShopAppointment>list= Helper.toList(apiResult.getData(),ShopAppointment.class);
                            tvNumAppointments.setText(list.size()+" appointments");
                            adapter.updateList(list);

                            adapter.setOnItemClickListener(new ShopAppointmentListAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(int position)
                                {
                                    ShopAppointment appointment=adapter.getDataList().get(position);
                                    startActivity(new Intent(getContext(), AppointmentDetails.class).putExtra("appointment",appointment));
                                    Animatoo.INSTANCE.animateSlideLeft(getContext());
                                }

                                @Override
                                public void onDelete(int position) {

                                }

                                @Override
                                public void onReshcedule(int position) {

                                }

                                @Override
                                public void mapClick(int positon) {

                                }
                            });

                        }
                    }
                });
            }

            @Override
            public void onDisabledDateSelected(int year, int month, int day, int dayOfWeek, boolean isDisabled) {
                // Do Something
            }
        });
        return view;
    }

}