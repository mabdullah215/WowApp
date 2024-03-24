package com.mobileapp.wowapp.driver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.gson.Gson;
import com.mobileapp.wowapp.Helper;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.customer.model.DayModel;
import com.mobileapp.wowapp.customer.utils.Converter;
import com.mobileapp.wowapp.customer.utils.GregorianCalView;
import com.mobileapp.wowapp.driver.adapter.SlotListAdapter;
import com.mobileapp.wowapp.driver.model.Slot;
import com.mobileapp.wowapp.interations.IResultData;
import com.mobileapp.wowapp.model.Appointment;
import com.mobileapp.wowapp.model.Compaign;
import com.mobileapp.wowapp.network.APIList;
import com.mobileapp.wowapp.network.APIResult;
import com.mobileapp.wowapp.network.NetworkManager;
import com.mobileapp.wowapp.serviceprovider.model.ServiceProvider;
import com.mobileapp.wowapp.utils.SlotManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CompaignBooking extends AppCompatActivity
{
    ConstraintLayout progressLayout;
    SlotListAdapter slotListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compaign_booking);
        TextView tvTitle=findViewById(R.id.tv_title);
        progressLayout=findViewById(R.id.progress_layout);
        tvTitle.setText("Campaign Booking");
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        updateCalendarView(month,year);
        ImageView imgBack=findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    public void updateCalendarView(int month,int year)
    {
        TextView tvLabelDay=findViewById(R.id.label_day);
        TextView tvNoSlots=findViewById(R.id.tv_slots);
        GregorianCalView calView=findViewById(R.id.cal_view);
        Button confirmButton=findViewById(R.id.button_done);
        tvLabelDay.setText("Notice");
        slotListAdapter=new SlotListAdapter(getBaseContext(),new ArrayList<>());
        ServiceProvider item=(ServiceProvider) getIntent().getSerializableExtra("item");
        Compaign campaign=(Compaign)getIntent().getSerializableExtra("campaign");
        SimpleDateFormat format = new SimpleDateFormat("EEEE, dd MMMM yyyy");
        SimpleDateFormat shortformat = new SimpleDateFormat("yyyy-MM-dd");
        int requestType=getIntent().getIntExtra("request_type",1);

        if(requestType==0)
        {
            Long minDate=Converter.getMinMaxDate(campaign.getEndTime(),1);
            Long maxDate=Converter.getMinMaxDate(campaign.getEndTime(),10);
            calView.setMinDate(minDate);
            calView.setMaxDate(maxDate);
            Calendar calendar=Calendar.getInstance();
            calendar.setTimeInMillis(minDate);
            calView.updateView(calendar.get(Calendar.MONTH),calendar.get(Calendar.YEAR));
        }
        else
        {
            Long minDate=Converter.getMinMaxDate(campaign.getStartTime(),-10);
            Long maxDate=Converter.getMinMaxDate(campaign.getStartTime(),0);
            calView.setMinDate(minDate);
            calView.setMaxDate(maxDate);
            Calendar calendar=Calendar.getInstance();
            calendar.setTimeInMillis(minDate);
            calView.updateView(calendar.get(Calendar.MONTH),calendar.get(Calendar.YEAR));
        }
        RecyclerView recyclerView=findViewById(R.id.slots_data);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        tvNoSlots.setText("Please select a date to check number of available slots");
        NetworkManager manager=NetworkManager.getInstance(this);
        calView.setEventHandler(new GregorianCalView.EventHandler() {
            @Override
            public void onDayPress(DayModel model)
            {
                Calendar calendar=Calendar.getInstance();
                calendar.set(Calendar.DATE,model.getDate());
                calendar.set(Calendar.MONTH,model.getMonth());
                calendar.set(Calendar.YEAR,model.getYear());
                String date=shortformat.format(calendar.getTimeInMillis());
                tvLabelDay.setText(format.format(calendar.getTimeInMillis()));
                manager.getRequest(APIList.GET_APPOINTMENT + "?shopId=" + item.getId() + "&dateTime=" + date, new IResultData()
                {
                   @Override
                    public void notifyResult(String result)
                    {
                        APIResult apiResult=new Gson().fromJson(result,APIResult.class);
                        List<Appointment>appointments= Helper.toList(apiResult.getData(),Appointment.class);
                        SlotManager slotManager=new SlotManager();
                        slotManager.getslots(item,appointments,date,new SlotManager.SlotUpdateListener() {
                            @Override
                            public void onSlotsUpdated(int availableSlots, List<Slot> slots)
                            {
                                slotListAdapter=new SlotListAdapter(getBaseContext(),slots);
                                recyclerView.setAdapter(slotListAdapter);
                                slotListAdapter.setOnItemClickListener(new SlotListAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(int position)
                                    {
                                        slotListAdapter.setSelected(position);
                                    }
                                });
                            }
                        });
                    }
                });
            }

            @Override
            public void onPrevClick()
            {
                slotListAdapter.setDocuments(new ArrayList<>());
            }

            @Override
            public void onNextClick()
            {
                slotListAdapter.setDocuments(new ArrayList<>());
            }
        });



        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(slotListAdapter.getSelected()!=-1)
                {
                    int requestType=getIntent().getIntExtra("request_type",1);
                    Slot slot=slotListAdapter.getDocuments().get(slotListAdapter.getSelected());
                    HashMap<String,Object> map=new HashMap<>();
                    map.put("campaignId",campaign.getId());
                    map.put("shopId",item.getId());
                    map.put("dateTime",slot.getAppointmentDate());
                    map.put("stickerRequest",requestType);

                    int appointmentid=getIntent().getIntExtra("appointmentId",-1);

                    if(appointmentid!=-1)
                    {
                        map.put("appointmentId",appointmentid);
                    }


                    startActivity(new Intent(getBaseContext(),BookingTerms.class)
                            .putExtra("map",map)
                            .putExtra("location",item.getName()+", "+item.getCity())
                            .putExtra("date",slot.getAppointmentDate()));
                    Animatoo.INSTANCE.animateSlideLeft(CompaignBooking.this);
                }
            }
        });


    }

    public List<Slot> getAvailableSlots(ServiceProvider serviceProvider,List<Appointment>appointments,Calendar calendar,TextView tvSlots)
    {
        Set<String> predefinedIntervals = new HashSet<>();
        SimpleDateFormat shortformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(!appointments.isEmpty())
        {
            for(int i=0;i<appointments.size();i++)
            {
                predefinedIntervals.add(appointments.get(i).getAppointment_time());
            }
        }

        ArrayList<Slot>slots=new ArrayList<>();
        int totalMinutes=getTimeDifferenceinMinutes(serviceProvider.getBusinessEndTime(),serviceProvider.getBusinessStartTime());
        int counter=totalMinutes/serviceProvider.getDuration();
        Date date=getDateFromTimeString(serviceProvider.getBusinessStartTime());
        calendar.set(Calendar.HOUR_OF_DAY,date.getHours());
        calendar.set(Calendar.MINUTE,date.getMinutes());
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        String startBreak=getBreakIntervalString(serviceProvider.getBreakStartTime());
        String endBreak=getBreakIntervalString(serviceProvider.getBreakEndTime());
        int availableslots=0;
        for(int i=0;i<counter;i++)
        {
            String startTime=getTimeStringFromMills(calendar.getTimeInMillis());
            calendar.add(Calendar.MINUTE,serviceProvider.getDuration());
            String endTime=getTimeStringFromMills(calendar.getTimeInMillis());
            Slot slot=new Slot(startTime,endTime,false);
            slot.setAppointmentDate(shortformat.format(calendar.getTime()));

            if(calendar.getTimeInMillis()<new Date().getTime()||predefinedIntervals.contains(slot.getAppointmentDate())
                    ||startTime.equalsIgnoreCase(startBreak)
                    ||endTime.equalsIgnoreCase(endBreak))
            {
                slot.setBooked(true);
            }
            else
            {
                availableslots++;
                slot.setBooked(false);
            }

            slot.setStartTime(startTime);
            slot.setEndTime(endTime);
            slots.add(slot);
            tvSlots.setText(availableslots+" Slots Available");
        }

        return slots;
    }

    public String getBreakIntervalString(String str)
    {
        Date timeDate=getDateFromTimeString(str);
        SimpleDateFormat shortformat = new SimpleDateFormat("hh:mm a");
        return shortformat.format(timeDate);
    }

    public String  getTimeStringFromMills(Long mills)
    {
        Date date=new Date();
        date.setTime(mills);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
        return simpleDateFormat.format(date);
    }


    public Date getDateFromTimeString(String s)
    {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        try
        {
            return format.parse(s);
        }
        catch (ParseException e)
        {
            throw new RuntimeException(e);
        }
    }

    public int getTimeDifferenceinMinutes(String end, String start)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

        int minutes=0;
        try
        {
            Date endTime = simpleDateFormat.parse(end);
            Date startTime = simpleDateFormat.parse(start);
            long millis = endTime.getTime() - startTime.getTime();
            int hours = (int) (millis / (1000 * 60 * 60));
            int mins = (int) ((millis / (1000 * 60)) % 60);
            minutes=(hours*60)+mins;
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return minutes;
    }


}