package com.mobileapp.wowapp.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.serviceprovider.adapters.CalendarListAdapter;
import com.mobileapp.wowapp.serviceprovider.model.DateModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class HorizontalCalendar extends RelativeLayout
{
    Context mContext;
    public CalendarListAdapter adapter;
    DateSetListener listener;

    public void setListener(DateSetListener listener) {
        this.listener = listener;
    }

    public CalendarListAdapter getAdapter() {
        return adapter;
    }

    public interface DateSetListener
    {
      void onDateSet(DateModel model);
    }
    public HorizontalCalendar(Context context)
    {
        super(context);
        mContext=context;
        init();
    }

    public void setAppointmentList(HashMap<String,DateModel>updateMap)
    {
        adapter.updateEventList(updateMap);
    }

    public HorizontalCalendar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        init();
    }

    public HorizontalCalendar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
        init();
    }

    public HorizontalCalendar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext=context;
        init();
    }

    public void init()
    {
        View view = inflate(getContext(), R.layout.view_horizontal_calendar, this);
        RecyclerView recyclerView=view.findViewById(R.id.calendar_view);
        String [] monthList={"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
        List<DateModel>dates=new ArrayList<>();
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        for(int i=0;i<365;i++)
        {
            String date=format.format(calendar.getTimeInMillis());
            DateModel model=new DateModel(date);
            model.setDay(""+calendar.get(Calendar.DAY_OF_MONTH));
            model.setMonth(monthList[calendar.get(Calendar.MONTH)]);
            model.setTimestamp(calendar.getTimeInMillis());
            dates.add(model);
            calendar.add(Calendar.DAY_OF_MONTH,1);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext,RecyclerView.HORIZONTAL,false));
        adapter=new CalendarListAdapter(mContext,dates);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new CalendarListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position)
            {
                adapter.setSelected(position);
                listener.onDateSet(dates.get(position));
            }
        });
    }
}
