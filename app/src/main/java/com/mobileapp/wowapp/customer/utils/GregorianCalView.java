package com.mobileapp.wowapp.customer.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.customer.adapter.CalendarViewAdapter;
import com.mobileapp.wowapp.customer.model.DayModel;

import java.util.ArrayList;
import java.util.Calendar;


public class GregorianCalView extends LinearLayout
{
	ArrayList<DayModel> cells = new ArrayList<>();
	CalendarViewAdapter adapter;
	RecyclerView mCalendarView;

	Long minDate;

	ImageView imgNext;
	ImageView imgPrev;

	Long maxDate;
	Context mContext;
	TextView labelMonth;
	EventHandler handler;
	public interface EventHandler
	{
		void onDayPress(DayModel model);
		void onPrevClick();
		void onNextClick();
	}

	public void setMinDate(Long minDate) {
		this.minDate = minDate;
	}

	public void setMaxDate(Long maxDate) {
		this.maxDate = maxDate;
	}

	public void setEventHandler(EventHandler handler)
	{
		this.handler = handler;
	}

	public GregorianCalView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		mContext=context;
		initControl();
	}

	private void initControl()
	{
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.control_calendar,this);
		imgNext=findViewById(R.id.img_next);
		labelMonth=findViewById(R.id.label_month);
		imgPrev=findViewById(R.id.img_prev);
		mCalendarView = findViewById(R.id.calendar_grid);
		mCalendarView.setHasFixedSize(true);
	}

	public void updateView(int month,int year)
	{
		final Calendar calendar=Calendar.getInstance();
		calendar.set(Calendar.YEAR,year);
		calendar.set(Calendar.MONTH,month);
		calendar.set(Calendar.DATE,1);
		int day= calendar.get(Calendar.DAY_OF_WEEK)-1;
		int dayCount=1;
		cells.clear();

		while (dayCount<=calendar.getActualMaximum(Calendar.DATE))
		{

			DayModel model= new DayModel();
			if(cells.size()<day)
			{
				model.setDate(-1);
				cells.add(model);
			}

			else
			{
				Calendar mCal=Calendar.getInstance();
				mCal.set(Calendar.DATE,dayCount);
				mCal.set(Calendar.MONTH,calendar.get(Calendar.MONTH));
				mCal.set(Calendar.YEAR,calendar.get(Calendar.YEAR));
				mCal.set(Calendar.HOUR_OF_DAY,0);
				mCal.set(Calendar.MINUTE,0);
				mCal.set(Calendar.SECOND,0);
				mCal.set(Calendar.MILLISECOND,0);


				if(minDate!=null&&mCal.getTimeInMillis()<minDate)
				{
					model.setDisabled(true);
				}

				else if(maxDate!=null&&mCal.getTimeInMillis()>maxDate)
				{
					model.setDisabled(true);
				}

				else
				{
					model.setDisabled(false);
				}

				model.setDate(dayCount);
				model.setMonth(calendar.get(Calendar.MONTH));
				model.setYear(calendar.get(Calendar.YEAR));
				cells.add(model);
				dayCount++;
			}
		}

		String monthName=(String)android.text.format.DateFormat.format("MMMM",calendar.getTimeInMillis());
		labelMonth.setText(monthName+" "+calendar.get(Calendar.YEAR));
		adapter=new CalendarViewAdapter(mContext,cells);
		adapter.selectedPosition(-1);
		GridLayoutManager gidlayout= new GridLayoutManager(mContext,7);
		mCalendarView.setLayoutManager(gidlayout);
		mCalendarView.setItemAnimator(new DefaultItemAnimator());
		mCalendarView.setAdapter(adapter);
		adapter.setClickListener(new CalendarViewAdapter.ItemClickListener() {
			@Override
			public void onItemClick(View view, int position)
			{
				DayModel model=cells.get(position);
				if(handler!=null&&!model.isDisabled())
				{
					if(position!=adapter.getSelected())
					{
						adapter.selectedPosition(position);
						handler.onDayPress(model);
					}
				}
			}
		});


		imgPrev.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view)
			{
				Calendar temp=Calendar.getInstance();
				temp.setTimeInMillis(calendar.getTimeInMillis());
				temp.add(Calendar.MONTH,-1);
				int lastDayOfMonth = temp.getActualMaximum(Calendar.DAY_OF_MONTH);
				temp.set(Calendar.DAY_OF_MONTH, lastDayOfMonth);
				if(temp.getTimeInMillis()>minDate)
				{
					handler.onPrevClick();
					calendar.add(Calendar.MONTH,-1);
					int month=calendar.get(Calendar.MONTH);
					int year=calendar.get(Calendar.YEAR);
					updateView(month,year);
				}
			}
		});

		imgNext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view)
			{
				Calendar temp=Calendar.getInstance();
				temp.setTimeInMillis(calendar.getTimeInMillis());
				temp.add(Calendar.MONTH,1);
				int firstDay = temp.getActualMinimum(Calendar.DAY_OF_MONTH);
				temp.set(Calendar.DAY_OF_MONTH,firstDay);
				if(temp.getTimeInMillis()<maxDate)
				{
					handler.onNextClick();
					calendar.add(Calendar.MONTH,1);
					int month=calendar.get(Calendar.MONTH);
					int year=calendar.get(Calendar.YEAR);
					updateView(month,year);
				}
			}
		});

	}

}
