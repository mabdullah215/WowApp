package com.mobileapp.wowapp.serviceprovider.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.gson.Gson;
import com.mobileapp.wowapp.Helper;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.customer.utils.Converter;
import com.mobileapp.wowapp.interations.IResultData;
import com.mobileapp.wowapp.network.APIList;
import com.mobileapp.wowapp.network.APIResult;
import com.mobileapp.wowapp.network.APIResultSingle;
import com.mobileapp.wowapp.network.NetworkManager;
import com.mobileapp.wowapp.serviceprovider.AppointmentDetails;
import com.mobileapp.wowapp.serviceprovider.adapters.ShopAppointmentListAdapter;
import com.mobileapp.wowapp.serviceprovider.model.DateModel;
import com.mobileapp.wowapp.serviceprovider.model.ServiceProvider;
import com.mobileapp.wowapp.serviceprovider.model.ShopAppointment;
import com.mobileapp.wowapp.utils.HorizontalCalendar;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class ServiceHomeFragment extends Fragment
{
    TextView tvNumAppointments;
    ConstraintLayout progressLayout;
    ShopAppointmentListAdapter adapter;
    HorizontalCalendar horizontalCalendar;
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
        adapter=new ShopAppointmentListAdapter(getContext(),appointments);
        recyclerView.setAdapter(adapter);
        progressLayout=view.findViewById(R.id.progress_layout);
        tvNumAppointments=view.findViewById(R.id.tv_num_appoitments);
        SimpleDateFormat format = new SimpleDateFormat("EEEE, dd MMMM yyyy");
        Calendar calendar=Calendar.getInstance();
        tvDate.setText(format.format(calendar.getTime()));
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
                    Picasso.get().load(serviceProvider.getProfilePic()).memoryPolicy(MemoryPolicy.NO_CACHE)
                        .networkPolicy(NetworkPolicy.NO_CACHE).into(imgProfile);
                }
            }
        });

        ActivityResultLauncher<Intent> launchSomeActivity = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>()
                {
                    @Override
                    public void onActivityResult(ActivityResult result)
                    {
                        if (result.getResultCode() == Activity.RESULT_OK)
                        {
                            String date=Converter.getShortDate(calendar.getTimeInMillis());
                            reloadList(date);
                        }
                    }
                });

        adapter.setOnItemClickListener(new ShopAppointmentListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position)
            {
                ShopAppointment appointment=adapter.getDataList().get(position);
                launchSomeActivity.launch(new Intent(getContext(), AppointmentDetails.class).putExtra("appointment",appointment));
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

        horizontalCalendar=view.findViewById(R.id.horizontal_calendar);
        horizontalCalendar.setListener(new HorizontalCalendar.DateSetListener() {
            @Override
            public void onDateSet(DateModel model)
            {
                calendar.setTimeInMillis(model.getTimestamp());
                tvDate.setText(format.format(calendar.getTime()));
                reloadList(model.getDate());
            }
        });

        String date=Converter.getShortDate(calendar.getTimeInMillis());
        reloadList(date);
        getAllAppointments();
        return view;
    }

    public void getAllAppointments()
    {
        NetworkManager manager=NetworkManager.getInstance(getContext());
        manager.getRequest(APIList.GET_APPOINTMENTS_SHOP, new IResultData() {
            @Override
            public void notifyResult(String result)
            {
                APIResult apiResult=new Gson().fromJson(result,APIResult.class);
                if(apiResult.getStatusCode().equalsIgnoreCase("200"))
                {
                    List<ShopAppointment>list= Helper.toList(apiResult.getData(),ShopAppointment.class);
                    HashMap<String, DateModel> appointmentCountMap = new HashMap<>();
                    for(ShopAppointment appointment:list)
                    {
                        if (appointmentCountMap.containsKey(appointment.getDate()))
                        {
                            DateModel model=appointmentCountMap.get(appointment.getDate());
                            model.addEvent();
                            appointmentCountMap.put(appointment.getDate(),model);
                        }
                        else
                        {
                            DateModel model=new DateModel(appointment.getDate());
                            model.addEvent();
                            appointmentCountMap.put(appointment.getDate(),model);
                        }
                    }

                    horizontalCalendar.setAppointmentList(appointmentCountMap);
                }
            }
        });
    }

    public void reloadList(String date)
    {
        NetworkManager manager=NetworkManager.getInstance(getContext());
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
                }
            }
        });
    }

}