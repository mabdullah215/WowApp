package com.mobileapp.wowapp.driver.fragment;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.gson.Gson;
import com.mobileapp.wowapp.Helper;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.driver.CompaignBooking;
import com.mobileapp.wowapp.driver.ServiceProvidrList;
import com.mobileapp.wowapp.driver.adapter.AppointmentListAdapter;
import com.mobileapp.wowapp.driver.adapter.CompaignListAdapter;
import com.mobileapp.wowapp.model.Appointment;
import com.mobileapp.wowapp.driver.model.Driver;
import com.mobileapp.wowapp.interations.IResult;
import com.mobileapp.wowapp.interations.IResultData;
import com.mobileapp.wowapp.model.Compaign;
import com.mobileapp.wowapp.network.APIList;
import com.mobileapp.wowapp.network.APIResult;
import com.mobileapp.wowapp.network.NetworkManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class AppointmentFragment extends Fragment
{
    ConstraintLayout progressLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view=inflater.inflate(R.layout.fragment_appointments, container, false);

        progressLayout=view.findViewById(R.id.progress_layout);

        ImageView imgRefresh=view.findViewById(R.id.img_refresh);
        imgRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View clickview)
            {
                getAppointments(view);
            }
        });

        getAppointments(view);

        return view;
    }

    public void getAppointments(View view)
    {
        NetworkManager manager=NetworkManager.getInstance(getContext());
        RecyclerView recyclerView=view.findViewById(R.id.data_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Appointment>appointmentList=new ArrayList<>();
        AppointmentListAdapter adapter=new AppointmentListAdapter(getContext(),appointmentList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new AppointmentListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position)
            {

            }

            @Override
            public void onDelete(int position)
            {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Delete appointment")
                        .setMessage("Are you sure you want to delete this appointment?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                progressLayout.setVisibility(View.VISIBLE);
                                Appointment appointment=adapter.getDataList().get(position);
                                String url="appointmentId="+appointment.getId();
                                manager.deleteRequest(APIList.DELETE_APPOINTMENT + url, new IResultData() {
                                    @Override
                                    public void notifyResult(String result)
                                    {
                                        adapter.getDataList().remove(position);
                                        adapter.notifyDataSetChanged();
                                        progressLayout.setVisibility(View.GONE);
                                        APIResult apiResult=new Gson().fromJson(result,APIResult.class);
                                        Toast.makeText(getContext(),apiResult.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .show();

            }

            @Override
            public void onReshcedule(int position)
            {
                Appointment appointment=adapter.getDataList().get(position);
                Intent intent=new Intent(getContext(),CompaignBooking.class);
                intent.putExtra("item",appointment.getShop());
                intent.putExtra("campaign",appointment.getCampaign());
                intent.putExtra("request_type",appointment.getSticker_request());
                intent.putExtra("appointmentId",appointment.getId());
                startActivity(intent);
                Animatoo.INSTANCE.animateSlideLeft(getContext());
            }

            @Override
            public void mapClick(int positon) {

            }
        });

        progressLayout.setVisibility(View.VISIBLE);
        LinearLayout layoutEmpty=view.findViewById(R.id.empty_state);
        manager.getRequest(APIList.GET_APPOINTMENTS, new IResultData() {
            @Override
            public void notifyResult(String result)
            {
                APIResult apiResult=new Gson().fromJson(result,APIResult.class);
                List<Appointment>list=Helper.toList(apiResult.getData(),Appointment.class);

                progressLayout.setVisibility(View.GONE);
                if(list.isEmpty())
                {
                    layoutEmpty.setVisibility(View.VISIBLE);
                }
                else
                {
                    layoutEmpty.setVisibility(View.GONE);
                    adapter.updateList(list);
                }

            }
        });
    }

}